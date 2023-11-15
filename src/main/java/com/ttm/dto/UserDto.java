package com.ttm.dto;

public class UserDto {
	private int id;
	private String name;
	private String email;
	private String role;
	private boolean status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public UserDto() {
		super();
	}

	public UserDto(int id, String name, String email, String role, boolean status) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
		this.status = status;
	}

}
