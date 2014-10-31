package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MyAnswersActivity extends CustomActivity {
	ListView myAnswersListView;
	ArrayList<Question> myAnswersQuestionList;
	QuestionListAdapter adapter;
	Account account;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_answers);
		myAnswersQuestionList = new ArrayList<Question>();
		myAnswersListView = (ListView) findViewById(R.id.listView1);
		testMyAnswers();
		myAnswersListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Question question = (Question) adapter.getItem(position);				
				Bundle bundle = new Bundle();
				bundle.putSerializable("Question", question);
				Intent intent = new Intent(MyAnswersActivity.this, QuestionPageActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);			
			}
		});
		
		
		
	}
	
	
	private void testMyAnswers() {
		ArrayList<Question> questionList = new ArrayList<Question>();
		adapter = new QuestionListAdapter(this, R.layout.question_list_adapter, questionList);
		Question question1 = new Question("What is the meaning of life?", "body 1 test", "user1");
		questionList.add(question1);
		question1.downVote();
		Answer answer1 = new Answer("Sweet", "user1");
		question1.addAnswer(answer1);
		myAnswersListView.setAdapter(adapter);	
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_answers, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// switch case to choose an item from the menu
	
//-------------------------------------------
// Menu Items Switch Case
//-------------------------------------------
		switch (item.getItemId()) {
			case R.id.home_menu_item:
				openMyHome();
				return true;
			case R.id.my_questions_menu_item:
				openMyQuestions();
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
//-------------------------------------------
//-------------------------------------------

}
