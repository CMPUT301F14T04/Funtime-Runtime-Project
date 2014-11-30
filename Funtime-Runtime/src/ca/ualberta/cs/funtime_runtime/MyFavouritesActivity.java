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
 * Displays the list of all questions the user has favourited.
 * 
 * @author Benjamin Holmwood
 *
 */
public class MyFavouritesActivity extends CustomActivity {

	ListView favouritesListView;
	
	ArrayList<Question> myFavouritesList;
	ArrayList<Question> appStateList;
	ArrayList<Integer> myFavouritesIdList;
	QuestionSorter sorter;
	
	QuestionListAdapter adapter;
	Account account;
	
	/**
	 * sets up activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_favourites);
		
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		// Retrieve logged in account
		favouritesListView = (ListView) findViewById(R.id.myFavouritesListView);	
		account = ApplicationState.getAccount();

		// Retrieve account's favourited questions list
		myFavouritesIdList = account.getFavouritesList();
		myFavouritesList = new ArrayList<Question>();
		appStateList = ApplicationState.getQuestionList(this);
		for (Integer id: myFavouritesIdList) {
			for (Question q: appStateList) {
				Integer qId = q.getId();
				if (qId.equals(id)) {
					myFavouritesList.add(q);
				}
			}
		}
		// Write code to convert ids to question, populate favorites list
		
		// Set up adapter for list
		//ArrayList<Question> favouritesList = new ArrayList<Question>();
		//favouritesList = myFavouritesList;
		adapter = new QuestionListAdapter(this, R.layout.question_list_adapter, myFavouritesList);
		favouritesListView.setAdapter(adapter);	
		sorter = new QuestionSorter(myFavouritesList);
		sorter.sortByDate();
		adapter.notifyDataSetChanged();
		
		
		favouritesListView.setOnItemClickListener(new OnItemClickListener() {
			/* The onClick listener for each question in the ListView
			 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
			 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Set the question to be passed
				Question question = (Question) adapter.getItem(position);				
				Bundle bundle = new Bundle();
				bundle.putSerializable("Question", question);
				
				ApplicationState.setPassableQuestion(question);
				
				// Set up the intent for QuestionPageActivity
				Intent intent = new Intent(MyFavouritesActivity.this, QuestionPageActivity.class);
				intent.putExtras(bundle);
				
				// Start the QuestionPageActivity
				startActivity(intent);			
			}
		});
		
		registerForContextMenu(favouritesListView);

	}
	
	/* Refresh the data upon returning from activity.
	 * @see android.app.Activity#onRestart()
	 */
	@Override
	public void onRestart() {
		super.onRestart();
		adapter.notifyDataSetChanged();
	
	}
	
	// 
	/* Context menu for onLongClick of question.
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

	/* Add or remove the selected question from the user's reading list
	 * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
	 */
	public boolean onContextItemSelected(MenuItem item) {

	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    int itemIndex = info.position;
	    Question selectedQuestion = adapter.getItem(itemIndex);
	    
		if (item.getTitle() == "Remove from reading list") {
		    account.removeReadLater(selectedQuestion, this);
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
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_favourites, menu);
		return true;
	}
	/**
	 * sorts questions by date
	 */
	@Override
	public void sortDate() {
		sorter.sortByDate();	
		adapter.notifyDataSetChanged();
	}
	/**
	 * sorts questions by votes
	 */
	@Override
	public void sortVotes() {
		sorter.sortByVotes();
		adapter.notifyDataSetChanged();
	}
	/**
	 * sorts questions by whether they have a photo or not 
	 * 
	 */
	@Override
	public void sortPhoto() {
		sorter.sortByPhoto();
		adapter.notifyDataSetChanged();
	}
	/**
	 * sorts questions by location
	 */
	@Override
	public void sortLocation() {
		sorter.sortByLocation(this);
		adapter.notifyDataSetChanged();
	}
	
	

}
