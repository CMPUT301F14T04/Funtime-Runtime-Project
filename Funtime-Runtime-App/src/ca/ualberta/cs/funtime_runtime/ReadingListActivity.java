package ca.ualberta.cs.funtime_runtime;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ReadingListActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reading_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reading_list, menu);
		return true;
	}

}
