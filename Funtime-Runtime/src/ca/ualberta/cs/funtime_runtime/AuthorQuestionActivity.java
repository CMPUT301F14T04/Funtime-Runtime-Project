package ca.ualberta.cs.funtime_runtime;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

public class AuthorQuestionActivity extends Activity{
	
	Button submitButton;
	Button addPhotoButton;
	Button cancelButton;
	EditText questionTitle;
	EditText questionBody;
	Account account;
	String username;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_author_question);
//		submitButton = (Button) findViewById(R.id.submit_question_button);
		//addPhotoButton = (Button) findViewById(R.id.add_question_image);
//		cancelButton = (Button) findViewById(R.id.cancel_button);
//		addPhotoButton = (Button) findViewById(R.id.add_image_button);
//		questionTitle = (EditText) findViewById(R.id.question_title);
//		questionBody = (EditText) findViewById(R.id.question_body_text);
		//account = ApplicationState.getAccount();
		//username = account.getName();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.author_question, menu);
		return true;
	}
	
	public Question submitQuestion() {
		//Question question = new Question(questionTitle, questionBody, "username");
		return null;
		
	}

}
