package com.ttm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.entity.CourseEntity;
import com.ttm.repository.CourseRepository;

@Service
public class CourseService {
	@Autowired
	CourseRepository cRep;

	public CourseEntity courseCreate(CourseEntity course) {
		return cRep.save(course);
	}

	public CourseEntity findById(Integer id) {
		return cRep.findById(id).get();
	}

	public List<CourseEntity> selectAll() {
		return cRep.findAll();
	}

	public List<CourseEntity> selectStatus() {
		return cRep.findByStatus(false);
	}

	public CourseEntity findByName(String courseName) {
		return cRep.findByCourseName(courseName);
	}

}
