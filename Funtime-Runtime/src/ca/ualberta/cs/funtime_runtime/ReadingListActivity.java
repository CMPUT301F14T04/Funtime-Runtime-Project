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

public class ReadingListActivity extends CustomActivity {

	ListView readingListView;
	ArrayList<Question> readingList;
	QuestionListAdapter adapter;
	Account account;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reading_list);
		
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		readingListView = (ListView) findViewById(R.id.readingListView);	
		account = ApplicationState.getAccount();
		
		//testReadingList();

		readingList = account.getReadingList();

		adapter = new QuestionListAdapter(this, R.layout.question_list_adapter2, readingList);
		readingListView.setAdapter(adapter);	
		adapter.notifyDataSetChanged();
		
		
		readingListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Question question = (Question) adapter.getItem(position);				
				Bundle bundle = new Bundle();
				bundle.putSerializable("Question", question);
				Intent intent = new Intent(ReadingListActivity.this, QuestionPageActivity.class);
				intent.putExtras(bundle);
				
				ApplicationState.setPassableQuestion(question);
				
				startActivity(intent);
			}
		});
		
		registerForContextMenu(readingListView);

	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		adapter.notifyDataSetChanged();
	
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
		getMenuInflater().inflate(R.menu.reading_list, menu);
		return true;
	}

	/*
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Toast.makeText(getApplicationContext(), "onActivityResult", 
				   Toast.LENGTH_LONG).show();

		readingList = account.getReadingList();
		adapter.notifyDataSetChanged();
	}
	*/
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// switch case to choose an item from the menu
		//IntentSwitcher switcher = new IntentSwitcher(HomeActivity.this);
		
//-------------------------------------------
//Menu Items Switch Case
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
			case R.id.my_favorites_menu_item:
				openMyFavourites();
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
//-----------------------------
//-------------------------------------------	
	

}
