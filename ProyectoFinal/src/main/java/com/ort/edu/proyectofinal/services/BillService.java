package com.ort.edu.proyectofinal.services;

import com.ort.edu.proyectofinal.dto.*;
import com.ort.edu.proyectofinal.entities.*;
import com.ort.edu.proyectofinal.exception.BillException;
import com.ort.edu.proyectofinal.exception.CartException;
import com.ort.edu.proyectofinal.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BillItemRepository billItemRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private PaymentTypeRepository paymenttypeRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserDirectionRepository userDirectionRepository;

    public String createBillNumber(Integer cartId) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");

        return String.format(
                "BILL-%s-%04d",
                now.format(formatter),
                cartId
        );
    }

    @Transactional
    public BillResponseDTO create(BillRequestDTO request) throws CartException, BillException {

        System.out.println();
        System.out.println("---------- create ---------------");
        System.out.println(request.toString());
        System.out.println("---------- create ---------------");
        System.out.println();

        Optional<Cart> optionalCart = cartRepository.findById(request.getCartId());

        if (optionalCart.isEmpty()) throw new CartException("Carrito inexistente");

        Cart cart = optionalCart.get();

        //Busco si pedidos sin procesar, si hay, me voy
        List<Cartitem> items = cartItemRepository.findByCartIdAndProcessed(request.getCartId(), 0);

        if (!items.isEmpty()) {
            throw new CartException("El carrito debe estar vacío para procesar la factura");
        }

        items.clear();
        //Me traigo todos los productos procesados para armar la factura
        items = cartItemRepository.findByCartIdAndProcessed(request.getCartId(), 1);

        if (items.isEmpty()) {
            throw new BillException("Debe haber pedidos procesados para generar la factura");
        }

        Bill bill = new Bill();

        bill.setBillNumber(createBillNumber(request.getCartId()));
        bill.setAmount(BigDecimal.ZERO);
        bill.setDate(LocalDateTime.now());

        BigDecimal amount = BigDecimal.ZERO;

        for (Cartitem cartItem : items) {
            amount = amount.add(cartItem.getItemAmount());
        }

        bill.setAmount(amount);

        // Llamada asincrona
        buildBill(request, bill, cart, items);

        BillResponseDTO bResponseDTO = new BillResponseDTO(bill);

        return bResponseDTO;
    }

    @Async
    @Transactional
    public void buildBill(BillRequestDTO request, Bill bill, Cart cart, List<Cartitem> items) {

        // Asignamos un valor por defecto, para mostrar previo a efectuar el pago
        Optional<Paymenttype> optionalPaymenttype = paymenttypeRepository.findById(99);

        optionalPaymenttype.ifPresent(bill::setPaymentType);

        billRepository.save(bill);

        BigDecimal amount = BigDecimal.ZERO;

        List<Billitem> bItems = new ArrayList<>();

        for (Cartitem cartItem : items) {
            Billitem bItem = new Billitem();
            BillitemId bItemId = new BillitemId();

            bItemId.setBillId(bill.getId());
            bItemId.setItemId(cartItem.getId().getItemId());
            bItemId.setCartId(cart.getId());

            bItem.setId(bItemId);
            bItem.setBill(bill);
            bItem.setCartItem(cartItem);
            bItem.setQuantity(cartItem.getQuantity());
            bItem.setExtraData(cartItem.toJson());

            bItems.add(bItem);

            amount = amount.add(cartItem.getItemAmount());
        }

        billItemRepository.saveAll(bItems);

        bill.setAmount(amount);

        billRepository.save(bill);
    }

    @Transactional
    public BillResponseDTO process(BillProcessDTO request) throws Exception {

        System.out.println();
        System.out.println("---------- process ---------------");
        System.out.println(request.toString());

        Optional<Bill> optionalBill = billRepository.findById(request.getBillId());

        if (optionalBill.isEmpty()) throw new Exception("No pudimos procesar el pago. Intente nuevamente");

        Bill bill = optionalBill.get();

        Optional<Paymenttype> optionalPaymenttype = paymenttypeRepository.findById(request.getPaymentTypeId());

        optionalPaymenttype.ifPresent(bill::setPaymentType);

        Optional<UserDirection> userDirection = userDirectionRepository.findById(request.getDirectionId());

        userDirection.ifPresent(direction -> request.setDirection(new UserDirectionDTO(direction)));

        bill.setExtraData(request.toJson());

        billRepository.save(bill);

        // Llamada asincrona
        cartService.closeCart(request.getCartId());

        BillResponseDTO bResponseDTO = new BillResponseDTO(bill);

        System.out.println("---------- process ---------------");
        System.out.println();

        return bResponseDTO;
    }
}
