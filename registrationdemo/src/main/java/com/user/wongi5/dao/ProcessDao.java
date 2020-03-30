package com.user.wongi5.dao;

import com.user.wongi5.model.Order_details;
import com.user.wongi5.model.Purchase_History;

public interface ProcessDao {
	
	boolean addPurchase(Purchase_History purchase);
	Purchase_History getPurchase(int id);
	boolean addOrder_details(Order_details order);
	Order_details getOrder(int id);
	
}
