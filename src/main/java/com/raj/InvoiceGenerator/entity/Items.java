package com.raj.InvoiceGenerator.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "items")
public class Items {

	@Id
	@GenericGenerator(name="itemId" , strategy="increment")
	@GeneratedValue(generator="itemId")
	private int id;
	private int itemNum;
	private String itemName;
	private int itemPrice;
	private int itemQty;
	private int subTotal;
	
	@OneToOne
	private Bills bill;

	public Items() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Items(int itemNum, String itemName, int itemPrice, int itemQty) {
		super();
		this.itemNum = itemNum;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.itemQty = itemQty;
	}

	public int getitemNum() {
		return itemNum;
	}

	public void setitemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}

	public int getItemQty() {
		return itemQty;
	}

	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}

	public int getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(int subTotal) {
		this.subTotal = subTotal;
	}

	public Bills getBill() {
		return bill;
	}

	public void setBill(Bills bill) {
		this.bill = bill;
	}

	@Override
	public String toString() {
		return "Items [id=" + id + ", itemNum=" + itemNum + ", itemName=" + itemName + ", itemPrice=" + itemPrice
				+ ", itemQty=" + itemQty + ", subTotal=" + subTotal + ", bill=" + bill + "]";
	}

}
