package com.xumeng.bookstore.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

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
import com.xumeng.bookstore.Service.UserPaymentService;
import com.xumeng.bookstore.Service.UserService;
import com.xumeng.bookstore.Service.impl.UserSecurityService;
import com.xumeng.bookstore.domain.Book;
import com.xumeng.bookstore.domain.User;
import com.xumeng.bookstore.domain.UserBilling;
import com.xumeng.bookstore.domain.UserPayment;
import com.xumeng.bookstore.domain.UserShipping;
import com.xumeng.bookstore.domain.security.PasswordResetToken;
import com.xumeng.bookstore.domain.security.Role;
import com.xumeng.bookstore.domain.security.UserRole;
import com.xumeng.bookstore.utility.MailConstructor;
import com.xumeng.bookstore.utility.SecurityUtility;
import com.xumeng.bookstore.utility.USConstants;

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
	
	@Autowired
	private UserPaymentService userPaymentService;

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

	@RequestMapping("/bookDetail")
	public String bookDetail(@PathParam("id") Long id, Model model, Principal principal) {
		if (principal != null) {
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
		}

		Book book = bookSerivce.findOne(id);

		model.addAttribute("book", book);
		List<Integer> qtyList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		model.addAttribute("qtyList", qtyList);
		model.addAttribute("qty", 1);
		return "bookDetail";
	}

	@RequestMapping("/forgetPassword")
	public String forgetPassword(HttpServletRequest request, @ModelAttribute("email") String userEmail, Model model) {
		model.addAttribute("classActiveForgetPassword", true);

		User user = userService.findByEmail(userEmail);

		if (user == null) {
			model.addAttribute("emailNotExist", true);
			return "myAccount";
		}

		String password = SecurityUtility.randomPassword();
		String encryptedpassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedpassword);

		userService.save(user);

		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);

		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

		SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user,
				password);

		mailSender.send(email);

		model.addAttribute("forgetPasswordEmailSent", "true");

		return "myAccount";
	}
	
	@RequestMapping("/myProfile")
	public String myProfile(Model model, Principal principal) {
		
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
//		model.addAllAttributes("orderList", user.getOrderList());
		
		UserShipping userShipping =  new UserShipping();
		model.addAttribute("userShipping", userShipping);
		
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("listOfShippingAddresses", true);
		
		List<String> stateList = USConstants.listOfUSStatesCode;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		model.addAttribute("classAcriveEdit", true);
		
		return "myProfile";
		
		
	}
	
	
	@RequestMapping("/listOfCreditCards")
	public String listOfCreditCards(Model model, Principal principal, HttpServletRequest request) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
//		model.addAttribute("orderList", user.orderList());
		
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);
		
		return "myProfile";
	}
	
	@RequestMapping("/listOfShippingAddresses")
	public String listOfShippingAddresses(Model model, Principal principal, HttpServletRequest request) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
//		model.addAttribute("orderList", user.orderList());
		
		model.addAttribute("listOfCreditsCards", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);
		
		return "myProfile";
	}
	
	@RequestMapping("/addNewCreditCard")
	public String addNewCreditCard(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("addNewCreditCard", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);

		
		UserBilling userBilling = new UserBilling();
		UserPayment userPayment = new UserPayment();
		
		model.addAttribute("userBilling", userBilling);
		model.addAttribute("userPayment", userPayment);
		
		List<String> stateList = USConstants.listOfUSStatesCode;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
//		model.addAttribute("orderList", user.orderList());

		return "myProfile";
	}
	
	@RequestMapping(value="/addNewCreditCard", method=RequestMethod.POST)
	public String addNewCreditCardPost(
			@ModelAttribute("userPayment") UserPayment userPayment,
			@ModelAttribute("userBilling") UserBilling userBilling,
			Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		userService.updateUserBilling(userBilling, userPayment, user);
		
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("listOfCreditsCards", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);

		return "myProfile";
	}
	
	@RequestMapping("/updateCreditCard")
	public String updateCreditCard(@ModelAttribute("id") Long creditCardId, Principal principal, Model model) {
		
//		Retrieve the current login user's id 
		User user = userService.findByUsername(principal.getName());
//		Retrieve the credit card's user id through credit card id
		UserPayment userPayment = userPaymentService.findById(creditCardId);
		
		if(user.getId() != userPayment.getUser().getId()) {
			return "badRequestPage";
		}else {
			model.addAttribute("user", user);
			UserBilling userBilling = userPayment.getUserBilling();
			model.addAttribute("userPayment", userPayment);
			model.addAttribute("userBilling", userBilling);
			
			List<String> stateList = USConstants.listOfUSStatesCode;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);
			
			model.addAttribute("addNewCreditCard", true);
			model.addAttribute("classActiveBilling", true);
			model.addAttribute("listOfShippingAddresses", true);
			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			
			return "myProfile";
			
		}
	}
	
	@RequestMapping("/addNewShippingAddress")
	public String addNewShippingAddress(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("addNewShippingAddress", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfCreditsCards", true);

		UserShipping userShipping = new UserShipping();
		
		
		model.addAttribute("userShipping", userShipping);
		
		List<String> stateList = USConstants.listOfUSStatesCode;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
//		model.addAttribute("orderList", user.orderList());

		return "myProfile";
	}
	

	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
	public String newUserPost(HttpServletRequest request, @ModelAttribute("email") String userEmail,
			@ModelAttribute("username") String username, Model model) throws Exception {
		model.addAttribute("classActiveNewAccount", true);
		model.addAttribute("email", userEmail);
		model.addAttribute("username", username);

		if (userService.findByUsername(username) != null) {
			model.addAttribute("usernameExists", true);
			return "myAccount";
		}

		if (userService.findByEmail(userEmail) != null) {
			model.addAttribute("emailExists", true);
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
		userService.createUser(user, userRoles);

		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);

		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

		SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user,
				password);

		mailSender.send(email);

		model.addAttribute("emailSent", "true");

		return "myAccount";

	}

	@RequestMapping("/newUser")
	public String newUser(Locale locale, @RequestParam("token") String token, Model model) {

		PasswordResetToken passToken = userService.getPasswordResetToken(token);

		if (passToken == null) {
			String message = "Invalid Token";
			model.addAttribute("message", message);
			return "redirect:/badRequest";
		}

		User user = passToken.getUser();
		String username = user.getUsername();

		UserDetails userDetails = userSecurityService.loadUserByUsername(username);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		model.addAttribute("user", user);

		model.addAttribute("classActiveEdit", true);
		return "myProfile";
	}

}
