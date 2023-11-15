package com.ttm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttm.entity.CourseEntity;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {
	public CourseEntity findByCourseName(String courseName);

	public List<CourseEntity> findByStatus(Boolean status);
}
