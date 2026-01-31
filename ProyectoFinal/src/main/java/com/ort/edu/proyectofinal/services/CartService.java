package com.ort.edu.proyectofinal.services;

import com.ort.edu.proyectofinal.CoreManager;
import com.ort.edu.proyectofinal.dto.UserDTO;
import com.ort.edu.proyectofinal.dto.SessionCartDTO;
import com.ort.edu.proyectofinal.dto.SessionCartItemDTO;
import com.ort.edu.proyectofinal.dto.OrderDTO;
import com.ort.edu.proyectofinal.entities.Cart;
import com.ort.edu.proyectofinal.entities.Cartitem;
import com.ort.edu.proyectofinal.entities.Session;
import com.ort.edu.proyectofinal.entities.*;
import com.ort.edu.proyectofinal.exception.CartException;
import com.ort.edu.proyectofinal.exception.OrderException;
import com.ort.edu.proyectofinal.repositories.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderStateRepository orderStateRepository;

    @Autowired
    private CartStateRepository cartStateRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TablesRepository tablesRepository;

    @Autowired
    private HttpSession httpSession;

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
        
        // Guardar tambien en sesion
        httpSession.setAttribute("cart", cart);
        
        return cart;
    }

    private Cart getOrCreateCartEntity(Principal principal) {

        String userName = principal.getName();

        User user = userRepository.findByUsername(userName);

        Cart cart = cartRepository.findByUserName(userName);

        if (cart == null) cart = createNewCart(principal);

        return cart;
    }

    private SessionCartDTO buildSessionCartDTO(Cart cart) {

        List<Cartitem> items = cartItemRepository.findByCartId(cart.getId());

        List<SessionCartItemDTO> dtoItems = items.stream()
                .map(SessionCartItemDTO::new)
                .collect(Collectors.toList());

        //TODO: Ver el tema de los precios de las variantes
        BigDecimal total = items.stream()
                .map(ci -> ci.getMenuItem().getBasePrice()
                .multiply(BigDecimal.valueOf(ci.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new SessionCartDTO(dtoItems, total);
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

        User user = userRepository.findByUsername(userName);

        Cart cart = cartRepository.findByUserName(userName);

        Menuitem menuitem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new IllegalArgumentException("Menuitem no encontrado"));

        List<Cartitem> items = cartItemRepository.findByCartId(cart.getId());

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

            newItem.setItemAmount(new BigDecimal("250"));
            newItem.setDelayTime(45);

            cartItemRepository.save(newItem);
        }
        else {
            //TODO: Validar si tiene variantes, xq pueden ser el mismo menuId pero con variantes
            existing.setQuantity(existing.getQuantity() + quantity);
            // Si usás itemAmount, recalculalo acá
            cartItemRepository.save(existing);
        }

        cart.setLastUpdate(LocalDateTime.now());
        cartRepository.save(cart);

        //Actualizo el carrito en la sesion
        httpSession.removeAttribute("cart");
        httpSession.setAttribute("cart", cart);

        return buildSessionCartDTO(cart);
    }

    // ============================
    // Confirmar carrito → crear Order
    // ============================
    public OrderDTO confirmCart(Principal principal) throws CartException {

        String userName = principal.getName();

        User user = userRepository.findByUsername(userName);

        Cart cart = cartRepository.findByUserName(userName);

        List<Cartitem> items = cartItemRepository.findByCartId(cart.getId());

        if (items.isEmpty()) {
            throw new CartException("El carrito está vacío, no se puede confirmar");
        }

        // Recalcular total
        BigDecimal total = items.stream()
                .map(Cartitem::getItemAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setAmount(total);
        cart.setLastUpdate(LocalDateTime.now());

        // Obtener estado "Confirmado"
        Cartstate confirmedState = cartStateRepository.findByName("Cerrado");

        if (confirmedState == null) {
            throw new RuntimeException("El estado de carrito 'Confirmado' no está configurado en la base");
        }

        cart.setCartState(confirmedState);
        cartRepository.saveAndFlush(cart);

        // Crear orden
        Order order = null;
        try {
            order = orderService.createOrder(cart, items, principal);
            return new OrderDTO(order);
        }
        catch (OrderException e) {
            throw new RuntimeException(e);
        }
    }

    // ============================
    // Cerrar carrito sin confirmar
    // ============================

    // TODO: revisar
    @Transactional
    public void closeCart(Principal principal) {
        Cart cart = getOrCreateCartEntity(principal);
        cart.setLastUpdate(LocalDateTime.now());
        // Si tenés estado de cart, marcarlo CERRADO
        cartRepository.save(cart);
    }

    /*
    private UserDTO getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
            "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }

        String principalName = authentication.getName();
        User user = userRepository.findByUsername(principalName);

        if (user == null) {
            user = userRepository.findByMail(principalName);
        }

        if (user == null) {
            return null;
        }

        return new UserDTO(user);
    }
     */
}
