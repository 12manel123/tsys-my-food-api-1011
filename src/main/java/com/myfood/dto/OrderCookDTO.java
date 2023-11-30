package com.myfood.dto;

import java.util.List;

public class OrderCookDTO {
    private Long orderId;
    private boolean maked;
    private Slot slot;
    private List<Dish> dishes;

    public OrderCookDTO() {
	}

	public OrderCookDTO(Long orderId, boolean maked, Slot slot, List<Dish> dishes) {
        this.orderId = orderId;
        this.maked = maked;
        this.slot = slot;
        this.dishes = dishes;
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
}
