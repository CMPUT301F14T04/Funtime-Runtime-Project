package ca.ualberta.cs.funtime_runtime.thread;

import java.util.ArrayList;

import android.app.Activity;
import ca.ualberta.cs.funtime_runtime.adapter.QuestionListAdapter;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.elastic.ESQuestionManager;

public class SearchActivityThread extends Thread {
	private String search;
	private Activity activity;
	private ESQuestionManager manager = new ESQuestionManager();;
	private ArrayList<Question> questionList;
	private QuestionListAdapter adapter;
	
	public SearchActivityThread(String query, ArrayList<Question> list, QuestionListAdapter adapter, Activity act){		
		search = query;
		activity = act;
		questionList = list;
		this.adapter = adapter;
	}
	
	@Override
	public void run() {
		questionList.clear();
		questionList.addAll(manager.searchQuestions(search, null));

		activity.runOnUiThread(doUpdateGUIList);
	}

	// Thread to update adapter after an operation
	private Runnable doUpdateGUIList = new Runnable() {
		public void run() {
			adapter.notifyDataSetChanged();
		}
	};

}
