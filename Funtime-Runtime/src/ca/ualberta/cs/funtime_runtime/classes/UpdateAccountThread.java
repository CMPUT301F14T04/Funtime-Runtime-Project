package ca.ualberta.cs.funtime_runtime.classes;

import ca.ualberta.cs.funtime_runtime.elastic.ESAccountManager;

public class UpdateAccountThread extends Thread {
	
	private Account account;
	private ESAccountManager manager = new ESAccountManager();
	
	public UpdateAccountThread(Account a){		
		account = a;
	}
	
	@Override
	public void run() {
		updateAccount(account);
	}

	private void updateAccount(Account account) {
		manager.deleteAccount(account.getId());
		manager.addAccount(account);	
	}

}
