package com.user.wongi5.controller;

import java.io.UnsupportedEncodingException;
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
import com.user.wongi5.model.LoginInfo;
import com.user.wongi5.model.Order_details;
import com.user.wongi5.model.Purchase_History;
import com.user.wongi5.model.User;

@Controller
public class HomeController {

	@Autowired
	ItemDao itemDao;
	
	@Autowired
	AuthDao authDao;
	
	@Autowired
	Purchase_HistoryDao purDao;
	
	@Autowired
	Order_detailsDao orderDao;
	
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
	public String showAccount(@ModelAttribute("user") User user, HttpSession session, Model model) {
		
		String email = (String)session.getAttribute("user_email");
		
		User userDetails = authDao.getUser(email);
		
		user.setName(userDetails.getName());
		user.setEmail(userDetails.getEmail());
		user.setPassword(userDetails.getPassword());
		user.setUserType(userDetails.getUserType());
		
		List<Purchase_History> list = purDao.getAllPurchases(email);

		model.addAttribute("purchases", list);
		
		return "account";
	}
	
	@PostMapping("/account")
	public String orderDetailsForm(HttpServletRequest request, HttpSession session) {
		session.setAttribute("review", (String) request.getParameter("select"));
		
		return "redirect:/order_details";
	}
	
	@GetMapping("/order_details")
	public String displayOrderDetails(HttpSession session, Model model) {
		
		String orderId = (String) session.getAttribute("review");
		
		Purchase_History p = purDao.getPurchase(Integer.parseInt(orderId));
		
		model.addAttribute("date", p.getDate());
		
		List<Order_details> o = orderDao.getAllDetails(p.getOrderId());
		
		model.addAttribute("details", o);
	
		List<String> names = new ArrayList<String>();
		
		for(int i = 0; i < o.size(); i++) {
			names.add(itemDao.getItem(o.get(i).getItemId()).getItemName());
		}
		model.addAttribute("names", names);
		
		return "order_details";
	}
	
	
	@PostMapping("/home")
	public String displayData(HttpServletRequest request, HttpSession session) {
		
		//display data
		List<Item> items = itemDao.getItems();
		
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
