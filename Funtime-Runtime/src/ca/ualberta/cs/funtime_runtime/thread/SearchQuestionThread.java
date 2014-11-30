package ca.ualberta.cs.funtime_runtime.thread;

import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;

/**
 * Thread that does a general load of server questions for local viewing
 * @author bsmolley
 *
 */

public class SearchQuestionThread extends Thread {
	private String search;
	
	public SearchQuestionThread(String s){		
		search = s;
	}
	
	/**
	 * Loads server questions into the main app question list
	 * for viewing and modifying
	 */
	@Override
	public void run() {
		ApplicationState.loadBySearch(search);	
	}
}
