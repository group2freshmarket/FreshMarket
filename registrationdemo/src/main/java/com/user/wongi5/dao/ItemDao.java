package com.user.wongi5.dao;


import java.util.List;

import com.user.wongi5.model.Item;

public interface ItemDao {

	boolean addItem(Item item);
	boolean updateItem(Item item);
	boolean deleteItem(int itemId);
	Item getItem(int itemId);
	List<Item> getItems();	
	
}
