package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.classes.Answer;
import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;
import ca.ualberta.cs.funtime_runtime.classes.Question;
/**
 * A view class that allows a user to edit text
 * and submit it as an answer to a question.
 * The user can also cancel the creation of
 * an answer. The question body and title are displayed above
 * the edit text segment. this class will
 * eventually allow you to add a photo
 * 
 * @author Kieran Boyle
 *
 */

public class AuthorAnswerActivity extends CustomActivity {
	
	Question question;
	Button submitButton;
	EditText answerBody;
	TextView questionTitle;
	TextView questionBody;
	Account account;
	String username;
	ArrayList<Question> userAnsweredList;

	/**
	 * This is a standard onCreate method
	 * In this method we link this java file with the xml.
	 * 
	 * @param savedInstanceState a bundle which maps string values to parceleable
	 * types
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_author_answer);
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		
		questionTitle = (TextView) findViewById(R.id.questionTitleAA);
		questionBody = (TextView) findViewById(R.id.questionBodyAA);
		answerBody = (EditText) findViewById(R.id.typeAnswerAA);
		submitButton = (Button) findViewById(R.id.submitAnswerButton);
		account = ApplicationState.getAccount();
		username = account.getName();
		question = ApplicationState.getPassableQuestion();
		questionTitle.setText(question.getTitle());
		questionBody.setText(question.getBody());
		
	}
	
	/**
	 * this function adds items to the action bar if it present
	 * 
	 * @param menu  an interface for managing menu items
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.author_answer, menu);
		return true;
	}
	
	
	/**
	 * this onCLick method linked to a button simply submits whatever has been entered into the text field.
     *this button also leaves the activity after the answer is submitted
	 * @param v is a button within the view
	 */
	 public void answer_question(View v){ 
		Answer answer = new Answer(answerBody.getText().toString(), username.toString());
		userAnsweredList = account.getAnsweredList();
		question.addAnswer(answer);
		userAnsweredList.add(0,question);
		
		finish();
				
	}
	 /**
	  * This onClick listener leaves the activity after the "cancel button is clicked
	  * @param v is a button within the view
	  */
	 public void answer_cancel(View v){
		 finish();
	 }

}
