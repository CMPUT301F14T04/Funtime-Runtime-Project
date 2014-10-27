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
import android.widget.TextView;
import android.widget.Toast;


public class QuestionPageActivity extends Activity
{
	ListView questionListView;
	AnswerList questionAnswerList;
	Account account;
	TextView questionTitle;
	TextView questionBody;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_page);
		Intent intent = getIntent();
		//Question question = (Question) intent.getSerializableExtra("Question");
		Bundle extras = intent.getExtras();
		Question question = (Question) extras.getSerializable("Question");
		
		questionTitle = (TextView) findViewById(R.id.question_title_text);
		questionTitle.setText(question.getTitle());
		questionBody = (TextView) findViewById(R.id.question_body_text);
		questionBody.setText(question.getBody());
		questionAnswerList = new AnswerList();
		questionListView =  (ListView) findViewById(R.id.listView1);
		account = ApplicationState.getAccount();
		questionAnswerList = question.getAnswerList();
		AnswerListAdapter adapter = new AnswerListAdapter(this, R.layout.answer_list_adapter, questionAnswerList.getAnswerList());
		questionListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		//testQuestionPage();	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question_page, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// switch case to choose an item from the menu
		
//-------------------------------------------
// Menu Items Switch Case
//-------------------------------------------
		switch (item.getItemId()) {
			case R.id.login_menu_item:
				openLogin();
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
			
	public void openSearch() {
		Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(QuestionPageActivity.this, SearchActivity.class);
		startActivity(intent);
	}
			
	public void openLogin(){
		Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(QuestionPageActivity.this, LoginActivity.class);
		startActivity(intent);
	}
			
	public void openMyQuestions() {
		Toast.makeText(this, "My Questions", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(QuestionPageActivity.this, MyQuestionsActivity.class);
		startActivity(intent);
	}
			
	public void openMyAnswers() {
		Toast.makeText(this, "My Answers", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(QuestionPageActivity.this, MyAnswersActivity.class);
		startActivity(intent);
	}
			
	public void openMyFavourites() {
		Toast.makeText(this, "My Favourites", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(QuestionPageActivity.this, MyFavouritesActivity.class);
		startActivity(intent);
	}
			
	public void openMyReadingList() {
		Toast.makeText(this, "My Reading List", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(QuestionPageActivity.this, ReadingListActivity.class);
		startActivity(intent);
	}
			
	public void openMyHistory() {
		Toast.makeText(this, "My History", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(QuestionPageActivity.this, MyHistoryActivity.class);
		startActivity(intent);
	}

	public void openSortList() {
		Toast.makeText(this, "Choose A Sorting Method", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(QuestionPageActivity.this, Sort.class);
		startActivity(intent);
	}
//-------------------------------------------
//-------------------------------------------
	
	private void testQuestionPage() {
		List<Answer> answerList = new ArrayList<Answer>();
		AnswerListAdapter adapter = new AnswerListAdapter(this, R.layout.answer_list_adapter, answerList);
		Answer answer1 = new Answer("Sweet", "user1");
		Answer answer2 = new Answer("Question", "user2");
		Answer answer3 = new Answer("Bro do you even lift????????????????????????????????????????", "user3");
		answerList.add(answer1);
		answerList.add(answer2);
		answerList.add(answer3);
		questionListView.setAdapter(adapter);	
		adapter.notifyDataSetChanged();
	}

	
	//start the AuthorAnswerActivity --> Author Answer Page
	public void addAnswer(View v)
	{
		Intent authorAnswer = new Intent(QuestionPageActivity.this, AuthorAnswerActivity.class);
		startActivity(authorAnswer);
	}
	
	

}
