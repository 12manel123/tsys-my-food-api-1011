package com.myfood.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myfood.dao.IListOrderDAO;
import com.myfood.dto.ListOrder;

@Service
public class ListOrderServiceImpl implements IListOrderService {

	@Autowired
	private IListOrderDAO dao;
	
	@Override
	public List<ListOrder> getAllListOrders() {
		return dao.findAll();
	}
	@Override
	public Optional<ListOrder> getOneListOrder(Long id) {
		return dao.findById(id);
	}
	@Override
	public ListOrder createListOrder(ListOrder entity) {
		return dao.save(entity);
	}
	@Override
	public ListOrder updateListOrder(ListOrder entity) {
		return dao.save(entity);
	}
	@Override
	public void deleteListOrder(Long id) {
		dao.deleteById(id);		
	}
	
	@Override
	public Optional<ListOrder> getListOrderByOrderAndMenu(Long orderId, Long menuId) {
        return dao.findByOrderIdAndMenuId(orderId, menuId);
    }
	
	@Override
    public Optional<ListOrder> getListOrderByOrderAndDish(Long orderId, Long dishId) {
        return dao.findByOrderIdAndDishId(orderId, dishId);
    }
}
