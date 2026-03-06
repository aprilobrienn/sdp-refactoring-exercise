import java.util.ArrayList; 

public abstract class CustomerAccount  {
   
	private String number;
	private double balance;
	private ArrayList<AccountTransaction> transactionList = new ArrayList<AccountTransaction>();

	//Blank Constructor
	public CustomerAccount()
	{
		this.number = "";
		this.balance = 0;
		this.transactionList = null;
	}
	
	//Constructor with Details
	public CustomerAccount(String number, double balance, ArrayList<AccountTransaction> transactionList)
	{
		this.number = number;
		this.balance = balance;
		this.transactionList = transactionList;
	}
	
	//Accessor methods
	
	public String getNumber()
	{
		return this.number;
	}
	
	public double getBalance()
	{
		return this.balance;
	}
	
	public ArrayList<AccountTransaction> getTransactionList()
	{
		return this.transactionList;
	}

	//Mutator methods
	public void setNumber(String number)
	{
		this.number = number;
	}
	
	public void setBalance(double balance)
	{
		this.balance = balance;
	}
	
	public void setTransactionList(ArrayList<AccountTransaction> transactionList)
	{
		this.transactionList = transactionList;
	}
	
	
	//---apply bank charges---
	public abstract String applyBankCharge();
	
	//---pin needed method---
	public boolean pinNeeded() {
	    return false;
	}
	
	//--- withdraw amount method---
	public boolean canWithdrawAmount(double amount) {
	    if (amount <= getBalance()) {
	    	setBalance(getBalance() - amount);
	    	addTransaction("Withdraw", amount);
	    	return true;
	    }
	    return false;
	}
	
	//--- add transaction method---
	public void addTransaction(String type, double amount) {
	    String date = new java.util.Date().toString();
	    if (getTransactionList() != null) {
	        getTransactionList().add(new AccountTransaction(date, type, amount));
	    }
	}
	
	//--can apply interest--
	public boolean canApplyInterest() {
	    return false;
	}
	
	//---lodge amount method---
	public void lodge(double amount) {
        setBalance(getBalance() + amount);
        addTransaction("Lodgement", amount);
    }

	
	
	
}
