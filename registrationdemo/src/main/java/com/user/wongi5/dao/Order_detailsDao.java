package com.user.wongi5.dao;

import com.user.wongi5.model.Order_details;

public interface Order_detailsDao {
	
	boolean addOrder_details(Order_details order);
	Order_details getOrder(int id);

}
