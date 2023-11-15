package com.ttm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ttm.dto.StudentReportDto;
import com.ttm.entity.StudentEntity;
import com.ttm.repository.StudentRepository;
import com.ttm.service.StudentService;

@SpringBootTest
public class TestStudentService {

	@Mock
	private StudentRepository studentRepository;

	@InjectMocks
	private StudentService studentService;

	@Test
	public void testStudentCreate() {
		StudentEntity student = new StudentEntity();
		student.setName("John");
		student.setDob("1990-01-01");
		student.setPhone("1234567890");
		student.setEducation("Bachelor's Degree");
		student.setGender("Male");
		student.setStatus(false);

		when(studentRepository.save(any(StudentEntity.class))).thenReturn(student);

		StudentEntity savedStudent = studentService.studentCreate(student);

		verify(studentRepository).save(student);

		assertEquals(student, savedStudent);
	}

	@Test
	public void testSelectAll() {
		StudentEntity student1 = new StudentEntity();
		student1.setId(1);
		student1.setName("John");
		student1.setStatus(false);

		StudentEntity student2 = new StudentEntity();
		student2.setId(2);
		student2.setName("Jane");
		student2.setStatus(false);

		List<StudentEntity> students = Arrays.asList(student1, student2);

		when(studentRepository.findByStatus(false)).thenReturn(students);

		List<StudentEntity> selectedStudents = studentService.selectAll();

		verify(studentRepository).findByStatus(false);

		assertEquals(students, selectedStudents);
	}

	@Test
	public void testDeleteStudent() {
		int studentId = 1;

		studentService.deleteStudent(studentId);

		verify(studentRepository).deleteById(studentId);
	}

	@Test
	public void testFindId() {
		int studentId = 1;
		StudentEntity student = new StudentEntity();
		student.setId(studentId);
		student.setName("John");

		when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

		StudentEntity foundStudent = studentService.findId(studentId);

		verify(studentRepository).findById(studentId);

		assertEquals(student, foundStudent);
	}

	@Test
	public void testFindById() {
		int studentId = 1;
		StudentEntity student = new StudentEntity();
		student.setId(studentId);
		student.setName("John");

		when(studentRepository.findByIdAndStatus(studentId, false)).thenReturn(Optional.of(student));

		Optional<StudentEntity> foundStudent = studentService.findById(studentId);

		verify(studentRepository).findByIdAndStatus(studentId, false);

		assertEquals(Optional.of(student), foundStudent);
	}

	@Test
	public void testFindByIdOrNameOrCourseName() {
		int studentId = 1;
		String name = "John";
		String courseName = "Math";

		StudentEntity student1 = new StudentEntity();
		student1.setId(1);
		student1.setName("John");
		student1.setStatus(false);

		StudentEntity student2 = new StudentEntity();
		student2.setId(2);
		student2.setName("Jane");
		student2.setStatus(false);

		List<StudentEntity> students = Arrays.asList(student1, student2);

		when(studentRepository.findByIdOrNameOrStudentCourse_Course_CourseNameAndStatus(studentId, name, courseName,
				false)).thenReturn(students);

		List<StudentEntity> foundStudents = studentService.findByIdOrNameOrCourseName(studentId, name, courseName);

		verify(studentRepository).findByIdOrNameOrStudentCourse_Course_CourseNameAndStatus(studentId, name, courseName,
				false);

		assertEquals(students, foundStudents);
	}

	@Test
	public void testFindByName() {
		String name = "John";

		StudentEntity student1 = new StudentEntity();
		student1.setId(1);
		student1.setName("John");
		student1.setStatus(false);

		StudentEntity student2 = new StudentEntity();
		student2.setId(2);
		student2.setName("John");
		student2.setStatus(false);

		List<StudentEntity> students = Arrays.asList(student1, student2);

		when(studentRepository.findByNameAndStatus(name, false)).thenReturn(students);

		List<StudentEntity> foundStudents = studentService.findByName(name);

		verify(studentRepository).findByNameAndStatus(name, false);

		assertEquals(students, foundStudents);
	}

	@Test
	public void testFindByCourseName() {
		String courseName = "Math";

		StudentEntity student1 = new StudentEntity();
		student1.setId(1);
		student1.setName("John");
		student1.setStatus(false);

		StudentEntity student2 = new StudentEntity();
		student2.setId(2);
		student2.setName("Jane");
		student2.setStatus(false);

		List<StudentEntity> students = Arrays.asList(student1, student2);

		when(studentRepository.findByStudentCourse_Course_CourseNameAndStatus(courseName, false)).thenReturn(students);

		List<StudentEntity> foundStudents = studentService.findByCourseName(courseName);

		verify(studentRepository).findByStudentCourse_Course_CourseNameAndStatus(courseName, false);

		assertEquals(students, foundStudents);
	}

	@Test
	public void testGetDistinctStudentsWithCourses() {
		List<StudentReportDto> studentReports = Arrays.asList(
				new StudentReportDto(1, "John", "09442071176", "Deploma in IT", "Female", "7/5/3001", "JWD"),
				new StudentReportDto(5, "John", "09442071176", "Deploma in IT", "Female", "7/5/3001", "JWD"));

		when(studentRepository.getDistinctStudentsWithCourses()).thenReturn(studentReports);

		List<StudentReportDto> distinctStudentsWithCourses = studentService.getDistinctStudentsWithCourses();

		verify(studentRepository).getDistinctStudentsWithCourses();

		assertEquals(studentReports, distinctStudentsWithCourses);
	}

	// Add more test cases for other methods in the StudentService if needed
}
