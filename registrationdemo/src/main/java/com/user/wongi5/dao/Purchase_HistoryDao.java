package com.user.wongi5.dao;

import com.user.wongi5.model.Purchase_History;

public interface Purchase_HistoryDao {
	
	boolean addPurchase(Purchase_History purchase);
	Purchase_History getPurchase(String email, String date);
	
}
