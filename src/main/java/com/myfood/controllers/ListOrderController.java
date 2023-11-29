package com.myfood.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myfood.dto.Dish;
import com.myfood.dto.ListOrder;
import com.myfood.dto.Menu;
import com.myfood.dto.Order;
import com.myfood.services.DishServiceImpl;
import com.myfood.services.ListOrderServiceImpl;
import com.myfood.services.MenuServiceImpl;
import com.myfood.services.OrderServiceImpl;


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
	 * Retrieves a list of all list orders with user information excluding passwords. It's for ADMIN
	 *
	 * @return ResponseEntity containing a list of {@link ListOrder} objects with user information excluding passwords.
	 * @apiNote This endpoint retrieves and returns a list of all list orders. The response includes a ResponseEntity
	 * with status 200 (OK) and a body containing the list of {@link ListOrder} objects representing different list orders.
	 * User information is included in the response, but passwords are excluded. If there are no list orders available,
	 * an empty list is returned. This endpoint is intended to provide a comprehensive list of list orders with user information
	 * for administrative purposes.
	 * @see ListOrderService#getAllListOrders()
	 * @see ListOrder
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
	 * Retrieves details of a specific list order identified by its ID. It's for ADMIN
	 *
	 * @param id The unique identifier of the list order.
	 * @return ResponseEntity containing the details of the list order as a {@link ListOrder} object with user information excluding passwords.
	 * @throws DataNotFoundException If the specified list order does not exist.
	 * @apiNote This endpoint retrieves and returns details of a specific list order identified by its unique ID.
	 * If the list order is found, its details are encapsulated in a ResponseEntity with status 200 (OK),
	 * and the body contains the {@link ListOrder} object representing the list order. User information is included
	 * in the response, but passwords are excluded. If the list order does not exist, a ResponseEntity with status 400 (BAD REQUEST)
	 * is returned, and an error message indicating that the list order does not exist is included in the response body.
	 * This endpoint is intended for administrative purposes to view details of a specific list order.
	 * @see ListOrderService#getOneListOrder(Long)
	 * @see ListOrder
	 */
	@GetMapping("/list-order/{id}")
	public ResponseEntity<?> getOneListOrder(@PathVariable(name = "id") Long id) { 
		Optional<ListOrder> entity = listOrderserv.getOneListOrder(id);
		if (entity.isPresent()) {
			ListOrder listOrder = entity.get();
	        listOrder.getOrder().getUser().setPassword(null);
			return ResponseEntity.ok(entity.get());
		} else {
            return createErrorResponse("The list order not exists", HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Creates a new list order based on the provided details. It's for ADMIN
	 *
	 * @param entity The list order details provided in the request body.
	 * @return ResponseEntity containing the details of the created list order as a {@link ListOrder} object with user information excluding passwords.
	 * @apiNote This endpoint creates a new list order based on the details provided in the request body.
	 * If the list order is successfully created, the details of the created list order are encapsulated in
	 * a ResponseEntity with status 200 (OK), and the body contains the {@link ListOrder} object representing the
	 * newly created list order. User information is included in the response, but passwords are excluded.
	 * This endpoint is intended for administrative purposes to create new list orders.
	 * @see ListOrderService#createListOrder(ListOrder)
	 * @see ListOrder
	 */
	@PostMapping("/list-order")
	public ResponseEntity<ListOrder> saveListOrder(@RequestBody ListOrder entity) {
	    entity.getOrder().getUser().setPassword(null);
		return ResponseEntity.ok(listOrderserv.createListOrder(entity));
	}
	
	/**
	 * Updates an existing list order with the provided details. It's for ADMIN
	 *
	 * @param id     The identifier of the list order to be updated.
	 * @param entity The updated list order details provided in the request body.
	 * @return ResponseEntity containing the details of the updated list order as a {@link ListOrder} object with user information excluding passwords.
	 * @apiNote This endpoint updates an existing list order based on the provided details in the request body.
	 * If the list order is successfully updated, a ResponseEntity with status 200 (OK) is returned,
	 * along with a message indicating the successful update and the details of the updated list order encapsulated
	 * in a ResponseEntity with status 200 (OK). If the list order with the specified ID does not exist,
	 * a ResponseEntity with status 400 (Bad Request) is returned, and an error message indicating that the list order
	 * does not exist is included in the response body. This endpoint is intended for administrative purposes
	 * to update existing list orders.
	 * @see ListOrderService#getOneListOrder(Long)
	 * @see ListOrderService#updateListOrder(ListOrder)
	 * @see ListOrder
	 */
	@PutMapping("/list-order/{id}")
	public ResponseEntity<?> updateListOrder(@PathVariable(name = "id") Long id, @RequestBody ListOrder entity) {
		Optional<ListOrder> entityOld = listOrderserv.getOneListOrder(id);
		if (entityOld.isPresent()) {
			entity.getOrder().getUser().setPassword(null);
			entity.setId(id);
			return ResponseEntity.ok(listOrderserv.updateListOrder(entity));
		} else {
            return createErrorResponse("The list order not exists", HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Deletes an existing list order based on the provided list order ID. It's for ADMIN
	 *
	 * @param id The identifier of the list order to be deleted.
	 * @return ResponseEntity indicating the success or failure of the delete operation.
	 * @apiNote This endpoint deletes an existing list order with the specified ID.
	 * If the list order is successfully deleted, a ResponseEntity with status 200 (OK) is returned,
	 * indicating that the operation was successful with no additional content. If the list order with the
	 * specified ID does not exist, a ResponseEntity with status 400 (Bad Request) is returned,
	 * and an error message indicating that the list order does not exist is included in the response body.
	 * This endpoint is intended for administrative purposes to delete existing list orders.
	 * @see ListOrderService#getOneListOrder(Long)
	 * @see ListOrderService#deleteListOrder(Long)
	 */
	@DeleteMapping("/list-order/{id}")
	public ResponseEntity<?> deleteListOrder(@PathVariable(name = "id") Long id) { 
        Map<String, Object> responseData = new HashMap<>();
		Optional<ListOrder> entity = listOrderserv.getOneListOrder(id);
		if (entity.isPresent()) {
			listOrderserv.deleteListOrder(id);
            return ResponseEntity.accepted().body(responseData);
		} else {
            return createErrorResponse("The list order not found", HttpStatus.BAD_REQUEST);

		}
	}
	
	
	/**
	 * Creates a new list order based on the provided order ID and menu ID. It's for USER
	 *
	 * @param orderid The identifier of the order associated with the list order.
	 * @param menuid  The identifier of the menu associated with the list order.
	 * @return ResponseEntity containing the details of the created list order as a {@link ListOrder} object.
	 * @apiNote This endpoint creates a new list order based on the provided order ID and menu ID.
	 * If the order and menu are found, a new list order is created, and its details are encapsulated in
	 * a ResponseEntity with status 200 (OK), and the body contains the {@link ListOrder} object representing the
	 * newly created list order. If either the order or menu is not found, a ResponseEntity with status 404 (NOT FOUND)
	 * is returned. This endpoint is intended for creating a list order associated with a menu.
	 * @see OrderService#getOneOrder(Long)
	 * @see MenuService#getOneMenu(Long)
	 * @see ListOrderService#createListOrder(ListOrder)
	 * @see ListOrder
	 */
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
            listOrder.getOrder().getUser().setPassword(null);
            return ResponseEntity.ok(listOrderserv.createListOrder(listOrder));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	/**
	 * Creates a new list order based on the provided order ID and dish ID. It's for USER
	 *
	 * @param orderid The identifier of the order associated with the list order.
	 * @param dishid  The identifier of the dish associated with the list order.
	 * @return ResponseEntity containing the details of the created list order as a {@link ListOrder} object.
	 * @apiNote This endpoint creates a new list order based on the provided order ID and dish ID.
	 * If the order and dish are found, a new list order is created, and its details are encapsulated in
	 * a ResponseEntity with status 200 (OK), and the body contains the {@link ListOrder} object representing the
	 * newly created list order. If either the order or dish is not found, a ResponseEntity with status 404 (NOT FOUND)
	 * is returned. This endpoint is intended for creating a list order associated with a dish.
	 * @see OrderService#getOneOrder(Long)
	 * @see DishService#getOneDish(Long)
	 * @see ListOrderService#createListOrder(ListOrder)
	 * @see ListOrder
	 */
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
            listOrder.getOrder().getUser().setPassword(null);
            return ResponseEntity.ok(listOrderserv.createListOrder(listOrder));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	/**
	 * Creates a new list order based on the provided order ID, item ID, and item type.
	 *
	 * @param orderid   The identifier of the order associated with the list order.
	 * @param itemid    The identifier of the item associated with the list order (dish or menu).
	 * @param itemType  The type of the item ("dish" or "menu").
	 * @return ResponseEntity containing the details of the created list order as a {@link ListOrder} object.
	 * @apiNote This endpoint creates a new list order based on the provided order ID, item ID, and item type.
	 * If the order is found, a new list order is created, and its details are encapsulated in
	 * a ResponseEntity with status 200 (OK), and the body contains the {@link ListOrder} object representing the
	 * newly created list order. If the order is not found or the item type is not valid, a ResponseEntity with status
	 * 400 (Bad Request) is returned. If the item type is "dish," the associated dish is added to the list order.
	 * If the item type is "menu," the associated menu is added to the list order. This endpoint is intended for creating
	 * a list order associated with either a dish or a menu.
	 * @see OrderService#getOneOrder(Long)
	 * @see DishService#getOneDish(Long)
	 * @see MenuService#getOneMenu(Long)
	 * @see ListOrderService#createListOrder(ListOrder)
	 * @see ListOrder
	 */
	@PostMapping("/list-order/{orderid}/{itemid}")
	public ResponseEntity<?> saveListOrder(
	        @PathVariable(name = "orderid") Long orderid,
	        @PathVariable(name = "itemid") Long itemid,
	        @RequestParam(name = "itemType") String itemType) {
	    Optional<Order> orderOptional = orderserv.getOneOrder(orderid);
	    if (orderOptional.isPresent()) {
	        Order order = orderOptional.get();
	        ListOrder listOrder = new ListOrder();
	        listOrder.setOrder(order);

	        if ("dish".equals(itemType)) {
	            Optional<Dish> dishOptional = dishserv.getOneDish(itemid);
	            if (dishOptional.isPresent()) {
	                listOrder.setDish(dishOptional.get());
	            } else {
	                return createErrorResponse("The dish not exists", HttpStatus.BAD_REQUEST);
	            }
	        } else if ("menu".equals(itemType)) {
	            Optional<Menu> menuOptional = menuserv.getOneMenu(itemid);
	            if (menuOptional.isPresent()) {
	                listOrder.setMenu(menuOptional.get());
	            } else {
	                return createErrorResponse("The menu not exists", HttpStatus.BAD_REQUEST);
	            }
	        } else {
	            return createErrorResponse("Chose dish or menu", HttpStatus.BAD_REQUEST);
	        }
	        listOrder.getOrder().getUser().setPassword(null);
	        return ResponseEntity.ok(listOrderserv.createListOrder(listOrder));
	    } else {
            return createErrorResponse("The order not exists", HttpStatus.BAD_REQUEST);
	    }
	}
	
	
	private ResponseEntity<?> createErrorResponse(String message, HttpStatus status) {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("Message", message);
        return ResponseEntity.status(status).body(responseData);
    }
}
