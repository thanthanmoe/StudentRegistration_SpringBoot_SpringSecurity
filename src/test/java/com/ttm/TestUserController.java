package com.ttm;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ModelMap;

import com.ttm.config.CustomAuthenticationFailureHandler;
import com.ttm.controller.UserController;
import com.ttm.entity.Role;
import com.ttm.entity.UserEntity;
import com.ttm.entity.UserUpdateEntity;
import com.ttm.service.CourseService;
import com.ttm.service.StudentCourseService;
import com.ttm.service.StudentPhotoService;
import com.ttm.service.StudentService;
import com.ttm.service.UserPhotoService;
import com.ttm.service.UserService;
import com.ttm.service.UserUpdateService;

@SpringBootTest
@AutoConfigureMockMvc
public class TestUserController {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserController userController;
	@MockBean
	private StudentService studentService;

	@MockBean
	private UserService userService;

	@MockBean
	private CourseService courseService;

	@MockBean
	private StudentPhotoService studentPhotoService;

	@MockBean
	private UserPhotoService userPhotoService;

	@MockBean
	private StudentCourseService studentCourseService;

	@MockBean
	private UserUpdateService userUpdateService;

	@MockBean
	private CustomAuthenticationFailureHandler authenticationSuccessHandler;

	@Test
	public void testView() throws Exception {
		int loginUserId = 2;
		UserEntity user = new UserEntity();
		user.setId(loginUserId);
		user.setName("Moe Moe");
		user.setEmail("moemoe@gmail.com");
		user.setPassword("123");
		user.setRole(Role.USER);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute("LoginUserId", loginUserId);
		session.setAttribute("LoginName", user.getName());
		session.setAttribute("LoginRole", user.getRole().name());

		mockMvc.perform(get("/user/userView").session(session)).andExpect(status().is3xxRedirection())
				.andExpect(status().isFound()) // Update the assertion for the status code
				.andExpect(redirectedUrl("http://localhost/login"));
	}

	@Test
	public void testSearchUsersIdOrName() throws Exception {
		ModelMap model = mock(ModelMap.class);
		String id = "1";
		String name = "Ma Ma";

		List<UserEntity> users = new ArrayList<>();
		UserEntity user = new UserEntity();
		user.setId(1);
		user.setName("Ma Ma");
		users.add(user);

		when(userService.findByIdOrName(1, "Ma Ma")).thenReturn(users);
		String result = userController.searchUsers(id, name, (ModelMap) model);

		assertEquals("USR002", result);
		verify(model).addAttribute("users", users);
		verify(model, never()).addAttribute("searchError", "User not found!");

		verify(userService).findByIdOrName(1, "Ma Ma");
	}

	@Test
	public void testSearchUserHistory() throws Exception {
		ModelMap model = mock(ModelMap.class);
		String name = "Ma Ma";

		List<UserUpdateEntity> users = new ArrayList<>();
		UserEntity user = new UserEntity();
		user.setName("Ma Ma");

		UserUpdateEntity usr = new UserUpdateEntity();
		usr.setUser(user);

		users.add(usr);

		when(userUpdateService.findByUser_Name("Ma Ma")).thenReturn(users);
		String result = userController.allUserHistory(null, name, (ModelMap) model);

		assertEquals("USRUH001", result);
		verify(model).addAttribute("users", users);
		verify(model, never()).addAttribute("searchError", "User not found!");

		verify(userUpdateService).findByUser_Name("Ma Ma");
	}
}