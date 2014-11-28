package ca.ualberta.cs.funtime_runtime.thread;

import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;

public class SearchAccountThread extends Thread {
	private String search;
	
	public SearchAccountThread(String s){		
		search = s;
	}
	
	@Override
	public void run() {
		ApplicationState.loadAccounts(search);	
	}
}
