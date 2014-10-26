package ca.ualberta.cs.funtime_runtime;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class MyQuestionsActivity extends Activity
{
	ListView myQuestionsListView;
	QuestionList myQuestionList;
	QuestionListAdapter adapter;
	Account account;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_questions);
	
		myQuestionList = new QuestionList();
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
				startActivity(intent);
				
			}
		});
		
	}
	
	private void myQuestionsExample()
	{
		List<Question> questionList = new ArrayList<Question>();
		adapter = new QuestionListAdapter(this, R.layout.question_list_adapter, questionList);
		Question question1 = new Question("Why are there animals in my van?", "Test: there are animals in my van etc", "user1");
		questionList.add(question1);
		question1.upVote();
		Answer answer1 = new Answer("Because the devil put it there", "user2");
		Answer answer2 = new Answer("Maybe you left the door open? Sometimes animals can also get in through the window", "user5");
		question1.addAnswer(answer1);
		question1.addAnswer(answer2);
		
		
		
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

}
