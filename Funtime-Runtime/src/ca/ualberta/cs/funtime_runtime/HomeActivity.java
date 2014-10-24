package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class HomeActivity extends Activity
{
	ListView homeListView;
	QuestionList homeQuestionList;
	Account account;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		homeQuestionList = new QuestionList();
		homeListView = (ListView) findViewById(R.id.questionListView);	
		testHome();
	}
		
	private void testHome() {
		List<Question> questionList = new ArrayList<Question>();
		QuestionListAdapter adapter = new QuestionListAdapter(this, R.layout.question_list_adapter, questionList);
		Question question1 = new Question("What is the meaning of life?", "body 1 test", "user1");
		Question question2 = new Question("Why does Computing Science homework take so long to do?", "body 2 test", "user2");
		Question question3 = new Question("In what world does gravity push you away at a faster rate than it pulls you in?", "body 3 test", "user3");
		questionList.add(question1);
		questionList.add(question2);
		questionList.add(question3);
		homeListView.setAdapter(adapter);	
		adapter.notifyDataSetChanged();
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

	//start the AuthorQuestionActivity --> Author Question Page to ask a new Question
	public void askQuestion(View v)
	{
		Intent authorQuestion = new Intent(HomeActivity.this, AuthorQuestionActivity.class);
		startActivity(authorQuestion); 
		
//		Boolean loggedIn = checkLoggedIn();
//		if (loggedIn) {
//			Intent authorQuestion = new Intent(HomeActivity.this, AuthorQuestionActivity.class);
//			startActivity(authorQuestion); 
//		}
//		else {
//			Toast.makeText(this, "Please login to post a question", Toast.LENGTH_SHORT).show();
//			//Intent createAccount = new Intent(this, LoginActivity.class);
//			//startActivity(createAccount);
//		}
	}
	
	public Boolean checkLoggedIn() {
		if (account == null) {
			return false;
		}
		else {
			return true;
		}	
	}
}
