package com.myfood.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.myfood.dto.Order;
import com.myfood.dto.OrderUserDTO;
import com.myfood.dto.Slot;
import com.myfood.services.IOrderService;
import com.myfood.services.ISlotService;
import com.myfood.services.IUserService;

/**
 * Controller class for handling order-related operations.
 *
 * This controller provides endpoints for basic CRUD operations on orders, as well as
 * specific operations for chefs and users.
 *
 * @RestController Indicates that this class is a Spring MVC Controller.
 * @RequestMapping("/api/v1") Base mapping for all endpoints in this controller.
 */
@RestController
@RequestMapping("api/v1")
public class OrderController {

    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private ISlotService slotService;
    
    @Autowired
    private IUserService userService;

    /**
     * Retrieve all orders.
     *
     * @return ResponseEntity containing a list of all orders.
     */
    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrders() {
    	  List<Order> listOrders = orderService.getAllOrders();
    	  List<OrderUserDTO> listOrdersUserDTO = new ArrayList<>();
    	  for (Order orders : listOrders) {
    		  listOrdersUserDTO.add(new OrderUserDTO(orders.getId(), orders.isMaked() , orders.getSlot()));
		}	  
        return ResponseEntity.ok(listOrdersUserDTO);
    }
    
    /**
     * Retrieve a specific order by its ID.
     *
     * @param id The ID of the order to retrieve.
     * @return ResponseEntity containing the requested order or a 404 response if not found.
     */
    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOneOrder(@PathVariable(name = "id") Long id) {
        Optional<Order> entity = orderService.getOneOrder(id);
        if (entity.isPresent()) {
            return ResponseEntity.ok(entity.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Create a new order.
     *
     * @param entity The order to be created.
     * @return ResponseEntity containing the created order.
     */
    @PostMapping("/order")
    public ResponseEntity<Order> saveOrder(@RequestBody Order entity) {
        return ResponseEntity.ok(orderService.createOrder(entity));
    }

    /**
     * Update the order.
     *
     * @param orderId The ID of the order to update.
     * @param slotId  The ID of the slot to associate with the order.
     * @return ResponseEntity containing the updated order or an error response.
     */
    @PutMapping("/order/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable(name = "id") Long id, @RequestBody Order entity) {
        Map<String, Object> responseData = new HashMap<String, Object>();
        Optional<Order> entityOld = orderService.getOneOrder(id);
        if (entityOld.isPresent()) {
            entity.setId(id);
            responseData.put("Message: Updated order", entity);
			return ResponseEntity.ok(responseData);
        } else {
        	responseData.put("Message", "The order not exists");
            return ResponseEntity.badRequest().body(responseData);
        }
    }
    
    /**
     * Delete a order
     *
     * @param id
     * @return
     */
    @DeleteMapping("/order/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable(name = "id") Long id) {
        Map<String, Object> responseData = new HashMap<String, Object>();
        Optional<Order> entity = orderService.getOneOrder(id);
        if (entity.isPresent()) {
            orderService.deleteOrder(id);
            responseData.put("Message", "Order deleted");
			return ResponseEntity.status(204).body(responseData);
        } else {
        	responseData.put("Message", "The order not exists");
            return ResponseEntity.badRequest().body(responseData);
        }
    }
    
    /**
     * Retrieve all orders not marked as done. This function is intended for chefs.
     *
     * @return ResponseEntity containing a list of orders not marked as done.
     */
    @GetMapping("/orders/cook")
    public ResponseEntity<List<Order>> getAllOrdersForCook() {
        List<Order> ordersForCook = orderService.getAllOrdersForCook();
        return ResponseEntity.ok(ordersForCook);
    }
    
    /**
     * Retrieve orders related to a user, showing only the slot and whether it is done or not.
     * Orders are displayed in descending order based on creation time.
     *
     * @param userId The ID of the user to retrieve orders for.
     * @return ResponseEntity containing a list of OrderUserDTO representing the user's orders.
     */
    @GetMapping("/orders/user/{userId}")
    public ResponseEntity<List<OrderUserDTO>> getAllOrdersForUser(@PathVariable(name = "userId") Long userId) {
    	if (!userService.getOneUser(userId).isPresent()) {
    		return ResponseEntity.notFound().build();
    	}
        List<OrderUserDTO> orderId = orderService.getAllOrdersForUserId(userId)
            .stream()
            .map(order -> new OrderUserDTO(order.getId(), order.isMaked(), order.getSlot()))
            .toList();
        return ResponseEntity.ok(orderId);
    }
    
    /**
     * Update the 'maked' variable to true with the requested command. This option is only used by the chef.
     *
     * @param id The ID of the order to mark as made.
     * @return ResponseEntity containing the updated order or a 404 response if the order is not found.
     */
    @PutMapping("/order/markAsMaked/{id}")
    public ResponseEntity<?> markOrderAsMaked(@PathVariable(name = "id") Long id) {
        Map<String, Object> responseData = new HashMap<String, Object>();
        Optional<Order> optionalOrder = orderService.getOneOrder(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setMaked(true);
            Order updatedOrder = orderService.updateOrder(order);
            return ResponseEntity.ok(updatedOrder);
        } else {
        	responseData.put("Message", "The order not exists");
            return ResponseEntity.badRequest().body(responseData);
        }
    }
 
    /**
     * Update the order when the user has finished ordering. It will update the pickup slot by checking if it is full or not.
     * It will not be executed if this function has already been executed.
     *
     * @param orderId The ID of the order to update.
     * @param slotId  The ID of the slot to associate with the order.
     * @return ResponseEntity containing the updated order or an error response.
     */
    @PutMapping("/order/finish/{orderId}/{slotId}")
    public ResponseEntity<?> updateOrderSlot(
            @PathVariable(name = "orderId") Long orderId,
            @PathVariable(name = "slotId") Long slotId) {
        //TODO: Set the total price for the dishes.
        Optional<Order> optionalOrder = orderService.getOneOrder(orderId);
        Map<String, Object> responseData = new HashMap<String, Object>();
		
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (order.getActualDate() != null) {
            	responseData.put("Message", "Order is confirmed previosly");
                return ResponseEntity.badRequest().body(responseData);
            }
            Optional<Slot> slotOptional = slotService.getOneSlot(slotId);
            if (slotOptional.isPresent()) {
                Slot slot = slotOptional.get();
                if (slot.getActual() >= slot.getLimitSlot()) {
                	responseData.put("Message", "Too many orders for this slot to create order");
                    return ResponseEntity.badRequest().body(responseData);
                }
                ZoneId madridZone = ZoneId.of("Europe/Madrid");
                order.setActualDate(LocalDateTime.now(madridZone));
                order.setSlot(slot);
                slot.setActual(slot.getActual() + 1);
                orderService.updateOrder(order);
                slotService.updateSlot(slot);
                return ResponseEntity.accepted().body(order);
            } else {
            	responseData.put("Message", "The slot not exists");
                return ResponseEntity.badRequest().body(responseData);
            }
        } else {
        	responseData.put("Message", "The order not exists");
            return ResponseEntity.badRequest().body(responseData);
        }
    }
    

}
