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

/**
 * This class is a view class that displays the questions that the user asked in the application
 * It contains various menu items options which can be selected from this activity page
 */

public class MyQuestionsActivity extends CustomActivity {
	ListView myQuestionsListView;
	ArrayList<Question> myQuestionList;
	QuestionListAdapter adapter;
	Account account;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_questions);
	
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		account = ApplicationState.getAccount();
		
		myQuestionList = new ArrayList<Question>();
		myQuestionsListView = (ListView) findViewById(R.id.listView1);
		myQuestionsExample();
		myQuestionsListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
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
		    account.readLater(selectedQuestion);
            
	    } 
		else {
	        return false;
	    }
		
	    return true;

	}
	
	
	/***
	 * 
	 * Sample Question examples that user1 asked on the app. This method will be removed once Elastic Search is implemented.
	 * This is needed now because questions are not being saved when the application is exited
	 * 
	 ***/
	
	private void myQuestionsExample()
	{
		ArrayList<Question> questionList = new ArrayList<Question>();
		adapter = new QuestionListAdapter(this, R.layout.question_list_adapter, questionList);
		
		//Example 1
		Question question1 = new Question("Why are there animals in my van?", "Test: there are animals in my van etc", "user1");
		questionList.add(question1);
		question1.upVote();
		Answer answer1 = new Answer("Because the devil put it there", "user2");
		Answer answer2 = new Answer("Maybe you left the door open? Sometimes animals can also get in through the window", "user5");
		question1.addAnswer(answer1);
		question1.addAnswer(answer2);
		
		
		//Example 2
		Question question2 = new Question("How many colors can the people see?", "Test: like when it comes to skin color what if someone is color blind??", "user1");
		questionList.add(question2);
		question2.downVote();
		question2.downVote();
		Answer answer3 = new Answer("Wow you're not very smart are you?", "user2");
		answer3.upVote();
		question2.addAnswer(answer3);
		
		
		myQuestionsListView.setAdapter(adapter);	
		adapter.notifyDataSetChanged();
	}
	

	
	/**
	 * menu at the top of the page(non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_questions, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// switch case to choose an item from the menu
		
//-------------------------------------------
// Menu Items Switch Case
//-------------------------------------------
		
		switch (item.getItemId()) {
			case android.R.id.home:
				openMyHome();
				return true;
			case R.id.home_menu_item:
				openMyHome();
				return true;
			case R.id.my_answers_menu_item:
				openMyAnswers();
				return true;
			case R.id.my_favorites_menu_item:
				openMyFavourites();
				return true;
			case R.id.my_reading_list_item:
				openMyReadingList();
				return true;
			case R.id.my_history_list_item:
				openMyHistory();
				return true;
			case R.id.sort_list_item:
				openSortList();
				return true;
			default:
				return true;
		}	
	}

//-------------------------------------------
//-------------------------------------------		

}
