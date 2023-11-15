package com.ttm;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.ttm.entity.Role;
import com.ttm.entity.UserEntity;
import com.ttm.service.UserPhotoService;
import com.ttm.service.UserService;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Transactional
class TestViewController {
	@Autowired
	private MockMvc mockMvc;

	@Mock
	private UserService userService;

	@Mock
	private UserPhotoService userPhotoService;
	@Autowired
	PasswordEncoder encoder;

	@Test
	void testTest() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("LGN001"));
	}

	@Test
	void testRegisterForm() throws Exception {
		mockMvc.perform(get("/users/userCreate")).andExpect(status().isOk()).andExpect(view().name("USR001"))
				.andExpect(model().attributeExists("user"));
	}

	/*
	 * @Test void testRegisterPost() throws Exception { String pass = "123"; //
	 * Create a sample UserEntity UserEntity user = new UserEntity();
	 * user.setName("Sanda"); user.setEmail("sanda@example.com");
	 * user.setPassword(pass); user.setRole(Role.USER);
	 * 
	 * // Set the plain password here
	 * 
	 * // Create a sample MultipartFile MockMultipartFile file = new
	 * MockMultipartFile("file", "test.jpg", "image/jpeg", "test".getBytes());
	 * 
	 * // Mock the findByEmail method to return an empty optional
	 * when(userService.findByEmail(user.getEmail())).thenReturn(Optional.empty());
	 * 
	 * mockMvc.perform(MockMvcRequestBuilders.multipart("/users/userCreate").file(
	 * file).param("name", "Sanda") .param("email",
	 * "sanda@example.com").param("password", pass).param("role", "USER"))
	 * .andExpect(status().is3xxRedirection()); }
	 */

	@Test
	public void testRegisterPost() throws Exception {
		// Create a sample UserEntity
		UserEntity user = new UserEntity();
		user.setName("John1");
		user.setEmail("john1@example.com");
		user.setPassword("password");
		user.setRole(Role.USER);

		// Create a sample MultipartFile
		MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test".getBytes());

		this.mockMvc.perform(post("/users/userCreate").flashAttr("user", user)).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));

	}

}