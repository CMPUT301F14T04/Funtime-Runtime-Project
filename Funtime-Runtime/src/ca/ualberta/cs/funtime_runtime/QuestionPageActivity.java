package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
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
	//TextView questionUpvote;
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
	int has_photo_color =Color.parseColor("#228b22");
	int upvote_color = Color.parseColor("#e77619");
	
	/**
	 * The function below initializes the view and links the 
	 * java document with the view. the functions also tests whether
	 * a specific question is in the favorites, bookmarks, or upvoted list.
	 * @param savedInstanceState a bundle which maps string values to parceleable
	 * types
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_page);
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		// adapted from http://stackoverflow.com/questions/8275669/how-do-i-use-listview-addheaderview - accessed Nov 1 2014
		
		answerListView =  (ListView) findViewById(R.id.answer_list);
		inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		questionHeader = (View)inflater.inflate(R.layout.question_page_header, null, false);
		answerListView.addHeaderView(questionHeader);
		
		questionTitle = (TextView) findViewById(R.id.question_title);
		questionBody = (TextView) findViewById(R.id.question_body);
		//questionBody.setMovementMethod(new ScrollingMovementMethod());
		
		//question = (Question) extras.getSerializable("Question");
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
			bookmarked = true;
			bookmark_button.setColorFilter(bookmarked_color);
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
		questionDate.setText(question.getDate().toString());
		
		questionAnswerList = new ArrayList<Answer>();
		questionAnswerList = question.getAnswerList();
		
		answersTitle.setText("Answers (" + questionAnswerList.size() + ")");
		
		repliesText.setText("Replies: " + question.getReplyCount());
		
		adapter = new AnswerListAdapter(this, R.layout.answer_list_adapter, questionAnswerList);
		answerListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		addAnswerBtn.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(QuestionPageActivity.this, AuthorAnswerActivity.class);
				startActivity(intent);
				}
			});
		
		answerListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//Needs to be position - 1. Why? I have no idea..
				Answer answer = adapter.getItem(position - 1);	
				
				Bundle bundle = new Bundle();
				
				ApplicationState.setPassableAnswer(answer);
				
				bundle.putString("ReplyType", "answer");
								
				Intent intent = new Intent(QuestionPageActivity.this, ReplyPageActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);	
				
			}
		});
		
	}
	/**
	 * When the activity moves into the foreground
	 * the function runs through an does a number of checks.
	 * The checks try to determine whether the current question 
	 * is in the bookmarks, favorites, or upvoted lists. 
	 * the rating for the question is also retrieved
	 * 
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
		
		repliesText.setText("Replies: " + question.getReplyCount());
		
		adapter.notifyDataSetChanged();
	
	}
	
	/**
	 * this function adds items to the action bar if it present
	 * 
	 * @param menu  an interface for managing menu items
	 */
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.question_page, menu);
		return true;
	}
	
	/**
	 * This function simply redirects to another activity when a certain menu 
	 * item is selected by the user. It operates a switch statement to transtion 
	 * between different activities
	 * 
	 * @param item is a menuItem signifying location within the menu that users 
	 * wish to visit
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// switch case to choose an item from the menu
		
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
			case R.id.searchQuestionsList:
				openSearch();
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
	 * test question page is simply a test that populates
	 * our question page with hard coded answers.
	 * 
	 */
	private void testQuestionPage() {
		List<Answer> answerList = new ArrayList<Answer>();
		AnswerListAdapter adapter = new AnswerListAdapter(this, R.layout.answer_list_adapter, answerList);
		Answer answer1 = new Answer("Sweet", "user1");
		Answer answer2 = new Answer("Question", "user2");
		Answer answer3 = new Answer("Bro do you even lift????????????????????????????????????????", "user3");
		answerList.add(answer1);
		answerList.add(answer2);
		answerList.add(answer3);
		answerListView.setAdapter(adapter);	
		adapter.notifyDataSetChanged();
	}

	
	//start the AuthorAnswerActivity --> Author Answer Page
	
	/**
	 * This is a simple ONclick listener that sends the user to 
	 * the add answer page
	 * 
	 * @param v is a button in the view that when clicked, navigates 
	 * the user to a different page. 
	 */
	public void addAnswer(View v)
	{
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
		if (favourited == false){
			ImageButton favourite_button = (ImageButton) findViewById(R.id.question_favorite_button);
			favourite_button.setImageResource(android.R.drawable.btn_star_big_on);
			account.addFavourite(question);
			favourited = true;
			
		}else if (favourited == true){
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
		if (upvoted) {
			question.downVote();
			account.downvoteQuestion(question);
			rating = question.getRating();
			questionUpvote.setText(Integer.toString(rating));
			questionUpvote.setTextColor(Color.parseColor("#000000"));
			upvoted = false;
		} else {
			question.upVote();
			account.upvoteQuestion(question);
			rating = question.getRating();
			questionUpvote.setText(Integer.toString(rating));
			questionUpvote.setTextColor(upvote_color);
			upvoted = true;
		}
	}
	
	/**
	 * this function add a question to the read later page.
	 * THe button changes color upon being clicked.
	 * 
	 * @param v is a button within the view 
	 */
	public void bookmark_question(View v){
		//bookmark_button.setColorFilter( R.color.red, Mode.MULTIPLY );
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

}
