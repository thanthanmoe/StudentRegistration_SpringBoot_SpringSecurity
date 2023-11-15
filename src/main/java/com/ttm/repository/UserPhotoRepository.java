package com.ttm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttm.entity.UserPhoto;

@Repository
public interface UserPhotoRepository extends JpaRepository<UserPhoto, Integer> {

}
