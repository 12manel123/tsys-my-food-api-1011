package com.myfood.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.myfood.dao.IOrderDAO;
import com.myfood.dto.Order;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderDAO orderDAO;
   
    @Override
    public List<Order> getAllOrders() {
        return orderDAO.findAll();
    }

    @Override
    public Optional<Order> getOneOrder(Long id) {
        return orderDAO.findById(id);
    }

    @Override
    public Order createOrder(Order entity) {
        return orderDAO.save(entity);
    }

    @Override
    public Order updateOrder(Order entity) {
        return orderDAO.save(entity);
    }

    @Override
    public void deleteOrder(Long id) {
        orderDAO.deleteById(id);
    }
    
    @Override
    public Order markOrderAsMaked(Order entity) {
        return orderDAO.save(entity);
    }
    
    @Override
    public Order updateOrderSlot(Order entity) {
        return orderDAO.save(entity);
    }
    
    @Override
    public List<Order> getAllOrdersForCook() {
        return orderDAO.findAllByMakedIsFalse();
    }

    @Override
    public List<Order> getAllOrdersForUserId(Long id) {
        return orderDAO.findAllByUserIdOrderByActualDateDesc(id);
    }

    @Override
	public Page<Order> getAllOrdersWithPagination(Pageable pageable) {
	    return orderDAO.findAll(pageable);
	}
    
    


}
