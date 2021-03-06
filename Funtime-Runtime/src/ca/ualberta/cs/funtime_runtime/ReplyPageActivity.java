package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cs.funtime_runtime.adapter.ReplyListAdapter;
import ca.ualberta.cs.funtime_runtime.classes.Answer;
import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.classes.QuestionSorter;
import ca.ualberta.cs.funtime_runtime.classes.Reply;
import ca.ualberta.cs.funtime_runtime.classes.ReplySorter;


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
	ArrayList<Reply> sortList = new ArrayList<Reply>();
	ReplySorter replySorter;
	QuestionSorter sorter;
	
	TextView parentTitleText;
	TextView parentBodyText;
	TextView parentDateText;
	TextView parentUserText;
	TextView parentReplyText;
	TextView location;
	
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
			parentDate = question.getStringDate();
			parentUsername = question.getUser();
			parentReply = question.getReplyCount();
			repliesList = question.getReplyList();
			replySorter = new ReplySorter(repliesList);
			replySorter.sortByDate();
			
			
			header = (View)inflater.inflate(R.layout.reply_page_header, null, false);
			replyListView.addHeaderView(header);	
			
			parentTitleText = (TextView) findViewById(R.id.reply_parent_title);
			parentTitleText.setText(parentTitle);	
			
			location = (TextView) findViewById(R.id.reply_question_location);
			location.setText("Location: " + question.getLocation());
			
			
		} else if (replyType.equals("answer")) {
			
			answer = ApplicationState.getPassableAnswer();
			parentBody = answer.getBody();
			parentDate = answer.getStringDate();
			parentUsername = answer.getUser();		
			parentReply = answer.getReplyCount();
			repliesList = answer.getReplyList();
			
			replySorter = new ReplySorter(repliesList);
			replySorter.sortByDate();
			
			header = (View)inflater.inflate(R.layout.reply_answers_page_header, null, false);
			replyListView.addHeaderView(header);	
			
			location = (TextView) findViewById(R.id.answer_reply_location);
			location.setText("Location: " + answer.getLocation());
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
		//loadServerReplies();
		adapter.notifyDataSetChanged();
		
		
	}
	
	public void onResume() {
		super.onResume();

		refresh();
		
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

	/**
	 * When the addReply button is selected this method takes the user to the author reply page
	 * The user can then add a new reply
	 * @param v
	 */
	public void addReply(View v) {
		if (ApplicationState.isLoggedIn()) {
			
			Bundle bundle = new Bundle();
			
			bundle.putString("ReplyType", replyType);
		
			Intent intent = new Intent(ReplyPageActivity.this, AuthorReplyActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		} else {
			String msg = ApplicationState.notLoggedIn();
			Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
		}
	}
	/**
	 * refreshes the listview so the most recent replies are visible.
	 */
	@Override
	public void refresh() {
		ApplicationState.refresh(this);
		ArrayList<Question> qList = ApplicationState.getQuestionList(this);
		ArrayList<Answer> aList;
		Question question = ApplicationState.getPassableQuestion();
		for (Question q: qList) {
			if (q.getId().equals(question.getId())) {
				question = q;
				ApplicationState.setPassableQuestion(question);
			}
			aList = question.getAnswerList();
		}
		
		if (replyType.equals("answer")) {
			aList = question.getAnswerList();
			for (Answer a: aList) {
				if (a.equals(answer)) {
					answer = a;
				}
			}

			repliesList = answer.getReplyList();
			adapter = new ReplyListAdapter(this, R.layout.reply_list_adapter, repliesList);
			replyListView.setAdapter(adapter);
			parentReply = repliesList.size();
			parentReplyText.setText("Replies " + "(" + parentReply + ")");
			replySorter = new ReplySorter(repliesList);
			replySorter.sortByDate();
			adapter.notifyDataSetChanged();
			
		} else if (replyType.equals("question")) {

			repliesList = question.getReplyList();
			adapter = new ReplyListAdapter(this, R.layout.reply_list_adapter, repliesList);
			replyListView.setAdapter(adapter);
			parentReply = repliesList.size();
			parentReplyText.setText("Replies " + "(" + parentReply + ")");
			replySorter = new ReplySorter(repliesList);
			replySorter.sortByDate();
			adapter.notifyDataSetChanged();
			
		}

	}
	/**
	 * sorts questions by date
	 */
	@Override
	public void sortDate() {
		replySorter.sortByDate();	
		adapter.notifyDataSetChanged();
	}
	/**
	 * sorts questions by location
	 */
	@Override
	public void sortLocation() {
		replySorter.sortByLocation(this);
		adapter.notifyDataSetChanged();
	}
	
	

	
}
