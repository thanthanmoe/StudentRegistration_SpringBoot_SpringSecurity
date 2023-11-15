package com.ttm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ttm.dto.UserDto;
import com.ttm.entity.UserEntity;
import com.ttm.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository uRep;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	public List<UserEntity> findAll() {
		return uRep.findByStatus(false);
	}

	public List<UserDto> findAllUser() {
		return uRep.getFindAllUser();
	}

	public boolean userDelete(Integer id) {
		uRep.deleteById(id);
		return true;
	}

	public UserEntity userbyId(Integer id) {
		return uRep.findById(id).get();
	}

	public Optional<UserEntity> findById(Integer id) {
		return uRep.findById(id);
	}

	public Optional<UserEntity> findByName(String name) {
		return uRep.findByName(name);
	}

	public Optional<UserEntity> findByEmail(String email) {
		return uRep.findByEmailAndStatus(email, false);
	}

	public List<UserEntity> findByIdOrName(Integer id, String name) {
		return uRep.findByIdOrName(id, name);
	}

	public List<UserEntity> findByNameAndEmail(String name, String email) {
		return uRep.findByNameAndEmail(name, email);
	}

	public Optional<UserEntity> findByNameAndPassword(String name, String password) {
		return uRep.findByNameAndPassword(name, password);
	}

	public UserEntity userCreate(UserEntity user) {
		return uRep.save(user);
	}

}
