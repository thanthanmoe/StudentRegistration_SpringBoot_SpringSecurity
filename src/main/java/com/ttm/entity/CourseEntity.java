package com.ttm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "course")
public class CourseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int id;
	@Column(length = 255, nullable = false, unique = true)
	private String courseName;

	@Column(nullable = false)
	private int fees;

	@Column(nullable = false)
	private Boolean status = false;

	public CourseEntity(int id, String courseName, int fees, Boolean status) {
		super();
		this.id = id;
		this.courseName = courseName;
		this.fees = fees;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getFees() {
		return fees;
	}

	public void setFees(int fees) {
		this.fees = fees;
	}

	public CourseEntity() {
		super();
	}

}
