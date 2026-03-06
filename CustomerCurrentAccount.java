import java.util.ArrayList;

public class CustomerCurrentAccount extends CustomerAccount 
{
	ATMCard atm;
	
public CustomerCurrentAccount()
{
	super();
	this.atm = null;
	
}

public CustomerCurrentAccount(ATMCard atm, String number, double balance, ArrayList<AccountTransaction> transactionList)
{
	super(number, balance, transactionList);	
	this.atm = atm;
}

public ATMCard getAtm()
{
	return this.atm;
}

public void setAtm(ATMCard atm)
{
	this.atm = atm;
}

//---apply bank charge---
public String applyBankCharge() {
    setBalance(getBalance() - 15);
    return "15" + "\u20ac" + " current account fee applied.";
}

//--- pin needed---
public boolean pinNeeded() {
    return true;
}


}