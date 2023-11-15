package com.ttm.entity;

import java.util.List;

import com.ttm.dto.StudentReportDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;

@Entity
@Table(name = "student")
@SqlResultSetMapping(name = "StudentReportMapping", classes = @ConstructorResult(targetClass = StudentReportDto.class, columns = {
		@ColumnResult(name = "id", type = Integer.class), @ColumnResult(name = "name", type = String.class),
		@ColumnResult(name = "phone", type = String.class), @ColumnResult(name = "education", type = String.class),
		@ColumnResult(name = "gender", type = String.class), @ColumnResult(name = "dob", type = String.class),
		@ColumnResult(name = "course_names", type = String.class) }))
public class StudentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(length = 225, nullable = false)
	private String name;
	@Column(length = 225, nullable = false)
	private String dob;
	@Column(length = 225, nullable = false)
	private String phone;
	@Column(length = 225, nullable = false)
	private String education;
	@Column(length = 225, nullable = false)
	private String gender;

	@Column(nullable = false)
	private Boolean status = false;

	@OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
	private List<StudentCourseEntity> studentCourse;

	@OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private StudentPhoto studentPhoto;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<StudentCourseEntity> getStudentCourse() {
		return studentCourse;
	}

	public void setStudentCourse(List<StudentCourseEntity> studentCourse) {
		this.studentCourse = studentCourse;
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

	public StudentPhoto getStudentPhoto() {
		return studentPhoto;
	}

	public void setStudentPhoto(StudentPhoto studentPhoto) {
		this.studentPhoto = studentPhoto;
	}

}
