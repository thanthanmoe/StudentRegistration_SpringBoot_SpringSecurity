package com.ttm.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ttm.entity.CourseEntity;
import com.ttm.entity.StudentCourseEntity;
import com.ttm.entity.StudentEntity;
import com.ttm.entity.StudentPhoto;
import com.ttm.entity.UserEntity;
import com.ttm.entity.UserPhoto;
import com.ttm.entity.UserUpdateEntity;
import com.ttm.service.CourseService;
import com.ttm.service.StudentCourseService;
import com.ttm.service.StudentPhotoService;
import com.ttm.service.StudentService;
import com.ttm.service.UserPhotoService;
import com.ttm.service.UserService;
import com.ttm.service.UserUpdateService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	StudentService sService;
	@Autowired
	UserService uService;
	@Autowired
	CourseService cService;
	@Autowired
	StudentPhotoService spService;
	@Autowired
	UserPhotoService upService;
	@Autowired
	StudentCourseService scService;
	@Autowired
	UserUpdateService userUpdateService;

	@GetMapping("/userView")
	public String view(ModelMap model, HttpSession session) {
		int LoginUserId = (int) session.getAttribute("LoginUserId");
		Optional<UserEntity> user = uService.findById(LoginUserId);
		session.setAttribute("LoginName", user.get().getName());
		session.setAttribute("LoginRole", user.get().getRole());
		session.setAttribute("LoginImage", user.get().getUserPhoto().getPhoto());
		return "MNU001";
	}

	@GetMapping("/student")
	public ModelAndView student(ModelMap model) {
		List<CourseEntity> courses = cService.selectStatus();
		model.addAttribute("courses", courses);
		System.out.println(courses);
		return new ModelAndView("STU001", "student", new StudentEntity());
	}

	@PostMapping("/studentCreate")
	public String studentCreate(@ModelAttribute("student") StudentEntity student,
			@RequestParam(name = "courseName", required = false) String[] courseName,
			@RequestParam(name = "file", required = false) MultipartFile file) throws IOException {

		if (file != null && !file.isEmpty()) {
			if (!file.equals(null)) {
				String uploadPath = "D:\\OJT_Batch9\\StudentRegistration\\src\\main\\resources\\static\\upload"
						+ File.separator;
				String fileName = file.getOriginalFilename();
				Path path = Paths.get(uploadPath + fileName);
				Files.write(path, file.getBytes());

				try (OutputStream outputStream = Files.newOutputStream(path)) {
					outputStream.write(file.getBytes());
				}
				sService.studentCreate(student);

				StudentPhoto photo = new StudentPhoto();
				photo.setStudent(student);
				photo.setPhoto(fileName);
				spService.save(photo);
				student.setStudentPhoto(photo);
			}
		}

		if (courseName != null && courseName.length > 0) {
			for (String course : courseName) {
				CourseEntity courses = cService.findByName(course);
				StudentCourseEntity studentCourse = new StudentCourseEntity();
				studentCourse.setCourse(courses);
				studentCourse.setStudent(student);
				scService.save(studentCourse);
			}
		}

		return "STU001";
	}

	@GetMapping("/allStudent")
	public String selectAll(ModelMap model) {
		List<StudentEntity> students = sService.selectAll();
		model.addAttribute("students", students);
		return "STU002";
	}

	@GetMapping("/allUser")
	public String allUser(ModelMap model, HttpSession session) {
		int loginId = (int) session.getAttribute("LoginUserId");
		Optional<UserEntity> user = uService.findById(loginId);
		session.setAttribute("LoginRole", user.get().getRole());
		if (user.get().getRole().name().equals("USER")) {
			model.addAttribute("users", user.get());
		} else if (user.get().getRole().name().equals("ADMIN")) {
			List<UserEntity> users = uService.findAll();
			model.addAttribute("users", users);
		}

		return "USR002";
	}

	@PostMapping("/userSearch")
	public String searchUsers(@RequestParam(value = "id", required = false) String idStr,
			@RequestParam(value = "name", required = false) String name, ModelMap model) {
		if ((idStr == null || idStr.isEmpty()) && (name == null || name.isEmpty())) {
			List<UserEntity> users = uService.findAll();
			model.addAttribute("users", users);
			model.addAttribute("error", "Please provide ID or name");
		} else if (idStr != null && !idStr.isEmpty() && name != null && !name.isEmpty()) {
			int id = Integer.parseInt(idStr);
			List<UserEntity> users = uService.findByIdOrName(id, name);
			if (!users.isEmpty()) {
				model.addAttribute("users", users);
			} else {
				List<UserEntity> user = uService.findAll();
				model.addAttribute("users", user);
				model.addAttribute("error", "User not found!");
			}
		} else if (idStr != null && !idStr.isEmpty()) {
			int id = Integer.parseInt(idStr);
			Optional<UserEntity> user = uService.findById(id);
			if (!user.isPresent()) {
				model.addAttribute("error", "User not found!");
				List<UserEntity> users = uService.findAll();
				model.addAttribute("users", users);
			} else {
				List<UserEntity> users = new ArrayList<>();
				users.add(user.get());
				model.addAttribute("users", users);
			}
		} else if (name != null && !name.isEmpty()) {
			Optional<UserEntity> users = uService.findByName(name);
			if (users.isPresent()) {
				model.addAttribute("users", users.get());
			} else {
				List<UserEntity> user = uService.findAll();
				model.addAttribute("users", user);
				model.addAttribute("error", "User not found!");
			}
		}
		return "USR002";
	}

	@GetMapping("/updateUser/{id}")
	public String updateUser(@PathVariable("id") int id, ModelMap model) {

		UserEntity users = uService.userbyId(id);
		model.addAttribute("users", users);
		return "USR003";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute("users") UserEntity users,
			@RequestParam(name = "file", required = false) MultipartFile file, HttpSession session) throws IOException {

		UserEntity usr = uService.userbyId(users.getId());
		if (!usr.getName().equals(users.getName())) {
			session.removeAttribute("LoginName");
			session.setAttribute("LoginName", users.getName());
		}

		if (file != null && !file.isEmpty()) {
			if (!file.equals(null)) {
				String uploadPath = "D:\\OJT_Batch9\\StudentRegistration\\src\\main\\resources\\static\\upload"
						+ File.separator;
				String fileName = file.getOriginalFilename();
				Path path = Paths.get(uploadPath + fileName);
				Files.write(path, file.getBytes());

				try (OutputStream outputStream = Files.newOutputStream(path)) {
					outputStream.write(file.getBytes());
				}
				session.removeAttribute("LoginImage");
				session.setAttribute("LoginImage", fileName);
				UserPhoto photo = new UserPhoto();
				photo.setUser(users);
				photo.setPhoto(fileName);

				upService.save(photo);

				users.setUserPhoto(photo);
			}

		}
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm");
		LocalDateTime dateTime = LocalDateTime.now();
		String formattedTime = dateTime.format(timeFormatter);
		uService.userCreate(users);

		UserUpdateEntity us = new UserUpdateEntity();
		us.setUser(users);
		us.setDetails("update");
		us.setUpdateTime(formattedTime);
		userUpdateService.userUpdateDetail(us);
		return "USR003";

	}

	@GetMapping("/allUserHistory")
	public String selectAll(ModelMap model, HttpSession session) {
		int loginId = (int) session.getAttribute("LoginUserId");
		Optional<UserEntity> user = uService.findById(loginId);
		session.setAttribute("LoginRole", user.get().getRole());
		if (user.get().getRole().name().equals("USER")) {
			List<UserUpdateEntity> users = userUpdateService.findByUser_Id(loginId);
			model.addAttribute("users", users);
		} else if (user.get().getRole().name().equals("ADMIN")) {
			List<UserUpdateEntity> users = userUpdateService.findAll();
			model.addAttribute("users", users);
		}
		return "USRUH001";
	}

	@PostMapping("/userHistorySearch")
	public String allUserHistory(@RequestParam(value = "id", required = false) String idStr,
			@RequestParam(value = "name", required = false) String name, ModelMap model) {

		if ((idStr == null || idStr.isEmpty()) && (name == null || name.isEmpty())) {
			List<UserUpdateEntity> users = userUpdateService.findAll();
			model.addAttribute("users", users);
			model.addAttribute("error", "Please provide ID or name");
		} else if (idStr != null && !idStr.isEmpty() && name != null && !name.isEmpty()) {
			int id = Integer.parseInt(idStr);
			List<UserUpdateEntity> user = userUpdateService.findByUser_IdOrUser_Name(id, name);
			if (!user.isEmpty()) {
				model.addAttribute("users", user);
			} else {
				List<UserUpdateEntity> users = userUpdateService.findAll();
				model.addAttribute("users", users);
				model.addAttribute("error", "User not found!");
			}
		} else if (idStr != null && !idStr.isEmpty()) {
			int id = Integer.parseInt(idStr);
			List<UserUpdateEntity> user = userUpdateService.findByUser_Id(id);
			if (!user.isEmpty()) {
				model.addAttribute("users", user);
			} else {
				List<UserUpdateEntity> users = userUpdateService.findAll();
				model.addAttribute("users", users);
				model.addAttribute("error", "User not found!");
			}
		} else if (name != null && !name.isEmpty()) {
			List<UserUpdateEntity> user = userUpdateService.findByUser_Name(name);
			if (!user.isEmpty()) {
				model.addAttribute("users", user);
			} else {
				List<UserUpdateEntity> users = userUpdateService.findAll();
				model.addAttribute("users", users);
				model.addAttribute("error", "User not found!");
			}
		}
		return "USRUH001";
	}

	@GetMapping("/deleteUserHistory/{id}")
	public String deleteHistory(@PathVariable("id") int id) {
		UserUpdateEntity user = userUpdateService.byId(id);
		user.setStatus(true);
		userUpdateService.userUpdateDetail(user);

		return "redirect:/user/allUserHistory";
	}

}
