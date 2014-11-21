package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import ca.ualberta.cs.funtime_runtime.adapter.QuestionListAdapter;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.classes.QuestionSorter;
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
	QuestionListAdapter adapter;
	ListView resultList;
	EditText queryEdit;
	Button searchButton;
	ESQuestionManager questionManager;
	QuestionSorter sorter;
	
	//private IQuestionManager questionManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		resultList = (ListView) findViewById(R.id.searchQuestionsList);
		queryEdit = (EditText) findViewById(R.id.searchField);
		searchButton = (Button) findViewById(R.id.searchButton);
		questions = new ArrayList<Question>();
		adapter = new QuestionListAdapter(this, R.layout.question_list_adapter, questions);
		questionManager = new ESQuestionManager();
		resultList.setAdapter(adapter);	
		sorter = new QuestionSorter(questions);
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
	 * This function simply redirects to another activity when a certain menu 
	 * item is selected by the user. It operates a switch statement to transition 
	 * between different activities
	 * 
	 * @param item is a menuItem signifying location within the menu that users 
	 * wish to visit
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {	
			case android.R.id.home:
				openMyHome();
				return true;
				
			case R.id.refresh:
				refresh();
				return true;
				
			case R.id.home_menu_item:
				openMyHome();
				return true;
				
			case R.id.searchQuestionsList :
				openSearch();
				return true;
				
			case R.id.login_menu_item :
				openLogin();
				return true;
				
			case R.id.my_questions_menu_item :
				openMyQuestions();
				return true;
				
			case R.id.my_answers_menu_item :
				openMyAnswers();
				return true;
				
			case R.id.my_favorites_menu_item :
				openMyFavourites();
				return true;
				
			case R.id.my_reading_list_item :
				openMyReadingList();
				return true;
				
			case R.id.my_history_list_item :
				openMyHistory();
				return true;
				
			case R.id.sort_list_item :
				return true;
				
			case R.id.sort_date_menu:
				questions = sorter.sortByDate();
				adapter.notifyDataSetChanged();
				return true;
				
			case R.id.sort_votes_menu:
				questions = sorter.sortByVotes();
				adapter.notifyDataSetChanged();
				return true;
				
			case R.id.sort_photo_menu:
				questions = sorter.sortByPhoto();
				adapter.notifyDataSetChanged();
				return true;
				
			default :
				return true;
		}
	}
	
	private void refresh() {
		// TODO Auto-generated method stub
		
	}


	/**
	 * Called when the search button is clicked, begins a search of the 
	 * elastic search server for questions and answer that match or contain
	 * the given query
	 */
	public void searchForQuery(View v) {
		questions.clear();
		adapter.notifyDataSetChanged();
		SimpleSearchCommand query = new SimpleSearchCommand(queryEdit.getText().toString());
		String command = query.getJsonCommand();
		Thread thread = new SearchThread(command);
		thread.start();	
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
			adapter.notifyDataSetChanged();
		}
	};

}
