package com.ttm.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ttm.dto.UserDto;
import com.ttm.repository.UserRepositoryCustom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<UserDto> getFindAllUser() {
		String sqlQuery = "select user.id,user.name,user.email,user.role  from user where status=false";

		Query query = entityManager.createNativeQuery(sqlQuery, "UserReportMapping");
		return query.getResultList();
	}

}
