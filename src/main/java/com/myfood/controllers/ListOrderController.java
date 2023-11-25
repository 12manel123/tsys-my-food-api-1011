package com.myfood.controllers;

import java.util.List;
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

import com.myfood.dto.ListOrder;
import com.myfood.services.IListOrderService;
import com.myfood.services.ListOrderServiceImpl;

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
	
	/**
     * Retrieve all list orders.
     *
     * @return ResponseEntity containing a list of all list orders.
     */
	@GetMapping("/list-orders")
	public ResponseEntity<List<ListOrder>> getAllListOrders(){
	 return	ResponseEntity.ok(listOrderserv.getAllListOrders());
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
	public ResponseEntity<Void> deleteListOrder(@PathVariable(name = "id") Long id) { 
		Optional<ListOrder> entity = listOrderserv.getOneListOrder(id);
		if (entity.isPresent()) {
			listOrderserv.deleteListOrder(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	

}
