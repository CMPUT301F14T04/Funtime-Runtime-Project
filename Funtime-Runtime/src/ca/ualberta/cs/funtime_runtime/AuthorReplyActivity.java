package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import ca.ualberta.cs.funtime_runtime.classes.Reply;
import ca.ualberta.cs.funtime_runtime.classes.UpdateQuestionThread;

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
	boolean hasLocation = false;
	int MAP_COLOR = Color.parseColor("#3366FF");
	
	
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
			Reply reply = new Reply(typeReply.getText().toString(), username.toString());
	
			if (replyType.equals("question")){
				question.addReply(reply);
			} else if (replyType.equals("answer")){
				answer.addReply(reply);
				question = ApplicationState.getPassableQuestion();
			}
			
			if (hasLocation){
				reply.setLocation(geoLocation.getLocation());
			}
			
			Thread updateThread = new UpdateQuestionThread(question);
			updateThread.start();
			
			finish();		
		} else {
			String msg = ApplicationState.notLoggedIn();
			Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
		}
	}
	 
	 public void addLocation(View v) {
		 geoLocation = new Geolocation(this);
		 geoLocation.findLocation();
		 hasLocation = true;
		 Toast.makeText(this, "Location added", Toast.LENGTH_LONG).show();
		 geoButton.setColorFilter(MAP_COLOR);
	 }
	 
	 /**
	  * This onClick listener leaves the activity after the "cancel button is clicked
	  * @param v is a button within the view
	  */
	 public void replyCancel(View v){
		 finish();
	 }

}
