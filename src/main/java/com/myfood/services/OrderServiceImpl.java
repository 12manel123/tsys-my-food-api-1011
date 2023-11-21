package com.myfood.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
}
