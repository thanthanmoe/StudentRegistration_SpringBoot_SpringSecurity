package com.ttm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Bean
	public AuthenticationFailureHandler failureHandler() {
		return new CustomAuthenticationFailureHandler();
	}

	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new CustomAuthenticationSuccessHandler();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(
						authorize -> authorize.requestMatchers("/css/*", "/image", "/upload").permitAll())
				.authorizeHttpRequests(
						authorize -> authorize.requestMatchers("/admin/*", "/student/*", "/studentHistory/*")
								.hasAnyAuthority("ADMIN").requestMatchers("/user/**").hasAnyAuthority("ADMIN", "USER")
								.requestMatchers("/", "/users/*").permitAll().anyRequest().authenticated())
				.formLogin(login -> login.loginPage("/login").usernameParameter("email")
						.successHandler(successHandler()).failureHandler(failureHandler()).permitAll())

				.logout(logout -> logout.logoutUrl("/logout") // Customize the logout URL if needed
						.logoutSuccessUrl("/?logout").invalidateHttpSession(true) // Invalidate the HTTP session on
																					// logout
						.deleteCookies("JSESSIONID") // Optionally, delete any relevant cookies
						.permitAll());
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}