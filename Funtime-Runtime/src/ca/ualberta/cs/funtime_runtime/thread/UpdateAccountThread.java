package ca.ualberta.cs.funtime_runtime.thread;

import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.elastic.ESAccountManager;
/**
 * Thread used to update account information via ESAccountManager,
 * used any time the user does a meaningful action in the application
 * @author bsmolley
 *
 */
public class UpdateAccountThread extends Thread {
	
	private Account account;
	private ESAccountManager manager = new ESAccountManager();
	
	public UpdateAccountThread(Account a){		
		account = a;
	}
	
	/**
	 * Runs the ESAccountManager update function
	 */
	@Override
	public void run() {
		updateAccount(account);
	}

	private void updateAccount(Account account) {
		manager.deleteAccount(account);
		manager.addAccount(account);	
	}

}
