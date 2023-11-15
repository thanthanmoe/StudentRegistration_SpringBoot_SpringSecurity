package com.ttm.repository;

import java.util.List;

import com.ttm.dto.UserDto;

public interface UserRepositoryCustom {
	List<UserDto> getFindAllUser();
}
