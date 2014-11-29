package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;
import java.util.Random;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import ca.ualberta.cs.funtime_runtime.classes.Reply;

/**
 * A view class that allows a user to edit text
 * and submit it as a reply to a question or answer.
 * The user can also cancel the creation of
 * an answer. The question body and title or the answer are displayed above
 * the edit text segment. 
 * 
 * @author Kieran Boyle
 *
 */
public class AuthorReplyActivity extends CustomActivity {

	Question question;
	Answer answer;
	Button submitButton;
	ImageButton geoButton;
	EditText typeReply;
	TextView parentTitleView;
	TextView parentBodyView;
	String parentTitle;
	String parentBody;
	String parentDate;
	Account account;
	String username;
	String parentUsername;
	ArrayList<Reply> replyList;
	String replyType;
	Geolocation geoLocation;
	String globalLocation;
	boolean hasLocation = false;
	int MAP_COLOR = Color.parseColor("#3366FF");
	private AlertDialog.Builder popDialog;
	private LayoutInflater inflater;
	
	/**
	 * This is a standard onCreate method
	 * In this method we link this java file with the xml.
	 * 
	 * @param savedInstanceState a bundle which maps string values to parceleable
	 * types
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_author_reply);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		popDialog = new AlertDialog.Builder(this);
		inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		replyType = extras.getString("ReplyType");
		if (replyType.equals("question")) {
			question = ApplicationState.getPassableQuestion();
			parentTitle = question.getTitle();
			parentBody = question.getBody();
			parentDate = question.getStringDate().toString();
			parentUsername = question.getUser();			
			replyList = question.getReplyList();
		} else if (replyType.equals("answer")) {
			answer = ApplicationState.getPassableAnswer();
			parentTitle = "";
			parentBody = answer.getBody();
			parentDate = answer.getDate().toString();
			parentUsername = answer.getUser();			
			replyList = answer.getReplyList();
		}
		
		parentTitleView = (TextView) findViewById(R.id.replyParentTitle);
		parentBodyView = (TextView) findViewById(R.id.replyParentBody);
		typeReply = (EditText) findViewById(R.id.typeReply);
		submitButton = (Button) findViewById(R.id.submitReplyButton);
		geoButton = (ImageButton) findViewById(R.id.reply_location_button);
		account = ApplicationState.getAccount();
		if (ApplicationState.isLoggedIn()) {
			username = account.getName();
		}
		parentTitleView.setText(parentTitle);
		parentBodyView.setText(parentBody);
		
	}
	
	/**
	 * this function adds items to the action bar if it present
	 * 
	 * @param menu  an interface for managing menu items
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.author_reply, menu);
		return true;
	}

	
	/**
	 * this onClick listener for a button simply submits whatever has been entered into the text field.
     *this button also leaves the activity after the reply is submitted
	 * @param v is a button within the view
	 */
	 public void addReply(View v) { 
		if (ApplicationState.isLoggedIn()) {
//			Reply reply = new Reply(typeReply.getText().toString(), username.toString());
	
			Reply reply;
			
			question = ApplicationState.getPassableQuestion();
			
			ArrayList<Question> newestQuestions = ApplicationState.getQuestionList(this);
			for (Question q: newestQuestions) {
				if (q.equals(question)) {
					question = q;
				}
			}
			
		
			
			if (replyType.equals("question")){
				reply = new Reply(-1 , question.getId(), typeReply.getText().toString(), username.toString());
				
				Random gen = new Random();
				Integer id = gen.nextInt(1000000);
				reply.setId(id);
				
				if (hasLocation){
					reply.setLocation(globalLocation);
				}
				
				question.addReply(reply);
				
				if ( (ApplicationState.isOnline(getApplicationContext())) ) {
					ApplicationState.updateServerQuestion(question);
				} else {
					ApplicationState.addOfflineQuestionReply(reply, getApplicationContext());
				}
				
			//} else if (replyType.equals("answer")){
			} else {
				answer = ApplicationState.getPassableAnswer();
				
				ArrayList<Answer> newestAnswers = question.getAnswerList();
				for (Answer a: newestAnswers) {
					if (a.equals(answer)) {
						answer = a;
					}
				}
				
				reply = new Reply(answer.getId(), question.getId(), typeReply.getText().toString(), username.toString());
				
				if (hasLocation){
					reply.setLocation(globalLocation);
				}
				
				answer.addReply(reply);
				if ( (ApplicationState.isOnline(getApplicationContext())) ) {
					ApplicationState.updateServerQuestion(question);
				} else {
					ApplicationState.addOfflineAnswerReply(reply, getApplicationContext());
				}
				//question = ApplicationState.getPassableQuestion();
				ApplicationState.setPassableAnswer(answer);
			}
			

			
			ApplicationState.cacheQuestion(question, this);
			ApplicationState.setPassableQuestion(question);
			
//			ApplicationState.updateServerQuestion(question);
//			Thread updateThread = new UpdateQuestionThread(question);
//			updateThread.start();
			
			finish();		
		} else {
			String msg = ApplicationState.notLoggedIn();
			Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
		}
	}
	 
	 public void addLocation(View v){ 
		 final View view = inflater.inflate(R.layout.geolocation_popup,(ViewGroup) findViewById(R.id.geolocation_dialog)); 
		 final EditText locationEdit = (EditText) view.findViewById(R.id.editText_Location); 
		 final CheckBox addCheck = (CheckBox) view.findViewById(R.id.add_location_box);
	 
		 popDialog.setTitle("Set Location");
		 popDialog.setView(view);		
//		 
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
//		 
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
//		 
		 popDialog.create();
		 popDialog.show();
		 
		 //Toast.makeText(this, globalLocation, Toast.LENGTH_LONG).show();
		
//		 //http://www.thaicreate.com/mobile/android-popup-custom-layout-and-returning-values-from-dialog.html
//		 //November 28 2014
		 
	 }
	 
	 /**
	  * This onClick listener leaves the activity after the "cancel button is clicked
	  * @param v is a button within the view
	  */
	 public void replyCancel(View v){
		 finish();
	 }

}
