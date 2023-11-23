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
	private String Atributes;

	
	public Atribut_Dish(Long id, Dish dish, String atributes) {
	
		this.id = id;
		this.dish = dish;
		Atributes = atributes;
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
		return Atributes;
	}

	public void setAtributes(String atributes) {
		Atributes = atributes;
	}


	public Dish getDish() {
		return dish;
	}


	public void setDish(Dish dish) {
		this.dish = dish;
	}


	@Override
	public String toString() {
		return "Atribut_Dish [id=" + id + ", dish=" + dish + ", Atributes=" + Atributes + "]";
	}

	

	
	
	

	
	
}
