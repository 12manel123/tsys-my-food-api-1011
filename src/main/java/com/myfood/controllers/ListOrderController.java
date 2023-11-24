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

@RestController
@RequestMapping("api/v1")
public class ListOrderController {
	
	@Autowired
	private IListOrderService listOrderserv;
	
	@GetMapping("/list-orders")
	public ResponseEntity<List<ListOrder>> getAllListOrders(){
	 return	ResponseEntity.ok(listOrderserv.getAllListOrders());
	}
	
	@GetMapping("/list-order/{id}")
	public ResponseEntity<ListOrder> getOneListOrder(@PathVariable(name = "id") Long id) { 
		Optional<ListOrder> entity = listOrderserv.getOneListOrder(id);
		if (entity.isPresent()) {
			return ResponseEntity.ok(entity.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/list-order")
	public ResponseEntity<ListOrder> saveListOrder(@RequestBody ListOrder entity) {
		return ResponseEntity.ok(listOrderserv.createListOrder(entity));
	}
	
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
