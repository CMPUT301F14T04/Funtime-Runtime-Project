package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

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
		homeListView =  (ListView) findViewById(R.id.questionListView);	
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
		// switch case to choose an item from the menu
		switch (item.getItemId()) {
			case R.id.searchQuestionsList:
				openSearch(this);
				return true;
			case R.id.login_menu_item:
				openLogin(this);
				return true;
			case R.id.my_questions_menu_item:
				openMyQuestions(this);
				return true;
			case R.id.my_answers_menu_item:
				openMyAnswers(this);
				return true;
			case R.id.my_favorites_menu_item:
				openMyFavourites(this);
				return true;
			case R.id.my_reading_list_item:
				openMyReadingList(this);
				return true;
			case R.id.my_history_list_item:
				openMyHistory(this);
				return true;
			case R.id.sort_list_item:
				openSortList(this);
				return true;
			default:
				return true;
		}	
	}
	
	public void openSearch(Context context) {
		Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(context, SearchActivity.class);
		startActivity(intent);
	}
	
	public void openLogin(Context context){
		Toast.makeText(context, "Login", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(context, LoginActivity.class);
		startActivity(intent);
	}
	
	public void openMyQuestions(Context context) {
		Toast.makeText(context, "My Questions", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(context, MyQuestionsActivity.class);
		startActivity(intent);
	}
	
	public void openMyAnswers(Context context) {
		Toast.makeText(context, "My Answers", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(context, MyAnswersActivity.class);
		startActivity(intent);
	}
	
	public void openMyFavourites(Context context) {
		Toast.makeText(context, "My Favourites", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(context, MyFavouritesActivity.class);
		startActivity(intent);
	}
	
	public void openMyReadingList(Context context) {
		Toast.makeText(context, "My Reading List", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(context, ReadingListActivity.class);
		startActivity(intent);
	}
	
	public void openMyHistory(Context context) {
		Toast.makeText(context, "My History", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(context, HistoryActivity.class);
		startActivity(intent);
	}

	public void openSortList(Context context) {
		Toast.makeText(context, "Choose A Sorting Method", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(context, Sort.class);
		startActivity(intent);
	}
	
	//start the AuthorQuestionActivity --> Author Question Page to ask a new Question
	public void askQuestion(View v)
	{
		Boolean loggedIn = checkLoggedIn();
		if (loggedIn) {
			Intent authorQuestion = new Intent(HomeActivity.this, AuthorQuestionActivity.class);
			startActivity(authorQuestion); 
		}
		else {
			Toast.makeText(this, "Please login to post a question", Toast.LENGTH_SHORT).show();
			Intent createAccount = new Intent(this, CreateAccountActivity.class);
			startActivity(createAccount);
		}
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
