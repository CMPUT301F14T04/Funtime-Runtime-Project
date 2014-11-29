package ca.ualberta.cs.funtime_runtime;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.zip.Deflater;

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
import android.widget.Toast;
import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;
import ca.ualberta.cs.funtime_runtime.classes.Geolocation;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.elastic.ESQuestionManager;
import ca.ualberta.cs.funtime_runtime.thread.SearchQuestionThread;
import ca.ualberta.cs.funtime_runtime.thread.UpdateAccountThread;
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
	
	Button submitButton;
	Button addPhotoButton;
	Button cancelButton;
	ImageButton geoButton;
	ImageButton photoButton;
	EditText questionTitle;
	EditText questionBody;
	Account account;
	String username;
	ArrayList<Question> questionList;
	Question question;
	//Geolocation geoLocation;
	
	//ArrayList<Question> userQuestionList;
	ArrayList<Integer> userQuestionIdList;
	String globalLocation;
	
	ESQuestionManager questionManager;
	Bitmap photoBitmap;
	boolean hasPhoto = false;
	boolean hasLocation = false;
	byte[] array;
	byte[] compressedData = new byte[64000];
    Deflater compressor = new Deflater();
    Random generator = new Random();
    private static final int RANDOM_NUMBER_CAP = 100000000;
    UpdateAccountThread updateThread;
    private AlertDialog.Builder popDialog;
    private LayoutInflater inflater;
    //compressor.setLevel(Deflater.BEST_COMPRESSION);
	int CAMERA_COLOR = Color.parseColor("#000000");
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
		inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);;
		setContentView(R.layout.activity_author_question);
		setResources();
	}
	
	private void setResources() {
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		submitButton = (Button) findViewById(R.id.submit_question_button);
		geoButton = (ImageButton) findViewById(R.id.add_location_button);
		//addPhotoButton = (Button) findViewById(R.id.add_question_image);
		cancelButton = (Button) findViewById(R.id.cancel_button);
		//addPhotoButton = (Button) findViewById(R.id.add_image_button);
		questionTitle = (EditText) findViewById(R.id.question_title_text);
		questionBody = (EditText) findViewById(R.id.question_body_text);
		photoButton = (ImageButton)  findViewById(R.id.add_answerImage_button);
		
		account = ApplicationState.getAccount();
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
		
		question = new Question(questionTitle.getText().toString(),questionBody.getText().toString(),username.toString());
		questionList = ApplicationState.getQuestionList(this);
		userQuestionIdList = account.getQuestionList();
		if (hasPhoto == true){
			//question.getPhoto(array);
			question.setPhoto(compressedData);
		}
		if (hasLocation) {
			//question.setLocation(globalLocation);
		}
		
		Geolocation geoLocation = new Geolocation(getApplicationContext());
		geoLocation.findLocation();
		question.setLocation(geoLocation.getLocation());
		Toast.makeText(getApplicationContext(), geoLocation.getLocation(), Toast.LENGTH_SHORT).show();
		questionList.add(0,question);
		//userQuestionIdList.add(0,question.getId());

		// Elastic search code
		generateId(question);		
		//ApplicationState.addServerQuestions(question, this);
		
		Log.i("AuthorQuestion", "AuthorQuestion starting");
		
		account.authorQuestion(question, this);
		
		Log.i("AuthorQuestion", "AuthorQuestion finished");

		Bundle bundle = new Bundle();
		bundle.putSerializable("Question", question);
		Intent intent = new Intent(AuthorQuestionActivity.this, QuestionPageActivity.class);
		intent.putExtras(bundle);
		
		ApplicationState.setPassableQuestion(question);
		
		startActivity(intent);	
		
		finish();
		
	}


	private void generateId(Question question) {
		Thread searchThread = new SearchQuestionThread("*");
		searchThread.start();

		int id = generator.nextInt(RANDOM_NUMBER_CAP);
		question.setId(id);
	}
	
	public void choosePhoto(View v){
		Intent  photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, 1);
	
	}
	
	public void addLocation(View v) {
		final View view = inflater.inflate(R.layout.geolocation_popup,(ViewGroup) findViewById(R.id.geolocation_dialog)); 
		final EditText locationEdit = (EditText) view.findViewById(R.id.editText_Location); 
		final CheckBox addCheck = (CheckBox) view.findViewById(R.id.add_location_box);
		//final Geolocation geoLocation;
		//final Button attachButton = (Button) view.findViewById(R.id.attachLocationButton);
		
		//question = new Question(questionTitle.getText().toString(),questionBody.getText().toString(),username.toString());
		
		popDialog.setTitle("Set Location");
		popDialog.setView(view);		
		
		
		
		addCheck.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				if (addCheck.isChecked()) {

					Geolocation geoLocation = new Geolocation(getApplicationContext());

					//final Geolocation geoLocation = new Geolocation(getApplicationContext());

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
				dialog.dismiss();
				String location = locationEdit.getText().toString();
				globalLocation = location;
				hasLocation = true;
				geoButton.setColorFilter(MAP_COLOR);			
			}
		});
		
		popDialog.create();
		popDialog.show();
		
		//http://www.thaicreate.com/mobile/android-popup-custom-layout-and-returning-values-from-dialog.html
		//November 28 2014
	}
	
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
	                	hasPhoto = true;
	                	photoButton.setColorFilter(CAMERA_COLOR);
	                	Log.i("Image Upload", ""+byteCount);
	                }
	                ByteArrayOutputStream blob = new ByteArrayOutputStream();
	                photoBitmap.compress(CompressFormat.JPEG, 20 /*ignored for PNG*/, blob);
	                compressedData = blob.toByteArray();
	                Log.i("size of byte array", ""+ (int)compressedData.length);
//	                ByteBuffer buffer = ByteBuffer.allocate(byteCount); //Create a new buffer
//	                photoBitmap.copyPixelsToBuffer(buffer); //Move the byte data to the buffer
//	                array = buffer.array(); //Get the underlying array containing the data.
//	                Deflater compressor = new Deflater();
//	                compressor.setLevel(Deflater.BEST_COMPRESSION);
//	                compressor.setInput(array);
//	                compressor.finish();
//	                ByteArrayOutputStream bos = new ByteArrayOutputStream(array.length);
//	                byte[] buf = new byte[10];
//	                while (!compressor.finished()) {
//	                    int count = compressor.deflate(buf);
//	                    bos.write(buf, 0, count);
//	                }
//	                try {
//	                    bos.close();
//	                } catch (IOException e) {
//	                }
//	                
//	                // Get the compressed data
//	                compressedData = bos.toByteArray();
//	                int byteArraySize = (int)compressedData.length;
//	                if (byteArraySize > 0){
//	                	hasPhoto = true;
//	                	Log.i("size of byte array", ""+byteArraySize);
//	                }
	            }catch (Exception e) {
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
		

}
