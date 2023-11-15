package com.ttm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.entity.StudentUpdateEntity;
import com.ttm.repository.StudentUpdateRepository;

@Service
public class StudentUpdateService {
	@Autowired
	private StudentUpdateRepository sURep;

	public StudentUpdateEntity updateStudentDetail(StudentUpdateEntity studentUpdateEntity) {
		return sURep.save(studentUpdateEntity);
	}

	public List<StudentUpdateEntity> findAll() {
		return sURep.findByStatus(false);
	}

	public StudentUpdateEntity findById(Integer id) {
		Optional<StudentUpdateEntity> optionalEntity = sURep.findById(id);
		return optionalEntity.orElse(null);
	}

	public List<StudentUpdateEntity> findByName(String name) {

		return sURep.findByStudent_NameAndStatus(name, false);
	}

	public List<StudentUpdateEntity> findByStudentIdAndStatus(int id, boolean status) {
		return sURep.findByStudent_IdAndStatus(id, status);
	}

	public List<StudentUpdateEntity> findByIdOrNameOrCourseNameAndStatus(int id, String name, String courseName) {
		return sURep.findByIdOrStudent_NameOrStudent_StudentCourse_Course_CourseNameAndStatus(id, name, courseName,
				false);
	}

	public List<StudentUpdateEntity> findByCourseNameAndStatus(String courseName) {
		return sURep.findByStudent_StudentCourse_Course_CourseNameAndStatus(courseName, false);
	}
}
