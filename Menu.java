import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Menu extends JFrame{
	private CustomerActions customerActions = new CustomerActions();
	private AdminActions adminActions = new AdminActions();
	private AccountActions accountActions = new AccountActions();
	private static final String ADMIN_USERNAME = "admin";
	private static final String ADMIN_PASS = "admin11";
	//private static final int PIN_ATTEMPS = 3;
	//private static final int MAX_WITHDRAWL = 500;
	//private static final int PASSWORD_LENGTH = 7;
	private ArrayList<Customer> customerList = new ArrayList<Customer>();
    private int position = 0;
	private Customer customer = null;
	private CustomerAccount acc;
	JFrame f, f1;
	 JLabel firstNameLabel, surnameLabel, pPPSLabel, dOBLabel;
	 JTextField firstNameTextField, surnameTextField, pPSTextField, dOBTextField;
		JLabel customerIDLabel, passwordLabel;
		JTextField customerIDTextField, passwordTextField;
	Container content;
		Customer e;
	
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
						
					JPanel panel2 = new JPanel();
					
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
				if (!customerActions.createNewCustomer(pPSTextField.getText(), firstNameTextField.getText(), surnameTextField.getText(), dOBTextField.getText(), f, customerList)) {
					return;
				}
				f1.dispose();
			    menuStart();
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
				Customer customer = customerActions.getExistingCustomer(f, customerList);
				
				if (customer == null) {
					return;
				}
				
				boolean validPassword = customerActions.checkCustomerPassword(f, customer);
			    if (!validPassword)  {
			    	return;
			    }
			    
			    if (f != null) {
			        f.dispose();
			    }
			    
			    customer(customer);
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
				adminActions.bankChargesMethod(f, customerList);
				admin();
			}
		}); 
		
		editCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editCustomerMethod();
			}
		}); 
		
		interestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminActions.interestMethod(f, customerList);
				admin();
			}
		}); 
		
		accountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminActions.accountMethod(f, customerList);
				admin();
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
				customerActions.deleteCustomerMethod(customerList, f);
				admin();
			}
		}); 
		
		
		deleteAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accountActions.deleteAccountMethod(customerList, f);
				admin();
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
	
	//----edit customer method----
	private void editCustomerMethod() {
	    Customer customer = customerActions.getACustomer("Enter Customer ID:", customerList, f);
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
	    textArea.setText(accountActions.summaryBuilder(customerList));


	    JPanel panel = new JPanel(new BorderLayout());
	    panel.add(label1, BorderLayout.NORTH);
	    panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
	    panel.add(returnButton, BorderLayout.SOUTH);

	    f.setContentPane(panel);
	    f.setVisible(true);
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


	
	
	//-----customer------
	
	public void customer(Customer customer)
	{
		if(customer.getAccounts().isEmpty())
		{
			JOptionPane.showMessageDialog(f, "This customer does not have any accounts yet. \n An admin must create an account for this customer \n for them to be able to use customer functionality. " ,"Oops!",  JOptionPane.INFORMATION_MESSAGE);
			if (f != null) {
				f.dispose();
			}				
			menuStart();
			return;
		}
		selectAnAccount(customer);
	}
	
	//---select accout method----
	private void selectAnAccount(Customer customer) {
		if (f != null) {
			f.dispose();
		}
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
		for (CustomerAccount acc : e.getAccounts()) {
	    	box.addItem(acc.getNumber());
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
				CustomerAccount selected = accountActions.findAccountByNumber(customer, box.getSelectedItem().toString());
		        if (selected == null) {
		            return;
		        }
		        openCustomerMenu(customer, selected);
			}
		});
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
				accountActions.lodgementMethod(customer, acc, f);
				openCustomerMenu(customer, acc);
			}
		}); 
		
		withdrawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accountActions.withdrawMethod(customer, acc, f);
				openCustomerMenu(customer, acc);
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
			for (AccountTransaction transaction : account.getTransactionList())
			{
				textArea.append(transaction.toString());
			}
		}
		
		textPanel.add(textArea);
		content.removeAll();
		
		
		Container content = f.getContentPane();
		content.setLayout(new GridLayout(1, 1));
		content.add(textPanel);
		
		returnButton.addActionListener(new ActionListener(  ) {
			public void actionPerformed(ActionEvent ae) {
				f.dispose();
		        openCustomerMenu(customer, account);	
			}		
	     });		
	}
}

