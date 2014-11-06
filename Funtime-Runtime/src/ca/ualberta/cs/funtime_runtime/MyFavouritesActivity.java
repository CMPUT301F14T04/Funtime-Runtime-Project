package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MyFavouritesActivity extends CustomActivity {

	ListView favouritesListView;
	ArrayList<Question> myFavouritesList;
	QuestionListAdapter adapter;
	Account account;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_favourites);
		
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		favouritesListView = (ListView) findViewById(R.id.myFavouritesListView);	
		account = ApplicationState.getAccount();
		
		//testMyFavourites(); // temporary test code

		myFavouritesList = account.getFavouritesList();
		
		ArrayList<Question> favouritesList = new ArrayList<Question>();
		favouritesList = myFavouritesList;
		adapter = new QuestionListAdapter(this, R.layout.question_list_adapter2, favouritesList);
		favouritesListView.setAdapter(adapter);	
		adapter.notifyDataSetChanged();
		
		
		favouritesListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Question question = (Question) adapter.getItem(position);				
				Bundle bundle = new Bundle();
				bundle.putSerializable("Question", question);
				Intent intent = new Intent(MyFavouritesActivity.this, QuestionPageActivity.class);
				intent.putExtras(bundle);
				
				ApplicationState.setPassableQuestion(question);
				
				startActivity(intent);			
			}
		});
		
		registerForContextMenu(favouritesListView);

	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		adapter.notifyDataSetChanged();
	
	}
		
	private void testMyFavourites() {
		Question question1 = new Question("What is the meaning of life?", "body 1 test", "user1");
		Question question2 = new Question("Why does Computing Science homework take so long to do?", "body 2 test", "user2");
		Question question3 = new Question("In what world does gravity push you away at a faster rate than it pulls you in?", "body 3 test", "user3");
		account.addFavourite(question1);
		account.addFavourite(question2);
		account.addFavourite(question3);
		question1.downVote();
		Answer answer1 = new Answer("Sweet", "user1");
		Answer answer2 = new Answer("Question", "user2");
		Answer answer3 = new Answer("Bro do you even lift????????????????????????????????????????", "user3");
		question1.addAnswer(answer1);
		question1.addAnswer(answer2);
		question1.addAnswer(answer3);
	}
	
	// Adapted from http://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long-clicks-in-an-android-listview/ 2014-09-21
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_favourites, menu);
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
			case R.id.my_questions_menu_item:
				openMyQuestions();
				return true;
			case R.id.my_answers_menu_item:
				openMyAnswers();
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
