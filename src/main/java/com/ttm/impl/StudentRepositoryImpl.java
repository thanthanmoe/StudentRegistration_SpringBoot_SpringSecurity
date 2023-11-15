package com.ttm.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ttm.dto.StudentReportDto;
import com.ttm.repository.StudentRepositoryCustom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class StudentRepositoryImpl implements StudentRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<StudentReportDto> getDistinctStudentsWithCourses() {
		String sqlQuery = "SELECT s.id, s.name, s.phone, s.education, s.gender, s.dob, GROUP_CONCAT(c.course_name SEPARATOR ', ') AS course_names "
				+ "FROM student s " + "INNER JOIN student_course sc ON s.id = sc.student_id "
				+ "INNER JOIN course c ON sc.course_id = c.id "
				+ "GROUP BY s.id, s.name, s.phone, s.education, s.gender, s.dob";

		Query query = entityManager.createNativeQuery(sqlQuery, "StudentReportMapping");
		return query.getResultList();
	}

}