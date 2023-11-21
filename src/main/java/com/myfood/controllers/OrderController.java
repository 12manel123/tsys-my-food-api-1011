package com.myfood.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.myfood.dto.Order;
import com.myfood.services.IOrderService;

@RestController
@RequestMapping("api/v1")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOneOrder(@PathVariable(name = "id") Long id) {
        Optional<Order> entity = orderService.getOneOrder(id);
        if (entity.isPresent()) {
            return ResponseEntity.ok(entity.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/order")
    public ResponseEntity<Order> saveOrder(@RequestBody Order entity) {
        return ResponseEntity.ok(orderService.createOrder(entity));
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable(name = "id") Long id, @RequestBody Order entity) {
        Optional<Order> entityOld = orderService.getOneOrder(id);
        if (entityOld.isPresent()) {
            entity.setId(id);
            return ResponseEntity.ok(orderService.updateOrder(entity));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable(name = "id") Long id) {
        Optional<Order> entity = orderService.getOneOrder(id);
        if (entity.isPresent()) {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
