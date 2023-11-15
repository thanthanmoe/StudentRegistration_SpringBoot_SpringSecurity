package com.ttm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ttm.entity.UserUpdateEntity;
import com.ttm.repository.UserUpdateRepository;
import com.ttm.service.UserUpdateService;

@SpringBootTest
public class TestUserUpdateService {

	@Mock
	private UserUpdateRepository userUpdateRepository;

	@InjectMocks
	private UserUpdateService userUpdateService;

	@Test
	public void testUserUpdateDetail() {
		UserUpdateEntity userUpdate = new UserUpdateEntity();
		userUpdate.setDetails("details");

		when(userUpdateRepository.save(any(UserUpdateEntity.class))).thenReturn(userUpdate);

		UserUpdateEntity savedUserUpdate = userUpdateService.userUpdateDetail(userUpdate);

		verify(userUpdateRepository).save(userUpdate);

		assertEquals(userUpdate, savedUserUpdate);
	}

	@Test
	public void testFindAll() {
		UserUpdateEntity userUpdate = new UserUpdateEntity();
		List<UserUpdateEntity> userUpdates = Collections.singletonList(userUpdate);

		when(userUpdateRepository.findByStatus(false)).thenReturn(userUpdates);

		List<UserUpdateEntity> result = userUpdateService.findAll();

		verify(userUpdateRepository).findByStatus(false);

		assertEquals(userUpdates, result);
	}

	// Add more test cases for other methods in the UserUpdateService if needed
}
