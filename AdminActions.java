import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AdminActions {
	private CustomerActions customerActions = new CustomerActions();
	private AccountActions accountActions = new AccountActions();
	
	//---add account method----
	public void accountMethod(JFrame f, ArrayList<Customer> customerList) {
		f.dispose();

	    Customer customer = customerActions.getACustomer("Customer ID of Customer You Wish to Add an Account to:", customerList, f);
	    if (customer == null) {
	        return;
	    }

	    String type = selectAccountType(f);
	    if (type == null) {
	        return;
	    }

	    if (type.equals("Current Account")) {
	        createCurrentAccount(customer, customerList, f);
	    } else if (type.equals("Deposit Account")) {
	        createDepositAccount(customer, customerList, f);
	    }
	    

	    return;
	}
	
	//---select account type---
	public String selectAccountType(JFrame f) {
		String[] choices = { "Current Account", "Deposit Account" };
	    return (String) JOptionPane.showInputDialog(f,"Please choose account type",
	            "Account Type",
	            JOptionPane.QUESTION_MESSAGE,null, choices,choices[1]);
	}
	
	
	//---create current account---
	public void createCurrentAccount(Customer customer, ArrayList<Customer> customerList, JFrame f) {
		boolean valid = true;
    	double balance = 0;
    	String number = String.valueOf("C" + (customerList.indexOf(customer)+1) * 10 + (customer.getAccounts().size()+1));//this simple algorithm generates the account number
    	ArrayList<AccountTransaction> transactionList = new ArrayList<AccountTransaction>();
    	int randomPIN = (int)(Math.random()*9000)+1000;
           String pin = String.valueOf(randomPIN);
    
           ATMCard atm = new ATMCard(randomPIN, valid);
    	
    	CustomerCurrentAccount current = new CustomerCurrentAccount(atm, number, balance, transactionList);
    	
    	customer.getAccounts().add(current);
    	JOptionPane.showMessageDialog(f, "Account number = " + number +"\n PIN = " + pin  ,"Account created.",  JOptionPane.INFORMATION_MESSAGE);
    	
	}

	
	//---create deposit account---
	public void createDepositAccount(Customer customer, ArrayList<Customer> customerList, JFrame f) {
		double balance = 0, interest = 0;
    	String number = String.valueOf("D" + (customerList.indexOf(customer)+1) * 10 + (customer.getAccounts().size()+1));//this simple algorithm generates the account number
    	ArrayList<AccountTransaction> transactionList = new ArrayList<AccountTransaction>();
        	
    	CustomerDepositAccount deposit = new CustomerDepositAccount(interest, number, balance, transactionList);
    	
    	customer.getAccounts().add(deposit);
    	JOptionPane.showMessageDialog(f, "Account number = " + number ,"Account created.",  JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	//-----bank charges method-------
	public void bankChargesMethod(JFrame f, ArrayList<Customer> customerList) {
		Customer customer = customerActions.getACustomer("Customer ID of Customer You Wish to Apply Charges to:", customerList, f);
	    if (customer == null) {
	    	//admin();
	        return;
	    }
	    
	    if (customer.getAccounts().isEmpty()) {
	        JOptionPane.showMessageDialog(f, "This customer has no accounts! \n The admin must add acounts to this customer.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
	        //admin();
	        return;
	    }

	    CustomerAccount customerAccount = customerActions.getCustomersAccount(customer, "Select an account to apply charges to:", f);
	    if (customerAccount == null) {
	    	//admin();
	        return;
	    }
	    
	    String msg = customerAccount.applyBankCharge();
	    JOptionPane.showMessageDialog(f, msg  ,"",  JOptionPane.INFORMATION_MESSAGE);

	    JOptionPane.showMessageDialog(f, "New balance = " + customerAccount.getBalance(), "Success!", JOptionPane.INFORMATION_MESSAGE);
	    //admin();
	    return;
	}
	
	//----interest method---
	public void interestMethod(JFrame f, ArrayList<Customer> customerList) {
	    Customer customer = customerActions.getACustomer("Customer ID of Customer You Wish to Apply Interest to:",customerList, f);
	    if (customer == null) {
	        //admin();
	        return;
	    }

	    if (customer.getAccounts().isEmpty()) {
	        JOptionPane.showMessageDialog(f, "This customer has no accounts! \n The admin must add acounts to this customer.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
	        //admin();
	        return;
	    }

	    CustomerAccount account = customerActions.getCustomersAccount(customer, "Select an account to apply interest to:", f);
	    if (account == null) {
	    	//admin();
	        return;
	    }

	    if (!account.canApplyInterest()) {
	        JOptionPane.showMessageDialog(f, "To apply interest you must choose a deposit account.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
	        //admin();
	        return;
	    }

	    Double interest = enterInterest("Enter interest percentage you wish to apply: \n NOTE: Please enter a numerical value. (with no percentage sign) \n E.g: If you wish to apply 8% interest, enter '8'", f);
	    if (interest == null) {
	    	//admin();
	        return;
	    }

	    account.setBalance(account.getBalance() + (account.getBalance() * (interest / 100.0)));
	    JOptionPane.showMessageDialog(f, interest + "% interest applied. New balance = " + account.getBalance(), "Success!", JOptionPane.INFORMATION_MESSAGE);

	    //admin();
	    return;
	}
	
	public Double enterInterest(String message, JFrame f) {
	    while (true) {
	    	String interestString = JOptionPane.showInputDialog(f, message);
	        if (interestString == null) {
	        	return null;
	        }
	        if (accountActions.isNumeric(interestString)) {
	        	Double interest = Double.parseDouble(interestString);
				return interest;
				
	        } else {
	        	JOptionPane.showMessageDialog(f, "You must enter a numerical value!" ,"Oops!",  JOptionPane.INFORMATION_MESSAGE);
			}
	        
	    }
	}

}
