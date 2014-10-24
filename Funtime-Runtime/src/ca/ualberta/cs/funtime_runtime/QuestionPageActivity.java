package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;


public class QuestionPageActivity extends Activity
{
	ListView questionListView;
	AnswerList questionAnswerList;
	Account account;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_page);
		//Intent intent = getIntent();
		//Question question = (Question) intent.getSerializableExtra("Question");
		//Bundle extras = intent.getExtras();
		questionAnswerList = new AnswerList();
		questionListView =  (ListView) findViewById(R.id.listView1);
		account = ApplicationState.getAccount();
		//testQuestionPage();	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question_page, menu);
		return true;
	}
	
	
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
