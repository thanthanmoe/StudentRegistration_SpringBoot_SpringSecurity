package com.ttm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ttm.entity.UserPhoto;
import com.ttm.repository.UserPhotoRepository;
import com.ttm.service.UserPhotoService;

@SpringBootTest
public class TestUserPhotoService {

	@Mock
	private UserPhotoRepository userPhotoRepository;

	@InjectMocks
	private UserPhotoService userPhotoService;

	@Test
	public void testSave() {
		UserPhoto userPhoto = new UserPhoto();
		userPhoto.setPhoto("photo");

		when(userPhotoRepository.save(any(UserPhoto.class))).thenReturn(userPhoto);

		UserPhoto savedPhoto = userPhotoService.save(userPhoto);

		verify(userPhotoRepository).save(userPhoto);

		assertEquals(userPhoto, savedPhoto);
	}

	// Add more test cases for other methods in the UserPhotoService if needed
}
