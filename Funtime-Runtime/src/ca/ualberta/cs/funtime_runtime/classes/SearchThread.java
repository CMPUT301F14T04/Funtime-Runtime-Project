package ca.ualberta.cs.funtime_runtime.classes;

import android.content.Context;


public class SearchThread extends Thread
{
	private String search;
	private Context context;
	
	public SearchThread(String s){		
		search = s;
	}
	
	@Override
	public void run() {
		ApplicationState.loadBySearch(search);	
	}
}
