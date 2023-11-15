package com.ttm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.entity.UserPhoto;
import com.ttm.repository.UserPhotoRepository;

@Service
public class UserPhotoService {
	@Autowired
	UserPhotoRepository upRep;

	public UserPhoto save(UserPhoto photo) {
		return upRep.save(photo);
	}

}
