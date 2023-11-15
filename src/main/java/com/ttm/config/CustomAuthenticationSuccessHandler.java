package com.ttm.config;

import java.io.IOException;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		Set<String> role = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		UserDetail userDetails = (UserDetail) authentication.getPrincipal();
		HttpSession session = request.getSession();
		session.setAttribute("LoginUserId", userDetails.getId());

		session.setAttribute("LoginUserDetails", userDetails);
		if (role.contains("ADMIN")) {
			response.sendRedirect("/admin/adminView");
		} else {

			response.sendRedirect("/user/userView");
		}
	}
}
