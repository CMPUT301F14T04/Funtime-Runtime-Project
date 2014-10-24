package ca.ualberta.cs.funtime_runtime;

import android.app.Application;

public class ApplicationState extends Application {
	// adapted from http://stackoverflow.com/questions/708012/how-to-declare-global-variables-in-android - Accessed Oct 22 2014
	
	private static Account account;
	private static AccountList accountList;
	private static boolean loggedIn = false;
	
	public void setAccount(Account newAccount) {
		account = newAccount;
		loggedIn = true;
	}
	
	public static Account getAccount() {
		return account;
	}
	
	public void setAccountList(AccountList newList) {
		accountList = newList;
	}
	
	public static AccountList getAccountList() {
		return accountList;
	}
	
	public static boolean isLoggedIn() {
		return loggedIn;
	}
}
