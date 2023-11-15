package com.ttm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttm.entity.StudentPhoto;

@Repository
public interface StudentPhotoRepository extends JpaRepository<StudentPhoto, Integer> {
	StudentPhoto findByStudent_Id(int id);
}
