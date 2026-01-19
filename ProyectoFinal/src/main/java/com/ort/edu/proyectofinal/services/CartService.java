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
    private OrderCanalRepository orderCanalRepository;

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

    private final CoreManager manager = CoreManager.getInstance();

    private Cart createNewCart(Session session) {

        Cartstate cartState = cartStateRepository.getReferenceById(1);
        //TODO: Ajustar para pedir por fecha
        Tables table = tablesRepository.getReferenceById(1);

        //Traigo mesas disponibles

        Cart cart = new Cart();
        cart.setSession(session);
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

    private Cart getOrCreateCartEntity() {

        UserDTO user = (UserDTO) httpSession.getAttribute("user");

        if (user == null) {
            throw new RuntimeException("Usuario no logueado en sesión");
        }

        // Intento recuperar el carrito de la sesion
        Cart sessionCart = (Cart) httpSession.getAttribute("cart");

        if (sessionCart != null) {
            System.out.println("DEBUG: Cart found in HttpSession cache. CartId=" + sessionCart.getId());
            return sessionCart;
        }

        try {
            System.out.println("DEBUG: CartService.httpSession.getId(): " + httpSession.getId());
            System.out.println("DEBUG: httpSession.getAttribute('user'): " + httpSession.getAttribute("user"));
        } catch(Exception e) {}

        // Si no encuentro el carrito en la sesion, lo busco por db
        Optional<Cart> cartOptional = cartRepository.findBySession_SessionId(user.getSessionId());

        Session session = sessionService.resolveSession(user.getSessionId());

        if (cartOptional.isPresent()) {
            sessionCart = cartOptional.get();
        }
        else {
            sessionCart = createNewCart(session);
        }

        httpSession.setAttribute("cart", sessionCart);

        return sessionCart;
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
    public SessionCartDTO getOrCreateCart() {
        Cart cart = getOrCreateCartEntity();
        return buildSessionCartDTO(cart);
    }

    @Transactional
    public SessionCartDTO addItemToCart(int menuItemId, int quantity) throws CartException {

        if (quantity <= 0) {
             throw new CartException("Cantidad insuficiente para el item " + menuItemId);
        }

        Cart cart = null;

        // Primero busco el cart en la sesion, sino, lo busco por la db
        cart = (Cart) httpSession.getAttribute("cart");

        if (cart != null) {
            System.out.println("DEBUG: Cart found in HttpSession cache. CartId=" + cart.getId());
        }

        if (cart == null) {
            UserDTO user = (UserDTO) httpSession.getAttribute("user");

            if (user == null) {
                throw new RuntimeException("Usuario no logueado en sesión");
            }

            // Si no encuentro el carrito en la sesion, lo busco por db
            Optional<Cart> cartOptional = cartRepository.findBySession_SessionId(user.getSessionId());

            Session session = sessionService.resolveSession(user.getSessionId());

            if (cartOptional.isPresent()) {
                cart = cartOptional.get();
            }
            else {
                cart = createNewCart(session);
            }
        }

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
    public OrderDTO confirmCart() throws CartException {

        UserDTO user = (UserDTO) httpSession.getAttribute("user");

        if (user == null) {
            throw new CartException("No se recibió el identificador de sesión");
        }

        Cart cart = null;

        // Primero busco el cart en la sesion, sino, lo busco por la db
        cart = (Cart) httpSession.getAttribute("cart");

        if (cart == null) {
            cart = cartRepository.findBySession_SessionId(user.getSessionId())
                    .orElseThrow(() -> new CartException("No existe un carrito abierto para la sesión"));
        }

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
            order = orderService.createOrder(cart, items);
            return new OrderDTO(order);
        }
        catch (OrderException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    @Transactional
    public OrderDTO confirmCart(String sessionIdHeader) {
        Cart cart = getOrCreateCartEntity(sessionIdHeader);
        List<Cartitem> items = cartItemRepository.findByCartId(cart.getId());

        if (items.isEmpty()) {
            throw new IllegalStateException("El carrito está vacío");
        }

        BigDecimal total = items.stream()
                .map(ci -> ci.getMenuItem().getBasePrice()
                        .multiply(BigDecimal.valueOf(ci.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Estado inicial "Pendiente" (ajustá el id según tu tabla)
        Orderstate pending = orderStateRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Estado Pendiente no encontrado"));

        // Canal por defecto (ej: PWA)
        Ordercanal canal = orderCanalRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Canal por defecto no encontrado"));

        Order order = new Order();
        //TODO: Hay que usar la sesion del usuario
        //TODO: Number y Id vienen de la db, no los gestionamos
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setDate(Instant.now());
        order.setAmount(total);
        order.setState(pending);
        order.setCanal(canal);
        order.setLastUpdate(Instant.now());
        // Si tu Order tiene relación con User o Session, setear aquí

        order = orderRepository.save(order);

        // Crear Orderitems a partir de los Cartitems
        int line = 1;
        for (Cartitem ci : items) {
            OrderitemId id = new OrderitemId();
            id.setOrderId(order.getId());
            id.setItemId(line++);

            Orderitem oi = new Orderitem();
            oi.setId(id);
            oi.setOrder(order);
            oi.setMenuItem(ci.getMenuItem());
            oi.setQuantity(ci.getQuantity());
            //oi.setExtraData(ci.getExtraData()); // si no usás extraData, podés omitir

            orderItemRepository.save(oi);
        }

        // Limpiar / cerrar carrito
        cartItemRepository.deleteAll(items);
        cart.setLastUpdate(Instant.now());
        // Si tenés estado de cart, marcarlo como CERRADO
        cartRepository.save(cart);

        // Mapear a tu OrderDTO actual (usado por el Kanban)
        return orderMapper.toDto(order);
    }
    */
    // ============================
    // Cerrar carrito sin confirmar
    // ============================

    // TODO: revisar
    @Transactional
    public void closeCart(String sessionIdHeader) {
        Cart cart = getOrCreateCartEntity();
        cart.setLastUpdate(LocalDateTime.now());
        // Si tenés estado de cart, marcarlo CERRADO
        cartRepository.save(cart);
    }
}
