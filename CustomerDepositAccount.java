import java.util.ArrayList;

public class CustomerDepositAccount extends CustomerAccount
{
   double interestRate;

public CustomerDepositAccount()
{
	super();
	this.interestRate = 0;
}

public CustomerDepositAccount(double interestRate, String number, double balance, ArrayList<AccountTransaction> transactionList)
{
	super(number, balance, transactionList);	
	this.interestRate = interestRate;
}

public double getInterestRate()
{
	return this.interestRate;
}

public void setInterestRate(double interestRate)
{
	this.interestRate = interestRate;
}

//---apply bank charge--
public String applyBankCharge() {
    setBalance(getBalance() - 25);
    return "25" + "\u20ac" + " deposit account fee applied.";
}



}