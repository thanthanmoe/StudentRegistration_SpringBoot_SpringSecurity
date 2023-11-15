package com.ttm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ttm.entity.CourseEntity;
import com.ttm.entity.StudentCourseEntity;
import com.ttm.entity.StudentEntity;
import com.ttm.repository.StudentCourseRepository;
import com.ttm.service.StudentCourseService;

@SpringBootTest
public class TestStudentCourseService {

	@Mock
	private StudentCourseRepository studentCourseRepository;

	@InjectMocks
	private StudentCourseService studentCourseService;

	@Test
	public void testSave() {
		StudentEntity student = new StudentEntity();
		student.setId(1);
		student.setName("Moe Moe");
		student.setPhone("09442071178");
		student.setEducation("Deploma in IT");
		student.setGender("Male");
		student.setDob("7/5/2001");
		CourseEntity course = new CourseEntity();
		course.setId(1);
		course.setCourseName("JWD");
		course.setFees(2800);

		StudentCourseEntity studentCourse = new StudentCourseEntity();
		studentCourse.setId(1);
		studentCourse.setStudent(student);
		studentCourse.setCourse(course);

		when(studentCourseRepository.save(any(StudentCourseEntity.class))).thenReturn(studentCourse);

		StudentCourseEntity savedStudentCourse = studentCourseService.save(studentCourse);

		verify(studentCourseRepository).save(studentCourse);

		assertEquals(studentCourse, savedStudentCourse);
	}

	// Add more test cases for other methods in the StudentCourseService if needed
}
