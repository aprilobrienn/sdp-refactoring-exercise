import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AccountActions {
	private static final int PIN_ATTEMPS = 3;
	private static final int MAX_WITHDRAWL = 500;
	CustomerActions customerActions = new CustomerActions();
	
	//----- lodgement method-----
	
	public void lodgementMethod(Customer customer, CustomerAccount account, JFrame f) {
	    if (!checkPin(account, f)) {
	        //openCustomerMenu(customer, account);
	        return;
	    }

	    double balance = 0;
	    String balanceTest = JOptionPane.showInputDialog(f, "Enter amount you wish to lodge:");//the isNumeric method tests to see if the string entered was numeric. 
		if(isNumeric(balanceTest))
		{
			balance = Double.parseDouble(balanceTest);
		}
		else
		{
			JOptionPane.showMessageDialog(f, "You must enter a numerical value!" ,"Oops!",  JOptionPane.INFORMATION_MESSAGE);
		}

	    String euro = "\u20ac";
	    account.lodge(balance);
		
		JOptionPane.showMessageDialog(f, balance + euro + " added to your account!" ,"Lodgement",  JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(f, "New balance = " + account.getBalance() + euro ,"Lodgement",  JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	//----method to check pin----
	public boolean checkPin(CustomerAccount account, JFrame f) {
		if(!account.pinNeeded()) {
			return true;
		}
		
		CustomerCurrentAccount acc = (CustomerCurrentAccount) account;
		int checkPin = acc.getAtm().getPin();
		int count = PIN_ATTEMPS;
		
		if (!acc.getAtm().getValid()) {
			JOptionPane.showMessageDialog(f, "Pin entered incorrectly 3 times. ATM card locked."  ,"Pin",  JOptionPane.INFORMATION_MESSAGE);
	        return false;
	    }

		while(count > 0) {
			String Pin = JOptionPane.showInputDialog(f, "Enter 4 digit PIN;");
			if (Pin == null) {
				return false;
			}
			int i = Integer.parseInt(Pin);
			if(checkPin == i)
				{
					JOptionPane.showMessageDialog(f, "Pin entry successful" ,  "Pin", JOptionPane.INFORMATION_MESSAGE);
					return true;
				}
				else
				{
					count --;
					JOptionPane.showMessageDialog(f, "Incorrect pin. " + count + " attempts remaining."  ,"Pin",  JOptionPane.INFORMATION_MESSAGE);					
				}
			
			}
		JOptionPane.showMessageDialog(f, "Pin entered incorrectly 3 times. ATM card locked."  ,"Pin",  JOptionPane.INFORMATION_MESSAGE);
		acc.getAtm().setValid(false);
		return false;
	}
	
	
	//----widthdraw method---
	public void withdrawMethod(Customer customer, CustomerAccount account, JFrame f) {
	    if (!checkPin(account, f)) {
	        return;
	    }

	    double withdraw = 0;
	    String balanceTest = JOptionPane.showInputDialog(f, "\"Enter amount you wish to withdraw (max 500):");//the isNumeric method tests to see if the string entered was numeric. 
		if(isNumeric(balanceTest))
		{
			withdraw = Double.parseDouble(balanceTest);
		}
		else
		{
			JOptionPane.showMessageDialog(f, "You must enter a numerical value!" ,"Oops!",  JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		if(withdraw > MAX_WITHDRAWL) {
			JOptionPane.showMessageDialog(f, "500 is the maximum you can withdraw at a time." ,"Oops!",  JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if(!account.canWithdrawAmount(withdraw)) {
			JOptionPane.showMessageDialog(f, "Insufficient funds." ,"Oops!",  JOptionPane.INFORMATION_MESSAGE);
			return;
		}

	    String euro = "\u20ac";
		
		JOptionPane.showMessageDialog(f, withdraw + euro + " withdrawn." ,"Withdraw",  JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(f, "New balance = " + account.getBalance() + euro ,"Withdraw",  JOptionPane.INFORMATION_MESSAGE);
	}

	
	//---isnumeric method---
	public static boolean isNumeric(String str)  // a method that tests if a string is numeric
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
	
	//---get selected account---
	public CustomerAccount findAccountByNumber(Customer customer, String number) {
		for (CustomerAccount acc : customer.getAccounts()) {
			if (acc.getNumber().equals(number)) {
				return acc;
			}
		}
	    return null;
	}
	
	//---summaryBuilder--
		public String summaryBuilder(ArrayList<Customer> customerList) {
		    StringBuilder sb = new StringBuilder();

		    for (Customer customer : customerList) {
				for (CustomerAccount acc : customer.getAccounts()) {
					for (AccountTransaction transaction : acc.getTransactionList()) {
						sb.append(transaction.toString());
					}
				}
			}

		    return sb.toString();
		}
		
		
		

		//---delete account method--
		public void deleteAccountMethod(ArrayList<Customer> customerList, JFrame f) {

		    Customer customer = customerActions.getACustomer("Customer ID of Customer from which you wish to delete an account:", customerList, f);
		    if (customer == null) {
		        //admin();
		        return;
		    }

		    if (customer.getAccounts().isEmpty()) {
		        JOptionPane.showMessageDialog(f, "This customer has no accounts to delete.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
		        //admin();
		        return;
		    }

		    CustomerAccount account = customerActions.getCustomersAccount(customer, "Select an account to delete:", f);
		    if (account == null) {
		        //admin();
		        return;
		    }

		    if (account.getBalance() != 0.0) {
		        JOptionPane.showMessageDialog(f,"Account cannot be deleted unless the balance is 0.\nCurrent balance: " + account.getBalance(),"Oops!",JOptionPane.INFORMATION_MESSAGE);
		        //admin();
		        return;
		    }

		    customer.getAccounts().remove(account);
		    JOptionPane.showMessageDialog(f, "Account deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
		   
		    //admin();
		} 
	


}
