package com.namta.ecom.services.admin.order;

import com.namta.ecom.dto.OrderDto;

import java.util.List;

public interface AdminOrderService {
    List<OrderDto> getAllPlacedOrders();

    OrderDto changeOrderStatus(Long orderId,String status);
}
