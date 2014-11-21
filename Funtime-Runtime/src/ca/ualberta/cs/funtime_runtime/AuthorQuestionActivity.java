package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;
import java.util.Random;

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
import android.widget.Toast;
import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.elastic.ESQuestionManager;
/**
 * This view allows the user to create a question.
 * They use an edit texts to give a title and a body to the question 
 * that they wish to ask. 
 * The user will be able to add a photo to there question 
 * in the next iteration
 * @author Kieran Boyle
 *
 */
public class AuthorQuestionActivity extends CustomActivity {
	
	private static final int RANDOM_NUMBER_CAP = 100000000;
	
	Button submitButton;
	Button addPhotoButton;
	Button cancelButton;
	ImageButton photoButton;
	EditText questionTitle;
	EditText questionBody;
	Account account;
	String username;
	ArrayList<Question> questionList;
	ArrayList<Question> userQuestionList;
	ESQuestionManager questionManager;
	Random generator = new Random();
	int camera_color = Color.parseColor("#001110");
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
		setContentView(R.layout.activity_author_question);
		setResources();
	}

	private void setResources() {
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		submitButton = (Button) findViewById(R.id.submit_question_button);
		//addPhotoButton = (Button) findViewById(R.id.add_question_image);
		cancelButton = (Button) findViewById(R.id.cancel_button);
		//addPhotoButton = (Button) findViewById(R.id.add_image_button);
		questionTitle = (EditText) findViewById(R.id.question_title_text);
		questionBody = (EditText) findViewById(R.id.question_body_text);
		photoButton = (ImageButton)  findViewById(R.id.add_image_button);
		photoButton.setColorFilter(camera_color);
		account = ApplicationState.getAccount();
		if (ApplicationState.isLoggedIn())
			username = account.getName();
		questionManager = new ESQuestionManager();
	}
	
	/**
	 * this function adds items to the action bar if it present
	 * 
	 * @param menu  an interface for managing menu items
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.author_question, menu);
		return true;
	}
	
	/**
	 * this onCLick listener responds to the button that submits the 
	 * question. An instance of the question is created and added to the
	 * various question lists throughout the app. After the button is clicked 
	 * the user is navigated away from the page.
	 * 
	 * @param v is a button within the activity.
	 */
	public void submitQuestion(View v) {
		if (ApplicationState.isLoggedIn()) {
			Question question = new Question(questionTitle.getText().toString(),questionBody.getText().toString(),username.toString());
			if (ApplicationState.isLoggedIn()) {
				userQuestionList = account.getQuestionList();
				questionList = ApplicationState.getQuestionList();
			}
			//questionList.add(0,question);
			
			userQuestionList.add(0,question);
			
			// Elastic search code
			generateId(question);		
			Log.i("List Size", ""+questionList.size());
			addServerQuestion(question);
			
			if (ApplicationState.isLoggedIn()) {
				account.addToHistory(question); // Add question clicked to history
			}
			Bundle bundle = new Bundle();
			bundle.putSerializable("Question", question);
			Intent intent = new Intent(AuthorQuestionActivity.this, QuestionPageActivity.class);
			intent.putExtras(bundle);
			
			ApplicationState.setPassableQuestion(question);
			
			startActivity(intent);	
			
			finish();
		} else {
			String msg = ApplicationState.notLoggedIn();
			Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
		}
		
		
	}

	private void addServerQuestion(Question question) {
		Thread thread = new AddThread(question);
		thread.start();
	}

	private void generateId(Question question) {
		Thread searchThread = new SearchThread("*");
		searchThread.start();

		int id;
		//id = questionList.size();
		//id = questionManager.getFound();
		id = generator.nextInt(RANDOM_NUMBER_CAP);
		//Log.i("Found", ""+id);
		
		question.setId(id);
	}
	
	public void choosePhoto(View v){
		Intent  photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, 1);
	
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
	/**
	 * this onCLick listener is connected to the cancel button and navigates aw
	 * from the page.
	 * @param v is a button in the activity.
	 */
	 public void cancel_question(View v){
		 finish();
	 }
	 
	 class AddThread extends Thread {
		private Question question;

		public AddThread(Question question) {
			this.question = question;
		}

		@Override
		public void run() {
			questionManager.addQuestion(question);
			
			// Give some time to get updated info
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			runOnUiThread(doFinishAdd);
		}
	 }
	 
	class SearchThread extends Thread {
		private String search;
		
		public SearchThread(String s){		
			search = s;
		}
		
		@Override
		public void run() {
			questionList.clear();
			questionList.addAll(questionManager.searchQuestions(search, null));
		}
	}
	 
	private Runnable doFinishAdd = new Runnable() {
		public void run() {
			finish();
		}
	};	
		
	
	

}
