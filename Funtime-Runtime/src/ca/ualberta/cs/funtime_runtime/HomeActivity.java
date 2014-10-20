package ca.ualberta.cs.funtime_runtime;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
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
		homeListView =  (ListView) findViewById(R.id.questionsListView);	
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
