package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


public class QuestionPageActivity extends CustomActivity {
	ListView answerListView;
	ArrayList<Answer> questionAnswerList;
	Account account;
	TextView questionTitle;
	TextView questionBody;
	TextView questionAuthor;
	TextView questionDate;
	//TextView questionUpvote;
	TextView answersTitle;
	Button questionUpvote;
	Button addAnswerBtn;
	ImageButton favourite_button;
	ImageButton bookmark_button;
	ImageButton photo_button;
	Question question;
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
	
		
	//ImageButton unfavourite_button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_page);
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		
		// adapted from http://stackoverflow.com/questions/8275669/how-do-i-use-listview-addheaderview - accessed Nov 1 2014
		
		answerListView =  (ListView) findViewById(R.id.answer_list);
		inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		questionHeader = (View)inflater.inflate(R.layout.question_page_header, null, false);
		answerListView.addHeaderView(questionHeader);
		
		questionTitle = (TextView) findViewById(R.id.question_title);
		questionBody = (TextView) findViewById(R.id.question_body);
		questionBody.setMovementMethod(new ScrollingMovementMethod());
		question = (Question) extras.getSerializable("Question");
		addAnswerBtn = (Button) findViewById(R.id.button_add_answer);
		bookmark_button = (ImageButton) findViewById(R.id.question_bookmark_button);
		photo_button = (ImageButton) findViewById(R.id.view_photo_button);
		questionUpvote = (Button) findViewById(R.id.overallRating);
		favourite_button = (ImageButton) findViewById(R.id.question_favorite_button);

		account = ApplicationState.getAccount();
		
		ArrayList<Question> favourited_list = account.getFavouritesList();
		if (favourited_list.contains(question)) {
			favourited = true;
			favourite_button.setImageResource(android.R.drawable.btn_star_big_on);
		} else {
			favourite_button.setImageResource(android.R.drawable.btn_star_big_off);
		}
		
		ArrayList<Question> upvoted_list = account.getUpvotedQuestions();
		if (upvoted_list.contains(question)) {
			upvoted = true;
			questionUpvote.setTextColor(upvote_color);
		} else {
			questionUpvote.setTextColor(Color.parseColor("#000000"));
		}
		rating = question.getRating();
		questionUpvote.setText(Integer.toString(rating));
		

		ArrayList<Question> bookmarked_list = account.getReadingList();
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
		
		AnswerListAdapter adapter = new AnswerListAdapter(this, R.layout.answer_list_adapter, questionAnswerList);
		answerListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		addAnswerBtn.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(QuestionPageActivity.this, AuthorAnswerActivity.class);
				startActivity(intent);
				}
			});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question_page, menu);
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
	public void addAnswer(View v)
	{
		Intent authorAnswer = new Intent(QuestionPageActivity.this, AuthorAnswerActivity.class);
		startActivity(authorAnswer);
	}
	
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

	public void view_replies(View v) {
		Bundle bundle = new Bundle();
		
		//bundle.putSerializable("Question", question);
		
		bundle.putString("Username", question.getUser());
		bundle.putString("Title", question.getTitle());
		bundle.putString("Body", question.getBody());
		bundle.putString("Date", question.getDate().toString());
		bundle.putSerializable("Replies", question.getReplyList());
		Intent intent = new Intent(QuestionPageActivity.this, ReplyPageActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);	
	}
	
	public void show_photo(View v) {
		
	}
	
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
