package com.myfood.dto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * The {@code User} class represents a user in the system.
 * It is annotated with JPA annotations for entity mapping.
 *
 * @author David Maza
 * @version 1.0
 */
@Entity
@Table(name = "users")
public class User {
	
	/**
	* The unique identifier for the user.
	*/
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	* The email address of the user. It is unique and cannot be null.
	*/
	@Email
	@NotBlank
	@Size(max = 80)
	@Column(name = "email", unique = true)
	private String email;
	
	/**
	* The password associated with the user. It cannot be null.
	*/
	@NotBlank
	@Column(name = "password")
	private String password;
	
	/**
	* The username of the user. It cannot be null.
	*/
	@NotBlank
	@Size(max = 60)
	@Column(name = "name", unique = true)
	private String username;
	
	/**
	* Represents the role of the user within the system. Multiple users can share the same role.
	* This field is mapped as a one-to-one relationship with the {@code Role} entity,
	* and it includes cascading persistence to ensure the role is persisted along with the user.
	*/
	@ManyToOne
	private Role role;

    @OneToMany(mappedBy = "user" ,fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Order> orders;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

	

	/**
	 * Default constructor for the {@code User} class.
	 */
	public User() {
	}
	
	public User(Long id, String email, String password, String username ,  LocalDateTime createdAt ,LocalDateTime updatedAt) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.username = username;
		this.role = new Role();
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	/**
	 * Parameterized constructor for the {@code User} class.
	 *
	 * @param id       The unique identifier for the user.
	 * @param email    The email address of the user.
	 * @param password The password associated with the user.
	 * @param username The username of the user.
	 * @param role     The role of the user in the system.
	 */
	public User(Long id, String email, String password, String username, Role role, LocalDateTime createdAt ,LocalDateTime updatedAt ) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.username = username;
		this.role = role;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	/**
	 * Get the unique identifier of the user.
	 *
	 * @return The unique identifier of the user.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set the unique identifier of the user.
	 *
	 * @param id The unique identifier of the user.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get the email address of the user.
	 *
	 * @return The email address of the user.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set the email address of the user.
	 *
	 * @param email The email address of the user.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get the password associated with the user.
	 *
	 * @return The password associated with the user.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set the password associated with the user.
	 *
	 * @param password The password associated with the user.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get the username of the user.
	 *
	 * @return The username of the user.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the username of the user.
	 *
	 * @param username The username of the user.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get the role of the user in the system.
	 *
	 * @return The role of the user.
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Set the role of the user in the system.
	 *
	 * @param role The role of the user.
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", username=" + username + ", role="
				+ role + "]";
	}




	/**
	 * @return the orders
	 */
	public List<Order> getOrders() {
		return orders;
	}

	/**
	 * @param orders the orders to set
	 */
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	/**
	 * @return the createdAt
	 */
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
