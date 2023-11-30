package com.myfood.dto;


public class OrderUserDTO {
	
    private Long id;
    private boolean maked;
    private Slot slot;

	public OrderUserDTO(Long id, boolean maked, Slot slot) {
		this.id = id;
		this.maked = maked;
		this.slot = slot;
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

	@Override
	public String toString() {
		return "OrderUserDTO [id=" + id + ", maked=" + maked + ", slot=" + slot + "]";
	}
	
}

