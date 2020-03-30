package com.user.wongi5.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.user.wongi5.dao.AuthDao;
import com.user.wongi5.model.LoginInfo;

@Controller
@SessionAttributes("user_email")
public class LoginController {
	
	@Autowired
	AuthDao authDao;
	
	
	/**
	 * Create new signUpForm object for empty from
	 * 
	 * @return
	 */
	@ModelAttribute("loginInfo")
	public LoginInfo loginForm() {
		return new LoginInfo();
	}

	/**
	 * Method to show the initial HTML form
	 * 
	 * @return
	 */
	@GetMapping("/login")
	public String login(HttpSession session) {
	    String user_email = (String) session.getAttribute("user_email");

		System.out.println("Entering.."+user_email);
	    if(user_email != null) {
	    	return "redirect:/home";
	    }
	    return "login";
	}

	@PostMapping("/login") 
	public String login(@ModelAttribute("loginInfo") LoginInfo loginInfo, HttpSession session){
		boolean status=authDao.checkUser(loginInfo.getUserType(), loginInfo.getEmail(), loginInfo.getPassword());
		session.setAttribute("message", "Login Fail");
		System.out.println("status : "+status);
		if(status==true) {
			session.setAttribute("user_email", loginInfo.getEmail());
			session.setAttribute("message", "Login Successful");
 
			if(loginInfo.getUserType().equalsIgnoreCase("customer")) {
				return "redirect:/home";
			} else {
				return "redirect:/items";
			}
		}
		return "login";
	}
	
}
