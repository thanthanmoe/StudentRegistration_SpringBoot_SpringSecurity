package com.ttm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.entity.StudentPhoto;
import com.ttm.repository.StudentPhotoRepository;

@Service
public class StudentPhotoService {
	@Autowired
	StudentPhotoRepository spRep;

	public StudentPhoto save(StudentPhoto photo) {
		return spRep.save(photo);
	}

	public StudentPhoto findByStudent_Id(int i) {
		return spRep.findByStudent_Id(i);
	}
}
