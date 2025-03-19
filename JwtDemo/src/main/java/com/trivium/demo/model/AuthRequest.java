package com.trivium.demo.model;

// This model class is used to carry the username & password
// entered by the user - model
public class AuthRequest {
	private String username;
    private String password;
	
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


}