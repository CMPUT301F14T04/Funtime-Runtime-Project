package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import ca.ualberta.cs.funtime_runtime.adapter.QuestionListAdapter;
import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.classes.QuestionSorter;
import ca.ualberta.cs.funtime_runtime.elastic.ESQuestionManager;

/**
 * This is a view class that displays the results of a search that the user initiated
 * Will be implemented for Project Part-4
 * 
 * @author Pranjali Pokharel
 */
public class SearchActivity extends CustomActivity {
	
	Account account;
	ArrayList<Question> questions;
	QuestionListAdapter adapter;
	ListView resultList;
	EditText queryEdit;
	Button searchButton;
	ESQuestionManager questionManager;
	QuestionSorter sorter;
	/**
	 * sets up the activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		account = ApplicationState.getAccount();
		resultList = (ListView) findViewById(R.id.searchQuestionsList);
		queryEdit = (EditText) findViewById(R.id.searchField);
		searchButton = (Button) findViewById(R.id.searchButton);
		questions = new ArrayList<Question>();
		adapter = new QuestionListAdapter(this, R.layout.question_list_adapter, questions);
		questionManager = new ESQuestionManager();
		resultList.setAdapter(adapter);	
		sorter = new QuestionSorter(questions);
		sorter.sortByDate();
		
		resultList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				openQuestion(position);
			}
		});
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


	/**
	 * This method is called when a question is clicked on. It takes in a
	 * integer of the position of the clicked question in the list. Then it
	 * passes the information about the question via an intent to the
	 * QuestionPageActivity and changes the application state. The clicked
	 * question is also added to the history list.
	 * 
	 * @param position
	 */
	private void openQuestion(int position) {
		Question question = (Question) adapter.getItem(position);
		if (ApplicationState.isLoggedIn()){
			account.addToHistory(question, this); // Add question clicked to history
		}
		Bundle bundle = new Bundle();
		bundle.putSerializable("Question", question);
		Intent intent = new Intent(this, QuestionPageActivity.class);
		intent.putExtras(bundle);

		ApplicationState.setPassableQuestion(question);

		startActivity(intent);
	}

	/**
	 * Called when the search button is clicked, begins a search of the 
	 * elastic search server for questions and answer that match or contain
	 * the given query
	 */
	public void searchForQuery(View v) {
		questions.clear();
		adapter.notifyDataSetChanged();
		String query = queryEdit.getText().toString();
		ApplicationState.searchQuery(query, questions, adapter, this);

	}

}
