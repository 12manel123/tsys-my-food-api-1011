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
    
    Order markOrderAsMaked(Order entity);
    
    Order updateOrderSlot(Order entity);
    
    List<Order> getAllOrdersForCook();

    List<Order> getAllOrdersForUserId(Long id);

}
