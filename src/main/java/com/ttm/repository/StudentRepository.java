package com.ttm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttm.entity.StudentEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer>, StudentRepositoryCustom {
	List<StudentEntity> findByStatus(Boolean status);

	List<StudentEntity> findByNameAndStatus(String name, Boolean status);

	Optional<StudentEntity> findByIdAndStatus(int id, Boolean status);

	List<StudentEntity> findByIdOrNameOrStudentCourse_Course_CourseNameAndStatus(int id, String name, String courseName,
			Boolean status);

	List<StudentEntity> findByStudentCourse_Course_CourseNameAndStatus(String courseName, Boolean status);

}
