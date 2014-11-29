package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import ca.ualberta.cs.funtime_runtime.adapter.QuestionListAdapter;
import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;
import ca.ualberta.cs.funtime_runtime.classes.Geolocation;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.classes.QuestionSorter;
import ca.ualberta.cs.funtime_runtime.elastic.ESQuestionManager;

/**
 * 
 * This class handles the Activity of the main page which acts as a hub for
 * displaying questions taken from the server and displaying the button to ask a
 * question.
 * 
 * @author Steven Cherfan (authored the comments)
 */

public class HomeActivity extends CustomActivity {

	ListView homeListView;
	ArrayList<Question> homeQuestionList;
	QuestionListAdapter adapter;
	Account account;
	ESQuestionManager questionManager;
	QuestionSorter sorter;

	static boolean first = true;
	boolean loggedIn;

	/**
	 * Initializes the listview, ArrayList that holds the questions, the adapter
	 * and an instance of an account. Also has a listener that opens a question
	 * when a question is clicked.
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		if (ApplicationState.isFirstLaunch()) {
			ApplicationState.startup(this);	
		}
		
		Log.i("HomeActivity", "Startup done");
		
		loggedIn = ApplicationState.isLoggedIn();
		if (loggedIn) {
			account = ApplicationState.getAccount();
		} 
		
		Log.i("HomeActivity", "Login check done");
		
		homeListView = (ListView) findViewById(R.id.questionListView);

		//homeQuestionList = new ArrayList<Question>();
		//loadServerQuestions();
		homeQuestionList = ApplicationState.getQuestionList(this);
		//loadServerQuestions();

		Log.i("HomeActivity", "Retrieved question list");
		
		sorter = new QuestionSorter(homeQuestionList);

		Log.i("HomeActivity", "Sorted");
		
		Geolocation go = new Geolocation(this);
		go.findLocation();
		Toast.makeText(this, go.getLocation(), Toast.LENGTH_LONG).show();
		
		account = ApplicationState.getAccount();
		adapter = new QuestionListAdapter(this, R.layout.question_list_adapter,
				homeQuestionList);

		
		homeListView.setAdapter(adapter);
		sorter.sortByDate();
		adapter.notifyDataSetChanged();

		homeListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				openQuestion(position);
			}
		});

		registerForContextMenu(homeListView);
		
		Log.i("HomeActivity", "onCreate finished");
		
		refresh();
		
	}

//	private void loadServerQuestions() {
//		Thread loadThread = new SearchThread("*");
//		//Thread loadThread = new LoadHomeThread("*", homeQuestionList, adapter);
//		loadThread.start();	
//		
//		try {
//			loadThread.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}

	

	/**
	 * Refreshes the adapter when the activity restarts
	 * in case anything changed.
	 */
	@Override
	public void onRestart() {
		super.onRestart();
		refresh();
	}

	/**
	 * On a long click if the question item is in the reading list
	 * it will give the option to remove it or if it is not in the
	 * reading list it will give the option to add the question to the
	 * reading list. This is all displayed via a context menu.
	 * 
	 * Adapted from
	 * http://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long
	 * -clicks-in-an-android-listview/ 2014-09-21
	 */
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		Question selectedQuestion = adapter.getItem(info.position);
		
		if (loggedIn) {
			
			if (account.getReadingList().contains(selectedQuestion.getId())) {
				menu.add("Remove from reading list");
			} else {
				menu.add("Add to reading list");
			}
		}
	}


	@Override
	public void refresh() {
		
		String sortType = sorter.getSortType();
		ApplicationState.refresh(this);
		homeQuestionList = ApplicationState.getQuestionList(this);
		if (sortType.equals("Date")){
			sorter.sortByDate();
		} else if (sortType.equals("Votes")) {
			sorter.sortByVotes();
		} else if (sortType.equals("Photo")) {
			sorter.sortByPhoto();
		} else if (sortType.equals("Location")) {
			sorter.sortByLocation(this);
		}
		
		adapter.notifyDataSetChanged();
	}

	/**
	 * On a long click if the question item is in the reading list
	 * it will give the option to remove it or if it is not in the
	 * reading list it will give the option to add the question to the
	 * reading list. This is all displayed via a context menu.
	 */
	public boolean onContextItemSelected(MenuItem item) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int itemIndex = info.position;
		Question selectedQuestion = adapter.getItem(itemIndex);

		if (item.getTitle() == "Remove from reading list") {
			account.removeReadLater(selectedQuestion, this);
		} else if (item.getTitle() == "Add to reading list") {
			account.readLater(selectedQuestion, this);

		} else {
			return false;
		}

		return true;

	}
	
	@Override
	public void sortDate() {
		sorter.sortByDate();	
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void sortVotes() {
		sorter.sortByVotes();
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void sortPhoto() {
		sorter.sortByPhoto();
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void sortLocation() {
		sorter.sortByLocation(this);
		adapter.notifyDataSetChanged();
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
		
		Intent intent = new Intent(this, QuestionPageActivity.class);
		
		ApplicationState.setPassableQuestion(question);

		startActivity(intent);
	}

	/**
	 * Populates the menu with activities to move to.
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}


	/**
	 * Takes in a view to give information to start the AuthorQuestionActivity
	 * to allow the user to write an answer to a question.
	 * 
	 * @param v
	 */
	public void askQuestion(View v) {
		if (ApplicationState.isLoggedIn()) {
			Intent authorQuestion = new Intent(HomeActivity.this, AuthorQuestionActivity.class);
			startActivity(authorQuestion);
		} else {
			String msg = ApplicationState.notLoggedIn();
			Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * Checks to see if there is a logged in account.
	 * 
	 * @return true
	 * @return false
	 */
	public Boolean checkLoggedIn() {
		if (account == null) {
			return false;
		} else {
			return true;
		}
	}

	// Comparator code http://stackoverflow.com/questions/5927109/sort-objects-in-arraylist-by-date  - Nov 20/2014, by Domchi
//	class SearchThread extends Thread {
//		private String search;
//		
//		public SearchThread(String s){		
//			search = s;
//		}
//		
//		@Override
//		public void run() {
//			ArrayList<Question> appStateList = ApplicationState.getQuestionList();
////			homeQuestionList.clear();		
////			homeQuestionList.addAll(questionManager.searchQuestions(search, null));			
//			runOnUiThread(updateHomeUI);	
//		}
//		
//		private Runnable updateHomeUI = new Runnable() {
//			public void run() {
//				adapter.notifyDataSetChanged();
//			}
//		};
//	}


}
