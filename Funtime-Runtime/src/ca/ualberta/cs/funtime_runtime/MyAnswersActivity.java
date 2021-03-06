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
 * This class is a view class that displays the questions which contain user authored answers
 * It contains various menu items options which can be selected from this activity page
 * The user can select to view each of the answers and replies corresponding
 * to each question in the list. The list of answers for the question will 
 * have at least one answer the user authored 
 * 
 * @author Pranjali Pokharel
 */
public class MyAnswersActivity extends CustomActivity {
	ListView myAnsweredListView;
	
	ArrayList<Question> myAnsweredQuestionsList;
	ArrayList<Integer> myAnsweredQuestionsIdList;
	ArrayList<Question> appStateList;
	QuestionSorter sorter;
	
	QuestionListAdapter adapter;
	Account account;
	boolean loggedIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_answers);
		loggedIn = ApplicationState.isLoggedIn();
		//Log.i("Logged in", ""+loggedIn);
		account = ApplicationState.getAccount();
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		account = ApplicationState.getAccount();
		
		
		myAnsweredQuestionsIdList = account.getAnsweredList();
		myAnsweredListView = (ListView) findViewById(R.id.listView1);

		myAnsweredQuestionsList = new ArrayList<Question>();
		appStateList = new ArrayList<Question>();
		appStateList = ApplicationState.getQuestionList(this);
		for (Integer id: myAnsweredQuestionsIdList) {
			for (Question q: appStateList) {
				Integer qId = q.getId();
				if (qId.equals(id)) {
					myAnsweredQuestionsList.add(q);
				}
			}
		}
		
		adapter = new QuestionListAdapter(this, R.layout.question_list_adapter, myAnsweredQuestionsList);
		
		myAnsweredListView.setAdapter(adapter);	
		sorter = new QuestionSorter(myAnsweredQuestionsList);
		sorter.sortByDate();
		adapter.notifyDataSetChanged();
		
		myAnsweredListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Question question = (Question) adapter.getItem(position);				

				Intent intent = new Intent(MyAnswersActivity.this, QuestionPageActivity.class);
				
				ApplicationState.setPassableQuestion(question);
				
				startActivity(intent);			
			}
		});
			
		registerForContextMenu(myAnsweredListView);
		
		adapter.notifyDataSetChanged();
		

		
	}
	
	
	@Override
	public void onRestart() {
		super.onRestart();
		adapter.notifyDataSetChanged();
	
	}
	
	/**
	 * Creates menu item which can be used to add functionalities
	 * Adapted from http://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long-clicks-in-an-android-listview/ 2014-09-21
	 * (non-Javadoc)
	 * @param ContextMenu, View, ContextMenuInfo
	 * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu, android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 */
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
	    Question selectedQuestion = adapter.getItem(info.position);
	    
	    if (account.getReadingList().contains(selectedQuestion)) {
	    	menu.add("Remove from reading list");
	    } else {
	    	menu.add("Add to reading list");
	    }
	}

	
	/**
	 * Activates menu item which when clicked adds a question item to reading list	(non-Javadoc)
	 * @param MenuItem- an item in the menu
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

	
	/**
	 * menu at the top of the page(non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_answers, menu);
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
	 * sorts questions by photo
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
