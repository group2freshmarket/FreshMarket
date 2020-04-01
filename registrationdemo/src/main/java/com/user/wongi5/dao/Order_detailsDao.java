package com.user.wongi5.dao;

import java.util.List;

import com.user.wongi5.model.Order_details;

public interface Order_detailsDao {
	
	boolean addOrder_details(Order_details order);
	List<Order_details> getAllDetails(int id);

}
