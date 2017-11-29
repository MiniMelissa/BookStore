package com.xumeng.bookstore.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xumeng.bookstore.Service.BookService;
import com.xumeng.bookstore.Service.UserService;
import com.xumeng.bookstore.Service.impl.UserSecurityService;
import com.xumeng.bookstore.domain.Book;
import com.xumeng.bookstore.domain.User;
import com.xumeng.bookstore.domain.security.PasswordResetToken;
import com.xumeng.bookstore.domain.security.Role;
import com.xumeng.bookstore.domain.security.UserRole;
import com.xumeng.bookstore.utility.MailConstructor;
import com.xumeng.bookstore.utility.SecurityUtility;

@Controller
public class HomeController {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailConstructor mailConstructor;
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserSecurityService userSecurityService;
	
	@Autowired
	private BookService bookSerivce;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("classActiveLogin", true);
		return "myAccount";
	}
	
	
	@RequestMapping("/bookshelf")
	public String bookshelf(Model model) {
		List<Book> bookList = bookSerivce.findAll();
		model.addAttribute("bookList", bookList);
		return "bookshelf";
	}
	
	@RequestMapping("/forgetPassword")
	public String forgetPassword(
			HttpServletRequest request,
			@ModelAttribute("email") String userEmail,
			Model model) {	
		model.addAttribute("classActiveForgetPassword", true);
	
		User user = userService.findByEmail(userEmail);
		
		if(user == null) {
			model.addAttribute("emailNotExist", true);
			return "myAccount";
		}
		
		String password = SecurityUtility.randomPassword();
		String encryptedpassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedpassword);
		
		userService.save(user);
		
		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);
		
		String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		
		SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user,password);
		
		mailSender.send(email);
		
		model.addAttribute("forgetPasswordEmailSent", "true");
		
		return "myAccount";
	}
	
	@RequestMapping(value="/newUser", method=RequestMethod.POST)
	public String newUserPost(
			HttpServletRequest request,
			@ModelAttribute("email") String userEmail,
			@ModelAttribute("username") String username,
			Model model) throws Exception{
		model.addAttribute("classActiveNewAccount", true);
		model.addAttribute("email", userEmail);
		model.addAttribute("username", username);
		
		if(userService.findByUsername(username) != null) {
			model.addAttribute("usernameExists",true);
			return "myAccount";
		}
		
		if(userService.findByEmail(userEmail) != null) {
			model.addAttribute("emailExists",true);
			return "myAccount";
		}
		
		User user = new User();
		user.setUsername(username);
		user.setEmail(userEmail);
		
		String password = SecurityUtility.randomPassword();
		String encryptedpassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedpassword);
		
		Role role = new Role();
		role.setRoleId(1);
		role.setName("ROLE_USER");
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user, role));
		userService.createUser(user,  userRoles);
		
		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);
		
		String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		
		SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user,password);
		
		mailSender.send(email);
		
		model.addAttribute("emailSent", "true");
		
		return "myAccount";
		
				
	}
	
	@RequestMapping("/newUser")
	public String newUser(
			Locale locale,
			@RequestParam("token") String token,
			Model model) {
					
		PasswordResetToken passToken = userService.getPasswordResetToken(token);

		if(passToken == null) {
			String message = "Invalid Token";
			model.addAttribute("message",message);
			return "redirect:/badRequest";
		}
		
		User user = passToken.getUser();
		String username = user.getUsername();
		
		UserDetails userDetails = userSecurityService.loadUserByUsername(username);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, 
				userDetails.getPassword(), userDetails.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		model.addAttribute("user", user);
		
		model.addAttribute("classActiveEdit", true);
		return "myProfile";
	}
	

	
}
