package com.myfood.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Represents a role in the system.
 *
 * This class is mapped to the "roles" table in the database.
 *
 * @author David Maza 
 */
@Entity
@Table(name = "roles")
public class Role {


    /** Unique identifier for the role. Automatically generated. */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	/** Name of the role. Must be unique and not null. */
    @Column(name = "name", nullable = false)
    private String name;


    @JsonIgnore
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<User> users;
    
    /** Default constructor required by JPA. */
    public Role() {
    }
    
    /**
    * Constructor for the {@code Role} class that allows setting the identifier,
    * name, and the list of users associated with this role.
    *
    * @param id   The unique identifier of the role.
    * @param name The name of the role.
    * @param user The list of users associated with this role.
    */
    public Role(Long id, String name) {
		this.id = id;
		this.name = name;
	}
    
    public Role(String name) {
    	this.name = name;
    }
    
    

    /**
     * Get the unique identifier of the role.
     *
     * @return The unique identifier of the role.
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the name of the role.
     *
     * @return The name of the role.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the unique identifier of the role.
     *
     * @param id The unique identifier of the role.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the name of the role.
     *
     * @param name The name of the role.
     */
    public void setName(String name) {
        this.name = name;
    }

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", users=" + users + "]";
	}


}
