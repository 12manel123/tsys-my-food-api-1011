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
	
	@Column(name = "attributes")
	private List<String> attributes;
	
	@Column(name = "visible")
    private boolean visible = false;  
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	    name = "dish_atribut_dish",
	    joinColumns = @JoinColumn(name = "dish_id"),
	    inverseJoinColumns = @JoinColumn(name = "atribut_dish_id")
	)
	@JsonIgnore
	private List<Atribut_Dish> atribut_dish;
		
	@OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ListOrder> listOrder;
	
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JsonIgnore
	@JoinColumn(name = "menu_id")
	private Menu menu;


	
	
	public Dish(Long id, String name, String description, String image, double price, String category,
			List<String> attributes, boolean visible, List<Atribut_Dish> atribut_dish, List<ListOrder> listOrder,
			Menu menu) {

		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
		this.price = price;
		this.category = category;
		this.attributes = attributes;
		this.visible = visible;
		this.atribut_dish = atribut_dish;
		this.listOrder = listOrder;
		this.menu = menu;
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


	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public List<Atribut_Dish> getAtribut_dish() {
		return atribut_dish;
	}

	public void setAtribut_dish(List<Atribut_Dish> atribut_dish) {
		this.atribut_dish = atribut_dish;
	}

	public List<ListOrder> getListOrder() {
		return listOrder;
	}

	public void setListOrder(List<ListOrder> listOrder) {
		this.listOrder = listOrder;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	

	@Override
	public String toString() {
		return "Dish [id=" + id + ", name=" + name + ", description=" + description + ", image=" + image + ", price="
				+ price + ", category=" + category + ", attributes=" + attributes + ", visible=" + visible
				+ ", atribut_dish=" + atribut_dish + ", listOrder=" + listOrder + ", menu=" + menu + "]";
	}

	public String[] getAttributes() {
	    if (atribut_dish != null && !atribut_dish.isEmpty()) {
	        return atribut_dish.stream()
	                .map(Atribut_Dish::getAttributes)
	                .toArray(String[]::new);
	    }
	    return new String[0]; 
	}

	

}

