package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class AuthorAnswerActivity extends CustomActivity {
	
	Question question;
	Button submitButton;
	EditText answerBody;
	TextView questionTitle;
	TextView questionBody;
	Account account;
	String username;
	ArrayList<Answer> userAnswerList;
	

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_author_answer);
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		
		questionTitle = (TextView) findViewById(R.id.questionTitleAA);
		questionBody = (TextView) findViewById(R.id.questionBodyAA);
		answerBody = (EditText) findViewById(R.id.typeAnswerAA);
		submitButton = (Button) findViewById(R.id.submitAnswerButton);
		account = ApplicationState.getAccount();
		username = account.getName();
		question = ApplicationState.getPassableQuestion();
		questionTitle.setText(question.getTitle());
		questionBody.setText(question.getBody());
		
		//questionBody.setEllipsize(TextUtils.TruncateAt.END);
		//questionBody.setMovementMethod(new ScrollingMovementMethod());
	}

	private void testAuthorAnswer() {
		TextView questionTitle = (TextView) findViewById(R.id.questionTitleAA);
		TextView questionBody = (TextView) findViewById(R.id.questionBodyAA);
		EditText answerBody = (EditText) findViewById(R.id.typeAnswerAA);
		Button submitBtn = (Button) findViewById(R.id.submitAnswerButton);
		
		question = new Question("Hello", "Goodbye", "troll");
		
		String title = question.getTitle();
		String body = question.getBody();
		String user = question.getUser();
		
		questionTitle.setText(title);
		questionBody.setText(body);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.author_answer, menu);
		return true;
	}
	
	 @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// switch case to choose an item from the menu
			
//-------------------------------------------
// Menu Items Switch Case
//-------------------------------------------
			
		switch (item.getItemId()) {
			case android.R.id.home:
				openMyHome();
				return true;
			case R.id.home_menu_item:
				openMyHome();
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
//-------------------------------------------
//-------------------------------------------	
	 public void answer_question(View v){ 
		Answer answer = new Answer(answerBody.getText().toString(), username.toString());
		userAnswerList = account.getAnswerList();
		question.addAnswer(answer);
		userAnswerList.add(0,answer);
		
		finish();
				
				
	}
	 public void answer_cancel(View v){
		 finish();
	 }

}
