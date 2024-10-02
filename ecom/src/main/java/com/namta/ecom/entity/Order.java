package com.namta.ecom.entity;

import com.namta.ecom.dto.OrderDto;
import com.namta.ecom.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderDescription;

    private Date date;

    private Long amount;

    private String address;

    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<CartItem> cartItems;

    public OrderDto getOrderDto() {
        OrderDto orderDto = new OrderDto();

        orderDto.setId(id);
        orderDto.setOrderDescription(orderDescription);
        orderDto.setAddress(address);
        orderDto.setAmount(amount);
        orderDto.setDate(date);
        orderDto.setOrderStatus(orderStatus);
        orderDto.setUserName(user.getName());

        return orderDto;
    }
}
