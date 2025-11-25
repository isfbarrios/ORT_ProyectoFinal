package com.ort.edu.proyectofinal.services;

import com.ort.edu.proyectofinal.dto.SessionCartDTO;
import com.ort.edu.proyectofinal.dto.SessionCartItemDTO;
import com.ort.edu.proyectofinal.dto.OrderDTO;
import com.ort.edu.proyectofinal.entities.Cart;
import com.ort.edu.proyectofinal.entities.Cartitem;
import com.ort.edu.proyectofinal.entities.CartitemId;
import com.ort.edu.proyectofinal.entities.Order;
import com.ort.edu.proyectofinal.entities.Ordercanal;
import com.ort.edu.proyectofinal.entities.Orderitem;
import com.ort.edu.proyectofinal.entities.OrderitemId;
import com.ort.edu.proyectofinal.entities.Orderstate;
import com.ort.edu.proyectofinal.entities.Session;
import com.ort.edu.proyectofinal.entities.Menuitem;
import com.ort.edu.proyectofinal.repositories.CartItemRepository;
import com.ort.edu.proyectofinal.repositories.CartRepository;
import com.ort.edu.proyectofinal.repositories.SessionRepository;
import com.ort.edu.proyectofinal.repositories.MenuItemRepository;
import com.ort.edu.proyectofinal.repositories.OrderRepository;
import com.ort.edu.proyectofinal.repositories.OrderItemRepository;
import com.ort.edu.proyectofinal.repositories.OrderStateRepository;
import com.ort.edu.proyectofinal.repositories.OrderCanalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private SessionRepository sessionRepository;

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

    //@Autowired
    //private OrderMapper orderMapper; // asumimos que ya lo tenés

    // ============================
    // Helpers internos
    // ============================

    private Session resolveSession(String sessionKey) {
        String key = (sessionKey == null || sessionKey.isBlank())
                ? UUID.randomUUID().toString()
                : sessionKey;

        Optional<Session> existing = sessionRepository.findBySessionKey(key);
        if (existing.isPresent()) {
            return existing.get();
        }

        Session s = new Session();
        s.setSessionKey(key);
        s.setCreatedDate(Instant.now());
        return sessionRepository.save(s);
    }

    private Cart createNewCart(Session session) {
        Cart cart = new Cart();
        cart.setSession(session);
        cart.setCreatedDate(Instant.now());
        cart.setLastUpdate(Instant.now());
        // Si tenés estado del cart (ABIERTO/CERRADO), setear acá
        return cartRepository.save(cart);
    }

    private Cart getOrCreateCartEntity(String sessionIdHeader) {
        Session session = resolveSession(sessionIdHeader);
        return cartRepository.findBySession(session)
                .orElseGet(() -> createNewCart(session));
    }

    private SessionCartDTO buildSessionCartDTO(Cart cart) {
        List<Cartitem> items = cartItemRepository.findByCart(cart);

        List<SessionCartItemDTO> dtoItems = items.stream()
                .map(SessionCartItemDTO::new)
                .collect(Collectors.toList());

        BigDecimal total = items.stream()
                .map(ci -> ci.getMenuItem().getBasePrice()
                        .multiply(BigDecimal.valueOf(ci.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new SessionCartDTO(dtoItems, total);
    }

    // ============================
    // API carrito de sesión
    // ============================

    @Transactional
    public SessionCartDTO getOrCreateCart(String sessionIdHeader) {
        Cart cart = getOrCreateCartEntity(sessionIdHeader);
        return buildSessionCartDTO(cart);
    }

    @Transactional
    public SessionCartDTO addItemToCart(String sessionIdHeader, int menuItemId, int quantity) {

        if (quantity <= 0) quantity = 1;

        Cart cart = getOrCreateCartEntity(sessionIdHeader);

        Menuitem menuitem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new IllegalArgumentException("Menuitem no encontrado"));

        List<Cartitem> items = cartItemRepository.findByCart(cart);

        Cartitem existing = items.stream()
                .filter(ci -> ci.getMenuItem().getMenuItemId().equals(menuItemId))
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
            // Si tenés itemAmount y delayTime en Cartitem, podés setearlos acá
            cartItemRepository.save(newItem);
        } else {
            existing.setQuantity(existing.getQuantity() + quantity);
            // Si usás itemAmount, recalculalo acá
            cartItemRepository.save(existing);
        }

        cart.setLastUpdate(Instant.now());
        cartRepository.save(cart);

        return buildSessionCartDTO(cart);
    }

    // ============================
    // Confirmar carrito → crear Order
    // ============================

    @Transactional
    public OrderDTO confirmCart(String sessionIdHeader) {
        Cart cart = getOrCreateCartEntity(sessionIdHeader);
        List<Cartitem> items = cartItemRepository.findByCart(cart);

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
            id.setOrderId(order.getOrderId());
            id.setItemId(line++);

            Orderitem oi = new Orderitem();
            oi.setId(id);
            oi.setOrder(order);
            oi.setMenuItem(ci.getMenuItem());
            oi.setQuantity(ci.getQuantity());
            oi.setExtraData(ci.getExtraData()); // si no usás extraData, podés omitir

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

    // ============================
    // Cerrar carrito sin confirmar
    // ============================

    @Transactional
    public void closeCart(String sessionIdHeader) {
        Cart cart = getOrCreateCartEntity(sessionIdHeader);
        List<Cartitem> items = cartItemRepository.findByCart(cart);

        cartItemRepository.deleteAll(items);
        cart.setLastUpdate(Instant.now());
        // Si tenés estado de cart, marcarlo CERRADO
        cartRepository.save(cart);
    }
}
