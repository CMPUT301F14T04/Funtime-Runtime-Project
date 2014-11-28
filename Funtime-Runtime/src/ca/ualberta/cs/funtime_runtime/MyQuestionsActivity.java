package ca.ualberta.cs.funtime_runtime;

import android.os.Bundle;
import android.app.ActionBar;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;

import ca.ualberta.cs.funtime_runtime.adapter.QuestionListAdapter;
import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.classes.QuestionSorter;

/**
 * This class is a view class that displays the questions that the user asked in the application
 * It contains various menu items options which can be selected from this activity page
 * The user can select to view each of the answers and replies corresponding to each question in the list
 * 
 * @author Pranjali Pokharel
 */

public class MyQuestionsActivity extends CustomActivity {
	ListView myQuestionsListView;
	ArrayList<Question> myQuestionsList;
	ArrayList<Integer> myQuestionsIdList;
	ArrayList<Question> appStateList;
	QuestionSorter sorter;
	
	QuestionListAdapter adapter;
	Account account;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_questions);
	
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		account = ApplicationState.getAccount();
		myQuestionsIdList = account.getQuestionList();
		
		myQuestionsList = new ArrayList<Question>();
		appStateList = ApplicationState.getQuestionList(this);
		for (Integer id: myQuestionsIdList) {
			for (Question q: appStateList) {
				Integer qId = q.getId();
				if (qId.equals(id)) {
					myQuestionsList.add(q);
				}
			}
		}

		myQuestionsListView = (ListView) findViewById(R.id.listView1);
		
		adapter = new QuestionListAdapter(this, R.layout.question_list_adapter, myQuestionsList);
		myQuestionsListView.setAdapter(adapter);
		sorter = new QuestionSorter(myQuestionsList);
		sorter.sortByDate();
		adapter.notifyDataSetChanged();
		
		myQuestionsListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Question question = (Question) adapter.getItem(position);
				Bundle bundle = new Bundle();
				bundle.putSerializable("Question", question);
				Intent intent = new Intent(MyQuestionsActivity.this, QuestionPageActivity.class);
				intent.putExtras(bundle);
				
				ApplicationState.setPassableQuestion(question);
				
				startActivity(intent);
				
			}
		});
		
		registerForContextMenu(myQuestionsListView);
		
	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		adapter.notifyDataSetChanged();
	
	}
	
	/**
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
		    account.removeReadLater(selectedQuestion);
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
		getMenuInflater().inflate(R.menu.my_questions, menu);
		return true;
	}
	

}
