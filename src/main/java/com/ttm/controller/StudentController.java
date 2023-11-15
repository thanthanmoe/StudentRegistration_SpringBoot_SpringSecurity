package com.ttm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ttm.dto.StudentReportDto;
import com.ttm.entity.CourseEntity;
import com.ttm.entity.StudentCourseEntity;
import com.ttm.entity.StudentEntity;
import com.ttm.entity.StudentPhoto;
import com.ttm.entity.StudentUpdateEntity;
import com.ttm.service.CourseService;
import com.ttm.service.StudentCourseService;
import com.ttm.service.StudentPhotoService;
import com.ttm.service.StudentService;
import com.ttm.service.StudentUpdateService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletResponse;
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
@RequestMapping("/student")
public class StudentController {
	@Autowired
	StudentService sService;
	@Autowired
	CourseService cService;
	@Autowired
	StudentPhotoService spService;
	@Autowired
	StudentCourseService scService;
	@Autowired
	StudentUpdateService studentUpdateService;

	@PostMapping("/studentUpdate")
	public String studentUpdate(@ModelAttribute("student") StudentEntity student, @RequestParam("pid") int id,
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

				StudentPhoto photo = new StudentPhoto();
				photo.setId(id);
				photo.setStudent(student);
				photo.setPhoto(fileName);
				spService.save(photo);
				student.setStudentPhoto(photo);

			}

		}
		sService.studentCreate(student);
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm");
		LocalDateTime dateTime = LocalDateTime.now();
		String formattedTime = dateTime.format(timeFormatter);

		StudentUpdateEntity students = new StudentUpdateEntity();
		students.setStudent(student);
		students.setDetails("update");
		students.setUpdateTime(formattedTime);
		studentUpdateService.updateStudentDetail(students);
		if (courseName != null && courseName.length > 0) {
			for (String course : courseName) {
				CourseEntity courses = cService.findByName(course);
				StudentCourseEntity studentCourse = new StudentCourseEntity();
				studentCourse.setCourse(courses);
				studentCourse.setStudent(student);
				scService.save(studentCourse);
			}
		}

		return "redirect:/student/allStudent";
	}

	@GetMapping("/studentUpdate/{id}")
	public ModelAndView studentUpdate(@PathVariable("id") Integer id, ModelMap model) {
		StudentEntity student = sService.findId(id);
		model.addAttribute("student", student);
		List<CourseEntity> courses = cService.selectStatus();
		model.addAttribute("courses", courses);

		return new ModelAndView("STU003", "student", sService.findId(id));
	}

	@GetMapping("/deleteStudent/{id}")
	public String deleteStudent(@PathVariable("id") int id) {
		StudentEntity student = sService.findId(id);
		student.setStatus(true);
		sService.studentCreate(student);
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm");
		LocalDateTime dateTime = LocalDateTime.now();
		String formattedTime = dateTime.format(timeFormatter);

		StudentUpdateEntity students = new StudentUpdateEntity();
		students.setStudent(student);
		students.setDetails("delete");
		students.setUpdateTime(formattedTime);
		studentUpdateService.updateStudentDetail(students);

		return "redirect:/student/allStudent";
	}

	@PostMapping("/studentSearch")
	public String studentSearch(@RequestParam(value = "id", required = false) String idStr,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "courseName", required = false) String courseName, ModelMap model) {
		if ((idStr == null || idStr.isEmpty()) && (name == null || name.isEmpty())) {
			List<StudentEntity> students = sService.selectAll();
			model.addAttribute("students", students);
			model.addAttribute("error", "Please provide ID or name");
		}
		if (idStr != null && !idStr.isEmpty() && name != null && !name.isEmpty() && courseName != null
				&& !courseName.isEmpty()) {
			int id = Integer.parseInt(idStr);
			List<StudentEntity> students = sService.findByIdOrNameOrCourseName(id, name, courseName);
			if (!students.isEmpty()) {
				model.addAttribute("students", students);
			} else {
				List<StudentEntity> student = sService.selectAll();
				model.addAttribute("students", student);
				model.addAttribute("error", "Student not found!");
			}
		}
		if (idStr != null && !idStr.isEmpty()) {
			try {
				int id = Integer.parseInt(idStr);
				Optional<StudentEntity> student = sService.findById(id);
				if (student.isPresent()) {
					model.addAttribute("students", student.get());
				} else {
					model.addAttribute("error", "Student not found!");
					List<StudentEntity> students = sService.selectAll();
					model.addAttribute("students", students);
				}
			} catch (NumberFormatException e) {
				model.addAttribute("error", "Invalid ID format!");
				List<StudentEntity> students = sService.selectAll();
				model.addAttribute("students", students);
			}
		} else if (name != null && !name.isEmpty()) {
			List<StudentEntity> students = sService.findByName(name);
			if (!students.isEmpty()) {
				model.addAttribute("students", students);
			} else {
				model.addAttribute("error", "Student not found!");
				List<StudentEntity> student = sService.selectAll();
				model.addAttribute("students", student);
			}

		} else if (courseName != null && !courseName.isEmpty()) {
			List<StudentEntity> students = sService.findByCourseName(courseName);
			if (!students.isEmpty()) {
				model.addAttribute("students", students);
			} else {
				List<StudentEntity> student = sService.selectAll();
				model.addAttribute("students", student);
				model.addAttribute("error", "Student not found!");
			}
		}
		return "STU002";
	}

	@PersistenceContext
	private EntityManager entityManager;

	@GetMapping("/student/{format}")
	public void downloadReport(@PathVariable("format") String format, HttpServletResponse response)
			throws JRException, IOException {
		String path = "C:\\Users\\User\\Downloads\\student." + format;

		List<StudentReportDto> reportData = sService.getDistinctStudentsWithCourses();
		File file = ResourceUtils.getFile("classpath:students.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportData);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("createdBy", "Students Table");

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

		response.setHeader("Content-Disposition", "attachment; filename=student." + format);

		try (InputStream inputStream = new FileInputStream(path);
				OutputStream outputStream = response.getOutputStream()) {
			IOUtils.copy(inputStream, outputStream);
		}
	}

}
