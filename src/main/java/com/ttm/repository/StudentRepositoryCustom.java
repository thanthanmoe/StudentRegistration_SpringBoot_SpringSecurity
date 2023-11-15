package com.ttm.repository;

import java.util.List;

import com.ttm.dto.StudentReportDto;

public interface StudentRepositoryCustom {
	List<StudentReportDto> getDistinctStudentsWithCourses();
}
