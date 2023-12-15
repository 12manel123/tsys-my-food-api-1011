package com.myfood.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderCookDTO {
    private Long orderId;
    private boolean maked;
    private Slot slot;
    private List<Dish> dishes;
    private Double totalPrice;
    private LocalDateTime actualDate;
    
    public OrderCookDTO() {
	}

	

    public OrderCookDTO(Long orderId, boolean maked, Slot slot, List<Dish> dishes, Double totalPrice,
			LocalDateTime actualDate) {
		super();
		this.orderId = orderId;
		this.maked = maked;
		this.slot = slot;
		this.dishes = dishes;
		this.totalPrice = totalPrice;
		this.actualDate = actualDate;
	}



	public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public boolean isMaked() {
        return maked;
    }

    public void setMaked(boolean maked) {
        this.maked = maked;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
    
    public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public LocalDateTime getActualDate() {
		return actualDate;
	}

	public void setActualDate(LocalDateTime actualDate) {
		this.actualDate = actualDate;
	}
}
