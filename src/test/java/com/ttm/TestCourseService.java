package com.ttm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.ttm.entity.CourseEntity;
import com.ttm.repository.CourseRepository;
import com.ttm.service.CourseService;

@SpringBootTest
class TestCourseService {
	@Autowired
	private CourseService courseService;

	@MockBean
	private CourseRepository courseRepository;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCourseCreate() {
		// Create a sample CourseEntity
		CourseEntity course = new CourseEntity();
		course.setId(1);
		course.setCourseName("Math");
		course.setFees(100);
		course.setStatus(false);

		// Mock the save method of the CourseRepository
		when(courseRepository.save(any(CourseEntity.class))).thenReturn(course);

		// Call the courseCreate method of the CourseService
		CourseEntity createdCourse = courseService.courseCreate(course);

		// Verify that the save method was called with the correct parameter
		verify(courseRepository).save(course);

		// Assert that the returned CourseEntity matches the expected one
		assertEquals(course, createdCourse);
	}

	@Test
	void testFindById() {
		// Create a sample CourseEntity
		CourseEntity course = new CourseEntity();
		course.setId(1);
		course.setCourseName("Math");
		course.setFees(100);
		course.setStatus(false);

		// Mock the findById method of the CourseRepository
		when(courseRepository.findById(1)).thenReturn(Optional.of(course));

		// Call the findById method of the CourseService
		CourseEntity foundCourse = courseService.findById(1);

		// Verify that the findById method was called with the correct parameter
		verify(courseRepository).findById(1);

		// Assert that the returned CourseEntity matches the expected one
		assertEquals(course, foundCourse);
	}

	@Test
	void testSelectAll() {
		// Create a sample list of CourseEntity
		List<CourseEntity> courses = new ArrayList<>();
		CourseEntity course1 = new CourseEntity();
		course1.setId(1);
		course1.setCourseName("Math");
		course1.setFees(100);
		course1.setStatus(false);
		CourseEntity course2 = new CourseEntity();
		course2.setId(2);
		course2.setCourseName("Science");
		course2.setFees(150);
		course2.setStatus(true);
		courses.add(course1);
		courses.add(course2);

		// Mock the findAll method of the CourseRepository
		when(courseRepository.findAll()).thenReturn(courses);

		// Call the selectAll method of the CourseService
		List<CourseEntity> allCourses = courseService.selectAll();

		// Verify that the findAll method was called
		verify(courseRepository).findAll();

		// Assert that the returned list of CourseEntity matches the expected one
		assertEquals(courses, allCourses);
	}

	@Test
	void testSelectStatus() {
		// Create a sample list of CourseEntity
		List<CourseEntity> activeCourses = new ArrayList<>();
		CourseEntity course1 = new CourseEntity();
		course1.setId(1);
		course1.setCourseName("Math");
		course1.setFees(100);
		course1.setStatus(false);
		CourseEntity course2 = new CourseEntity();
		course2.setId(2);
		course2.setCourseName("Science");
		course2.setFees(150);
		course2.setStatus(true);
		activeCourses.add(course1);

		// Mock the findByStatus method of the CourseRepository
		when(courseRepository.findByStatus(false)).thenReturn(activeCourses);

		// Call the selectStatus method of the CourseService
		List<CourseEntity> statusCourses = courseService.selectStatus();

		// Verify that the findByStatus method was called with the correct parameter
		verify(courseRepository).findByStatus(false);

		// Assert that the returned list of CourseEntity matches the expected one
		assertEquals(activeCourses, statusCourses);
	}

	@Test
	void testFindByName() {
		// Create a sample CourseEntity
		CourseEntity course = new CourseEntity();
		course.setId(1);
		course.setCourseName("Math");
		course.setFees(100);
		course.setStatus(false);

		// Mock the findByCourseName method of the CourseRepository
		when(courseRepository.findByCourseName("Math")).thenReturn(course);

		// Call the findByName method of the CourseService
		CourseEntity foundCourse = courseService.findByName("Math");

		// Verify that the findByCourseName method was called with the correct parameter
		verify(courseRepository).findByCourseName("Math");

		// Assert that the returned CourseEntity matches the expected one
		assertEquals(course, foundCourse);
	}

}
