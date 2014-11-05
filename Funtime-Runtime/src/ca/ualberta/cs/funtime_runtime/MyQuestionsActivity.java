package ca.ualberta.cs.funtime_runtime;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;


public class MyQuestionsActivity extends CustomActivity {
	ListView myQuestionsListView;
	ArrayList<Question> myQuestionList;
	QuestionListAdapter adapter;
	Account account;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_questions);
	
		myQuestionList = new ArrayList<Question>();
		myQuestionsListView = (ListView) findViewById(R.id.listView1);
		myQuestionsExample();
		myQuestionsListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Question question = (Question) adapter.getItem(position);
				Bundle bundle = new Bundle();
				bundle.putSerializable("Question", question);
				Intent intent = new Intent(MyQuestionsActivity.this, QuestionPageActivity.class);
				intent.putExtras(bundle);
				
				ApplicationState.setPassableQuestion(question);
				
				startActivity(intent);
				
			}
		});
		
	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		adapter.notifyDataSetChanged();
	
	}
	
	private void myQuestionsExample()
	{
		ArrayList<Question> questionList = new ArrayList<Question>();
		adapter = new QuestionListAdapter(this, R.layout.question_list_adapter, questionList);
		
		//Example 1
		Question question1 = new Question("Why are there animals in my van?", "Test: there are animals in my van etc", "user1");
		questionList.add(question1);
		question1.upVote();
		Answer answer1 = new Answer("Because the devil put it there", "user2");
		Answer answer2 = new Answer("Maybe you left the door open? Sometimes animals can also get in through the window", "user5");
		question1.addAnswer(answer1);
		question1.addAnswer(answer2);
		
		
		//Example 2
		Question question2 = new Question("How many colors can the people see?", "Test: like when it comes to skin color what if someone is color blind??", "user1");
		questionList.add(question2);
		question2.downVote();
		question2.downVote();
		Answer answer3 = new Answer("Wow you're not very smart are you?", "user2");
		answer3.upVote();
		question2.addAnswer(answer3);
		
		
		myQuestionsListView.setAdapter(adapter);	
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_questions, menu);
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

//-------------------------------------------
//-------------------------------------------		

}
