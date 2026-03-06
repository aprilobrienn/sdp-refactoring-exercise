import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CustomerActions {
	private static final int PASSWORD_LENGTH = 7;
	
	//--create customer---
	public Customer createCustomer(String pps, String surname, String firstName, String dob, String password) {
		String customerID = "ID" + pps;
		ArrayList<CustomerAccount> accounts = new ArrayList<CustomerAccount>();
		return new Customer(pps, surname, firstName, dob, customerID, password, accounts);
	}
	
	//---get exisiting customer---
	public Customer getExistingCustomer(JFrame f, ArrayList<Customer> customerList) {
	    while (true) {
	        String customerId = JOptionPane.showInputDialog(f, "Enter Customer ID:");
	        if (customerId == null) {
	        	//backToMenuStart(f);
	            return null;
	        }

	        Customer customer = findCustomerById(customerId, customerList);
	        if (customer != null) {
	            return customer;
	        }

	        int reply = JOptionPane.showConfirmDialog(f,"User not found. Try again?",null,JOptionPane.YES_NO_OPTION);

	        if (reply == JOptionPane.NO_OPTION) {
	        	//backToMenuStart(f);
	            return null;
	        }
	    }
	}
	
	//---find customer by id----
	public Customer findCustomerById(String customerID, ArrayList<Customer> customerList) {
	    for (Customer aCustomer: customerList) {
	        if (aCustomer.getCustomerID().equals(customerID)) {
	            return aCustomer;
	        }
	    }
	    return null;
	}
	
	
	//--check password---
	public boolean checkCustomerPassword(JFrame f, Customer customer) {
	    while (true) {
	        String customerPassword = JOptionPane.showInputDialog(f, "Enter Customer Password:");
	        if (customerPassword == null) {
	        	//backToMenuStart(f);
	            return false;
	        }

	        if (customer.getPassword().equals(customerPassword)) {
	            return true;
	        }

	        int reply = JOptionPane.showConfirmDialog(f,"Incorrect password. Try again?",null,JOptionPane.YES_NO_OPTION);

	        if (reply == JOptionPane.NO_OPTION) {
	        	//backToMenuStart(f);
	            return false;
	        }
	    }
	}
	
	//--get a customer---
	public Customer getACustomer(String message, ArrayList<Customer> customerList, JFrame f) {
		if(customerList.isEmpty())
		{
			JOptionPane.showMessageDialog(f, "There are no customers yet!"  ,"Oops!",  JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
		while (true) {
			String customerId = JOptionPane.showInputDialog(f, message);
	        if (customerId == null) {
	        	return null;
	        }
	        
	        Customer customer = findCustomerById(customerId, customerList);
	        if (customer != null) {
	        	return customer;
	        }
	        
	        int reply = JOptionPane.showConfirmDialog(f, "User not found. Try again?", null, JOptionPane.YES_NO_OPTION);
	        
	        if (reply == JOptionPane.NO_OPTION) {
	        	return null;
	        }
		}
	}
	
	//--set password---
	public String setCustomerPassword(JFrame f) {
		while(true){
		 String password = JOptionPane.showInputDialog(f, "Enter 7 character Password;");
		 
		 if (password == null) {
			 return null;
		 }
		
		 if(password.length() != PASSWORD_LENGTH)//Making sure password is 7 characters
		    {
		    	JOptionPane.showMessageDialog(null, null, "Password must be 7 charatcers long", JOptionPane.OK_OPTION);
		    }
		 else
		 {
			 return password;
		 }
		 
		}
	}
	
	
	//---create new customer--
	public boolean createNewCustomer(String PPS, String firstName, String surname, String DOB, JFrame f, ArrayList<Customer> customerList) {
	    if (PPS.isEmpty()) {
	    	JOptionPane.showMessageDialog(null, null, "Please enter PPSN", JOptionPane.OK_OPTION);
	        return false;
	    }
	    if (firstName.isEmpty()) {
	    	JOptionPane.showMessageDialog(null, null, "Please enter first name", JOptionPane.OK_OPTION);
	        return false;
	    }
	    if (surname.isEmpty()) {
	    	JOptionPane.showMessageDialog(null, null, "Please enter surname", JOptionPane.OK_OPTION);
	        return false;
	    }
	    if (DOB.isEmpty()) {
	    	JOptionPane.showMessageDialog(null, null, "Please enter DOB", JOptionPane.OK_OPTION);
	        return false;
	    }

	    String password = setCustomerPassword(f);
	    if (password == null) {
	        return false;
	    }

	    Customer customer = createCustomer(PPS, surname, firstName, DOB, password);
	    customerList.add(customer);

	    JOptionPane.showMessageDialog(f, "CustomerID = " + customer.getCustomerID() +"\n Password = " + password  ,"Customer created.",  JOptionPane.INFORMATION_MESSAGE);

	    return true;
	}
	
	
	//----get customer account method--
	public CustomerAccount getCustomersAccount(Customer customer, String message, JFrame f) {
		JComboBox<String> box = new JComboBox<>();
	    for (CustomerAccount a : customer.getAccounts()) {
	        box.addItem(a.getNumber());
	    }

	    JPanel panel = new JPanel();
	    panel.add(new JLabel(message));
	    panel.add(box);
	 
	    int result = JOptionPane.showConfirmDialog(f,panel,"Administrator Menu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE
	    );

	    if (result != JOptionPane.OK_OPTION) return null;

	    String selectedNumber = (String) box.getSelectedItem();
	    for (CustomerAccount a : customer.getAccounts()) {
	        if (a.getNumber().equals(selectedNumber)) {
	            return a;
	        }
	    }
	    return null;
		
	}
	

}
