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

import com.ttm.entity.StudentUpdateEntity;
import com.ttm.repository.StudentUpdateRepository;
import com.ttm.service.StudentUpdateService;

@SpringBootTest
public class TestStudentUpdateService {

	@Mock
	private StudentUpdateRepository studentUpdateRepository;

	@InjectMocks
	private StudentUpdateService studentUpdateService;

	@Test
	public void testUpdateStudentDetail() {
		StudentUpdateEntity studentUpdateEntity = new StudentUpdateEntity();
		studentUpdateEntity.setDetails("Updated details");
		studentUpdateEntity.setUpdateTime("2023-06-29");
		studentUpdateEntity.setStatus(false);

		when(studentUpdateRepository.save(any(StudentUpdateEntity.class))).thenReturn(studentUpdateEntity);

		StudentUpdateEntity updatedStudent = studentUpdateService.updateStudentDetail(studentUpdateEntity);

		verify(studentUpdateRepository).save(studentUpdateEntity);

		assertEquals(studentUpdateEntity, updatedStudent);
	}

	@Test
	public void testFindAll() {
		StudentUpdateEntity studentUpdate1 = new StudentUpdateEntity();
		studentUpdate1.setId(1);
		studentUpdate1.setDetails("Update 1");
		studentUpdate1.setUpdateTime("2023-06-29");
		studentUpdate1.setStatus(false);

		StudentUpdateEntity studentUpdate2 = new StudentUpdateEntity();
		studentUpdate2.setId(2);
		studentUpdate2.setDetails("Update 2");
		studentUpdate2.setUpdateTime("2023-06-30");
		studentUpdate2.setStatus(false);

		List<StudentUpdateEntity> studentUpdates = Arrays.asList(studentUpdate1, studentUpdate2);

		when(studentUpdateRepository.findByStatus(false)).thenReturn(studentUpdates);

		List<StudentUpdateEntity> foundStudentUpdates = studentUpdateService.findAll();

		verify(studentUpdateRepository).findByStatus(false);

		assertEquals(studentUpdates, foundStudentUpdates);
	}

	@Test
	public void testFindById() {
		int studentUpdateId = 1;
		StudentUpdateEntity studentUpdate = new StudentUpdateEntity();
		studentUpdate.setId(studentUpdateId);
		studentUpdate.setDetails("Update 1");
		studentUpdate.setUpdateTime("2023-06-29");
		studentUpdate.setStatus(false);

		when(studentUpdateRepository.findById(studentUpdateId)).thenReturn(Optional.of(studentUpdate));

		StudentUpdateEntity foundStudentUpdate = studentUpdateService.findById(studentUpdateId);

		verify(studentUpdateRepository).findById(studentUpdateId);

		assertEquals(studentUpdate, foundStudentUpdate);
	}

	@Test
	public void testFindByName() {
		String name = "John";

		StudentUpdateEntity studentUpdate1 = new StudentUpdateEntity();
		studentUpdate1.setId(1);
		studentUpdate1.setDetails("Update 1");
		studentUpdate1.setUpdateTime("2023-06-29");
		studentUpdate1.setStatus(false);

		StudentUpdateEntity studentUpdate2 = new StudentUpdateEntity();
		studentUpdate2.setId(2);
		studentUpdate2.setDetails("Update 2");
		studentUpdate2.setUpdateTime("2023-06-30");
		studentUpdate2.setStatus(false);

		List<StudentUpdateEntity> studentUpdates = Arrays.asList(studentUpdate1, studentUpdate2);

		when(studentUpdateRepository.findByStudent_NameAndStatus(name, false)).thenReturn(studentUpdates);

		List<StudentUpdateEntity> foundStudentUpdates = studentUpdateService.findByName(name);

		verify(studentUpdateRepository).findByStudent_NameAndStatus(name, false);

		assertEquals(studentUpdates, foundStudentUpdates);
	}

	@Test
	public void testFindByStudentIdAndStatus() {
		int studentId = 1;
		boolean status = false;

		StudentUpdateEntity studentUpdate1 = new StudentUpdateEntity();
		studentUpdate1.setId(1);
		studentUpdate1.setDetails("Update 1");
		studentUpdate1.setUpdateTime("2023-06-29");
		studentUpdate1.setStatus(false);

		StudentUpdateEntity studentUpdate2 = new StudentUpdateEntity();
		studentUpdate2.setId(2);
		studentUpdate2.setDetails("Update 2");
		studentUpdate2.setUpdateTime("2023-06-30");
		studentUpdate2.setStatus(false);

		List<StudentUpdateEntity> studentUpdates = Arrays.asList(studentUpdate1, studentUpdate2);

		when(studentUpdateRepository.findByStudent_IdAndStatus(studentId, status)).thenReturn(studentUpdates);

		List<StudentUpdateEntity> foundStudentUpdates = studentUpdateService.findByStudentIdAndStatus(studentId,
				status);

		verify(studentUpdateRepository).findByStudent_IdAndStatus(studentId, status);

		assertEquals(studentUpdates, foundStudentUpdates);
	}

	@Test
	public void testFindByIdOrNameOrCourseNameAndStatus() {
		int id = 1;
		String name = "John";
		String courseName = "Math";

		StudentUpdateEntity studentUpdate1 = new StudentUpdateEntity();
		studentUpdate1.setId(1);
		studentUpdate1.setDetails("Update 1");
		studentUpdate1.setUpdateTime("2023-06-29");
		studentUpdate1.setStatus(false);

		StudentUpdateEntity studentUpdate2 = new StudentUpdateEntity();
		studentUpdate2.setId(2);
		studentUpdate2.setDetails("Update 2");
		studentUpdate2.setUpdateTime("2023-06-30");
		studentUpdate2.setStatus(false);

		List<StudentUpdateEntity> studentUpdates = Arrays.asList(studentUpdate1, studentUpdate2);

		when(studentUpdateRepository.findByIdOrStudent_NameOrStudent_StudentCourse_Course_CourseNameAndStatus(id, name,
				courseName, false)).thenReturn(studentUpdates);

		List<StudentUpdateEntity> foundStudentUpdates = studentUpdateService.findByIdOrNameOrCourseNameAndStatus(id,
				name, courseName);

		verify(studentUpdateRepository).findByIdOrStudent_NameOrStudent_StudentCourse_Course_CourseNameAndStatus(id,
				name, courseName, false);

		assertEquals(studentUpdates, foundStudentUpdates);
	}

	@Test
	public void testFindByCourseNameAndStatus() {
		String courseName = "Math";

		StudentUpdateEntity studentUpdate1 = new StudentUpdateEntity();
		studentUpdate1.setId(1);
		studentUpdate1.setDetails("Update 1");
		studentUpdate1.setUpdateTime("2023-06-29");
		studentUpdate1.setStatus(false);

		StudentUpdateEntity studentUpdate2 = new StudentUpdateEntity();
		studentUpdate2.setId(2);
		studentUpdate2.setDetails("Update 2");
		studentUpdate2.setUpdateTime("2023-06-30");
		studentUpdate2.setStatus(false);

		List<StudentUpdateEntity> studentUpdates = Arrays.asList(studentUpdate1, studentUpdate2);

		when(studentUpdateRepository.findByStudent_StudentCourse_Course_CourseNameAndStatus(courseName, false))
				.thenReturn(studentUpdates);

		List<StudentUpdateEntity> foundStudentUpdates = studentUpdateService.findByCourseNameAndStatus(courseName);

		verify(studentUpdateRepository).findByStudent_StudentCourse_Course_CourseNameAndStatus(courseName, false);

		assertEquals(studentUpdates, foundStudentUpdates);
	}

	// Add more test cases for other methods in the StudentUpdateService if needed
}
