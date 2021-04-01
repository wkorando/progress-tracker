package com.bk.progresstracker;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Configuration class for spring security
 * 
 * @author bkorando
 *
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Allow access to only /actuator/health endpoint, and disallow access to other endpoints.
	 */
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/actuator/health").permitAll()
				.antMatchers(HttpMethod.GET, "/actuator/**").authenticated().anyRequest().permitAll();
	}
}