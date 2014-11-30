package ca.ualberta.cs.funtime_runtime.thread;

import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.elastic.ESAccountManager;

/**
 * This thread is used to add an Account made locally and store it 
 * on the server
 * @author bsmolley
 *
 */
public class AddAccountThread extends Thread {
	private Account account;
	private ESAccountManager manager = new ESAccountManager();

	/**
	 * Associated the account
	 * @param account
	 */
	public AddAccountThread(Account account) {
		this.account = account;
	}

	/**
	 * Send the account to the ESAccountManager for server addition
	 */
	@Override
	public void run() {
		manager.addAccount(account);

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
