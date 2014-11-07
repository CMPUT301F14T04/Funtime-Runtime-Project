package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A view class that displays the replies corresponding to either an answer or a question
 * It contains various menu items options which can be selected from this activity page
 * The user can see the reply body, date, and author's username
 * The user can also see the information associated with an answer or a question,
 * depending on which parent the replies belong to
 * 
 * @author Pranjali Pokharel
 *
 */
public class ReplyPageActivity extends CustomActivity {
	
	int parentReply;
	String parentTitle;
	String parentBody;
	String parentDate;
	String parentUsername;
	ArrayList<Reply> repliesList;
	
	TextView parentTitleText;
	TextView parentBodyText;
	TextView parentDateText;
	TextView parentUserText;
	TextView parentReplyText;
	
	ListView replyListView;
	
	ReplyListAdapter adapter;
	
	String replyType;
	Question question;
	Answer answer;
	
	LayoutInflater inflater;
	View header;
	
	Button addReply;
	
	
	/**
	 * 
	 * This function displays the layout views depending on whether the reply belongs to a question or an answer
	 * Question option will display all attributes associated with a Question object (eg: title, body etc)
	 * Answer option will display all attributes associated with an Answer object (eg: body, username, etc)
	 * The header of the reply page will look different depending on which one the reply belongs to
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reply_page);
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		replyType = extras.getString("ReplyType");

		replyListView = (ListView) findViewById(R.id.reply_list_view);
		inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		
		if (replyType.equals("question")) {
			
			question = ApplicationState.getPassableQuestion();
			parentTitle = question.getTitle();
			parentBody = question.getBody();
			parentDate = question.getDate();
			parentUsername = question.getUser();
			parentReply = question.getReplyCount();
			repliesList = question.getReplyList();
			
			header = (View)inflater.inflate(R.layout.reply_page_header, null, false);
			replyListView.addHeaderView(header);	
			
			parentTitleText = (TextView) findViewById(R.id.reply_parent_title);
			parentTitleText.setText(parentTitle);			
		} else if (replyType.equals("answer")) {
			
			answer = ApplicationState.getPassableAnswer();
			parentBody = answer.getBody();
			parentDate = answer.getDate();
			parentUsername = answer.getUser();		
			parentReply = answer.getReplyCount();
			repliesList = answer.getReplyList();
			
			header = (View)inflater.inflate(R.layout.reply_answers_page_header, null, false);
			replyListView.addHeaderView(header);	
		}
		
		parentBodyText = (TextView) findViewById(R.id.reply_parent_body);
		parentDateText = (TextView) findViewById(R.id.reply_parent_date);
		parentUserText = (TextView) findViewById(R.id.reply_parent_user);
		parentReplyText = (TextView) findViewById(R.id.reply_seperator);
		
		addReply = (Button) findViewById(R.id.add_reply_button);
		
		parentBodyText.setText(parentBody);
		parentDateText.setText("Posted: " + parentDate);
		parentUserText.setText("Author: " + parentUsername);
		parentReplyText.setText("Replies " + "(" + parentReply + ")");
		
		adapter = new ReplyListAdapter(this, R.layout.reply_list_adapter, repliesList);
		replyListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		
	}
	
	public void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}
	
	/**
	 * menu at the top of the page(non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reply_page, menu);
		return true;
		
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//switch case to select a certain item from the menu
		
//-------------------------------------------
// Menu Items Switch Case
//-------------------------------------------
		switch (item.getItemId()) {
			case android.R.id.home:
				openMyHome();
				return true;
			case R.id.home_menu_item:
				openMyHome();
				return true;
			case R.id.login_menu_item:
				openLogin();
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

	/**
	 * When the addReply button is selected this method takes the user to the author reply page
	 * The user can then add a new reply
	 * @param v
	 */
	public void addReply(View v) {
		
		Bundle bundle = new Bundle();
		
		bundle.putString("ReplyType", replyType);
	
		Intent intent = new Intent(ReplyPageActivity.this, AuthorReplyActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
}
