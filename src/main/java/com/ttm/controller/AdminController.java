package com.ttm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ttm.dto.UserDto;
import com.ttm.entity.CourseEntity;
import com.ttm.entity.UserEntity;
import com.ttm.entity.UserUpdateEntity;
import com.ttm.service.CourseService;
import com.ttm.service.UserService;
import com.ttm.service.UserUpdateService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	CourseService cService;
	@Autowired
	UserService uService;
	@Autowired
	UserUpdateService upService;

	@GetMapping("/adminView")
	public String view(ModelMap model, HttpSession session) {
		int LoginUserId = (int) session.getAttribute("LoginUserId");
		session.setAttribute("loginId", LoginUserId);

		Optional<UserEntity> user = uService.findById(LoginUserId);
		session.setAttribute("LoginName", user.get().getName());
		session.setAttribute("LoginRole", user.get().getRole());
		session.setAttribute("LoginImage", user.get().getUserPhoto().getPhoto());
		if (user.get().getRole().name().equals("USER")) {
			session.setAttribute("u", "user");
		} else {
			session.setAttribute("a", "admin");
		}
		session.setAttribute("LoginAccount", user);
		session.setAttribute("LoginName", user.get().getName());
		session.setAttribute("LoginRole", user.get().getRole());

		return "MNU001";
	}

	@GetMapping("/course")
	public ModelAndView course() {
		return new ModelAndView("BUD003", "course", new CourseEntity());
	}

	@PostMapping("/courseCreate")
	public String createCourse(@ModelAttribute("course") CourseEntity course, ModelMap model) {
		cService.courseCreate(course);
		model.addAttribute("success", "Successfully");
		return "BUD003";
	}

	@GetMapping("/allCourse")
	public String selectAllCourse(ModelMap model) {
		List<CourseEntity> courses = cService.selectAll();
		model.addAttribute("courses", courses);
		return "BUD002";
	}

	@GetMapping("/deleteCourse/{id}")
	public String deleteCourse(@PathVariable("id") int id) {
		CourseEntity course = cService.findById(id);
		System.out.println(course.getStatus());
		if (course != null) {
			if (course.getStatus() == false) {
				course.setStatus(true);
			} else {
				course.setStatus(false);
			}
			cService.courseCreate(course);
		}
		return "redirect:/admin/allCourse";
	}

	@GetMapping("/courseFees/{id}")
	public ModelAndView updateCourse(@PathVariable("id") int id) {

		return new ModelAndView("BUD003", "course", cService.findById(id));
	}

	@GetMapping("/report/{format}")
	public void downloadReport(@PathVariable("format") String format, HttpServletResponse response)
			throws JRException, IOException {
		String path = "C:\\Users\\User\\Downloads\\user." + format;
		List<UserDto> users = uService.findAllUser();

		File file = ResourceUtils.getFile("classpath:users.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(users);
		Map<String, Object> parameters = new HashMap<>();

		parameters.put("createdBy", "Users Table");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

		if (format.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint, path);
			response.setContentType("text/html");
		} else if (format.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint, path);
			response.setContentType("application/pdf");
		} else if (format.equalsIgnoreCase("xlsx")) {
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new File(path)));
			exporter.exportReport();

			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		}

		response.setHeader("Content-Disposition", "attachment; filename=user." + format);

		try (InputStream inputStream = new FileInputStream(path);
				OutputStream outputStream = response.getOutputStream()) {
			IOUtils.copy(inputStream, outputStream);
		}

	}

	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") int id) {
		UserEntity users = uService.userbyId(id);
		users.setStatus(true);
		uService.userCreate(users);

		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm");
		LocalDateTime dateTime = LocalDateTime.now();
		String formattedTime = dateTime.format(timeFormatter);

		uService.userCreate(users);
		UserUpdateEntity us = new UserUpdateEntity();
		us.setUser(users);
		us.setDetails("delete");
		us.setUpdateTime(formattedTime);
		upService.userUpdateDetail(us);

		System.out.println(id);

		return "redirect:/user/allUser";
	}

}
