package ca.ualberta.cs.funtime_runtime;

import android.app.Application;

public class ApplicationState extends Application {
	// adapted from http://stackoverflow.com/questions/708012/how-to-declare-global-variables-in-android - Accessed Oct 22 2014
	
	private static Account account;
	
	// TODO pull accountList from server
	private static AccountList accountList = new AccountList();
	private static boolean loggedIn = false;
	
	public static void setAccount(Account newAccount) {
		account = newAccount;
		loggedIn = true;
	}
	
	public static Account getAccount() {
		return account;
	}
	
	public static void setAccountList(AccountList newList) {
		accountList = newList;
	}
	
	public static AccountList getAccountList() {
		return accountList;
	}
	
	public static void addAccount(Account newAccount) {
		accountList.add(newAccount);
	}
	
	public static boolean isLoggedIn() {
		return loggedIn;
	}
}
