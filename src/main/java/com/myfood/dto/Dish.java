 package com.myfood.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "dishes")
public class Dish {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "image")
	private String image;

	@Column(name = "price", nullable = false)
	private double price;

	@Column(name = "category", nullable = false)
	private String category;
	
	@OneToMany(mappedBy = "dish")
	@JsonIgnore
	private List<Atribut_Dish> atribut_dish;
	
	@OneToMany(mappedBy = "dish")
    @JsonIgnore
    private List<ListOrder> listOrder;
		

	public Dish(Long id, String name, String description, String image, double price, String category) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
		this.price = price;
		this.category = category;
	}
	
	public Dish() {	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Dish [id=" + id + ", name=" + name + ", description=" + description + ", image=" + image + ", price="
				+ price + ", category=" + category + "]";
	}

}
