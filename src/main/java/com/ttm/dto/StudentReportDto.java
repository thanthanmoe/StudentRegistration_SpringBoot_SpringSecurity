package com.ttm.dto;

public class StudentReportDto {
	private int id;
	private String name;
	private String phone;
	private String education;
	private String gender;
	private String dob;
	private String course_names;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCourse_names() {
		return course_names;
	}

	public void setCourse_names(String course_names) {
		this.course_names = course_names;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public StudentReportDto(int id, String name, String phone, String education, String gender, String dob,
			String course_names) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.education = education;
		this.gender = gender;
		this.dob = dob;
		this.course_names = course_names;
	}

}
