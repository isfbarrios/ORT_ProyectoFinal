package com.ort.edu.proyectofinal.services;

import com.ort.edu.proyectofinal.dto.BillRequestDTO;
import com.ort.edu.proyectofinal.dto.BillResponseDTO;
import com.ort.edu.proyectofinal.dto.ResponseDTO;
import com.ort.edu.proyectofinal.entities.*;
import com.ort.edu.proyectofinal.exception.BillException;
import com.ort.edu.proyectofinal.exception.CartException;
import com.ort.edu.proyectofinal.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private CartStateRepository cartStateRepository;

    public String createBillNumber(Integer cartId) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");

        return String.format(
                "BILL-%s-%04d",
                now.format(formatter),
                cartId
        );
    }

    public BillResponseDTO create(BillRequestDTO request) throws CartException, BillException {
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

        billRepository.save(bill);

        BigDecimal amount = BigDecimal.ZERO;

        for (Cartitem cartItem : items) {
            Billitem bItem = new Billitem();

            BillitemId bItemId = new BillitemId();

            bItemId.setBillId(bill.getId());
            bItemId.setItemId(cartItem.getMenuItem().getId());
            bItemId.setCartId(cart.getId());

            bItem.setId(bItemId);

            bItem.setBill(bill);
            bItem.setCartItem(cartItem);
            bItem.setQuantity(cartItem.getQuantity());
            bItem.setExtraData(cartItem.toJson());

            billItemRepository.save(bItem);

            amount = amount.add(cartItem.getItemAmount());
        }

        bill.setAmount(amount);

        billRepository.save(bill);

        Cartstate confirmedState = cartStateRepository.findByName("Cerrado");

        cart.setCartState(confirmedState);

        cartRepository.save(cart);

        BillResponseDTO bResponseDTO = new BillResponseDTO(bill);

        System.out.println("\n\n"+bResponseDTO.toString()+"\n\n");

        return bResponseDTO;
    }
}
