package com.ort.edu.proyectofinal.services;

import com.ort.edu.proyectofinal.dto.SessionCartDTO;
import com.ort.edu.proyectofinal.dto.SessionCartItemDTO;
import com.ort.edu.proyectofinal.dto.OrderDTO;
import com.ort.edu.proyectofinal.entities.Cart;
import com.ort.edu.proyectofinal.entities.Cartitem;
import com.ort.edu.proyectofinal.entities.*;
import com.ort.edu.proyectofinal.exception.CartException;
import com.ort.edu.proyectofinal.exception.OrderException;
import com.ort.edu.proyectofinal.repositories.*;
import jakarta.transaction.Transactional;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private CartStateRepository cartStateRepository;

    @Autowired
    private TablesRepository tablesRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    private Cart createNewCart(Principal principal) {

        String userName = principal.getName();

        User user = userRepository.findByUsername(userName);

        Cartstate cartState = cartStateRepository.getReferenceById(1);

        //TODO: Ajustar para pedir por fecha
        Tables table = tablesRepository.getReferenceById(1);

        //Traigo mesas disponibles

        Cart cart = new Cart();
        cart.setUserName(userName);
        cart.setDate(LocalDateTime.now());
        cart.setLastUpdate(LocalDateTime.now());
        cart.setCartState(cartState);
        cart.setTable(table);

        BigDecimal amount = BigDecimal.ZERO;

        //TODO: Recalcular
        cart.setAmount(amount);
        cart.setDelayTime(120);

        cart = cartRepository.save(cart);

        return cart;
    }

    private Cart getOrCreateCartEntity(Principal principal) {

        Cart cart = getActiveUserCart(principal.getName());

        if (cart == null) cart = createNewCart(principal);

        return cart;
    }

    private Cart getActiveUserCart(String userName) {
        return cartRepository.findTopByUserNameAndCartState_IdOrderByDateDesc(userName, 1);
    }

    private SessionCartDTO buildSessionCartDTO(Cart cart) {

        List<Cartitem> items = cartItemRepository.findByCartIdAndProcessed(cart.getId(), 0);

        List<SessionCartItemDTO> dtoItems = items.stream()
                .map(SessionCartItemDTO::new)
                .collect(Collectors.toList());

        //TODO: Ver el tema de los precios de las variantes
        BigDecimal total = items.stream()
                .map(ci -> calculateItemAmount(ci.getMenuItem(), ci.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new SessionCartDTO(cart.getId(), dtoItems, total);
    }

    private BigDecimal calculateItemAmount(Menuitem menuitem, int quantity) {
        return menuitem.getBasePrice().multiply(BigDecimal.valueOf(quantity));
    }

    @Transactional
    public SessionCartDTO getOrCreateCart(Principal principal) {
        Cart cart = getOrCreateCartEntity(principal);
        return buildSessionCartDTO(cart);
    }

    @Transactional
    public SessionCartDTO addItemToCart(Principal principal, int menuItemId, int quantity) throws CartException {

        if (quantity <= 0) {
            throw new CartException("Cantidad insuficiente para el item " + menuItemId);
        }

        String userName = principal.getName();

        Cart cart = cartRepository.findTopByUserNameAndCartState_IdOrderByDateDesc(userName, 1);

        if (cart == null) cart = createNewCart(principal);

        Menuitem menuitem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new IllegalArgumentException("Menuitem no encontrado"));

        List<Cartitem> items = cartItemRepository.findByCartIdAndProcessed(cart.getId(), 0);

        Cartitem existing = items.stream()
                .filter(ci -> ci.getMenuItem().getId().equals(menuItemId))
                .findFirst()
                .orElse(null);

        if (existing == null) {
            CartitemId id = new CartitemId();
            id.setCartId(cart.getId());
            id.setItemId(items.isEmpty()
                    ? 1
                    : items.get(items.size() - 1).getId().getItemId() + 1);

            Cartitem newItem = new Cartitem();
            newItem.setId(id);
            newItem.setCart(cart);
            newItem.setMenuItem(menuitem);
            newItem.setQuantity(quantity);
            newItem.setProcessed(0);
            newItem.setItemAmount(calculateItemAmount(menuitem, quantity));
            newItem.setDelayTime(45);

            cartItemRepository.save(newItem);
        }
        else {
            //TODO: Validar si tiene variantes, xq pueden ser el mismo menuId pero con variantes
            existing.setQuantity(existing.getQuantity() + quantity);
            existing.setItemAmount(calculateItemAmount(menuitem, existing.getQuantity()));
            cartItemRepository.save(existing);
        }

        cart.setLastUpdate(LocalDateTime.now());
        cartRepository.save(cart);

        return buildSessionCartDTO(cart);
    }

    @Async
    @Transactional
    public void closeCart(Integer cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);

        if (cart.isPresent())
            closeCart(cart.get());
    }

    @Async
    @Transactional
    public void closeCart(Cart cart) {
        Cartstate confirmedState = cartStateRepository.findByName("Cerrado");

        cart.setCartState(confirmedState);

        cartRepository.save(cart);
    }

    // ============================
    // Confirmar carrito → crear Order
    // ============================
    public OrderDTO confirmCart(Principal principal) throws CartException {

        Cart cart = cartRepository.findTopByUserNameAndCartState_IdOrderByDateDesc(principal.getName(), 1);

        List<Cartitem> items = cartItemRepository.findByCartIdAndProcessed(cart.getId(), 0);

        if (items.isEmpty()) {
            throw new CartException("El carrito está vacío, no se puede confirmar");
        }

        // Recalcular total
        BigDecimal total = items.stream()
                .map(Cartitem::getItemAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setAmount(total);
        cart.setLastUpdate(LocalDateTime.now());

        cartRepository.saveAndFlush(cart);

        // Crear orden
        Order order = null;

        try {
            order = orderService.createOrder(cart, items);

            return new OrderDTO(order);
        }
        catch (OrderException e) {
            throw new RuntimeException(e);
        }
    }
}
