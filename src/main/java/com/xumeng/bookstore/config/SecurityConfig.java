package com.xumeng.bookstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.xumeng.bookstore.Service.impl.UserSecurityService;
import com.xumeng.bookstore.utility.SecurityUtility;

/* This is a configuration class, spring will register this configuration */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	private BCryptPasswordEncoder passwordEncoder() {
		return SecurityUtility.passwordEncoder();
	}
	
	private static final String[] PUBLIC_MATCHES = {
			"/css/**",
			"/js/**",
			"/image/**",
			"/",
			"/newUser",
			"/forgetPassword"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		/* For any requests, if it matches PUBLIC_MATCHES, we allow it; otherwise we do authentication */
		http
			.authorizeRequests()
//			.antMatchers("/**")
			.antMatchers(PUBLIC_MATCHES)
			.permitAll().anyRequest().authenticated();
		
		/* disable csrf and cors; check login; login; logout*/
		http
			.csrf().disable().cors().disable()
			.formLogin().failureUrl("/login?error").defaultSuccessUrl("/")
			.loginPage("/login").permitAll()
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/?logout").deleteCookies("remember-me").permitAll()
			.and()
			.rememberMe();
	}
	
	/* encode password to store it in database safely, not the raw password*/
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	}
}
