package com.ttm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.entity.UserUpdateEntity;
import com.ttm.repository.UserUpdateRepository;

@Service
public class UserUpdateService {
	@Autowired
	UserUpdateRepository upRep;

	public UserUpdateEntity userUpdateDetail(UserUpdateEntity users) {
		return upRep.save(users);
	}

	public List<UserUpdateEntity> findAll() {
		return upRep.findByStatus(false);
	}

	public UserUpdateEntity byId(Integer id) {
		return upRep.findById(id).get();
	}

	public List<UserUpdateEntity> findByUser_Id(Integer id) {
		return upRep.findByUser_IdAndStatus(id, false);
	}

	public List<UserUpdateEntity> findByUser_Name(String name) {
		return upRep.findByUser_NameAndStatus(name, false);
	}

	public List<UserUpdateEntity> findByUser_IdOrUser_Name(int id, String name) {
		return upRep.findByUser_IdOrUser_NameAndStatus(id, name, false);
	}

}
