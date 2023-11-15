package com.ttm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttm.entity.StudentUpdateEntity;

@Repository

public interface StudentUpdateRepository extends JpaRepository<StudentUpdateEntity, Integer> {
	List<StudentUpdateEntity> findByStatus(Boolean status);

	List<StudentUpdateEntity> findByStudent_IdAndStatus(int id, boolean status);

	List<StudentUpdateEntity> findByStudent_NameAndStatus(String name, boolean status);

	List<StudentUpdateEntity> findByIdOrStudent_NameOrStudent_StudentCourse_Course_CourseNameAndStatus(int id,
			String name, String courseName, Boolean status);

	List<StudentUpdateEntity> findByStudent_StudentCourse_Course_CourseNameAndStatus(String courseName, Boolean status);
}
