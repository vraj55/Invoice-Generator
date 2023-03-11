package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import com.raj.InvoiceGenerator.controller.InvoiceController;
import com.raj.InvoiceGenerator.controller.InvoiceControllerImpl;
import com.raj.InvoiceGenerator.entity.Bills;
import com.raj.InvoiceGenerator.entity.Items;

import javafx.event.ActionEvent;

import javafx.scene.control.DatePicker;

public class BillsController {
	@FXML
	private TextField fxTotalPrice;
	@FXML
	private TextField fxItemName;
	@FXML
	private TextField fxItemPrice;
	@FXML
	private TextField fxItemQty;
	@FXML
	private TextField fxSubTotal;
	@FXML
	private TextField fxTotalItems;
	@FXML
	private TextField fxCustomerName;
	@FXML
	private Button fxAddButton;
	@FXML
	private Button fxResetButton;
	@FXML
	private DatePicker fxDate;
	@FXML
	private Button fxPrintButton;
	@FXML
	private Button fxDeleteButton;
	@FXML
	private TextField fxBillNum;
	@FXML
	private Button prepBill;

	InvoiceController inv = new InvoiceControllerImpl();
	
	Bills b;
	// Event Listener on Button.onAction
	@FXML
	public void addItems(ActionEvent event) {
		
		int iPrice = Integer.parseInt(fxItemPrice.getText());
		int iQty = Integer.parseInt(fxItemQty.getText());
		int subT = iPrice * iQty;
		
		inv.addItem(fxItemName.getText(), iPrice, iQty, b);
		fxSubTotal.setText(Integer.toString(subT));
		fxItemName.clear();
		fxItemPrice.clear();
		fxItemQty.clear();
		
		List<Items> iList = inv.getItemsByBillId(b.getBillId());
		int totalPrice = inv.getTotalPriceOfBill(iList);
		fxTotalItems.setText(Integer.toString(iList.size()));
		fxTotalPrice.setText(Integer.toString(totalPrice));
		
		Alert alert = new Alert(AlertType.NONE);
		alert.getButtonTypes().add(ButtonType.OK);
		
		List<Items> itList = inv.getItemsByBillId(b.getBillId());
		
		if(itList.size() > 0) {
			alert.setTitle("Confirmation");
			alert.setHeaderText("Item "+ itList.size()+" added successfully!");
		}
		else {
			alert.setTitle("Confirmation");
			alert.setHeaderText("Failed to add the Item."
					+ "Please retry!");
		}
		
		alert.showAndWait();
	}
	// Event Listener on Button.onAction
	@FXML
	public void resetItems(ActionEvent event) {
		
		inv.clearItems(b.getBillId());
		fxSubTotal.clear();
		fxItemName.clear();
		fxItemPrice.clear();
		fxItemQty.clear();
	}
	// Event Listener on Button.onAction
	@FXML
	public void printInvoice(ActionEvent event) {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Do you want to Print the Invoice?");
		
		Optional<ButtonType> res = alert.showAndWait();
		
		if(res.get() == ButtonType.OK) {
		
		inv.printBill(b);
		fxSubTotal.clear();
		fxItemName.clear();
		fxItemPrice.clear();
		fxItemQty.clear();
		fxCustomerName.clear();
		fxDate.setAccessibleText(null);
		fxTotalItems.clear();
		fxTotalPrice.clear();
		
		alert.setHeaderText("Invoice is generated successfully! you can access that in the below path "
				+ "/n file:///C:/Users/rajvr/eclipse-workspace/InvoiceGenerator/ ");
		alert.show();
		
		disableFileds(event);
		}
		else {
			alert.setHeaderText("Invoice is not Printed!");
			alert.show();
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void deleteInvoice(ActionEvent event) {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Do you want to Delete the Invoice?");
		
		Optional<ButtonType> res = alert.showAndWait();
		
		if(res.get() == ButtonType.OK) {
			inv.deleteBill(b);
			alert.setHeaderText("Invoice deleted successfully");
			alert.show();
			
			resetItems(event);
			fxCustomerName.clear();
			fxDate.setAccessibleText(null);
			fxTotalItems.clear();
			fxTotalPrice.clear();
			
			disableFileds(event);
		}
		else {
			alert.setHeaderText("Invoice is not deleted!");
			alert.show();
		}
	}
	
	@FXML
	public void prepareBill(ActionEvent event) {
		
		if(fxDate != null && fxCustomerName != null) {
			b = inv.createBill(fxDate.getValue(), fxCustomerName.getText());
		
			Alert alert = new Alert(AlertType.NONE);
			alert.getButtonTypes().add(ButtonType.OK);
			alert.setTitle("Confirmation");
		
			if(b.getBillId() != 0) {
				alert.setHeaderText("Bill has been prepared."
						+ "You can add the Items now!");
			}
			else {
				alert.setHeaderText("Failed to prepare the Bill."
						+ "Please retry!");
			}
			
			alert.show();
			
			fxItemName.setDisable(false);
			fxItemPrice.setDisable(false);
			fxItemQty.setDisable(false);
			fxAddButton.setDisable(false);
			fxResetButton.setDisable(false);
		
			fxSubTotal.setDisable(false);
			fxTotalItems.setDisable(false);
			fxTotalPrice.setDisable(false);
		
			fxPrintButton.setDisable(false);
			fxDeleteButton.setDisable(false);
			
		}
	}
	
	public void disableFileds(ActionEvent event) {
		
		fxItemName.setDisable(true);
		fxItemPrice.setDisable(true);
		fxItemQty.setDisable(true);
		fxSubTotal.setDisable(true);
		fxTotalItems.setDisable(true);
		fxTotalPrice.setDisable(true);
		
		fxAddButton.setDisable(true);
		fxResetButton.setDisable(true);
		fxPrintButton.setDisable(true);
		fxDeleteButton.setDisable(true);
	}
}
