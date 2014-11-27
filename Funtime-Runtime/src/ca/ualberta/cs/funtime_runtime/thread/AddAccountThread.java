package ca.ualberta.cs.funtime_runtime.thread;

import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.elastic.ESAccountManager;

public class AddAccountThread extends Thread {
	private Account account;
	private ESAccountManager manager = new ESAccountManager();

	public AddAccountThread(Account account) {
		this.account = account;
	}

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
