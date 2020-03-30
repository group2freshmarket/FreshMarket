package com.user.wongi5.model;

public class Order_details {
	private int detailId;
	private int itemId;
	private int orderId;
	private int itemQty;
	private double itemPrice;
	
	public int getDetailId() {
		return detailId;
	}
	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getItemQty() {
		return itemQty;
	}
	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}
	public double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	@Override
	public String toString() {
		return "Order_details [detailId=" + detailId + ", itemId=" + itemId + ", orderId=" + orderId + ", itemQty="
				+ itemQty + ", itemPrice=" + itemPrice + "]";
	}

	
	
	
	
	
	
	
}
