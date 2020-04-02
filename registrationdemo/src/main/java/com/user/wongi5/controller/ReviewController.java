package com.user.wongi5.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.user.wongi5.dao.AuthDao;
import com.user.wongi5.dao.ItemDao;
import com.user.wongi5.dao.Order_detailsDao;
import com.user.wongi5.dao.Purchase_HistoryDao;
import com.user.wongi5.model.Item;
import com.user.wongi5.model.Order_details;
import com.user.wongi5.model.Purchase_History;
import com.user.wongi5.model.User;

@Controller
public class ReviewController {

	@Autowired
	ItemDao itemDao;

	@Autowired
	AuthDao authDao;
	
	@Autowired
	Purchase_HistoryDao purchaseDao;
	
	@Autowired
	Order_detailsDao orderDao;

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
	
	//Sets data to session and redirects
	@PostMapping("/review")
	public String processOrder(HttpSession session, HttpServletRequest request) {
		session.setAttribute("method", request.getParameter("method"));
		session.setAttribute("time", request.getParameter("time"));
		
		return "redirect:/process";
	}

	//Gathers info about cart and displays all details for user to review before entering payment
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
		
		session.removeAttribute("sub");
		session.setAttribute("total", total);
		
		model.addObject("discount", discount);
		model.addObject("total", total);
		
		
		return model;
	}
	
	//Processing order form
	@PostMapping("/process")
	public String processOrder(HttpServletRequest request, HttpSession session, Model model) {
		//Create instance of purchase and set all values check if added properly
		Purchase_History p = new Purchase_History();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		Date date = new Date();
		
		p.setDate(format.format(date));
		p.setEmail((String) session.getAttribute("user_email"));
		p.setTotal_Price((Double) session.getAttribute("total"));
		
		boolean statusPurchase = purchaseDao.addPurchase(p);
		
		if(statusPurchase == true) {
			//find id of purchase history
			//Get all lists of cart to loop through and input into order_details
			Purchase_History p1 = new Purchase_History();
			p1 = purchaseDao.getPurchase((String) session.getAttribute("user_email"), format.format(date));
			
			int orderId = p1.getOrderId();
			
			List<Item> items = (List<Item>) session.getAttribute("itemList");
			List<Integer> quantity = (List<Integer>) session.getAttribute("quantity");
			
			boolean status = true;
			
			for(int i = 0; i < items.size(); i++) {
				if(status) {
					Order_details o = new Order_details();
					
					o.setItemId(items.get(i).getItemId());
					o.setOrderId(orderId);
					o.setItemQty(quantity.get(i));
					o.setItemPrice(items.get(i).getItemPrice());
					
					status = orderDao.addOrder_details(o);
				} else 
					break;
			}
			
			if(status) {
				model.addAttribute("message", "Order Successful!");
			} else {
				model.addAttribute("message", "Please try again.");
			}
		} else {
			model.addAttribute("message", "Please try again.");
		}
		
		return "process-success";
	}
	
}
