package ca.ualberta.cs.funtime_runtime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class HomeActivity extends Activity
{
	ListView homeListView;
	QuestionList homeQuestionList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		homeQuestionList = new QuestionList();
		homeListView =  (ListView) findViewById(R.id.questionListView);	
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.searchQuestionsList:
				openSearch();
				return true;
			default:
				return true;
		}
		
	}
	
	public void openSearch() {
		Intent intent = new Intent(this, SearchActivity.class);
		startActivity(intent);
	}

}
