package com.ttm;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ttm.controller.AdminController;
import com.ttm.entity.CourseEntity;
import com.ttm.entity.Role;
import com.ttm.entity.UserEntity;
import com.ttm.service.CourseService;
import com.ttm.service.UserService;
import com.ttm.service.UserUpdateService;

@SpringBootTest
@AutoConfigureMockMvc
public class TestAdminController {
	@Autowired
	AdminController adminController;
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CourseService courseService;

	@MockBean
	private UserService userService;

	@MockBean
	private UserUpdateService userUpdateService;

	@Test
	public void testView() throws Exception {
		int loginUserId = 1;
		UserEntity user = new UserEntity();
		user.setId(loginUserId);
		user.setName("Ma Ma");
		user.setEmail("mama@gmail.com");
		user.setPassword("123");
		user.setRole(Role.ADMIN);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute("LoginUserId", loginUserId);
		session.setAttribute("LoginName", user.getName());
		session.setAttribute("LoginRole", user.getRole().name());

		mockMvc.perform(get("/admin/adminView").session(session)).andExpect(status().is3xxRedirection())
				.andExpect(status().isFound()) // Update the assertion for the status code
				.andExpect(redirectedUrl("http://localhost/login"));
	}

	@Test
	public void testCourse() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

		// Perform the request and assert the response
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/course")).andExpect(status().isOk())
				.andExpect(view().name("BUD003")).andExpect(model().attributeExists("course"));

	}

	@Test
	public void testCreateCourse() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

		// Create a mock CourseEntity object
		CourseEntity course = new CourseEntity();
		// Set the necessary properties of the course object

		// Perform the request and assert the response
		mockMvc.perform(MockMvcRequestBuilders.post("/admin/courseCreate").flashAttr("course", course))
				.andExpect(status().isOk()).andExpect(view().name("BUD003"))
				.andExpect(model().attribute("success", "Successfully"));

		// Verify that the courseCreate method of the cService is called with the
		// correct argument
		// Replace "cService" with the actual instance of your CourseService class
		verify(courseService, times(1)).courseCreate(course);
	}

	@Test
	public void testSelectAllCourse() throws Exception {
		this.mockMvc.perform(get("/admin/allCourse")).andExpect(status().is3xxRedirection()) // Check for redirection
				.andExpect(redirectedUrl("http://localhost/login"));
	}

	@Test
	public void testDownloadReport() throws Exception {

		String format = "pdf";

		mockMvc.perform(get("/admin/report/{format}", format)).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));

	}

	@Test
	public void testDeleteUser() throws Exception {
		int userId = 1;
		mockMvc.perform(get("/admin/deleteUser/{id}", userId)).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));

	}

}