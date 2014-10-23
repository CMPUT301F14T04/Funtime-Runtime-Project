package ca.ualberta.cs.funtime_runtime;

import android.app.Application;

public class ApplicationState extends Application {
	// adapted from http://stackoverflow.com/questions/708012/how-to-declare-global-variables-in-android - Accessed Oct 22 2014
	
	private static Account account;
	private AccountList accountList;
	
	public void setAccount(Account newAccount) {
		account = newAccount;
	}
	
	public static Account getAccount() {
		return account;
	}
	
	public void setAccountList(AccountList newList) {
		accountList = newList;
	}
	
	public AccountList getAccountList() {
		return accountList;
	}
	
}
