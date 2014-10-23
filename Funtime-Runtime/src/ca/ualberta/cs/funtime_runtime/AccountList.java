package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

public class AccountList{

	private static ArrayList<Account> accountList;
	
	public AccountList(){
		accountList = new ArrayList<Account>();
	}

	public static void add(Account account) {
		accountList.add(account);
	}

	public int size() {
		return accountList.size();
	}

	public Account get(int i) {
		return accountList.get(i);
	}

}