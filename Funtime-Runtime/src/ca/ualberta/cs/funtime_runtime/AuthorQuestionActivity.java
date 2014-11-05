package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AuthorQuestionActivity extends CustomActivity {
	
	Button submitButton;
	Button addPhotoButton;
	Button cancelButton;
	EditText questionTitle;
	EditText questionBody;
	Account account;
	String username;
	ArrayList<Question> questionList;
	ArrayList<Question> userQuestionList;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_author_question);
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		submitButton = (Button) findViewById(R.id.submit_question_button);
		//addPhotoButton = (Button) findViewById(R.id.add_question_image);
		cancelButton = (Button) findViewById(R.id.cancel_button);
		//addPhotoButton = (Button) findViewById(R.id.add_image_button);
		questionTitle = (EditText) findViewById(R.id.question_title_text);
		questionBody = (EditText) findViewById(R.id.question_body_text);
		account = ApplicationState.getAccount();
		username = account.getName();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.author_question, menu);
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
	
	public void submitQuestion(View v) {
		//Question question = new Question(questionTitle, questionBody, "username");
		//Question question1 = new Question("What is the meaning of life?", "body 1 test", "user1");
		Question query = new Question(questionTitle.getText().toString(),questionBody.getText().toString(),username.toString());
		questionList = ApplicationState.getQuestionList();
		userQuestionList = account.getQuestionList();
		questionList.add(0,query);
		userQuestionList.add(0,query);
		
		account.addToHistory(query); // Add question clicked to history
		Bundle bundle = new Bundle();
		bundle.putSerializable("Question", query);
		Intent intent = new Intent(AuthorQuestionActivity.this, QuestionPageActivity.class);
		intent.putExtras(bundle);
		
		ApplicationState.setPassableQuestion(query);
		
		startActivity(intent);	
		
		finish();
		
		
	}
	 public void cancel_question(View v){
		 finish();
	 }

}
