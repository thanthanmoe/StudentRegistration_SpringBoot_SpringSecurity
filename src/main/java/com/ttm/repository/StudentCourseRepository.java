package com.ttm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttm.entity.StudentCourseEntity;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourseEntity, Integer> {

}
