package com.raj.InvoiceGenerator.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Bills {

	@Id
	@GenericGenerator(name="billId" , strategy="increment")
	@GeneratedValue(generator="billId")
	private int billId;
	
	private LocalDate billDate;
	
	private String customerName;
	
	public Bills() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Bills(LocalDate billDate, String customerName) {
		super();
		this.billDate = billDate;
		this.customerName = customerName;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public LocalDate getBillDate() {
		return billDate;
	}

	public void setBillDate(LocalDate billDate) {
		this.billDate = billDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Override
	public String toString() {
		return "Bills [billId=" + billId + ", billDate=" + billDate + ", customerName=" + customerName + "]";
	}
}
