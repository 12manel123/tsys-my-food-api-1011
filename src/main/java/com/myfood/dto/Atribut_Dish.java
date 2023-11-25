package com.myfood.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "atributes_dishes")
public class Atribut_Dish {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "dish_id")
	private Dish dish;
	
	@Column(name = "atributes", nullable = false)
	private String atributes;

	
	public Atribut_Dish(Long id, Dish dish, String atributes1) {
	
		this.id = id;
		this.dish = dish;
		atributes = atributes1;
	}


	public Atribut_Dish() {	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getAtributes() {
		return atributes;
	}

	public void setAtributes(String atributes1) {
		atributes = atributes1;
	}


	public Dish getDish() {
		return dish;
	}


	public void setDish(Dish dish) {
		this.dish = dish;
	}

	@Override
	public String toString() {
		return "Atribut_Dish [id=" + id + ", dish=" + dish + ", Atributes=" + atributes + "]";
	}

}
