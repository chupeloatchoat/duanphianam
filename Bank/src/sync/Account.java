package sync;

import java.time.Clock;

public class Account {
	private String accNo;
	private String name;
	private double balance = 100.0;

	public Account(String accNo, String name) {
		super();
		this.accNo = accNo;
		this.name = name;
	}

	public String getAccNo() {
		return accNo;
	}

	public String getName() {
		return name;
	}

	public double getBalance() {
		return balance;
	}
	
	public synchronized void deposit(double amount) {
		if(amount > 0) {
			balance += amount;
		}
	}
	public synchronized double withdraw(double amount) {
		if(amount > balance)
			return 0;
		balance -= amount;
		return amount;
	}
}
