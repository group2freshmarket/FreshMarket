package com.user.wongi5.model;

public class Purchase_History {

	private int orderId;
	private String date;
	private String email;
	private double total_Price;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getTotal_Price() {
		return total_Price;
	}
	public void setTotal_Price(double total_Price) {
		this.total_Price = total_Price;
	}
	@Override
	public String toString() {
		return "Purchase_History [orderId=" + orderId + ", date=" + date + ", email=" + email + ", total_Price="
				+ total_Price + "]";
	}
	

	
	
}
