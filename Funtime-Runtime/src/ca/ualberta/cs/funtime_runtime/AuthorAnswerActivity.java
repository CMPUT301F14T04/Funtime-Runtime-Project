package ca.ualberta.cs.funtime_runtime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AuthorAnswerActivity extends CustomActivity {
	
	Question question;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_author_answer);
//		TextView questionTitle = (TextView) findViewById(R.id.questionTitleAA);
//		TextView questionBody = (TextView) findViewById(R.id.questionBodyAA);
//		EditText answerBody = (EditText) findViewById(R.id.typeAnswerAA);
//		Button submitBtn = (Button) findViewById(R.id.submitAnswerButton);
		testAuthorAnswer();
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

}
