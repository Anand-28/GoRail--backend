package com.railway.userservice.dto;

import lombok.Data;

@Data
public class AuthRequest {
	private String fullName;
    private String email;
    private String mobile;
    private String password;
    private String role;
	public AuthRequest(String fullName, String email, String mobile, String password, String role) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
		this.role = role;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	

}
