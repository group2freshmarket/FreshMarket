package com.user.wongi5.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.user.wongi5.dao.AuthDao;
import com.user.wongi5.dao.ItemDao;
import com.user.wongi5.model.Item;
import com.user.wongi5.model.LoginInfo;
import com.user.wongi5.model.User;

@Controller
public class HomeController {

	@Autowired
	ItemDao itemDao;
	
	@Autowired
	AuthDao authDao;
	
	@ModelAttribute("loginInfo")
	public LoginInfo loginForm() {
		return new LoginInfo();
	}
	
	//Shows available items
	@GetMapping("/home")
	public ModelAndView showItems() {
		ModelAndView mv = new ModelAndView("home");
		List<Item> itemList = null;
		itemList = itemDao.getItems();

		List<String> imageList = new ArrayList<String>();
		for (Item i : itemList) {
			byte[] encodeBase64 = Base64.encodeBase64(i.getItemImage());
			String base64Encoded;
			try {
				base64Encoded = new String(encodeBase64, "UTF-8");
				imageList.add(base64Encoded);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("entering........il");
		}
		mv.addObject("imageList", imageList);

		mv.addObject("itemList", itemList);

		return mv;
	}
	
	//Send to logout from link in nav bar
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		String user = (String) session.getAttribute("user_email");
		
		if(user != null)
			session.removeAttribute("user_email");
		
		return "logout";
	}
	
	//Navigates to account page and displays user information
	@GetMapping("/account")
	public String showUser(@ModelAttribute("user") User user, HttpSession session) {
		
		User userDetails = authDao.getUser((String)session.getAttribute("user_email"));
		
		user.setName(userDetails.getName());
		user.setEmail(userDetails.getEmail());
		user.setPassword(userDetails.getPassword());
		user.setUserType(userDetails.getUserType());
		
		return "account";
	}
	
	@PostMapping("/home")
	public String displayData(HttpServletRequest request, HttpSession session) {
		
		//display data
		List<Item> items = null;
		items = itemDao.getItems();
		
		List<Item> itemList = new ArrayList<Item>();
		List<Integer> quantity = new ArrayList<Integer>();
		
		//gathering data for display
		for(int i = 0; i < items.size(); i++) {
			int id = items.get(i).getItemId();
			String check = request.getParameter(Integer.toString(id));
			
			if(check != null) {
				String qty = request.getParameter("count"+id);
				itemList.add(itemDao.getItem(id));
				quantity.add(Integer.parseInt(qty));
			}
		}
		
		session.setAttribute("itemList", itemList);
		session.setAttribute("quantity", quantity);
		
		return "redirect:/review";
	}
}
