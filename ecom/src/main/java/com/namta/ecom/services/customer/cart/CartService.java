package com.namta.ecom.services.customer.cart;

import com.namta.ecom.dto.AddProductInCartDto;
import com.namta.ecom.dto.OrderDto;
import com.namta.ecom.dto.PlaceOrderDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {
    ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);

    OrderDto getCartByUserId(Long userId);

    OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto);

    OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto);

    OrderDto placeOrder(PlaceOrderDto placeOrderDto);

    List<OrderDto> getMyPlacedOrders(Long userId);


}
