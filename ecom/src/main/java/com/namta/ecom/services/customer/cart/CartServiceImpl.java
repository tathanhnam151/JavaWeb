package com.namta.ecom.services.customer.cart;

import com.namta.ecom.dto.AddProductInCartDto;
import com.namta.ecom.dto.CartItemDto;
import com.namta.ecom.dto.OrderDto;
import com.namta.ecom.dto.PlaceOrderDto;
import com.namta.ecom.entity.CartItem;
import com.namta.ecom.entity.Order;
import com.namta.ecom.entity.Product;
import com.namta.ecom.entity.User;
import com.namta.ecom.enums.OrderStatus;
import com.namta.ecom.repository.CartItemRepository;
import com.namta.ecom.repository.OrderRepository;
import com.namta.ecom.repository.ProductRepository;
import com.namta.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(),
                OrderStatus.Pending);
        Optional<CartItem> optionalCartItem = cartItemRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId());

        if (optionalCartItem.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
            Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());

            if (optionalProduct.isPresent() && optionalUser.isPresent()) {
                CartItem cart = new CartItem();
                cart.setProduct(optionalProduct.get());
                cart.setPrice(optionalProduct.get().getPrice());
                cart.setQuantity(1L);
                cart.setUser(optionalUser.get());
                cart.setOrder(activeOrder);

                CartItem updatedCart = cartItemRepository.save(cart);

                activeOrder.setAmount(activeOrder.getAmount() + cart.getPrice());
                activeOrder.getCartItems().add(cart);

                orderRepository.save(activeOrder);

                return ResponseEntity.status(HttpStatus.CREATED).body(cart);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Product Not Found");
            }
        }
    }

    public OrderDto getCartByUserId(Long userId) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        List<CartItemDto> cartItemDtoList = activeOrder.getCartItems().stream().map(CartItem::getCartDto)
                .collect(Collectors.toList());
        OrderDto orderDto = new OrderDto();

        orderDto.setAmount(activeOrder.getAmount());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());

        orderDto.setCartItems(cartItemDtoList);

        return orderDto;
    }

    @Override
    public OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(),OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
        Optional<CartItem> optionalCartItem = cartItemRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(),activeOrder.getId(),addProductInCartDto.getUserId()
        );

        if(optionalProduct.isPresent() && optionalCartItem.isPresent()){
            CartItem cartItem = optionalCartItem.get();
            Product product = optionalProduct.get();

            activeOrder.setAmount(activeOrder.getAmount()+product.getPrice());

            cartItem.setQuantity(cartItem.getQuantity()+1);

            return getOrderDto(activeOrder, cartItem);
        }
        return null;
    }

    @Override
    public OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(),OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
        Optional<CartItem> optionalCartItem = cartItemRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(),activeOrder.getId(),addProductInCartDto.getUserId()
        );

        if(optionalProduct.isPresent() && optionalCartItem.isPresent()){
            CartItem cartItem = optionalCartItem.get();
            Product product = optionalProduct.get();

            activeOrder.setAmount(activeOrder.getAmount()-product.getPrice());

            cartItem.setQuantity(cartItem.getQuantity()-1);

            return getOrderDto(activeOrder, cartItem);
        }
        return null;
    }

    private OrderDto getOrderDto(Order activeOrder, CartItem cartItem) {
        cartItemRepository.save(cartItem);
        orderRepository.save(activeOrder);

        return activeOrder.getOrderDto();
    }


    public OrderDto placeOrder(PlaceOrderDto placeOrderDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.Pending);
        Optional<User> optionalUser = userRepository.findById(placeOrderDto.getUserId());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (activeOrder != null) {
                // Update the existing active order
                activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
                activeOrder.setAddress(placeOrderDto.getAddress());
                activeOrder.setOrderStatus(OrderStatus.Placed);
                activeOrder.setDate(new Date());
                orderRepository.save(activeOrder);
            } else {
                // If no active order, create a new placed order
                activeOrder = new Order();
                activeOrder.setUser(user);
                activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
                activeOrder.setAddress(placeOrderDto.getAddress());
                activeOrder.setOrderStatus(OrderStatus.Pending);
                activeOrder.setDate(new Date());
                activeOrder.setAmount(0L); // Initialize amount
                orderRepository.save(activeOrder);
            }

            // Create a new pending order for the user
            Order newPendingOrder = new Order();
            newPendingOrder.setAmount(0L);
            newPendingOrder.setUser(user);
            newPendingOrder.setOrderStatus(OrderStatus.Pending);
            orderRepository.save(newPendingOrder);

            return activeOrder.getOrderDto();
        }
        return null;
    }

    public List<OrderDto> getMyPlacedOrders(Long userId) {
        return orderRepository
                .findByUserIdAndOrderStatusIn(userId,List.of(OrderStatus.Pending,OrderStatus.Shipped,OrderStatus.Delivered))
                .stream()
                .map(Order::getOrderDto)
                .collect(Collectors.toList());
    }
}
