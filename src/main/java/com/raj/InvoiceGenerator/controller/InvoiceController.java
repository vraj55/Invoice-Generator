package com.raj.InvoiceGenerator.controller;

import java.time.LocalDate;
import java.util.List;

import com.raj.InvoiceGenerator.entity.Bills;
import com.raj.InvoiceGenerator.entity.*;



public interface InvoiceController {

	Bills createBill(LocalDate date, String customer);
	
	void addItem(String itemName, int itemPrice, int itemQty, Bills bill1);
	
	void printBill(Bills bill);
	
	List<Items> getItemsByBillId(int billId);
	
	int getTotalPriceOfBill(List<Items> items);
	
	void deleteBill(Bills bill);
	
	void clearItems(int billId);
}
