package com.ttm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.dto.StudentReportDto;
import com.ttm.entity.StudentEntity;
import com.ttm.repository.StudentRepository;

@Service
public class StudentService {
	@Autowired
	StudentRepository sRep;

	public StudentEntity studentCreate(StudentEntity student) {
		return sRep.save(student);
	}

	public List<StudentEntity> selectAll() {
		List<StudentEntity> students = sRep.findByStatus(false);
		return students;
	}

	public void deleteStudent(Integer id) {
		sRep.deleteById(id);
	}

	public StudentEntity findId(int id) {
		return sRep.findById(id).get();
	}

	public Optional<StudentEntity> findById(int id) {
		return sRep.findByIdAndStatus(id, false);
	}

	public List<StudentEntity> findByIdOrNameOrCourseName(int id, String name, String courseName) {
		List<StudentEntity> students = sRep.findByIdOrNameOrStudentCourse_Course_CourseNameAndStatus(id, name,
				courseName, false);
		return students;
	}

	public List<StudentEntity> findByName(String name) {
		List<StudentEntity> students = sRep.findByNameAndStatus(name, false);
		return students;
	}

	public List<StudentEntity> findByCourseName(String courseName) {
		List<StudentEntity> students = sRep.findByStudentCourse_Course_CourseNameAndStatus(courseName, false);
		return students;
	}

	public List<StudentReportDto> getDistinctStudentsWithCourses() {
		return sRep.getDistinctStudentsWithCourses();
	}
}
