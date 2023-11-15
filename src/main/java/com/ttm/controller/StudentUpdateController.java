package com.ttm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ttm.entity.StudentUpdateEntity;
import com.ttm.service.StudentUpdateService;
import com.ttm.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/studentHistory")
public class StudentUpdateController {
	@Autowired
	StudentUpdateService studentUpdateService;
	@Autowired
	UserService userService;

	@GetMapping("/allStudentHistory")
	public String selectAll(ModelMap model, HttpSession session) {

		List<StudentUpdateEntity> students = studentUpdateService.findAll();
		model.addAttribute("students", students);

		return "STUH001";
	}

	@PostMapping("/studentSearch")
	public String studentSearch(@RequestParam(value = "id", required = false) String idStr,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "courseName", required = false) String courseName, ModelMap model) {
		if ((idStr == null || idStr.isEmpty()) && (name == null || name.isEmpty())) {
			List<StudentUpdateEntity> students = studentUpdateService.findAll();
			model.addAttribute("students", students);
			model.addAttribute("error", "Please provide ID or name");
		} else if (idStr != null && !idStr.isEmpty() && name != null && !name.isEmpty() && courseName != null
				&& !courseName.isEmpty()) {
			int id = Integer.parseInt(idStr);
			List<StudentUpdateEntity> students = studentUpdateService.findByIdOrNameOrCourseNameAndStatus(id, name,
					courseName);
			if (!students.isEmpty()) {
				model.addAttribute("students", students);
			} else {
				List<StudentUpdateEntity> student = studentUpdateService.findAll();
				model.addAttribute("students", student);
				model.addAttribute("error", "Student not found!");
			}
		} else if (idStr != null && !idStr.isEmpty()) {
			int id = Integer.parseInt(idStr);
			StudentUpdateEntity students = studentUpdateService.findById(id);
			if (students != null) {
				model.addAttribute("students", students);
			} else {
				model.addAttribute("error", "Student not found!");
				List<StudentUpdateEntity> student = studentUpdateService.findAll();
				model.addAttribute("students", student);
			}
		} else if (name != null && !name.isEmpty()) {
			List<StudentUpdateEntity> students = studentUpdateService.findByName(name);
			if (!students.isEmpty()) {
				model.addAttribute("students", students);
			} else {
				model.addAttribute("error", "Student not found!");
				List<StudentUpdateEntity> student = studentUpdateService.findAll();
				model.addAttribute("students", student);
			}

		} else if (courseName != null && !courseName.isEmpty()) {
			List<StudentUpdateEntity> students = studentUpdateService.findByCourseNameAndStatus(courseName);
			if (!students.isEmpty()) {
				model.addAttribute("students", students);
			} else {
				List<StudentUpdateEntity> student = studentUpdateService.findAll();
				model.addAttribute("students", student);
				model.addAttribute("error", "Student not found!");
			}
		}
		return "STU002";
	}

	@GetMapping("/studentHistoryDelete/{id}")
	public String deleteHistory(@PathVariable("id") int id) {
		StudentUpdateEntity student = studentUpdateService.findById(id);
		student.setStatus(true);
		studentUpdateService.updateStudentDetail(student);

		return "redirect:/studentHistory/allStudentHistory";
	}

}
