package com.myfood.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.myfood.dto.ListOrder;

public interface IListOrderService {
	
	List<ListOrder> getAllListOrders();

    Optional<ListOrder> getOneListOrder(Long id);

    ListOrder createListOrder(ListOrder entity);

    ListOrder updateListOrder(ListOrder entity);

    void deleteListOrder(Long id);
    
    Optional<ListOrder> getListOrderByOrderAndMenu(Long orderId, Long menuId);
    
    Optional<ListOrder> getListOrderByOrderAndDish(Long orderId, Long dishId);
    
    Page<ListOrder> getAllListOrders(Pageable pageable);
}
