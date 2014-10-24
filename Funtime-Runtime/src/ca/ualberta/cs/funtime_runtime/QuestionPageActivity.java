package ca.ualberta.cs.funtime_runtime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class QuestionPageActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_page);
		//Intent intent = getIntent();
		Question question = (Question) getIntent().getSerializableExtra("Question");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question_page, menu);
		return true;
	}
	
	//start the AuthorAnswerActivity --> Author Answer Page
	public void addAnswer(View v)
	{
		Intent authorAnswer = new Intent(QuestionPageActivity.this, AuthorAnswerActivity.class);
		startActivity(authorAnswer);
	}

}
