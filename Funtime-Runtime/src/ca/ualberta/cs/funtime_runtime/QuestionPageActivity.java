package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import ca.ualberta.cs.funtime_runtime.adapter.AnswerListAdapter;
import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.classes.Answer;
import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.classes.UpdateQuestionThread;

/**
 * A view class that displays the question and
 * all it's attributes
 * Allows users to see a question that has been created 
 * as well as all the answers associated with the question.
 * The user can favorite, bookmark and upvote questions here.
 * further releases will allow a user to view photos attached to 
 * the current question and answers.
 * This page also allows users to upvote answers to questions.
 * This page also links to the view reply pages for both questions and answers. 
 * 
 * @author Kieran Boyle
 *
 */
public class QuestionPageActivity extends CustomActivity {
	ListView answerListView;
	ArrayList<Answer> questionAnswerList;
	AnswerListAdapter adapter;
	Account account;
	TextView questionTitle;
	TextView questionBody;
	TextView questionAuthor;
	TextView questionDate;
	TextView answersTitle;
	Button questionUpvote;
	Button addAnswerBtn;
	Button repliesText;
	ImageButton favourite_button;
	ImageButton bookmark_button;
	ImageButton photo_button;
	Question question;
	ArrayList<Question> favourited_list;
	ArrayList<Question> upvoted_list;
	ArrayList<Question> bookmarked_list;
	Boolean favourited = false;
	Boolean upvoted = false;
	Boolean bookmarked = false;
	Boolean has_photo = false;
	Integer rating;
	LayoutInflater inflater;
	View questionHeader;
	int bookmarked_color = Color.parseColor("#cc0000");
	int not_bookmarked_color = Color.parseColor("#cecece");
	int has_photo_color = Color.parseColor("#228b22");
	int upvote_color = Color.parseColor("#e77619");
	
	/**
	 * The function below initializes the view and links the 
	 * java document with the view. the functions also tests whether
	 * a specific question is in the favorites, bookmarks, or upvoted list.
	 * @param savedInstanceState a bundle which maps string values to parceleable
	 * types
	 * 
	 */
	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_page);
		
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		// adapted from http://stackoverflow.com/questions/8275669/how-do-i-use-listview-addheaderview - accessed Nov 1 2014
		answerListView =  (ListView) findViewById(R.id.answer_list);
		inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		questionHeader = (View)inflater.inflate(R.layout.question_page_header, null);
		answerListView.addHeaderView(questionHeader);
		
		questionTitle = (TextView) findViewById(R.id.question_title);
		questionBody = (TextView) findViewById(R.id.question_body);
		question = ApplicationState.getPassableQuestion();
		
		addAnswerBtn = (Button) findViewById(R.id.button_add_answer);
		bookmark_button = (ImageButton) findViewById(R.id.question_bookmark_button);
		photo_button = (ImageButton) findViewById(R.id.view_photo_button);
		questionUpvote = (Button) findViewById(R.id.overallRating);
		favourite_button = (ImageButton) findViewById(R.id.question_favorite_button);
		repliesText = (Button) findViewById(R.id.replies_text);

		account = ApplicationState.getAccount();
		
		favourited_list = account.getFavouritesList();
		if (favourited_list.contains(question)) {
			favourited = true;
			favourite_button.setImageResource(android.R.drawable.btn_star_big_on);
		} else {
			favourite_button.setImageResource(android.R.drawable.btn_star_big_off);
		}
		
		upvoted_list = account.getUpvotedQuestions();
		if (upvoted_list.contains(question)) {
			upvoted = true;
			questionUpvote.setTextColor(upvote_color);
		} else {
			questionUpvote.setTextColor(Color.parseColor("#000000"));
		}
		rating = question.getRating();
		questionUpvote.setText(Integer.toString(rating));
		

		bookmarked_list = account.getReadingList();
		if (bookmarked_list.contains(question)) {
			Intent intent = getIntent();
			Bundle extras = intent.getExtras();
			
			String readingCheck = extras.getString("ReadingCheck");
			if (readingCheck != null) {
				extras.remove("ReadingCheck");
				bookmarked_list.remove(question);
				bookmark_button.setColorFilter(not_bookmarked_color);
			} else {
				bookmarked = true;
				bookmark_button.setColorFilter(bookmarked_color);
			}
		} else {
			bookmark_button.setColorFilter(not_bookmarked_color);
		}
		
		if (has_photo == true){
			photo_button.setColorFilter(has_photo_color);
		}
		
		questionAuthor = (TextView) findViewById(R.id.question_author_text);
		questionDate = (TextView) findViewById(R.id.question_date_text);
		answersTitle = (TextView) findViewById(R.id.answers_title_text);
		
		
		questionTitle.setText(question.getTitle());		
		questionBody.setText(question.getBody());
		
		questionAuthor.setText("Author: " + question.getUser());
		questionDate.setText("Posted: " + question.getStringDate().toString());
		
		questionAnswerList = new ArrayList<Answer>();
		questionAnswerList = question.getAnswerList();
		
		answersTitle.setText("Answers (" + questionAnswerList.size() + ")");
		
		repliesText.setText("Replies: " + question.getReplyCount());
		
		adapter = new AnswerListAdapter(this, R.layout.answer_list_adapter, questionAnswerList);
		answerListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		answerListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Header is the first element in the listview, so
				//  we need to access ArrayList item at listview position - 1
				Answer answer = adapter.getItem(position - 1);	
				
				Bundle bundle = new Bundle();
				
				ApplicationState.setPassableAnswer(answer);
				
				bundle.putString("ReplyType", "answer");
								
				Intent intent = new Intent(QuestionPageActivity.this, ReplyPageActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);	
				
			}
		});
		
		// TODO save reading list locally
		
	}
	
	/**
	 * When the activity moves into the foreground
	 * the function runs through and does a number of checks.
	 * The checks try to determine whether the current question 
	 * is in the bookmarks, favorites, or upvoted lists. 
	 * The rating for the question is also retrieved.
	 */
	@Override
	public void onRestart() {
		super.onRestart();
		if (favourited_list.contains(question)) {
			favourited = true;
			favourite_button.setImageResource(android.R.drawable.btn_star_big_on);
		} else {
			favourite_button.setImageResource(android.R.drawable.btn_star_big_off);
		}
		
		if (upvoted_list.contains(question)) {
			upvoted = true;
			questionUpvote.setTextColor(upvote_color);
		} else {
			questionUpvote.setTextColor(Color.parseColor("#000000"));
		}
		rating = question.getRating();
		questionUpvote.setText(Integer.toString(rating));
		

		if (bookmarked_list.contains(question)) {
			bookmarked = true;
			bookmark_button.setColorFilter(bookmarked_color);
		} else {
			bookmark_button.setColorFilter(not_bookmarked_color);
		}
		
		answersTitle.setText("Answers (" + questionAnswerList.size() + ")");
		
		repliesText.setText("Replies: " + question.getReplyCount());
		
		
		adapter.notifyDataSetChanged();
		
		// TODO save reading list locally
	
	}
	
	/**
	 * this function adds items to the action bar if it present
	 * 
	 * @param menu  an interface for managing menu items
	 */
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.question_page, menu);
		return true;
	}

	
	/**
	 * This is a simple ONclick listener that sends the user to 
	 * the add answer page
	 * 
	 * @param v is a button in the view that when clicked, navigates 
	 * the user to a different page. 
	 */
	public void addAnswer(View v)	{
		ApplicationState.setPassableQuestion(question);
		Intent authorAnswer = new Intent(QuestionPageActivity.this, AuthorAnswerActivity.class);
		startActivity(authorAnswer);
	}
	
	/**
	 * This function adds a specific question to the favorites list
	 * it also triggers a change in the appearance of the star/favorites button 
	 * within the app.
	 * 
	 * @param v is a button within the view that the user presses to favorite.
	 * it looks like a star.
	 */
	public void favourited(View v){
		//http://stackoverflow.com/questions/12249495/android-imagebutton-change-image-onclick-solved  -Tuesday October 28 2014
		if (favourited == false) {
			ImageButton favourite_button = (ImageButton) findViewById(R.id.question_favorite_button);
			favourite_button.setImageResource(android.R.drawable.btn_star_big_on);
			account.addFavourite(question);
			favourited = true;
		} else if (favourited == true) {
			ImageButton favourite_button = (ImageButton) findViewById(R.id.question_favorite_button);
			favourite_button.setImageResource(android.R.drawable.btn_star_big_off);
			account.removeFavourite(question);
			favourited = false;
		}
	}
	
	/**
	 * this function is linked to a button that sends the user to
	 * the reply page for a question
	 * @param v is a button within the view. 
	 */
	public void view_replies(View v) {
		Bundle bundle = new Bundle();
		
		ApplicationState.setPassableQuestion(question);
		
		bundle.putString("ReplyType", "question");
		
		Intent intent = new Intent(QuestionPageActivity.this, ReplyPageActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);	
	}
	
	/**
	 * This function shows a photo attached to the question when it is clicked
	 * @param v this is another button within the view.
	 */
	
	public void show_photo(View v) {
		
	}
	
	/**
	 * this button allows the user to upvote a question once 
	 * the text changes color upon being clicked. changes the overall rating of
	 * the question. 
	 * 
	 * @param v is a button within the view
	 * 
	 */
	public void upvote_question(View v){
		// Note: using the back button will save the question status (cannot upvote again)
		// Note: using the home icon will not save the question status (can upvote many times)
		Thread updateThread = new UpdateQuestionThread(question);
		if (upvoted) {
			//question.downVote();
			account.downvoteQuestion(question);
			rating = question.getRating();
			questionUpvote.setText(Integer.toString(rating));
			questionUpvote.setTextColor(Color.parseColor("#000000"));
			upvoted = false;
			
		} else {
			//question.upVote();
			account.upvoteQuestion(question);
			rating = question.getRating();
			questionUpvote.setText(Integer.toString(rating));
			questionUpvote.setTextColor(upvote_color);
			upvoted = true;
		}
		updateThread.start();
	}
	
	/**
	 * this function add a question to the read later page.
	 * THe button changes color upon being clicked.
	 * 
	 * @param v is a button within the view 
	 */
	public void bookmark_question(View v){
		if (bookmarked == false){
			account.readLater(question);
			bookmark_button.setColorFilter(bookmarked_color);
			bookmarked = true;
		} else if (bookmarked == true){
			account.removeReadLater(question);
			bookmark_button.setColorFilter(not_bookmarked_color);
			bookmarked = false;
		}
	}
	
	
	/**
	 * Take the user to the author answer activity
	 * @param v		 the view, unused.
	 */
	public void authorAnswer(View v) {
		openAuthorAnswerPage();
	}
	
}
