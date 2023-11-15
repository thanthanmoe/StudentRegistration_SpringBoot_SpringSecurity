package com.ttm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.ttm.controller.StudentController;
import com.ttm.service.StudentCourseService;
import com.ttm.service.StudentPhotoService;
import com.ttm.service.StudentService;
import com.ttm.service.StudentUpdateService;

public class TestStudentController {

	@MockBean
	private StudentService studentService;

	@MockBean
	private StudentPhotoService studentPhotoService;

	@MockBean
	private StudentCourseService studentCourseService;

	@MockBean
	private StudentUpdateService studetntUpdateService;
	@Autowired
	private StudentController studentController;

	/*
	 * @Test public void testStudentSearch() throws Exception { ModelMap model = new
	 * ModelMap();
	 * 
	 * String courseName = "JWD";
	 * 
	 * CourseEntity course = new CourseEntity(); course.setCourseName(courseName);
	 * 
	 * List<StudentCourseEntity> courses = new ArrayList<>();
	 * 
	 * StudentCourseEntity studentCourse = new StudentCourseEntity();
	 * studentCourse.setCourse(course); courses.add(studentCourse);
	 * 
	 * List<StudentEntity> students = new ArrayList<>(); StudentEntity student = new
	 * StudentEntity(); student.setStudentCourse(courses); students.add(student);
	 * 
	 * when(studentService.findByCourseName("JWD")).thenReturn(students);
	 * 
	 * String result = studentController.studentSearch(null, null, courseName,
	 * model);
	 * 
	 * assertEquals("STU002", result); assertTrue(model.containsKey("students"));
	 * assertFalse(model.containsKey("searchError"));
	 * 
	 * verify(studentService).findByCourseName("JWD"); }
	 */
}
