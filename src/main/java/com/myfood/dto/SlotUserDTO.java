package com.myfood.dto;

public class SlotUserDTO {
	
	private Long id;
    private String time;
    
	public SlotUserDTO(Long id, String time) {
		this.id = id;
		this.time = time;
	}
	
	public SlotUserDTO() {
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	@Override
	public String toString() {
		return "SlotIUserDTO [id=" + id + ", time=" + time + "]";
	}
	    
}
