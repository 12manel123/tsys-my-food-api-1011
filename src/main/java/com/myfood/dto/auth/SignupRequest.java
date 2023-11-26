package com.myfood.dto.auth;



public class SignupRequest  {

	private String email;
	private String password;
	private String username;
	
	public SignupRequest(String email, String password, String username) {

		this.email = email;
		this.password = password;
		this.username = username;
	}
	
	public SignupRequest() {
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
