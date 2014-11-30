package ca.ualberta.cs.funtime_runtime.thread;

import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;
/**
 * Thread used to search the server for existing accounts, mainly
 * used for Login and Create Account checking to prevent repeat
 * accounts
 * @author bsmolley
 *
 */
public class SearchAccountThread extends Thread {
	private String search;
	
	public SearchAccountThread(String s){		
		search = s;
	}
	
	/**
	 * Load the account from the server
	 */
	@Override
	public void run() {
		ApplicationState.loadAccounts(search);
	}
}
