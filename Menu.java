import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.text.ParseException;
import javax.swing.text.MaskFormatter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Menu extends JFrame{
	
	private static final String ADMIN_USERNAME = "admin";
	private static final String ADMIN_PASS = "admin11";
	private ArrayList<Customer> customerList = new ArrayList<Customer>();
    private int position = 0;
	private String password;
	private Customer customer = null;
	private CustomerAccount acc;
	JFrame f, f1;
	 JLabel firstNameLabel, surnameLabel, pPPSLabel, dOBLabel;
	 JTextField firstNameTextField, surnameTextField, pPSTextField, dOBTextField;
		JLabel customerIDLabel, passwordLabel;
		JTextField customerIDTextField, passwordTextField;
	Container content;
		Customer e;


	 JPanel panel2;
		JButton add;
		String 	PPS,firstName,surname,DOB,CustomerID;
	
	public static void main(String[] args)
	{
		Menu driver = new Menu();
		driver.menuStart();
	}
	
	public void menuStart()
	{
		   /*The menuStart method asks the user if they are a new customer, 
		    an existing customer or an admin. 
		    It will then start the create customer process if they are a new customer, 
		    or will ask them to log in if they are an existing customer or admin.*/
		
			
			f = new JFrame("User Type");
			f.setSize(400, 300);
			f.setLocation(200, 200);
			f.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) { System.exit(0); }
			});

			JPanel userTypePanel = new JPanel();
			final ButtonGroup userType = new ButtonGroup();
			JRadioButton radioButton;
			userTypePanel.add(radioButton = new JRadioButton("Existing Customer"));
			radioButton.setActionCommand("Customer");
			userType.add(radioButton);
			
			userTypePanel.add(radioButton = new JRadioButton("Administrator"));
			radioButton.setActionCommand("Administrator");
			userType.add(radioButton);
			
			userTypePanel.add(radioButton = new JRadioButton("New Customer"));
			radioButton.setActionCommand("New Customer");
			userType.add(radioButton);

			JPanel continuePanel = new JPanel();
			JButton continueButton = new JButton("Continue");
			continuePanel.add(continueButton);

			Container content = f.getContentPane();
			content.setLayout(new GridLayout(2, 1));
			content.add(userTypePanel);
			content.add(continuePanel);



			continueButton.addActionListener(new ActionListener(  ) {
				public void actionPerformed(ActionEvent ae) {
					if (userType.getSelection() != null) {
						String user = userType.getSelection().getActionCommand();
						selectedUserType(user);
					}
				}
			});
			
			f.setVisible(true);	
	}
			
			
		private void selectedUserType(String type) {
		    switch (type) {
		        case "New Customer":
		            openNewCustomerUI();
		            break;
		        case "Administrator":
		            openAdministratorUI();
		            break;
		        case "Customer":
		            openExistingCustomerUI();
		            break;
		        default:
		            throw new IllegalArgumentException("invalid user type selected");
		    }
		}
			
		
		//------OPEN NEW CUSTOMER UI--------
		
		
			private void openNewCustomerUI() {
				
				f.dispose();		
				f1 = new JFrame("Create New Customer");
				f1.setSize(400, 300);
				f1.setLocation(200, 200);
				f1.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent we) { System.exit(0); }
				});
					Container content = f1.getContentPane();
					content.setLayout(new BorderLayout());
					
					firstNameLabel = new JLabel("First Name:", SwingConstants.RIGHT);
					surnameLabel = new JLabel("Surname:", SwingConstants.RIGHT);
					pPPSLabel = new JLabel("PPS Number:", SwingConstants.RIGHT);
					dOBLabel = new JLabel("Date of birth", SwingConstants.RIGHT);
					firstNameTextField = new JTextField(20);
					surnameTextField = new JTextField(20);
					pPSTextField = new JTextField(20);
					dOBTextField = new JTextField(20);
					JPanel panel = new JPanel(new GridLayout(6, 2));
					panel.add(firstNameLabel);
					panel.add(firstNameTextField);
					panel.add(surnameLabel);
					panel.add(surnameTextField);
					panel.add(pPPSLabel);
					panel.add(pPSTextField);
					panel.add(dOBLabel);
					panel.add(dOBTextField);
						
					panel2 = new JPanel();
					
					JButton add = new JButton("Add");
					JButton cancel = new JButton("Cancel");
					
					//add button action listener
					add.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							createNewCustomer();
						}
					});
					
					//cancel button action listener
					cancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							f1.dispose();
							menuStart();
						}
					});	
							
					
					panel2.add(add);
					panel2.add(cancel);
					
					content.add(panel, BorderLayout.CENTER);
					content.add(panel2, BorderLayout.SOUTH);
			
					f1.setVisible(true);
				
			}
			
			//method to create new customer
			private void createNewCustomer() {
				String PPS = pPSTextField.getText();
			    String firstName = firstNameTextField.getText();
			    String surname = surnameTextField.getText();
			    String DOB = dOBTextField.getText();

			    if (PPS.isEmpty()) {
			    	JOptionPane.showMessageDialog(null, null, "Please enter PPSN", JOptionPane.OK_OPTION);
			        return;
			    }
			    if (firstName.isEmpty()) {
			    	JOptionPane.showMessageDialog(null, null, "Please enter first name", JOptionPane.OK_OPTION);
			        return;
			    }
			    if (surname.isEmpty()) {
			    	JOptionPane.showMessageDialog(null, null, "Please enter surname", JOptionPane.OK_OPTION);
			        return;
			    }
			    if (DOB.isEmpty()) {
			    	JOptionPane.showMessageDialog(null, null, "Please enter DOB", JOptionPane.OK_OPTION);
			        return;
			    }

			    String password = setCustomerPassword();
			    if (password == null) {
			        return;
			    }

			    String customerID = "ID" + PPS;

			    ArrayList<CustomerAccount> accounts = new ArrayList<CustomerAccount>();
			    Customer customer = new Customer(PPS, surname, firstName, DOB, customerID, password, accounts);
			    customerList.add(customer);

			    JOptionPane.showMessageDialog(f, "CustomerID = " + customerID +"\n Password = " + password  ,"Customer created.",  JOptionPane.INFORMATION_MESSAGE);

			    f1.dispose();
			    menuStart();
			}
			
			
			//method to let customer set password
			private String setCustomerPassword() {
				while(true){
				 String password = JOptionPane.showInputDialog(f, "Enter 7 character Password;");
				 
				 if (password == null) {
					 return null;
				 }
				
				 if(password.length() != 7)//Making sure password is 7 characters
				    {
				    	JOptionPane.showMessageDialog(null, null, "Password must be 7 charatcers long", JOptionPane.OK_OPTION);
				    }
				 else
				 {
					 return password;
				 }
				 
				}
			}
			
			
			//------OPEN ADMIN UI--------
			
			private void openAdministratorUI() {
				
				
				String adminUsername = checkAdminDetails(f,"Enter Administrator Username:", ADMIN_USERNAME, "Incorrect Username. Try again?");
			    if (adminUsername == null) {
			    	return;
			    }
			    
			    String adminPassword = checkAdminDetails(f,"Enter Administrator Password:", ADMIN_PASS, "Incorrect Password. Try again?");
			    if (adminPassword == null) {
			    	return;
			    }
			    
			    if (f1 != null) {
		    		f1.dispose();
		    	}
			    admin();	
			}
			
			private String checkAdminDetails(JFrame f, String message, String value ,String retryMsg) {
			    while (true) {
			    	
			        String valueEntered = JOptionPane.showInputDialog(f, message);

			        if (valueEntered == null) {
			        	backToMenuStart(f);
			            return null;
			        }

			        if (valueEntered.equals(value)) {
			            return valueEntered;
			        }

			        int reply = JOptionPane.showConfirmDialog(f,retryMsg,null,JOptionPane.YES_NO_OPTION);

			        if (reply == JOptionPane.NO_OPTION) {
			        	backToMenuStart(f);
			            return null;
			        }
			    }
			}
			
			private void backToMenuStart(JFrame f) {
			    if (f != null) {
			        f.dispose();
			    }
			    if (f1 != null) {
			        f1.dispose();
			    }
			    menuStart();
			}
				
			
			//------OPEN CUSTOOMER UI--------
			
			
			private void openExistingCustomerUI() {
				Customer customer = getExistingCustomer();
				
				if (customer == null) {
					return;
				}
				
				boolean validPassword = checkCustomerPassword(customer);
			    if (!validPassword)  {
			    	return;
			    }
			    
			    if (f != null) {
			        f.dispose();
			    }
			    
			    customer(customer);
			}
			
			private Customer getExistingCustomer() {
			    while (true) {
			        String customerId = JOptionPane.showInputDialog(f, "Enter Customer ID:");
			        if (customerId == null) {
			        	backToMenuStart(f);
			            return null;
			        }

			        Customer customer = findCustomerById(customerId);
			        if (customer != null) {
			            return customer;
			        }

			        int reply = JOptionPane.showConfirmDialog(f,"User not found. Try again?",null,JOptionPane.YES_NO_OPTION);

			        if (reply == JOptionPane.NO_OPTION) {
			        	backToMenuStart(f);
			            return null;
			        }
			    }
			}
			
			private Customer findCustomerById(String customerID) {
			    for (Customer aCustomer: customerList) {
			        if (aCustomer.getCustomerID().equals(customerID)) {
			            return aCustomer;
			        }
			    }
			    return null;
			}
			
			private boolean checkCustomerPassword(Customer customer) {
			    while (true) {
			        String customerPassword = JOptionPane.showInputDialog(f, "Enter Customer Password:");
			        if (customerPassword == null) {
			        	backToMenuStart(f);
			            return false;
			        }

			        if (customer.getPassword().equals(customerPassword)) {
			            return true;
			        }

			        int reply = JOptionPane.showConfirmDialog(f,"Incorrect password. Try again?",null,JOptionPane.YES_NO_OPTION);

			        if (reply == JOptionPane.NO_OPTION) {
			        	backToMenuStart(f);
			            return false;
			        }
			    }
			}
				
				
	

	//-----admin menu----
	public void admin()
	{
		dispose();
		
		f = new JFrame("Administrator Menu");
		f.setSize(400, 400);
		f.setLocation(200, 200);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) { System.exit(0); }
		});          
		
		JButton deleteCustomer = createButton("Delete Customer");
		JButton deleteAccount = createButton("Delete Account");
		JButton bankChargesButton = createButton("Apply Bank Charges");
		JButton interestButton = createButton("Apply Interest");
		JButton editCustomerButton = createButton("Edit existing Customer");
		JButton navigateButton = createButton("Navigate Customer Collection");
		JButton summaryButton = createButton("Display Summary Of All Accounts");
		JButton accountButton = createButton("Add an Account to a Customer");
		JButton returnButton = new JButton("Exit Admin Menu");

		
		JLabel label1 = new JLabel("Please select an option");
		
		content = f.getContentPane();
		content.setLayout(new GridLayout(10, 1));
		content.add(label1);
		content.add(flowLeft(accountButton, true));
		content.add(flowLeft(bankChargesButton, true));
		content.add(flowLeft(interestButton, true));
		content.add(flowLeft(editCustomerButton, true));
		content.add(flowLeft(navigateButton, true));
		content.add(flowLeft(summaryButton, true));	
		content.add(flowLeft(deleteCustomer, true));
		content.add(flowLeft(deleteAccount, true));
		content.add(flowLeft(returnButton, false));
		
		bankChargesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bankChargesMethod();
			}
		}); 
		
		editCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editCustomerMethod();
			}
		}); 
		
		interestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				interestMethod();
			}
		}); 
		
		accountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accountMethod();
			}
		}); 
		
		navigateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				navigateMethod();
			}
		}); 
		
		summaryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				summaryMethod();
			}
		}); 
		
		deleteCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteCustomerMethod();
			}
		}); 
		
		
		deleteAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteAccountMethod();
			}
		}); 
		
		returnButton.addActionListener(new ActionListener(  ) {
			public void actionPerformed(ActionEvent ae) {
				returnButtonMethod(f);				
			}
	     });
		
		f.setVisible(true);
	}
	
	
	//return button
	private void returnButtonMethod(JFrame f) {
		f.dispose();		
		menuStart();
	}
	
	
	//create jbuttons & set size
	private JButton createButton(String text) {
	    JButton b = new JButton(text);
	    b.setPreferredSize(new Dimension(250, 20));
	    return b;
	}

	//set flowlayout
	private JPanel flowLeft(JButton b, boolean left) {
		JPanel p = new JPanel();
		if (!left) {
			p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		} else {
			p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		}
	    p.add(b);
	    return p;
	}
	
	
	//reusable get customers methid
	private Customer getACustomer(String message) {
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
	        
	        Customer customer = findCustomerById(customerId);
	        if (customer != null) {
	        	return customer;
	        }
	        
	        int reply = JOptionPane.showConfirmDialog(f, "User not found. Try again?", null, JOptionPane.YES_NO_OPTION);
	        
	        if (reply == JOptionPane.NO_OPTION) {
	        	return null;
	        }
		}
	}
	
	
	//----get customer account method--
	private CustomerAccount getCustomersAccount(Customer customer, String message) {
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
	
	//-----bank charges method-------
	private void bankChargesMethod() {
		Customer customer = getACustomer("Customer ID of Customer You Wish to Apply Charges to:");
	    if (customer == null) {
	    	admin();
	        return;
	    }
	    
	    if (customer.getAccounts().isEmpty()) {
	        JOptionPane.showMessageDialog(f, "This customer has no accounts! \n The admin must add acounts to this customer.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
	        admin();
	        return;
	    }

	    CustomerAccount customerAccount = getCustomersAccount(customer, "Select an account to apply charges to:");
	    if (customerAccount == null) {
	    	admin();
	        return;
	    }
	    
	    //String euro = "\u20ac";
	    String msg = customerAccount.applyBankCharge();
	    JOptionPane.showMessageDialog(f, msg  ,"",  JOptionPane.INFORMATION_MESSAGE);

	    JOptionPane.showMessageDialog(f, "New balance = " + customerAccount.getBalance(), "Success!", JOptionPane.INFORMATION_MESSAGE);
	    admin();
	}
	
	//----interest method---
	private void interestMethod() {
	    Customer customer = getACustomer("Customer ID of Customer You Wish to Apply Interest to:");
	    if (customer == null) {
	        admin();
	        return;
	    }

	    if (customer.getAccounts().isEmpty()) {
	        JOptionPane.showMessageDialog(f, "This customer has no accounts! \n The admin must add acounts to this customer.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
	        admin();
	        return;
	    }

	    CustomerAccount account = getCustomersAccount(customer, "Select an account to apply interest to:");
	    if (account == null) {
	    	admin();
	        return;
	    }

	    if (!(account instanceof CustomerDepositAccount)) {
	        JOptionPane.showMessageDialog(f, "To apply interest you must choose a deposit account.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
	        admin();
	        return;
	    }

	    Double interest = enterInterest("Enter interest percentage you wish to apply: \n NOTE: Please enter a numerical value. (with no percentage sign) \n E.g: If you wish to apply 8% interest, enter '8'");
	    if (interest == null) {
	    	admin();
	        return;
	    }

	    account.setBalance(account.getBalance() + (account.getBalance() * (interest / 100.0)));
	    JOptionPane.showMessageDialog(f, interest + "% interest applied. New balance = " + account.getBalance(), "Success!", JOptionPane.INFORMATION_MESSAGE);

	    admin();
	}
	
	private Double enterInterest(String message) {
	    while (true) {
	    	String interestString = JOptionPane.showInputDialog(f, message);
	        if (interestString == null) {
	        	return null;
	        }
	        if (isNumeric(interestString)) {
	        	Double interest = Double.parseDouble(interestString);
				return interest;
				
	        } else {
	        	JOptionPane.showMessageDialog(f, "You must enter a numerical value!" ,"Oops!",  JOptionPane.INFORMATION_MESSAGE);
			}
	        
	    }
	}
	
	//----delete customer method---
	private void deleteCustomerMethod() {
	    Customer customer = getACustomer("Customer ID of Customer You Wish to Delete:");
	    if (customer == null) {
	        admin();
	        return;
	    }

	    if (!customer.getAccounts().isEmpty()) {
	        JOptionPane.showMessageDialog(f, "This customer has accounts. \n You must delete a customer's accounts before deleting a customer " ,"Oops!",  JOptionPane.INFORMATION_MESSAGE);
	        admin();
	        return;
	    }

	    customerList.remove(customer);
	    JOptionPane.showMessageDialog(f, "Customer Deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
	    admin();
	}
	
	
	//---delete account method--
	private void deleteAccountMethod() {

	    Customer customer = getACustomer("Customer ID of Customer from which you wish to delete an account:");
	    if (customer == null) {
	        admin();
	        return;
	    }

	    if (customer.getAccounts().isEmpty()) {
	        JOptionPane.showMessageDialog(f, "This customer has no accounts to delete.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
	        admin();
	        return;
	    }

	    CustomerAccount account = getCustomersAccount(customer, "Select an account to delete:");
	    if (account == null) {
	        admin();
	        return;
	    }

	    if (account.getBalance() != 0.0) {
	        JOptionPane.showMessageDialog(f,"Account cannot be deleted unless the balance is 0.\nCurrent balance: " + account.getBalance(),"Oops!",JOptionPane.INFORMATION_MESSAGE);
	        admin();
	        return;
	    }

	    customer.getAccounts().remove(account);
	    JOptionPane.showMessageDialog(f, "Account deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
	   
	    admin();
	}
	
	//----edit customer method----
	private void editCustomerMethod() {
	    Customer customer = getACustomer("Enter Customer ID:");
	    if (customer == null) {
	        admin();
	        return;
	    }
	    
	    f.dispose();

	    f = new JFrame("Administrator Menu");
	    f.setSize(400, 300);
	    f.setLocation(200, 200);
	    f.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent we) { System.exit(0); }
	    });

	    firstNameLabel = new JLabel("First Name:", SwingConstants.LEFT);
	    surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
	    pPPSLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
	    dOBLabel = new JLabel("Date of birth", SwingConstants.LEFT);
	    customerIDLabel = new JLabel("CustomerID:", SwingConstants.LEFT);
	    passwordLabel = new JLabel("Password:", SwingConstants.LEFT);

	    firstNameTextField = new JTextField(20);
	    surnameTextField = new JTextField(20);
	    pPSTextField = new JTextField(20);
	    dOBTextField = new JTextField(20);
	    customerIDTextField = new JTextField(20);
	    passwordTextField = new JTextField(20);

	    JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		JPanel cancelPanel = new JPanel();

	    textPanel.add(firstNameLabel); 
	    textPanel.add(firstNameTextField);
	    textPanel.add(surnameLabel);   
	    textPanel.add(surnameTextField);
	    textPanel.add(pPPSLabel);      
	    textPanel.add(pPSTextField);
	    textPanel.add(dOBLabel);       
	    textPanel.add(dOBTextField);
	    textPanel.add(customerIDLabel);
	    textPanel.add(customerIDTextField);
	    textPanel.add(passwordLabel);  
	    textPanel.add(passwordTextField);
	    
	    firstNameTextField.setText(customer.getFirstName());
	    surnameTextField.setText(customer.getSurname());
	    pPSTextField.setText(customer.getPPS());
	    dOBTextField.setText(customer.getDOB());
	    customerIDTextField.setText(customer.getCustomerID());
	    passwordTextField.setText(customer.getPassword());

	    JButton saveButton = new JButton("Save");
	    JButton cancelButton = new JButton("Exit");

	    JPanel buttonPanel = new JPanel();
	    buttonPanel.add(saveButton);
	    buttonPanel.add(cancelButton);

	    Container content = f.getContentPane();
	    content.setLayout(new GridLayout(2, 1));
	    content.add(textPanel);
	    content.add(buttonPanel);

	    saveButton.addActionListener(new ActionListener(  ) {
			public void actionPerformed(ActionEvent ae) {
				setCustomerEdits(customer);
				JOptionPane.showMessageDialog(f, "Changes Saved.");
			}
	    });

	    cancelButton.addActionListener(new ActionListener(  ) {
			public void actionPerformed(ActionEvent ae) {
				f.dispose();
				admin();
			}
	    });

	    f.setVisible(true);
	}
	
	
	//---set edited customer details---
	private void setCustomerEdits(Customer customer) {
	    customer.setFirstName(firstNameTextField.getText());
	    customer.setSurname(surnameTextField.getText());
	    customer.setPPS(pPSTextField.getText());
	    customer.setDOB(dOBTextField.getText());
	    customer.setCustomerID(customerIDTextField.getText());
	    customer.setPassword(passwordTextField.getText());
	}
	
	
	//---summmary method----
	private void summaryMethod() {
	    f.dispose();

	    f = new JFrame("Summary of Transactions");
	    f.setSize(400, 700);
	    f.setLocation(200, 200);
	    f.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent we) { System.exit(0); }
	    });
	    

	    JLabel label1 = new JLabel("Summary of all transactions:");
	    
	    JButton returnButton = new JButton("Return");
	    returnButton.addActionListener(new ActionListener(  ) {
			public void actionPerformed(ActionEvent ae) {
				f.dispose();
				admin();
			}
	    });

	    JTextArea textArea = new JTextArea(40, 20);
	    textArea.setEditable(false);
	    textArea.setText(summaryBuilder());


	    JPanel panel = new JPanel(new BorderLayout());
	    panel.add(label1, BorderLayout.NORTH);
	    panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
	    panel.add(returnButton, BorderLayout.SOUTH);

	    f.setContentPane(panel);
	    f.setVisible(true);
	}
	
	//---summaryBuilder--
	private String summaryBuilder() {
	    StringBuilder sb = new StringBuilder();

	    for (int a = 0; a < customerList.size(); a++)//For each customer, for each account, it displays each transaction.
		{
			for (int b = 0; b < customerList.get(a).getAccounts().size(); b ++ )
			{
				acc = customerList.get(a).getAccounts().get(b);
				for (int c = 0; c < customerList.get(a).getAccounts().get(b).getTransactionList().size(); c++)
				{
					sb.append(acc.getTransactionList().get(c).toString());
					//Int total = acc.getTransactionList().get(c).getAmount(); //I was going to use this to keep a running total but I couldnt get it  working.
					
				}				
			}				
		}

	    return sb.toString();
	}
	
	
	//--navigate method--
	private void navigateMethod() {
	    f.dispose();

	    if (customerList.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "There are currently no customers to display.");
	        admin();
	        return;
	    }

	    JButton first, previous, next, last, cancel;
		JPanel gridPanel, buttonPanel, cancelPanel;			

		Container content = getContentPane();
		
		content.setLayout(new BorderLayout());
		
		buttonPanel = new JPanel();
		gridPanel = new JPanel(new GridLayout(8, 2));
		cancelPanel = new JPanel();
						
		firstNameLabel = new JLabel("First Name:", SwingConstants.LEFT);
		surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
		pPPSLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
		dOBLabel = new JLabel("Date of birth", SwingConstants.LEFT);
		customerIDLabel = new JLabel("CustomerID:", SwingConstants.LEFT);
		passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
		firstNameTextField = new JTextField(20);
		surnameTextField = new JTextField(20);
		pPSTextField = new JTextField(20);
		dOBTextField = new JTextField(20);
		customerIDTextField = new JTextField(20);
		passwordTextField = new JTextField(20);
		
		first = new JButton("First");
		previous = new JButton("Previous");
		next = new JButton("Next");
		last = new JButton("Last");
		cancel = new JButton("Cancel");
		
		firstNameTextField.setText(customerList.get(0).getFirstName());
		surnameTextField.setText(customerList.get(0).getSurname());
		pPSTextField.setText(customerList.get(0).getPPS());
		dOBTextField.setText(customerList.get(0).getDOB());
		customerIDTextField.setText(customerList.get(0).getCustomerID());
		passwordTextField.setText(customerList.get(0).getPassword());
		
		firstNameTextField.setEditable(false);
		surnameTextField.setEditable(false);
		pPSTextField.setEditable(false);
		dOBTextField.setEditable(false);
		customerIDTextField.setEditable(false);
		passwordTextField.setEditable(false);
		
		gridPanel.add(firstNameLabel);
		gridPanel.add(firstNameTextField);
		gridPanel.add(surnameLabel);
		gridPanel.add(surnameTextField);
		gridPanel.add(pPPSLabel);
		gridPanel.add(pPSTextField);
		gridPanel.add(dOBLabel);
		gridPanel.add(dOBTextField);
		gridPanel.add(customerIDLabel);
		gridPanel.add(customerIDTextField);
		gridPanel.add(passwordLabel);
		gridPanel.add(passwordTextField);
		
		buttonPanel.add(first);
		buttonPanel.add(previous);
		buttonPanel.add(next);
		buttonPanel.add(last);
		
		cancelPanel.add(cancel);

		content.add(gridPanel, BorderLayout.NORTH);
		content.add(buttonPanel, BorderLayout.CENTER);
		content.add(cancelPanel, BorderLayout.AFTER_LAST_LINE);

	    position = 0;
	    displayCustomerAtPosition(position);

	    first.addActionListener(new ActionListener(  ) {
			public void actionPerformed(ActionEvent ae) {
		        position = 0;
		        displayCustomerAtPosition(position);
			}
	    });

	    previous.addActionListener(new ActionListener(  ) {
			public void actionPerformed(ActionEvent ae) {
		        if (position > 0) position--;
		        displayCustomerAtPosition(position);
			}
	    });

	    next.addActionListener(new ActionListener(  ) {
			public void actionPerformed(ActionEvent ae) {
		        if (position < customerList.size() - 1) position++;
		        displayCustomerAtPosition(position);
			}
		  });

	    last.addActionListener(new ActionListener(  ) {
			public void actionPerformed(ActionEvent ae) {
		        position = customerList.size() - 1;
		        displayCustomerAtPosition(position);
			}
		  });

	    cancel.addActionListener(new ActionListener(  ) {
			public void actionPerformed(ActionEvent ae) {
				dispose();
		        admin();
			}
	    });

	    setVisible(true);
	}

	private void displayCustomerAtPosition(int pos) {
	    Customer c = customerList.get(pos);
	    firstNameTextField.setText(c.getFirstName());
	    surnameTextField.setText(c.getSurname());
	    pPSTextField.setText(c.getPPS());
	    dOBTextField.setText(c.getDOB());
	    customerIDTextField.setText(c.getCustomerID());
	    passwordTextField.setText(c.getPassword());
	}

	
	
	//---account method----
	private void accountMethod() {
		f.dispose();

	    Customer customer = getACustomer("Customer ID of Customer You Wish to Add an Account to:");
	    if (customer == null) {
	        admin();
	        return;
	    }

	    String type = selectAccountType();
	    if (type == null) {
	        admin();
	        return;
	    }

	    if (type.equals("Current Account")) {
	        createCurrentAccount(customer);
	    } else if (type.equals("Deposit Account")) {
	        createDepositAccount(customer);
	    }

	    admin();
	}
	
	//--select account type to create
	private String selectAccountType() {
		String[] choices = { "Current Account", "Deposit Account" };
	    return (String) JOptionPane.showInputDialog(f,"Please choose account type",
	            "Account Type",
	            JOptionPane.QUESTION_MESSAGE,null, choices,choices[1]);
	}
	
	//---create current account---
	private void createCurrentAccount(Customer customer) {
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
	private void createDepositAccount(Customer customer) {
		double balance = 0, interest = 0;
    	String number = String.valueOf("D" + (customerList.indexOf(customer)+1) * 10 + (customer.getAccounts().size()+1));//this simple algorithm generates the account number
    	ArrayList<AccountTransaction> transactionList = new ArrayList<AccountTransaction>();
        	
    	CustomerDepositAccount deposit = new CustomerDepositAccount(interest, number, balance, transactionList);
    	
    	customer.getAccounts().add(deposit);
    	JOptionPane.showMessageDialog(f, "Account number = " + number ,"Account created.",  JOptionPane.INFORMATION_MESSAGE);
	}

	
	
	//-----customer------
	
	public void customer(Customer e1)
	{	
		e = e1;
		if(e.getAccounts().isEmpty())
		{
			JOptionPane.showMessageDialog(f, "This customer does not have any accounts yet. \n An admin must create an account for this customer \n for them to be able to use customer functionality. " ,"Oops!",  JOptionPane.INFORMATION_MESSAGE);
			if (f != null) {
				f.dispose();
			}				
			menuStart();
			return;
		}
		selectAnAccount(e);
	}
	
	//---select accout method----
	private void selectAnAccount(Customer customer) {
		if (f != null) {
			f.dispose();
		}
		e = customer;
		f = new JFrame("Customer Menu");
		f.setSize(400, 300);
		f.setLocation(200, 200);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) { System.exit(0); }
		});          
		f.setVisible(true);
		
		JPanel buttonPanel = new JPanel();
		JPanel boxPanel = new JPanel();
		JPanel labelPanel = new JPanel();
		
		JLabel label = new JLabel("Select Account:");
		labelPanel.add(label);
		
		JButton returnButton = new JButton("Return");
		buttonPanel.add(returnButton);
		JButton continueButton = new JButton("Continue");
		buttonPanel.add(continueButton);
		
		JComboBox<String> box = new JComboBox<String>();
	    for (int i =0; i < e.getAccounts().size(); i++)
	    {
	     box.addItem(e.getAccounts().get(i).getNumber());
	    }
		
	    
		boxPanel.add(box);
		content = f.getContentPane();
		content.setLayout(new GridLayout(3, 1));
		content.add(labelPanel);
		content.add(boxPanel);
		content.add(buttonPanel);
		
		returnButton.addActionListener(new ActionListener(  ) {
			public void actionPerformed(ActionEvent ae) {
			returnButtonMethod(f);		
			}		
	     });
		
		continueButton.addActionListener(new ActionListener(  ) {
			public void actionPerformed(ActionEvent ae) {
				if (box.getSelectedItem() == null) {
					return;
				}
				CustomerAccount selected = findAccountByNumber(customer, box.getSelectedItem().toString());
		        if (selected == null) {
		            return;
		        }
		        openCustomerMenu(customer, selected);
			}
		});
	}
	
	//---get selected account---
	private CustomerAccount findAccountByNumber(Customer customer, String number) {
    	for (int i =0; i < customer.getAccounts().size(); i++)
    		if(customer.getAccounts().get(i).getNumber().equals(number)) {
    			return customer.getAccounts().get(i);
	    }
	    return null;
	}
	
	
	//---open customer menu----
	private void openCustomerMenu(Customer customer, CustomerAccount acc){
		f.dispose();
		
		f = new JFrame("Customer Menu");
		f.setSize(400, 300);
		f.setLocation(200, 200);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) { System.exit(0); }
		});          
		f.setVisible(true);
		
		JButton statementButton = createButton("Display Bank Statement");
	    JButton lodgementButton = createButton("Lodge money into account");
	    JButton withdrawButton = createButton("Withdraw money from account");
	    JButton returnButton = new JButton("Exit Customer Menu");
	    
	    JLabel label1 = new JLabel("Please select an option");
	    
	    content = f.getContentPane();
		content.setLayout(new GridLayout(5, 1));
		content.add(label1);
		content.add(flowLeft(statementButton, true));
		content.add(flowLeft(lodgementButton, true));
		content.add(flowLeft(withdrawButton, true));
		content.add(flowLeft(returnButton, false));
		
		statementButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statementMethod(customer, acc);
			}
		}); 
		
		lodgementButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lodgementMethod(customer, acc);
			}
		}); 
		
		withdrawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				withdrawMethod(customer, acc);
			}
		}); 
		
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnButtonMethod(f);
			}
		}); 
	}
	
	//----- statment method------
	private void statementMethod(Customer customer, CustomerAccount account) {
		f.dispose();
		f = new JFrame("Customer Menu");
		f.setSize(400, 600);
		f.setLocation(200, 200);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) { System.exit(0); }
		});          
		f.setVisible(true);
		
		JLabel label1 = new JLabel("Summary of account transactions: ");
		
		JPanel returnPanel = new JPanel();
		JButton returnButton = new JButton("Return");
		returnPanel.add(returnButton);
		
		JPanel textPanel = new JPanel();
		
		textPanel.setLayout( new BorderLayout() );
		JTextArea textArea = new JTextArea(40, 20);
		textArea.setEditable(false);
		textPanel.add(label1, BorderLayout.NORTH);
		textPanel.add(textArea, BorderLayout.CENTER);
		textPanel.add(returnButton, BorderLayout.SOUTH);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		textPanel.add(scrollPane);
		
		if (account.getTransactionList() != null) {
			for (int i = 0; i < account.getTransactionList().size(); i ++)
			{
				textArea.append(account.getTransactionList().get(i).toString());
			}
		}
		
		textPanel.add(textArea);
		content.removeAll();
		
		
		Container content = f.getContentPane();
		content.setLayout(new GridLayout(1, 1));
	//	content.add(label1);
		content.add(textPanel);
		//content.add(returnPanel);
		
		returnButton.addActionListener(new ActionListener(  ) {
			public void actionPerformed(ActionEvent ae) {
				f.dispose();
		        openCustomerMenu(customer, account);	
			}		
	     });		
	}
	
	
	//----- lodgement method-----
	
	private void lodgementMethod(Customer customer, CustomerAccount account) {
	    if (!checkPin(account)) {
	        openCustomerMenu(customer, account);
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
	    account.setBalance(account.getBalance() + balance);
	    
	    account.addTransaction("Lodgement", balance);
		
		JOptionPane.showMessageDialog(f, balance + euro + " added to your account!" ,"Lodgement",  JOptionPane.INFORMATION_MESSAGE);
		 JOptionPane.showMessageDialog(f, "New balance = " + account.getBalance() + euro ,"Lodgement",  JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	//----method to check pin----
	private boolean checkPin(CustomerAccount account) {
		if(!account.pinNeeded()) {
			return true;
		}
		
		CustomerCurrentAccount acc = (CustomerCurrentAccount) account;
		int checkPin = acc.getAtm().getPin();
		int count = 3;
		
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
	private void withdrawMethod(Customer customer, CustomerAccount account) {
	    if (!checkPin(account)) {
	        openCustomerMenu(customer, account);
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
		
		if(withdraw > 500) {
			JOptionPane.showMessageDialog(f, "500 is the maximum you can withdraw at a time." ,"Oops!",  JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if(!account.canWithdrawAmount(withdraw)) {
			JOptionPane.showMessageDialog(f, "Insufficient funds." ,"Oops!",  JOptionPane.INFORMATION_MESSAGE);
			return;
		}

	    String euro = "\u20ac";
	    account.setBalance(account.getBalance()-withdraw);
	    
	    account.addTransaction("Withdraw", withdraw);
		
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
}

