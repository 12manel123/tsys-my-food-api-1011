package com.myfood.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myfood.dto.Dish;
import com.myfood.dto.ListOrder;
import com.myfood.dto.Menu;
import com.myfood.dto.Order;
import com.myfood.services.DishServiceImpl;
import com.myfood.services.ListOrderServiceImpl;
import com.myfood.services.MenuServiceImpl;
import com.myfood.services.OrderServiceImpl;

/**
 * Controller class for handling list-order-related operations.
 *
 * This controller provides endpoints for basic CRUD operations on list orders.
 *
 * @RestController Indicates that this class is a Spring MVC Controller.
 * @RequestMapping("/api/v1") Base mapping for all endpoints in this controller.
 */
@RestController
@RequestMapping("api/v1")
public class ListOrderController {
	
	@Autowired
	private ListOrderServiceImpl listOrderserv;
	
	@Autowired
	private OrderServiceImpl orderserv;
	
	@Autowired
	private MenuServiceImpl menuserv;
	
	@Autowired
	private DishServiceImpl dishserv;
	
	/**
     * Retrieve all list orders.
     *
     * @return ResponseEntity containing a list of all list orders.
     */
	@GetMapping("/list-orders")
	public ResponseEntity<List<ListOrder>> getAllListOrders(){
		List<ListOrder> allLists=listOrderserv.getAllListOrders();
		for (ListOrder listOrder : allLists) {
			listOrder.getOrder().getUser().setPassword(null);
		}
		return	ResponseEntity.ok(allLists);
	}
	
	/**
     * Retrieve a specific list order by its ID.
     *
     * @param id The ID of the list order to retrieve.
     * @return ResponseEntity containing the requested list order or a 404 response if not found.
     */
	@GetMapping("/list-order/{id}")
	public ResponseEntity<ListOrder> getOneListOrder(@PathVariable(name = "id") Long id) { 
		Optional<ListOrder> entity = listOrderserv.getOneListOrder(id);
		if (entity.isPresent()) {
			return ResponseEntity.ok(entity.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
     * Create a new list order.
     *
     * @param entity The list order to be created.
     * @return ResponseEntity containing the created list order.
     */
	@PostMapping("/list-order")
	public ResponseEntity<ListOrder> saveListOrder(@RequestBody ListOrder entity) {
		return ResponseEntity.ok(listOrderserv.createListOrder(entity));
	}
	
	/**
     * Update an existing list order.
     *
     * @param id The ID of the list order to update.
     * @param entity The updated list order.
     * @return ResponseEntity containing the updated list order or a 404 response if not found.
     */
	@PutMapping("/list-order/{id}")
	public ResponseEntity<ListOrder> updateListOrder(@PathVariable(name = "id") Long id, @RequestBody ListOrder entity) {
		Optional<ListOrder> entityOld = listOrderserv.getOneListOrder(id);
		if (entityOld.isPresent()) {
			entity.setId(id);
			return ResponseEntity.ok(listOrderserv.updateListOrder(entity));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
     * Delete a list order.
     *
     * @param id The ID of the list order to delete.
     * @return ResponseEntity indicating success or a 404 response if the list order is not found.
     */
	@DeleteMapping("/list-order/{id}")
	public ResponseEntity<?> deleteListOrder(@PathVariable(name = "id") Long id) { 
        Map<String, Object> responseData = new HashMap<>();
		Optional<ListOrder> entity = listOrderserv.getOneListOrder(id);
		if (entity.isPresent()) {
			listOrderserv.deleteListOrder(id);
        	responseData.put("Message", "Eliminado perfecto");
            return ResponseEntity.accepted().body(responseData);
		} else {
        	responseData.put("Message", "No se ha podido eliminar");
            return ResponseEntity.badRequest().body(responseData);
		}
	}
	
	
	
	@PostMapping("/list-order/menu/{orderid}/{menuid}")
	public ResponseEntity<ListOrder> saveListOrderMenu(
            @PathVariable(name = "orderid") Long orderid,
            @PathVariable(name = "menuid") Long menuid) {
        Optional<Order> orderOptional = orderserv.getOneOrder(orderid);
        Optional<Menu> menuOptional = menuserv.getOneMenu(menuid);

        if (orderOptional.isPresent() && menuOptional.isPresent()) {
            Order order = orderOptional.get();
            Menu menu = menuOptional.get();

            ListOrder listOrder = new ListOrder();
            listOrder.setOrder(order);
            listOrder.setMenu(menu);

            return ResponseEntity.ok(listOrderserv.createListOrder(listOrder));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	@PostMapping("/list-order/dish/{orderid}/{dishid}")
	public ResponseEntity<ListOrder> saveListOrderDish(
            @PathVariable(name = "orderid") Long orderid,
            @PathVariable(name = "dishid") Long dishid) {
        Optional<Order> orderOptional = orderserv.getOneOrder(orderid);
        Optional<Dish> dishOptional = dishserv.getOneDish(dishid);

        if (orderOptional.isPresent() && dishOptional.isPresent()) {
            Order order = orderOptional.get();
            Dish dish = dishOptional.get();

            ListOrder listOrder = new ListOrder();
            listOrder.setOrder(order);
            listOrder.setDish(dish);

            return ResponseEntity.ok(listOrderserv.createListOrder(listOrder));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@DeleteMapping("/list-order/menu/{orderid}/{menuid}")
    public ResponseEntity<?> deleteListOrderMenu(
            @PathVariable(name = "orderid") Long orderid,
            @PathVariable(name = "menuid") Long menuid) {
        Map<String, Object> responseData = new HashMap<>();
        Optional<ListOrder> entity = listOrderserv.getListOrderByOrderAndMenu(orderid, menuid);
        if (entity.isPresent()) {
            listOrderserv.deleteListOrder(entity.get().getId());
        	responseData.put("Message", "Eliminado perfecto");
            return ResponseEntity.accepted().body(responseData);
        } else {
        	responseData.put("Message", "Menu not found");
            return ResponseEntity.badRequest().body(responseData);
        }
    }
	
	@DeleteMapping("/list-order/dish/{orderid}/{dishid}")
    public ResponseEntity<Void> deleteListOrderDish(
            @PathVariable(name = "orderid") Long orderid,
            @PathVariable(name = "dishid") Long dishid) {
        Optional<ListOrder> entity = listOrderserv.getListOrderByOrderAndDish(orderid, dishid);
        if (entity.isPresent()) {
            listOrderserv.deleteListOrder(entity.get().getId());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	

}
