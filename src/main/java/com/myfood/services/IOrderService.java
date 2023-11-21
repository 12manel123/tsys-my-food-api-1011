package com.myfood.services;

import java.util.List;
import java.util.Optional;

import com.myfood.dto.Order;

public interface IOrderService {

    List<Order> getAllOrders();

    Optional<Order> getOneOrder(Long id);

    Order createOrder(Order entity);

    Order updateOrder(Order entity);

    void deleteOrder(Long id);
}
