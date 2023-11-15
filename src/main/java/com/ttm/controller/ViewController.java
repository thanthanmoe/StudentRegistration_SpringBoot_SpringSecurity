package com.ttm.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ttm.entity.UserEntity;
import com.ttm.entity.UserPhoto;
import com.ttm.repository.UserRepository;
import com.ttm.service.UserPhotoService;
import com.ttm.service.UserService;

@Controller

public class ViewController {
	@Autowired
	UserService uService;
	@Autowired
	UserPhotoService upService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder encoder;

	@GetMapping("/")
	public String test() {
		return "LGN001";
	}

	@GetMapping("/users/userCreate")
	public String registerForm(Model model) {
		model.addAttribute("user", new UserEntity());
		return "USR001";
	}

	@PostMapping("/users/userCreate")
	public String registerPost(@ModelAttribute("user") UserEntity entity,
			@RequestParam(name = "file", required = false) MultipartFile file, ModelMap model) throws IOException {

		Optional<UserEntity> users = uService.findByEmail(entity.getEmail());

		if (users.isPresent()) {
			model.addAttribute("error", "This account is already created");
			return "redirect:/users/userCreate";
		} else {
			UserEntity entity2 = new UserEntity();
			entity2.setName(entity.getName());
			entity2.setEmail(entity.getEmail());
			entity2.setPassword(encoder.encode(entity.getPassword()));
			entity2.setRole(entity.getRole());

			uService.userCreate(entity2);

			if (file != null && !file.isEmpty()) {
				String uploadPath = "D:\\OJT_Batch9\\StudentRegistration\\src\\main\\resources\\static\\upload"
						+ File.separator;
				String fileName = file.getOriginalFilename();
				Path path = Paths.get(uploadPath + fileName);
				Files.write(path, file.getBytes());

				try (OutputStream outputStream = Files.newOutputStream(path)) {
					outputStream.write(file.getBytes());
				}

				UserPhoto photo = new UserPhoto();
				photo.setUser(entity2);
				photo.setPhoto(fileName);
				upService.save(photo);
			}

			model.addAttribute("success", "Successfully Registered. Please Sign In");
			return "redirect:/";
		}
	}

}
