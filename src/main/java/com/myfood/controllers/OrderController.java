package com.myfood.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import com.myfood.dto.Dish;
import com.myfood.dto.ListOrder;
import com.myfood.dto.Order;
import com.myfood.dto.OrderCookDTO;
import com.myfood.dto.OrderUserDTO;
import com.myfood.dto.Slot;
import com.myfood.dto.User;
import com.myfood.services.OrderServiceImpl;
import com.myfood.services.SlotServiceImpl;
import com.myfood.services.UserServiceImpl;

@RestController
@RequestMapping("api/v1")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;
    
    @Autowired
    private SlotServiceImpl slotService;
    
    @Autowired
    private UserServiceImpl userService;

    /**
     * Retrieves a paginated list of user orders. It's for the ADMIN
     *
     * @param page The page number (default is 0).
     * @param size The number of orders per page (default is 10).
     * @return ResponseEntity containing a paginated list of {@link OrderUserDTO}.
     * @apiNote This endpoint retrieves a paginated list of user orders, where each order is represented
     * by an {@link OrderUserDTO} containing essential information such as order ID, completion status,
     * and slot.
     * @see OrderService#getAllOrdersWithPagination(Pageable)
     */
    @GetMapping("/orders")
    public ResponseEntity<Page<OrderUserDTO>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Order> allOrders = orderService.getAllOrders();
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> paginatedOrders = paginate(allOrders, pageable);

        List<OrderUserDTO> orderUserDTOList = paginatedOrders.getContent().stream()
                .map(order -> new OrderUserDTO(order.getId(), order.isMaked(), order.getSlot()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PageImpl<>(orderUserDTOList, pageable, paginatedOrders.getTotalElements()));
    }
    
    /**
     * Retrieves details of a specific order identified by its ID. It's for the ADMIN
     *
     * @param id The unique identifier of the order.
     * @return ResponseEntity containing the details of the order as an {@link OrderUserDTO}.
     * @throws DataNotFoundException If the specified order does not exist.
     * @apiNote This endpoint retrieves and returns details of a specific order identified by its unique ID.
     * If the order is found, its details are encapsulated in an {@link OrderUserDTO} and returned in the
     * ResponseEntity with status 200 (OK). If the order does not exist, a ResponseEntity with status 404 (NOT FOUND)
     * is returned, and the message "The order not exists" is included in the response body.
     * @see OrderService#getOneOrder(Long)
     */
    @GetMapping("/order/{id}")
    public ResponseEntity<?> getOneOrder(@PathVariable(name = "id") Long id) {
        Optional<Order> entity = orderService.getOneOrder(id);
        if (entity.isPresent()) {
            Order order = entity.get();
            OrderUserDTO orderUserDTO = new OrderUserDTO(order.getId(), order.isMaked(), order.getSlot());
            return ResponseEntity.ok(orderUserDTO);
        } else {
        	return createErrorResponse("The order not exists", HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Creates a new order based on the provided order details. It's for the ADMIN
     *
     * @param entity The order details provided in the request body.
     * @return ResponseEntity containing the details of the created order as an {@link OrderUserDTO}.
     * @apiNote This endpoint creates a new order based on the details provided in the request body.
     * If the order is successfully created, the details of the created order are encapsulated in an
     * {@link OrderUserDTO} and returned in the ResponseEntity with status 200 (OK).
     * @see OrderService#createOrder(Order)
     */
    @PostMapping("/order")
    public ResponseEntity<OrderUserDTO> saveOrder(@RequestBody Order entity) {
        Order savedOrder = orderService.createOrder(entity);
        OrderUserDTO orderUserDTO = new OrderUserDTO(savedOrder.getId(), savedOrder.isMaked(), savedOrder.getSlot());
        return ResponseEntity.ok(orderUserDTO);
    }

    /**
     * Updates an existing order with the provided order details. It's for ADMIN
     *
     * @param id     The identifier of the order to be updated.
     * @param entity The updated order details provided in the request body.
     * @return ResponseEntity containing a message and the details of the updated order as an {@link OrderUserDTO}.
     * @apiNote This endpoint updates an existing order based on the provided details in the request body.
     * If the order is successfully updated, a ResponseEntity with status 200 (OK) is returned,
     * along with a message indicating the successful update and the details of the updated order encapsulated
     * in an {@link OrderUserDTO}. If the order with the specified ID does not exist, a ResponseEntity with
     * status 400 (Bad Request) is returned, and an error message indicating that the order does not exist.
     * @see OrderService#getOneOrder(Long)
     * @see OrderService#updateOrder(Long, Order)
     */
    @PutMapping("/order/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable(name = "id") Long id, @RequestBody Order entity) {
        Optional<Order> entityOld = orderService.getOneOrder(id);
        if (entityOld.isPresent()) {
            entity.setId(id);
            return ResponseEntity.ok(Map.of("Message", "Updated order", "order", new OrderUserDTO(entity.getId(), entity.isMaked(), entity.getSlot())));
        } else {
            return createErrorResponse("The order not exists", HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Deletes an existing order based on the provided order ID. It's for ADMIN
     *
     * @param id The identifier of the order to be deleted.
     * @return ResponseEntity indicating the success or failure of the delete operation.
     * @apiNote This endpoint deletes an existing order with the specified ID.
     * If the order is successfully deleted, a ResponseEntity with status 204 (No Content) is returned,
     * indicating that the operation was successful with no additional content. If the order with the
     * specified ID does not exist, a ResponseEntity with status 400 (Bad Request) is returned,
     * and an error message indicating that the order does not exist.
     * @see OrderService#getOneOrder(Long)
     * @see OrderService#deleteOrder(Long)
     */
    @DeleteMapping("/order/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable(name = "id") Long id) {
        Optional<Order> entity = orderService.getOneOrder(id);
        if (entity.isPresent()) {
            orderService.deleteOrder(id);
            return ResponseEntity.status(204).body(Map.of("Message", "Order deleted"));
        } else {
            return createErrorResponse("The order not exists", HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * Retrieves a paginated list of orders suitable for a cook, including associated dishes. It's for CHEFF
     *
     * @param page The page number for pagination (default is 0).
     * @param size The number of orders per page (default is 8).
     * @return ResponseEntity containing a paginated list of OrderCookDTO objects.
     * @apiNote This endpoint retrieves orders suitable for a cook, filtering out orders with a null actual date.
     * It returns a paginated list of OrderCookDTO objects, including information such as order ID, status,
     * time slot, and associated dishes. If the specified page is invalid, a ResponseEntity with status 400 (Bad Request)
     * and an error message is returned. The paginated list is based on the provided page and size parameters.
     * @see OrderService#getAllOrdersForCook()
     * @see OrderController#mapToOrderCookDTO(Order)
     * @see OrderController#paginate(List, Pageable)
     * @see OrderCookDTO
     */
    @GetMapping("/orders/cheff")
    public ResponseEntity<Page<OrderCookDTO>> getAllOrdersForCheff(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
        List<Order> ordersForCook = orderService.getAllOrdersForCook();
        List<Order> filteredOrders = ordersForCook.stream()
                .filter(order -> order.getActualDate() != null)
                .collect(Collectors.toList());
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> paginatedOrders = paginate(filteredOrders, pageable);
        List<OrderCookDTO> orderCookDTOList = paginatedOrders.getContent().stream()
                .map(this::mapToOrderCookDTOWithDishes)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new PageImpl<>(orderCookDTOList, pageable, filteredOrders.size()));
    }

    private OrderCookDTO mapToOrderCookDTOWithDishes(Order order) {
        OrderCookDTO orderCookDTO = new OrderCookDTO();
        orderCookDTO.setOrderId(order.getId());
        orderCookDTO.setMaked(order.isMaked());
        orderCookDTO.setSlot(order.getSlot());
        List<ListOrder> listOrders = order.getListOrder();
        List<Dish> dishDTOList = listOrders.stream()
                .map(this::mapToListOrderDishDTO)
                .collect(Collectors.toList());
        for (ListOrder listOrder : listOrders) {
            if (listOrder.getMenu() != null) {
            	dishDTOList.addAll(Arrays.asList(
                        listOrder.getMenu().getAppetizer(),
                        listOrder.getMenu().getFirst(),
                        listOrder.getMenu().getSecond(),
                        listOrder.getMenu().getDessert()
                ).stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
            }
        }
        orderCookDTO.setDishes(dishDTOList);
        return orderCookDTO;
    }

    private Dish mapToListOrderDishDTO(ListOrder listOrder) {
        Dish dishDTO = new Dish();
        dishDTO.setId(listOrder.getDish().getId());
        dishDTO.setName(listOrder.getDish().getName());
        dishDTO.setPrice(listOrder.getDish().getPrice());
        return dishDTO;
    }

    


    /**
     * Retrieves a paginated list of orders for a specific user. It's for the history for USER
     *
     * @param page   The page number for pagination (default is 0).
     * @param size   The number of orders per page (default is 10).
     * @param userId The ID of the user for whom to retrieve orders.
     * @return ResponseEntity containing a paginated list of OrderUserDTO objects.
     * @apiNote This endpoint retrieves orders associated with a specific user, based on the provided user ID.
     * If the specified user ID is not found, a ResponseEntity with status 404 (Not Found) is returned.
     * The paginated list includes information such as order ID, status, and time slot. If the specified page
     * is invalid, a ResponseEntity with status 400 (Bad Request) and an error message is returned. The paginated
     * list is based on the provided page and size parameters.
     * @see UserService#getOneUser(Long)
     * @see OrderService#getAllOrdersForUserId(Long)
     * @see OrderUserDTO
     */
    @GetMapping("/orders/{userId}")
    public ResponseEntity<?> getAllOrdersForUserPaginate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable(name = "userId") Long userId) {
        if (!userService.getOneUser(userId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<Order> userOrders = orderService.getAllOrdersForUserId(userId);
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> paginatedOrders = paginate(userOrders, pageable);

        List<OrderUserDTO> orderUserDTOList = paginatedOrders.getContent().stream()
                .map(order -> new OrderUserDTO(order.getId(), order.isMaked(), order.getSlot()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PageImpl<>(orderUserDTOList, pageable, paginatedOrders.getTotalElements()));
    }
    
    /**
     * Marks an order as "maked" (fulfilled) based on the provided order ID. It's for CHEFF
     *
     * @param id The ID of the order to be marked as "maked."
     * @return ResponseEntity containing the updated OrderUserDTO after marking the order as "maked."
     * @apiNote This endpoint marks the specified order as "maked," indicating that the order has been fulfilled.
     * If the order ID is not found, a ResponseEntity with status 400 (Bad Request) and an error message is returned.
     * The response includes the updated OrderUserDTO, reflecting the changes made to the order status.
     * @see OrderService#getOneOrder(Long)
     * @see OrderService#updateOrder(Order)
     * @see OrderUserDTO
     */
    @PutMapping("/order/markAsMaked/{id}")
    public ResponseEntity<?> markOrderAsMaked(@PathVariable(name = "id") Long id) {
        Optional<Order> optionalOrder = orderService.getOneOrder(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setMaked(true);
            Order updatedOrder = orderService.updateOrder(order);
            OrderUserDTO orderUserDTO = new OrderUserDTO(updatedOrder.getId(), updatedOrder.isMaked(), updatedOrder.getSlot());
            return ResponseEntity.ok(orderUserDTO);
        } else {
            return createErrorResponse("The order not exists", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Updates the slot of an order and confirms it, setting the actual date. Also, calculates and sets the total price of the order. It's for USER
     *
     * @param orderId The ID of the order to be updated.
     * @param slotId  The ID of the slot to be associated with the order.
     * @return ResponseEntity containing the updated OrderUserDTO after updating the order slot and confirming it.
     * @apiNote This endpoint updates the slot of the specified order, confirms the order by setting the actual date,
     * and calculates the total price of the order based on associated dishes and menu items.
     * If the order or slot ID is not found, or if there are issues with the slot's capacity or order requirements,
     * a ResponseEntity with status 400 (Bad Request) and an error message is returned.
     * The response includes the updated OrderUserDTO, reflecting the changes made to the order.
     * @see OrderService#getOneOrder(Long)
     * @see SlotService#getOneSlot(Long)
     * @see OrderService#updateOrder(Order)
     * @see SlotService#updateSlot(Slot)
     * @see #calculateTotalPrice(Order)
     * @see OrderUserDTO
     */
    @PutMapping("/order/finish/{orderId}/{slotId}")
    public ResponseEntity<?> updateOrderSlot(
            @PathVariable(name = "orderId") Long orderId,
            @PathVariable(name = "slotId") Long slotId) {
        Optional<Order> optionalOrder = orderService.getOneOrder(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (order.getActualDate() != null) {
                return createErrorResponse("Order is confirmed previously", HttpStatus.BAD_REQUEST);
            }
            if (order.getListOrder() == null || order.getListOrder().isEmpty()) {
                return createErrorResponse("Order must have at least one ListOrder associated", HttpStatus.BAD_REQUEST);
            }
            Optional<Slot> slotOptional = slotService.getOneSlot(slotId);
            if (slotOptional.isPresent()) {
                Slot slot = slotOptional.get();
                if (slot.getActual() >= slot.getLimitSlot()) {
                    return createErrorResponse("Too many orders for this slot to create order", HttpStatus.BAD_REQUEST);
                }
                Double totalPrice = calculateTotalPrice(order);
                ZoneId madridZone = ZoneId.of("Europe/Madrid");
                order.setActualDate(LocalDateTime.now(madridZone));
                order.setSlot(slot);
                order.setTotalPrice(totalPrice);
                slot.setActual(slot.getActual() + 1);
                orderService.updateOrder(order);
                slotService.updateSlot(slot);
                OrderUserDTO orderUserDTO = new OrderUserDTO(order.getId(), order.isMaked(), order.getSlot());
                return ResponseEntity.accepted().body(orderUserDTO);
            } else {
                return createErrorResponse("The slot not exists", HttpStatus.BAD_REQUEST);
            }
        } else {
            return createErrorResponse("The order not exists", HttpStatus.BAD_REQUEST);
        }
    }
    
    private Double calculateTotalPrice(Order order) {
        List<ListOrder> listOrders = order.getListOrder();
        List<Dish> dishes = listOrders.stream()
                .map(ListOrder::getDish)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<Dish> menuDishes = listOrders.stream()
                .map(listOrder -> listOrder.getMenu())
                .filter(Objects::nonNull)
                .flatMap(menu -> Arrays.asList(menu.getAppetizer(), menu.getFirst(), menu.getSecond(), menu.getDessert()).stream())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<Dish> allDishes = new ArrayList<>(dishes);
        allDishes.addAll(menuDishes);
        Double totalPrice = allDishes.stream()
                .mapToDouble(Dish::getPrice)
                .sum();
        return totalPrice;
    }

    /**
     * Creates and saves a new order for the specified user. It's for the USER
     *
     * @param userId The ID of the user for whom the order is created.
     * @return ResponseEntity containing the OrderUserDTO representing the newly created order.
     * @apiNote This endpoint creates a new order for the specified user, associates the user with the order,
     * and saves the order to the database. The response includes the OrderUserDTO containing essential information
     * about the newly created order, such as its ID, completion status, and associated slot.
     * If the specified user ID is not found, a ResponseEntity with status 404 (Not Found) is returned.
     * @see UserService#getOneUser(Long)
     * @see OrderService#createOrder(Order)
     * @see OrderUserDTO
     */
    @PostMapping("/order/{userId}")
    public ResponseEntity<?> saveOrder(@PathVariable(name = "userId") Long userId) {
        Optional<User> userOptional = userService.getOneUser(userId);
        if (!userOptional.isPresent()) {
            return createErrorResponse("User not found", HttpStatus.BAD_REQUEST);
        }
        User user = userOptional.get();
        Order order = new Order();
        order.setUser(user);
        Order savedOrder = orderService.createOrder(order);
        OrderUserDTO orderUserDTO = new OrderUserDTO(savedOrder.getId(), savedOrder.isMaked(), savedOrder.getSlot());
        return ResponseEntity.ok(orderUserDTO);
    }
    
    /**
     * Retrieves a paginated list of orders based on the specified date parameters. It's for the ADMIN
     *
     * @param page  Page number (default is 0).
     * @param size  Number of items per page (default is 10).
     * @param year  The year to filter orders by (optional).
     * @param month The month to filter orders by (optional).
     * @param day   The day to filter orders by (optional).
     * @return A ResponseEntity containing a paginated list of OrderUserDTO objects or an error message.
     */
    @GetMapping("/orders/date")
    public ResponseEntity<?> getOrdersByDatePaginate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "month", required = false) Integer month,
            @RequestParam(name = "day", required = false) Integer day) {

        List<Order> listOrders = orderService.getAllOrders();
        List<OrderUserDTO> listOrdersUserDTO = new ArrayList<>();
        List<Order> filteredOrders = listOrders.stream()
                .filter(order -> {
                    LocalDateTime actualDate = order.getActualDate();
                    Long slotId = (order.getSlot() != null) ? order.getSlot().getId() : null;
                    
                    return (actualDate != null && slotId != null) &&
                           (year == null || actualDate.getYear() == year) &&
                           (month == null || actualDate.getMonthValue() == month) &&
                           (day == null || actualDate.getDayOfMonth() == day);
                })
                .collect(Collectors.toList());
        int totalPages = (int) Math.ceil((double) filteredOrders.size() / size);
        if (totalPages == 0) {
            return createErrorResponse("Not orders in this date", HttpStatus.BAD_REQUEST);
        }
        if (page < 0 || page >= totalPages) {
            return createErrorResponse("Invalid page number", HttpStatus.BAD_REQUEST);
        }
        int start = page * size;
        int end = Math.min(start + size, filteredOrders.size());
        List<Order> paginatedOrders = filteredOrders.subList(start, end);
        paginatedOrders.forEach(order -> {
            listOrdersUserDTO.add(new OrderUserDTO(order.getId(), order.isMaked(), order.getSlot()));
        });
        Page<OrderUserDTO> orderUserDTOPage = new PageImpl<>(listOrdersUserDTO, PageRequest.of(page, size), filteredOrders.size());
        return ResponseEntity.ok(orderUserDTOPage);
    }

    
    private ResponseEntity<?> createErrorResponse(String message, HttpStatus status) {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("Message", message);
        return ResponseEntity.status(status).body(responseData);
    }
    
    private Page<Order> paginate(List<Order> orders, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), orders.size());

        return new PageImpl<>(orders.subList(start, end), pageable, orders.size());
    }
}