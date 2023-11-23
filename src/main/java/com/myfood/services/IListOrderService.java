package com.myfood.services;

import java.util.List;
import java.util.Optional;

import com.myfood.dto.ListOrder;

public interface IListOrderService {
	
	List<ListOrder> getAllListOrders();

    Optional<ListOrder> getOneListOrder(Long id);

    ListOrder createListOrder(ListOrder entity);

    ListOrder updateListOrder(ListOrder entity);

    void deleteListOrder(Long id);
}
