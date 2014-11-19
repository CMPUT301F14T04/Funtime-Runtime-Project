package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import ca.ualberta.cs.funtime_runtime.classes.Reply;
import ca.ualberta.cs.funtime_runtime.elastic.ESQuestionManager;
import ca.ualberta.cs.funtime_runtime.elastic.ESReplyManager;

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
	
	ESQuestionManager questionManager;
	ESReplyManager replyManager;
	
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
		
		replyManager = new ESReplyManager();
		questionManager = new ESQuestionManager();
		replyType = extras.getString("ReplyType");
		
		if (replyType.equals("question")) {
			question = ApplicationState.getPassableQuestion();
			parentTitle = question.getTitle();
			parentBody = question.getBody();
			parentDate = question.getDate().toString();
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
		account = ApplicationState.getAccount();
		username = account.getName();
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
	
	private void addServerReply(Reply reply) {
		Thread thread = new AddReplyThread(reply);
		thread.start();
	}

	private void generateId(Reply reply) {
		Thread searchThread = new SearchReplyThread("*");
		searchThread.start();
		try {
			searchThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int id;
		id = replyList.size();
		//Log.i("Reply size", ""+replyList.size());
		
		reply.setId(id);
	}
	
	
	/**
	 * this onClick listener for a button simply submits whatever has been entered into the text field.
     *this button also leaves the activity after the reply is submitted
	 * @param v is a button within the view
	 */
	 public void addReply(View v) { 
		Reply reply = new Reply(typeReply.getText().toString(), username.toString());
		
		//generateId(reply);		
		//addServerReply(reply);
		//Log.i("Q Title", question.getTitle());
		// Add code to pull question (make sure we have most updated question)
		question.addReply(reply);
		Thread updateThread = new UpdateQuestionThread(question);
		updateThread.start();
		try {
			updateThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		finish();		
	}
	 
	 /**
	  * This onClick listener leaves the activity after the "cancel button is clicked
	  * @param v is a button within the view
	  */
	 public void replyCancel(View v){
		 finish();
	 }

	 class AddReplyThread extends Thread {
		private Reply reply;

		public AddReplyThread(Reply reply) {
			this.reply = reply;
		}

		@Override
		public void run() {
			replyManager.addReply(reply);
			
			// Give some time to get updated info
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			runOnUiThread(doFinishAdd);
		}
	 }
	 
	 
	// Thread that close the activity after finishing add
		private Runnable doFinishAdd = new Runnable() {
			public void run() {
				finish();
			}
		};
		
	class SearchReplyThread extends Thread {
		private String search;
		
		public SearchReplyThread(String s){		
			search = s;
		}
		
		@Override
		public void run() {
			replyList.clear();
			replyList.addAll(replyManager.searchReplies(search, null));
			//Log.i("Size", ""+replyList.size());
		}
	}
	
	class UpdateQuestionThread extends Thread {
		private Question question;
		private ESQuestionManager manager = new ESQuestionManager();
		
		public UpdateQuestionThread(Question q){		
			question = q;
		}
		
		@Override
		public void run() {
			updateQuestion(question);
		}

		private void updateQuestion(Question question) {
			manager.deleteQuestion(question.getId());
			manager.addQuestion(question);
			
		}
	}
}
