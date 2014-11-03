package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class ReplyPageActivity extends CustomActivity {
	
	String parentTitle;
	String parentBody;
	String parentDate;
	String parentUsername;
	ArrayList<Reply> repliesList;
	
	TextView parentTitleText;
	TextView parentBodyText;
	TextView parentDateText;
	TextView parentUserText;
	
	ListView replyListView;
	
	ReplyListAdapter adapter;
	
	Question parentQuestion;
	
	LayoutInflater inflater;
	View header;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reply_page);
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		
		parentTitle = extras.getString("Title");
		parentBody = extras.getString("Body");
		parentDate = extras.getString("Date");
		parentUsername = extras.getString("Username");
		
		//parentQuestion = (Question) extras.getSerializable("Question");
		//repliesList = parentQuestion.getReplyList();
		
		repliesList = (ArrayList<Reply>) extras.getSerializable("Replies");
		
		replyListView = (ListView) findViewById(R.id.reply_list_view);
		
		inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		header = (View)inflater.inflate(R.layout.reply_page_header, null, false);
		replyListView.addHeaderView(header);
		
		parentTitleText = (TextView) findViewById(R.id.reply_parent_title);
		parentBodyText = (TextView) findViewById(R.id.reply_parent_body);
		parentDateText = (TextView) findViewById(R.id.reply_parent_date);
		parentUserText = (TextView) findViewById(R.id.reply_parent_user);
		
		parentTitleText.setText(parentTitle);
		parentBodyText.setText(parentBody);
		parentDateText.setText(parentDate);
		parentUserText.setText(parentUsername);
				
		adapter = new ReplyListAdapter(this, R.layout.reply_list_adapter, repliesList);
		replyListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reply_page, menu);
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
}
