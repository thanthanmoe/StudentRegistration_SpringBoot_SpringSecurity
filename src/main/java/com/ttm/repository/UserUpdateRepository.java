package com.ttm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ttm.entity.UserUpdateEntity;

public interface UserUpdateRepository extends JpaRepository<UserUpdateEntity, Integer> {
	List<UserUpdateEntity> findByStatus(Boolean status);

	List<UserUpdateEntity> findByUser_IdAndStatus(int id, boolean status);

	List<UserUpdateEntity> findByUser_NameAndStatus(String name, Boolean status);

	List<UserUpdateEntity> findByUser_IdOrUser_NameAndStatus(int id, String name, Boolean status);

}
