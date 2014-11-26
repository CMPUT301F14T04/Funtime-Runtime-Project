package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.classes.Answer;
import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;
import ca.ualberta.cs.funtime_runtime.classes.Geolocation;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.classes.UpdateQuestionThread;
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
	ImageButton geoButton;
	EditText answerBody;
	TextView questionTitle;
	TextView questionBody;
	Account account;
	String username;
	
	//ArrayList<Question> userAnsweredList;
	ArrayList<Integer> userAnsweredIdList;
	boolean hasLocation = false;

	Geolocation geoLocation;
	int CAMERA_COLOR = Color.parseColor("#001110");
	int MAP_COLOR = Color.parseColor("#3366FF");

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
		geoButton = (ImageButton) findViewById(R.id.answer_geo_button);
		account = ApplicationState.getAccount();
		if (ApplicationState.isLoggedIn()) {
			username = account.getName();
		}
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
		 if (ApplicationState.isLoggedIn()) {
			Answer answer = new Answer(answerBody.getText().toString(), username.toString());
			
			if (hasLocation){
				answer.setLocation(geoLocation.getLocation());
			} 
			
			userAnsweredIdList = account.getAnsweredList();
			question.addAnswer(answer);
			account.answerQuestion(question);
			Thread updateThread = new UpdateQuestionThread(question);
			updateThread.start();
			try {
				updateThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	
			//userAnsweredIdList.add(0,question.getId());
			//TODO save account
		
			finish();
		 } else {
			 String msg = ApplicationState.notLoggedIn();
			 Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
		 }
				
	}
	 /**
	  * This onClick listener leaves the activity after the "cancel button is clicked
	  * @param v is a button within the view
	  */
	 public void answer_cancel(View v){
		 finish();
	 }
	 
	 public void add_photo(View v){
			Intent  photoPickerIntent = new Intent(Intent.ACTION_PICK);
			photoPickerIntent.setType("image/*");
			startActivityForResult(photoPickerIntent, 1);
	 }
	 
	 public void addLocation(View v){
		 geoLocation = new Geolocation(this);
		 geoLocation.findLocation();
		 hasLocation = true;
		 Toast.makeText(this, "Location added", Toast.LENGTH_LONG).show();
		 geoButton.setColorFilter(MAP_COLOR);
	 }
	
	 protected void onActivityResult(int requestCode, int resultCode,
		        Intent intent) {
		    super.onActivityResult(requestCode, resultCode, intent);

		    if (resultCode == RESULT_OK) {
		        Uri photoUri = intent.getData();

		        if (photoUri != null) {
		            try {
		                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
		                int byteCount = bitmap.getByteCount();
		                if (byteCount > 0)
		                	Log.i("Image Upload", ""+byteCount);
		                //your_imgv.setImageBitmap(bitmap);
		                //profilePicPath = photoUri.toString();
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
		    }
		}

}
