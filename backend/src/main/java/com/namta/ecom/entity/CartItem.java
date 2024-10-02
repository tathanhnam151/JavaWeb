package com.namta.ecom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.namta.ecom.dto.CartItemDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long price;

    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    @ToString.Exclude
    private Order order;

    public CartItemDto getCartDto() {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(id);
        cartItemDto.setPrice(price);
        cartItemDto.setQuantity(quantity);
        cartItemDto.setOrderId(order.getId());
        cartItemDto.setUserId(user.getId());
        cartItemDto.setProductId(product.getId());
        cartItemDto.setProductName(product.getName());

        return cartItemDto;
    }
}
