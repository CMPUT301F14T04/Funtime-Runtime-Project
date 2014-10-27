package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ReadingListActivity extends Activity
{

	ListView readingListView;
	QuestionList readingList;
	QuestionListAdapter adapter;
	Account account;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reading_list);
		
		readingListView = (ListView) findViewById(R.id.readingListView);	
		account = ApplicationState.getAccount();
		
		//testMyFavourites();

		readingList = account.getFavouritesList();

		ArrayList<Question> favouritesAList = new ArrayList<Question>();
		favouritesAList = readingList.getList();
		adapter = new QuestionListAdapter(this, R.layout.question_list_adapter, favouritesAList);
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
				startActivity(intent);			
			}
		});

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_favourites, menu);
		return true;
	}

}
