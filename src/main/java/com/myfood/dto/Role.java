package com.myfood.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    @Column(name = "name", unique = true, nullable = false)
    private String name;


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


}
