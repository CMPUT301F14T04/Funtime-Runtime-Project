package ca.ualberta.cs.funtime_runtime.thread;

import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;


public class SearchQuestionThread extends Thread {
	private String search;
	
	public SearchQuestionThread(String s){		
		search = s;
	}
	
	@Override
	public void run() {
		ApplicationState.loadBySearch(search);	
	}
}
