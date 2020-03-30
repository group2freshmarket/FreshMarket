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
import com.user.wongi5.model.User;

@Controller
public class ReviewController {

	@Autowired
	ItemDao itemDao;

	@Autowired
	AuthDao authDao;

	@GetMapping("/review")
	public ModelAndView showOrder(@ModelAttribute("user") User user, HttpSession session) {

		User userDetails = authDao.getUser((String) session.getAttribute("user_email"));

		user.setName(userDetails.getName());

		// create view and display

		ModelAndView mv = new ModelAndView("review");

		List<Item> itemList = (List<Item>) session.getAttribute("itemList");
		List<Integer> quantity = (List<Integer>) session.getAttribute("quantity");

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

		mv.addObject("quantityList", quantity);
		
		//calculate totals
		double sub = 0;
		
		
		for(int i = 0; i < itemList.size(); i++) {
			sub += itemList.get(i).getItemPrice() * quantity.get(i);
		}
		
		double tax = sub * 0.12;
		
		session.setAttribute("sub", sub);
		
		mv.addObject("sub", sub);
		mv.addObject("tax", tax);

		return mv;
	}
	
	@PostMapping("/review")
	public String processOrder(HttpSession session, HttpServletRequest request) {
		session.setAttribute("method", request.getParameter("method"));
		session.setAttribute("time", request.getParameter("time"));
		
		return "redirect:/process";
	}

	@GetMapping("/process")
	public ModelAndView display(@ModelAttribute("user") User user, HttpSession session) {
		ModelAndView model = new ModelAndView("process");
		
		User userDetails = authDao.getUser((String) session.getAttribute("user_email"));
		
		String method = (String) session.getAttribute("method");
		
		model.addObject("user", userDetails.getName());
		model.addObject("time", session.getAttribute("time"));
		model.addObject("method", method);
		
		double total = (Double) session.getAttribute("sub");
		double discount = 0;
		
		if(method.equals("pickup")) {
			discount = total*0.1;
			total = total - discount;
		}
		
		model.addObject("discount", discount);
		model.addObject("total", total);
		
		
		return model;
	}
	
	@PostMapping("/process")
	public String processOrder() {
		
		
		return "process-success";
	}
	
}
