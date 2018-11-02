package com.jpmorgan.salesmessage.bean;

public class SalesBean {

	private String prodType;
	private int quantity;
	private double price;
	private String operation;
	private double priceAdjust;
	
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public double getPriceAdjust() {
		return priceAdjust;
	}
	public void setPriceAdjust(double priceAdjust) {
		this.priceAdjust = priceAdjust;
	}
	
	
}
