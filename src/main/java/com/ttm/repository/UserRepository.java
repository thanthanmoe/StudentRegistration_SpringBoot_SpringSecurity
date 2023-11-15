package com.ttm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttm.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>, UserRepositoryCustom {
	UserEntity findById(int id);

	List<UserEntity> findByStatus(Boolean status);

	Optional<UserEntity> findByName(String name);

	Optional<UserEntity> findByEmailAndStatus(String email, Boolean status);

	List<UserEntity> findByIdOrName(int id, String name);

	List<UserEntity> findByNameAndEmail(String name, String email);

	Optional<UserEntity> findByNameAndPassword(String name, String password);
}
