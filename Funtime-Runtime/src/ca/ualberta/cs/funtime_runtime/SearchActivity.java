package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import ca.ualberta.cs.funtime_runtime.adapter.QuestionListAdapter;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.elastic.ESQuestionManager;
import ca.ualberta.cs.funtime_runtime.elastic.SimpleSearchCommand;

/**
 * This is a view class that displays the results of a search that the user initiated
 * Will be implemented for Project Part-4
 * 
 * @author Pranjali Pokharel
 */
public class SearchActivity extends CustomActivity {
	
	ArrayList<Question> questions;
	QuestionListAdapter questionAdapter;
	ListView resultList;
	EditText queryEdit;
	Button searchButton;
	ESQuestionManager questionManager;
	
	//private IQuestionManager questionManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		resultList = (ListView) findViewById(R.id.searchQuestionsList);
		queryEdit = (EditText) findViewById(R.id.searchField);
		searchButton = (Button) findViewById(R.id.searchButton);
		questions = new ArrayList<Question>();
		questionAdapter = new QuestionListAdapter(this, R.layout.question_list_adapter, questions);
		questionManager = new ESQuestionManager();
		resultList.setAdapter(questionAdapter);
		
	}

	
	/**
	 * The methods below are used to inflate and interact with menu objects
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	
	/**
	 * Called when the search button is clicked, begins a search of the 
	 * elastic search server for questions and answer that match or contain
	 * the given query
	 */
	public void searchForQuery(View v) {
		//questions.add(new Question("Help", "Me", "please"));
		questions.clear();
		SimpleSearchCommand query = new SimpleSearchCommand(queryEdit.getText().toString());
		String command = query.getJsonCommand();
		//Toast.makeText(this, command, Toast.LENGTH_LONG).show();
		Thread thread = new SearchThread(command);
		thread.start();
		//Toast.makeText(this, questions.size(), Toast.LENGTH_LONG).show();
		
	}
	
	class SearchThread extends Thread {
		private String search;
		
		public SearchThread(String query){		
			search = query;
		}
		
		@Override
		public void run() {
			List<Question> results = new ArrayList<Question>();
			questions.clear();
			results = questionManager.searchQuestions(search, null);
			// Code to convert results into Question format
			questions.addAll(results);
		
			runOnUiThread(doUpdateGUIList);
		}
	}
	
	// Thread to update adapter after an operation
	private Runnable doUpdateGUIList = new Runnable() {
		public void run() {
			questionAdapter.notifyDataSetChanged();
		}
	};

}
