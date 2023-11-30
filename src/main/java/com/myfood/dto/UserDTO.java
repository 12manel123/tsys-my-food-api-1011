package com.myfood.dto;

/**
 * The {@code UserDTO} class represents a user in the system using the Builder
 * pattern.
 *
 * @author David Maza
 */
public class UserDTO {

	private Long id;
	private String email;
	private String username;
	private Role role;

	private UserDTO(Builder builder) {
		this.id = builder.id;
		this.email = builder.email;
		this.username = builder.username;
		this.role = builder.role;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	public static class Builder {
		private Long id;
		private String email;
		private String username;
		private Role role;

		public Builder() {
		}

		/**
		 * @param id the id to set
		 */
		public Builder setId(Long id) {
			this.id = id;
			return this;
		}

		/**
		 * @param email the email to set
		 */
		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}

		/**
		 * @param username the username to set
		 */
		public Builder setUsername(String username) {
			this.username = username;
			return this;
		}

		/**
		 * @param role the role to set
		 */
		public Builder setRole(Role role) {
			this.role = role;
			return this;
		}

		/**
		 * @return newUserDTO
		 */
		public UserDTO build() {
			return new UserDTO(this);
		}
	}

}
