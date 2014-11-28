package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import ca.ualberta.cs.funtime_runtime.adapter.QuestionListAdapter;
import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.classes.QuestionSorter;

/**
 * This is a view class that displays all of the questions that a user has added to 
 * their Reading List (Type: ArrayList<Question>)
 * The question list displays all of the components associated with a question 
 * (eg: title, body, rating, number of answers etc)
 * The user can chose to select a question to view the answers and replies associated with it
 * @author Pranjali Pokharel
 *
 */
public class ReadingListActivity extends CustomActivity {

	ListView readingListView;
	
	ArrayList<Question> readingList;
	ArrayList<Question> appStateList;
	ArrayList<Integer> readingIdList;
	QuestionSorter sorter;
	
	QuestionListAdapter adapter;
	Account account;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reading_list);
		
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		// Retrieve logged in account
		readingListView = (ListView) findViewById(R.id.readingListView);	
		account = ApplicationState.getAccount();

		// Retrieve account's reading list
		readingIdList = account.getReadingList();
		readingList = new ArrayList<Question>();
		appStateList = ApplicationState.getQuestionList(this);
		for (Integer id: readingIdList) {
			for (Question q: appStateList) {
				Integer qId = q.getId();
				if (qId.equals(id)) {
					readingList.add(q);
				}
			}
		}

		// Set adapter for list
		adapter = new QuestionListAdapter(this, R.layout.question_list_adapter, readingList);
		readingListView.setAdapter(adapter);
		sorter = new QuestionSorter(readingList);
		sorter.sortByDate();
		adapter.notifyDataSetChanged();
		
		
		readingListView.setOnItemClickListener(new OnItemClickListener() {
			/* The onClick listener for each question in the ListView
			 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
			 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Set the question to be passed
				Question question = (Question) adapter.getItem(position);				
				Bundle bundle = new Bundle();
				bundle.putSerializable("Question", question);
				bundle.putString("ReadingCheck", "true");
				ApplicationState.setPassableQuestion(question);
				
				// Set up the intent for QuestionPageActivity
				Intent intent = new Intent(ReadingListActivity.this, QuestionPageActivity.class);
				
				intent.putExtras(bundle);
				
				
				
				// Start the QuestionPageActivity
				startActivity(intent);
			}
		});
		
		registerForContextMenu(readingListView);

	}
	
	/* Refresh the data upon returning from activity.
	 * @see android.app.Activity#onRestart()
	 */
	@Override
	public void onRestart() {
		super.onRestart();
		adapter.notifyDataSetChanged();
	
	}
	
	/** Context menu for onLongClick of question.
	 * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu, android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 * 
	 * Adapted from http://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long-clicks-in-an-android-listview/ 2014-09-21
	 * 
	 */
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
	    Question selectedQuestion = adapter.getItem(info.position);
	    
	    // Check if the question is in the user's reading list and display the appropriate context menu
	    if (account.getReadingList().contains(selectedQuestion)) {
	    	menu.add("Remove from reading list");
	    } else {
	    	menu.add("Add to reading list");
	    }
	}

	/** Add or remove the selected question from the user's reading list
	 * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
	 */
	public boolean onContextItemSelected(MenuItem item) {

	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    int itemIndex = info.position;
	    Question selectedQuestion = adapter.getItem(itemIndex);
	    
		if (item.getTitle() == "Remove from reading list") {
		    account.removeReadLater(selectedQuestion);
		    adapter.notifyDataSetChanged();
		}
		else if (item.getTitle() == "Add to reading list") {
		    account.readLater(selectedQuestion, this);
            
	    } 
		else {
	        return false;
	    }
		
	    return true;

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reading_list, menu);
		return true;
	}
	

}
