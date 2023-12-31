package com.myfood.dto.auth;
/**
 * @author David Maza
 *
 */
public class LoginRequest {

	private String username;
	private String password;

	
	/**
	 * @param username
	 * @param password

	 */
	public LoginRequest(String username, String password) {
		this.username = username;
		this.password = password;
	
	}


	public LoginRequest() {
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

}