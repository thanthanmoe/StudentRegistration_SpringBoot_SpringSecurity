package com.ttm.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ttm.entity.UserEntity;
import com.ttm.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	public UserDetailService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Logger logger = LoggerFactory.getLogger(UserDetailService.class);
		logger.info("login");
		UserEntity user = userRepository.findByEmailAndStatus(email, false)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

		return new UserDetail(user);
	}
}
