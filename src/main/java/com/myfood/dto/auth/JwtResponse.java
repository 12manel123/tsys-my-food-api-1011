package com.myfood.dto.auth;

import java.util.List;

public class JwtResponse {

	private String token;
	private String type;
	private String username;
	private int userid;
	private List<String> roles;


	/**
	 * @param token
	 * @param type
	 * @param username
	 */
	public JwtResponse(String token, String username, Integer userid, List<String> roles) {
		super();
		this.token = token;
		this.type = "Bearer";
		this.username = username;
		this.userid = userid;
		this.roles = roles;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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

	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getUserid() {
		return userid;
	}

	/**
	 * @return the roles
	 */
	public List<String> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}


}
