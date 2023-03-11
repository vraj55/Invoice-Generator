package com.raj.InvoiceGenerator.controller;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.raj.InvoiceGenerator.entity.*;
import com.raj.InvoiceGenerator.helper.FactoryProvider;

public class InvoiceControllerImpl implements InvoiceController{

	Bills bill = new Bills();
	int iNum = 0;
	
	@Override
	public Bills createBill(LocalDate billDate, String customerName) {
		
		bill.setBillDate(billDate);
		bill.setCustomerName(customerName);
		
		Session s = FactoryProvider.getFactory().openSession();
		
		Transaction tx = s.beginTransaction();
		
		s.save(bill);
		
		tx.commit();
		s.close();
		
		return bill;
	}

	@Override
	public void addItem(String itemName, int itemPrice, int itemQty, Bills bill1) {
		
		iNum += 1;
		
		int subTotal = itemPrice*itemQty;
		
		Items item = new Items();
		item.setBill(bill1);
		item.setitemNum(iNum);
		item.setItemName(itemName);
		item.setItemPrice(itemPrice);
		item.setItemQty(itemQty);
		item.setSubTotal(subTotal);
		
		Session s = FactoryProvider.getFactory().openSession();
		
		Transaction tx = s.beginTransaction();
		
		s.save(item);
		
		tx.commit();
		s.close();
	}

	@Override
	public void printBill(Bills bill) {
		
		List<Items> iList = getItemsByBillId(bill.getBillId());
		
		try {
			if(iList.size() > 0) {
			Document doc = new Document();
			String fileName = bill.getCustomerName()+ bill.getBillDate();
			PdfWriter.getInstance(doc, new FileOutputStream(fileName+".pdf"));
			
			int totalPrice = 0;
			
			doc.open();
			
			doc.add(new Paragraph("Vijay Electricals"));
			doc.add(new Paragraph("Bill Date: " + bill.getBillDate()));
			doc.add(new Paragraph("Customer Name: " + bill.getCustomerName()));
			doc.add(new Paragraph("-------------------------------------"));
			
			for (Items i: iList) {
				
				int itemNum = i.getitemNum();
				String itemName = i.getItemName();
				int itemPrice = i.getItemPrice();
				int itemQty = i.getItemQty();
				int itemSubTotal = i.getSubTotal();
				
				doc.add(new Paragraph("Item Number : " + itemNum));
				doc.add(new Paragraph("Item Name: " + itemName));
				doc.add(new Paragraph("Item Price: " + itemPrice));
				doc.add(new Paragraph("Item Quantity: " + itemQty));
				doc.add(new Paragraph("Sub Total: " + itemSubTotal));
				doc.add(new Paragraph("-----------------------"));
				totalPrice += itemSubTotal;
			}
			doc.add(new Paragraph("-------------------------------------"));
            doc.add(new Paragraph("Total Items: " + Integer.toString(iList.size())));
            doc.add(new Paragraph("Total Price: " + Integer.toString(totalPrice)));
            doc.add(new Paragraph("-------------------------------------"));
            doc.add(new Paragraph("Thank You!"));
            
            doc.close();
            
            System.out.println("PDF generated");
			}
			else {
				System.out.println("Bill is Empty");
			}
		} catch (Exception e) {
			System.out.println("Error occured: "+ e.getMessage());
		}
	}

	@Override
	public List<Items> getItemsByBillId(int billId) {
		
		if(billId > 0) {
			
			try {
				Session s = FactoryProvider.getFactory().openSession();
				
				Criteria c = s.createCriteria(Items.class);
				c.add(Restrictions.eq("bill.billId", billId));
				
				List<Items> iList = c.list();
			
			return iList;
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public int getTotalPriceOfBill(List<Items> items) {
		
		int tPrice = 0;
		
		for (Items items2 : items) {
			tPrice += items2.getSubTotal();
		}
		return tPrice;
	}

	@Override
	public void deleteBill(Bills bill) {
		
		if(bill != null) {
			try {
				
				clearItems(bill.getBillId());
				
				Session s = FactoryProvider.getFactory().openSession();
								
				String hql = "delete from bills where billId = :bId";
				
				Transaction tx = s.beginTransaction();
				
				Query q1 = s.createSQLQuery(hql);
				q1.setParameter("bId", bill.getBillId());
				String msg = q1.executeUpdate() > 0 ? "deleted Bill" : "Bill not deleted";
				
				tx.commit();
				s.close();
				
				System.out.println(msg);
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	@Override
	public void clearItems(int billId) {
		
		try {
			Session s = FactoryProvider.getFactory().openSession();
			
			String hql1 = "delete from items where bill_billId = :bId";

			Transaction tx = s.beginTransaction();
			
			Query q2 = s.createSQLQuery(hql1);
			q2.setParameter("bId", billId);
			
			String msg = q2.executeUpdate()>0 ? "Items cleared": "Items not cleared";
			
			tx.commit();
			s.close();
			
			System.out.println(msg);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
