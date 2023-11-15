package com.ttm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.entity.StudentCourseEntity;
import com.ttm.repository.StudentCourseRepository;

@Service
public class StudentCourseService {
	@Autowired
	StudentCourseRepository scRep;

	public StudentCourseEntity save(StudentCourseEntity student) {
		return scRep.save(student);
	}

}
