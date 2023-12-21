package com.myfood.dto;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "atributes_dishes")
public class Atribut_Dish {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany(mappedBy = "atribut_dish", fetch = FetchType.EAGER)
	private List<Dish> dishes;

	@Column(name = "attributes", nullable = false)
	private String attributes;

	public Atribut_Dish(Long id, List<Dish> dishes, String attributes) {
		super();
		this.id = id;
		this.dishes = dishes;
		this.attributes = attributes;
	}

	public Atribut_Dish() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(List<Dish> dishes) {
		this.dishes = dishes;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return "Atribut_Dish [id=" + id + ", dishes=" + dishes + ", attributes=" + attributes + "]";
	}	
}
