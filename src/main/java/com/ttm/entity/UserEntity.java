package com.ttm.entity;

import com.ttm.dto.UserDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
@SqlResultSetMapping(name = "UserReportMapping", classes = @ConstructorResult(targetClass = UserDto.class, columns = {
		@ColumnResult(name = "id", type = Integer.class), @ColumnResult(name = "name", type = String.class),
		@ColumnResult(name = "email", type = String.class), @ColumnResult(name = "role", type = String.class) }))
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 255, nullable = false)
	private String name;

	@Column(length = 255, nullable = false)
	private String email;

	@Column(length = 255, nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(nullable = false)
	private Boolean status = false;
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private UserPhoto userPhoto;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public UserPhoto getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(UserPhoto userPhoto) {
		this.userPhoto = userPhoto;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public boolean isEnable() {
		return false;
	}

	public UserEntity(int id, String name, String email, String password, Role role, Boolean status) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.status = status;

	}

	public UserEntity() {
		super();
	}

}
