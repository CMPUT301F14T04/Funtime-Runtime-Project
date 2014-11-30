package ca.ualberta.cs.funtime_runtime;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.classes.Answer;
import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;
import ca.ualberta.cs.funtime_runtime.classes.Geolocation;
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
	
	Answer answer;
	Question question;
	Button submitButton;
	ImageButton geoButton;
	ImageButton photoButton;
	EditText answerBody;
	TextView questionTitle;
	TextView questionBody;
	Account account;
	String username;
	ArrayList<Integer> userAnsweredList;
	ArrayList<Integer> userAnswerIdList;
	String globalLocation;
	
	Bitmap photoBitmap;
	boolean hasPhoto = false;
	boolean hasLocation = false;
	byte[] array;
	byte[] compressedData = new byte[64000];
	
	private AlertDialog.Builder popDialog;
	private LayoutInflater inflater;
	
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
		
		final Context ctx = this;
		
		popDialog = new AlertDialog.Builder(this);
		inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
		setContentView(R.layout.activity_author_answer);
		setResources();
	}
	/**
	 * THis fuintion sets up all of the onCreate information. 
	 */
	private void setResources(){
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		submitButton = (Button) findViewById(R.id.submitAnswerButton);
		geoButton = (ImageButton) findViewById(R.id.answer_geo_button);
		photoButton = (ImageButton) findViewById(R.id.add_answerImage_button);
		
		questionTitle = (TextView) findViewById(R.id.questionTitleAA);
		questionBody = (TextView) findViewById(R.id.questionBodyAA);
		answerBody = (EditText) findViewById(R.id.typeAnswerAA);
		
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
			answer = new Answer(question.getId(), answerBody.getText().toString(), username.toString());
	
			Random gen = new Random();
			Integer id = gen.nextInt(1000000);
			answer.setId(id);
			if (hasPhoto == true){
				answer.setPhoto(compressedData);
			}
			
			userAnsweredList = account.getAnsweredList();
			
			if (hasLocation){
				answer.setLocation(globalLocation);
			}
			
			ArrayList<Question> newestQuestions = ApplicationState.getQuestionList(this);
			for (Question q: newestQuestions) {
				if (q.equals(question)) {
					question = q;
				}
			}
			

			question.addAnswer(answer);
			account.answerQuestion(question, answer, this);
			
			
			ApplicationState.cacheQuestion(question, this);
			ApplicationState.setPassableQuestion(question);

//			ApplicationState.updateServerQuestion(question);
//			ApplicationState.updateAccount();
//			Thread updateThread = new UpdateQuestionThread(question);
//			updateThread.start();
//			try {
//				updateThread.join();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
	
			//userAnsweredIdList.add(0,question.getId());
			//TODO save account
		
			finish();
		 } else {
			 String msg = ApplicationState.notLoggedIn();
			 Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
		 }
				
	}
	 /**
	  * add_photo sends you to the gallery where you choose a picture to upload
	  * @param v is the camera button that you click when you want to view a photo
	  */
	 
	 public void add_photo(View v){
		Intent  photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, 1);
	 }
	 /**
	  * This part of the program simply  adds the location by  an alert dialog 
	  * and then getting the current geolocation so that it can be displayed to the user
	  * upon submission of the answer
	  * @param v is a view that the application is currently located wihtin
	  */
	 
	 public void addLocation(View v){
		 final View view = inflater.inflate(R.layout.geolocation_popup,(ViewGroup) findViewById(R.id.geolocation_dialog)); 
		 final EditText locationEdit = (EditText) view.findViewById(R.id.editText_Location); 
		 final CheckBox addCheck = (CheckBox) view.findViewById(R.id.add_location_box);
		 
		 popDialog.setTitle("Set Location");
		 popDialog.setView(view);		
		 
		 addCheck.setOnClickListener(new View.OnClickListener() {		
			 @Override
			 public void onClick(View v) {
				 if (addCheck.isChecked()) {
					 
					 Geolocation geoLocation = new Geolocation(getApplicationContext());
					 
					 geoLocation.findLocation();
					 hasLocation = true;
					 String location = geoLocation.getLocation();
					 Toast.makeText(getApplicationContext(), location, Toast.LENGTH_SHORT).show();
					 globalLocation = location;
					 locationEdit.setText(location);	
					 } else {
						 locationEdit.setText("");
					 }
			 }
		 });
		 
		 popDialog.setNegativeButton("Attach Location" , new DialogInterface.OnClickListener() {
			 
			 @Override
			 public void onClick(DialogInterface dialog, int which)
			 {
				 String location = locationEdit.getText().toString();
				 globalLocation = location;
				 hasLocation = true;
				 geoButton.setColorFilter(MAP_COLOR);
				 dialog.dismiss();
			 }
		});
		 
		 popDialog.create();
		 popDialog.show();
			
		 //http://www.thaicreate.com/mobile/android-popup-custom-layout-and-returning-values-from-dialog.html
		 //November 28 2014
	 }
	
	 /**
	  * When the user returns from the gallary app they are directed towards this method where the 
	  * photo bitmap is converted to a JPEG, compressed and stored in a byte array where it is 
	  * uploaded with the question. 
	  */
	 protected void onActivityResult(int requestCode, int resultCode,
		        Intent intent) {
		    super.onActivityResult(requestCode, resultCode, intent);

		    if (resultCode == RESULT_OK) {
		        Uri photoUri = intent.getData();

		        if (photoUri != null) {
		            try {
		                photoBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
		                int byteCount = photoBitmap.getByteCount();
		                if (byteCount > 0){
		                	//photoButton.setColorFilter(CAMERA_COLOR);
		                	Context context = getApplicationContext();
		                	CharSequence text = "Photo Added!";
		                	int duration = Toast.LENGTH_LONG;
		                	Toast toast = Toast.makeText(context, text, duration);
		                	toast.show();
		                	hasPhoto = true;
		                	Log.i("Image Upload", ""+byteCount);
		                	hasPhoto = true;
		                }
		                ByteArrayOutputStream blob = new ByteArrayOutputStream();
		                photoBitmap.compress(CompressFormat.JPEG, 20 /*ignored for PNG*/, blob);
		                compressedData = blob.toByteArray();
		                
		                Log.i("size of byte array", ""+ (int)compressedData.length);
//		                answer = ApplicationState.getPassableAnswer();
//		                answer.setPhoto(compressedData);
		                //your_imgv.setImageBitmap(bitmap);
		                //profilePicPath = photoUri.toString();
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
		    }
	 }
	 /**
	  * This onClick listener leaves the activity after the "cancel button is clicked
	  * @param v is a button within the view
	  */
	 public void answer_cancel(View v){
		 finish();
	 }
}
		    
