package com.user.wongi5.controller;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import org.apache.commons.codec.binary.Base64;

import com.user.wongi5.dao.ItemDao;
import com.user.wongi5.model.Item;

@Controller
public class ItemController {
	
	@Autowired
	ItemDao itemDao;
	
	
	@ModelAttribute("item")
	public Item itemForm() {
		return new Item();
	}

	@GetMapping("/items")
	public ModelAndView itemView() {
	    //String user_email = (String) session.getAttribute("user_email");
		ModelAndView mv=new ModelAndView("items");
		List<Item> itemList=null;
		itemList=itemDao.getItems();
		
		List<String> imageList = new ArrayList<String>();
		for(Item i:itemList)
		{
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
		mv.addObject("imageList",imageList);
//		if(itemList!=null)
//		{
		mv.addObject("itemList",itemList);		
//		}else {
//		}
		return mv;
	    
	}
	
	//@RequestParam("itemId") int itemId,
	//@RequestMapping("/saveItem")
	@PostMapping("/saveItem")
	public ModelAndView addItems(@RequestParam("itemName") String itemName,@RequestParam("itemType") String  itemType,@RequestParam("itemImage") MultipartFile itemImage,@RequestParam("itemCount") int itemCount,
			@RequestParam("itemPrice") double itemPrice,@RequestParam("itemDesc") String itemDesc)
	
	{
		ModelAndView mv=new ModelAndView("items");
		try {
			Item item=addData(itemName,itemType, itemCount, itemPrice, itemImage, itemDesc);	
			boolean status=itemDao.addItem(item);
			System.out.println("Save Item Status "+status);			
			mv=itemView();
			mv.addObject("item_status","Added");
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return mv;
	}
	
	@RequestMapping("/updateItem")
	//@GetMapping("/updateItems")
	public ModelAndView updateItem(@RequestParam("itemId") int id)
	{
		ModelAndView mv=new ModelAndView("items");
		try {
			
			 Item item=itemDao.getItem(id);
					mv.addObject("item", item);
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return mv;
	}
	@RequestMapping("/showItems")
	//@PostMapping("/showItems")
	public ModelAndView showItems()
	{
		ModelAndView mv=new ModelAndView("items");
		try {
			
			List<Item> itemList=(List<Item>) itemDao.getItems();
			mv.addObject("items",itemList);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return mv;
	}
// @RequestMapping("/deleteItem")
   @GetMapping("/deleteItem")
	public ModelAndView deleteItems(@RequestParam("itemId") int id)
	{
		ModelAndView mv=new ModelAndView("items");
		try {
			
			boolean status=itemDao.deleteItem(id);
			System.out.println("Deleted item : Status "+status);
			mv=itemView();
	      	}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return mv;
	}
	
	public Item addData(String itemName,String itemType, int itemCount,double itemPrice, MultipartFile itemImage, String itemDesc)
	{

		Item item=new Item();
		try {
		//item.setItemId(itemId);
		item.setItemName(itemName);
		item.setItemType(itemType);
		item.setItemCount(itemCount);
		item.setItemImage(itemImage.getBytes());
		item.setItemPrice(itemPrice);
		item.setItemDesc(itemDesc);	
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return item;
	}

}
