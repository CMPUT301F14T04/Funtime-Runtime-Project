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
import android.widget.TextView;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_author_reply);
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.author_reply, menu);
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

	 public void addReply(View v){ 
		Reply reply = new Reply(typeReply.getText().toString(), username.toString());
		
		replyList.add(0, reply);
		
		finish();
				
				
	}
	 public void replyCancel(View v){
		 finish();
	 }
	
}
