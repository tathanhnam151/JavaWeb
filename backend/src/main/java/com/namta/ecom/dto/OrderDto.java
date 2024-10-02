package com.namta.ecom.dto;


import com.namta.ecom.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDto {
    private Long id;

    private String orderDescription;

    private Date date;

    private Long amount;

    private String address;

    private OrderStatus orderStatus;

    private String userName;

    private List<CartItemDto> cartItems;
}
