package ca.ualberta.cs.funtime_runtime.classes;

import android.content.Context;

public class SearchAccountThread extends Thread {
	private String search;
	private Context context;
	
	public SearchAccountThread(String s){		
		search = s;
	}
	
	@Override
	public void run() {
		ApplicationState.loadAccounts(search);	
	}
}
