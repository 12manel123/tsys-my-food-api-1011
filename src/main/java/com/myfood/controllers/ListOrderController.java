package com.myfood.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

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
	 * Retrieves a list of all list orders with user information excluding
	 * passwords. It's for ADMIN
	 *
	 * @return ResponseEntity containing a list of {@link ListOrder} objects with
	 *         user information excluding passwords.
	 * @see ListOrderService#getAllListOrders()
	 * @see ListOrder
	 */
	@Operation(summary = "Endpoint ADMIN", security = @SecurityRequirement(name = "bearerAuth"))
	@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list-orders")
    public ResponseEntity<Page<ListOrder>> getAllListOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<ListOrder> listOrdersPage = listOrderserv.getAllListOrders(pageable);
        listOrdersPage.getContent().forEach(listOrder -> {
            listOrder.getOrder().getUser().setPassword(null);
        });
        return ResponseEntity.ok(listOrdersPage);
    }

	/**
	 * Retrieves details of a specific list order identified by its ID. It's for
	 * ADMIN
	 *
	 * @param id The unique identifier of the list order.
	 * @return ResponseEntity containing the details of the list order as a
	 *         {@link ListOrder} object with user information excluding passwords.
	 * @throws DataNotFoundException If the specified list order does not exist.
	 * @see ListOrderService#getOneListOrder(Long)
	 * @see ListOrder
	 */
	@Operation(summary = "Endpoint ADMIN", security = @SecurityRequirement(name = "bearerAuth"))
	@PreAuthorize("hasRole('ADMIN')")
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
     * Retrieves a paginated list of list orders associated with a specific order ID.
     *
     * @param orderId The unique identifier of the order.
     * @param page     The page number (default is 0).
     * @param size     The number of elements per page (default is 10).
     * @return ResponseEntity containing a paginated list of {@link ListOrder} objects associated with the specified order ID.
     * @see ListOrderService#getListOrdersByOrderId(Long, Pageable)
     * @see ListOrder
     */
	@Operation(summary = "Endpoint USER", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/list-order/orderid/{orderId}")
    public ResponseEntity<?> getOrdersByOrderId(
            @PathVariable(name = "orderId") Long orderId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<ListOrder> listOrdersPage = listOrderserv.getListOrdersByOrderId(orderId, pageable);
        
        if (listOrdersPage.hasContent()) {
            listOrdersPage.getContent().forEach(listOrder -> listOrder.getOrder().getUser().setPassword(null));
            return ResponseEntity.ok(listOrdersPage);
        } else {
            return createErrorResponse("No list orders found for the specified order ID", HttpStatus.NOT_FOUND);
        }
    }

	/**
	 * Creates a new list order based on the provided details. It's for ADMIN
	 *
	 * @param entity The list order details provided in the request body.
	 * @return ResponseEntity containing the details of the created list order as a
	 *         {@link ListOrder} object with user information excluding passwords.
	 * @see ListOrderService#createListOrder(ListOrder)
	 * @see ListOrder
	 */
	@Operation(summary = "Endpoint ADMIN", security = @SecurityRequirement(name = "bearerAuth"))
	@PreAuthorize("hasRole('ADMIN')")
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
	 * @return ResponseEntity containing the details of the updated list order as a
	 *         {@link ListOrder} object with user information excluding passwords.
	 * @see ListOrderService#getOneListOrder(Long)
	 * @see ListOrderService#updateListOrder(ListOrder)
	 * @see ListOrder
	 */
	@Operation(summary = "Endpoint ADMIN", security = @SecurityRequirement(name = "bearerAuth"))
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/list-order/{id}")
	public ResponseEntity<?> updateListOrder(@PathVariable(name = "id") Long id, @RequestBody ListOrder entity) {
		Optional<ListOrder> entityOld = listOrderserv.getOneListOrder(id);
		if (entityOld.isPresent()) {
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("Message", "The list order with id: "+entity.getId()+" updated.");
			entity.setId(id);
			return ResponseEntity.ok(responseData);
		} else {
			return createErrorResponse("The list order not exists", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Deletes an existing list order based on the provided list order ID. It's for
	 * ADMIN
	 *
	 * @param id The identifier of the list order to be deleted.
	 * @return ResponseEntity indicating the success or failure of the delete
	 *         operation.
	 * @see ListOrderService#getOneListOrder(Long)
	 * @see ListOrderService#deleteListOrder(Long)
	 */
	@Operation(summary = "Endpoint ADMIN", security = @SecurityRequirement(name = "bearerAuth"))
	@PreAuthorize("hasRole('ADMIN')")
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
	 * Creates a new list order based on the provided order ID, item ID, and item
	 * type.
	 *
	 * @param orderid  The identifier of the order associated with the list order.
	 * @param itemid   The identifier of the item associated with the list order
	 *                 (dish or menu).
	 * @param itemType The type of the item ("dish" or "menu").
	 * @return ResponseEntity containing the details of the created list order as a
	 *         {@link ListOrder} object.
	 * @see OrderService#getOneOrder(Long)
	 * @see DishService#getOneDish(Long)
	 * @see MenuService#getOneMenu(Long)
	 * @see ListOrderService#createListOrder(ListOrder)
	 * @see ListOrder
	 */
	@Operation(summary = "Endpoint USER", security = @SecurityRequirement(name = "bearerAuth"))
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
