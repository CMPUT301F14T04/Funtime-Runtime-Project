package ca.ualberta.cs.funtime_runtime;

import android.app.Application;

public class ApplicationState extends Application {
	// adapted from http://stackoverflow.com/questions/708012/how-to-declare-global-variables-in-android - Accessed Oct 22 2014
	
	private Account account;
	private AccountList accountList = new AccountList();
	private boolean loggedIn = false;
	
	public void setAccount(Account newAccount) {
		account = newAccount;
		loggedIn = true;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public void setAccountList(AccountList newList) {
		accountList = newList;
	}
	
	public AccountList getAccountList() {
		return accountList;
	}
	
	public boolean isLoggedIn() {
		return loggedIn;
	}
}
