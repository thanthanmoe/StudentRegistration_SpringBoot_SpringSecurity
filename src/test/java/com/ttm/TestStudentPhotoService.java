package com.ttm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ttm.entity.StudentPhoto;
import com.ttm.repository.StudentPhotoRepository;
import com.ttm.service.StudentPhotoService;

@SpringBootTest
public class TestStudentPhotoService {

	@Mock
	private StudentPhotoRepository studentPhotoRepository;

	@InjectMocks
	private StudentPhotoService studentPhotoService;

	@Test
	public void testSave() {
		StudentPhoto studentPhoto = new StudentPhoto();
		studentPhoto.setPhoto("photo.jpg");

		when(studentPhotoRepository.save(any(StudentPhoto.class))).thenReturn(studentPhoto);

		StudentPhoto savedStudentPhoto = studentPhotoService.save(studentPhoto);

		verify(studentPhotoRepository).save(studentPhoto);

		assertEquals(studentPhoto, savedStudentPhoto);
	}

	@Test
	public void testFindByStudent_Id() {
		int studentId = 1;
		StudentPhoto studentPhoto = new StudentPhoto();
		studentPhoto.setId(1);
		studentPhoto.setPhoto("photo.jpg");

		when(studentPhotoRepository.findByStudent_Id(studentId)).thenReturn(studentPhoto);

		StudentPhoto foundStudentPhoto = studentPhotoService.findByStudent_Id(studentId);

		verify(studentPhotoRepository).findByStudent_Id(studentId);

		assertEquals(studentPhoto, foundStudentPhoto);
	}

	// Add more test cases for other methods in the StudentPhotoService if needed
}
