package com.myfood.dto;

import java.time.LocalDateTime;

public class OrderUserDTO {
	
    private Long id;
    private boolean maked;
    private Slot slot;
    private Double totalPrice;
    private LocalDateTime actualDate;

    
	public OrderUserDTO(Long id, boolean maked, Slot slot, Double totalPrice, LocalDateTime actualDate) {
		super();
		this.id = id;
		this.maked = maked;
		this.slot = slot;
		this.totalPrice = totalPrice;
		this.actualDate = actualDate;
	}

	public OrderUserDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "OrderUserDTO [id=" + id + ", maked=" + maked + ", slot=" + slot + ", totalPrice=" + totalPrice
				+ ", actualDate=" + actualDate + "]";
	}

	
}

