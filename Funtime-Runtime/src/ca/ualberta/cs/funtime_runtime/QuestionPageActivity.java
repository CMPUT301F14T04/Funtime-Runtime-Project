package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
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
	TextView question_rating_value;
	TextView answersTitle;
	//TextView questionUpvote;
	Button addAnswerBtn;
	ImageButton favorite_button;
	ImageButton bookmark_button;
	Question question;
	Boolean favorited = false;
	Boolean upvoted = false;
	Integer rating;
	LayoutInflater inflater;
	View questionHeader;
	
	
		
	//ImageButton unfavorite_button;
	
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
		
		//Question question 		answersTitle.setText()= (Question) intent.getSerializableExtra("Question");
		questionTitle = (TextView) findViewById(R.id.question_title);
		questionBody = (TextView) findViewById(R.id.question_body);
		questionBody.setMovementMethod(new ScrollingMovementMethod());
		//questionUpvote = (TextView) findViewById(R.id.question_upvote_text);
		question = (Question) extras.getSerializable("Question");
		addAnswerBtn = (Button) findViewById(R.id.button_add_answer);
		question_rating_value = (TextView) findViewById(R.id.overallRating);
		bookmark_button = (ImageButton) findViewById(R.id.question_bookmark_button);
		
		//TODO: change bookmark color based on wether it is in their bookmarks
		bookmark_button.setColorFilter( R.color.gray, Mode.MULTIPLY );
		
		
		questionAuthor = (TextView) findViewById(R.id.question_author_text);
		questionDate = (TextView) findViewById(R.id.question_date_text);
		answersTitle = (TextView) findViewById(R.id.answers_title_text);
		
		
		questionTitle.setText(question.getTitle());		
		questionBody.setText(question.getBody());
		
		questionAuthor.setText("Author: " + question.getUser());
		questionDate.setText(question.getDate().toString());
		
		rating = question.getRating();
		question_rating_value.setText(Integer.toString(rating));
		
		question_rating_value.setOnTouchListener(new UpvoteTouchListener());
		
		questionAnswerList = new ArrayList<Answer>();
		account = ApplicationState.getAccount();
		questionAnswerList = question.getAnswerList();
		
		answersTitle.setText("Answers (" + questionAnswerList.size() + ")");
		
		AnswerListAdapter adapter = new AnswerListAdapter(this, R.layout.answer_list_adapter, questionAnswerList);
		answerListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		//testQuestionPage();	
		favorite_button = (ImageButton) findViewById(R.id.question_favorite_button);
		addAnswerBtn.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(QuestionPageActivity.this, AuthorAnswerActivity.class);
				startActivity(intent);
				}
			});

		/*
		question_rating_value.setOnClickListener(new View.OnClickListener() {		
			@SuppressLint("ResourceAsColor")
			@Override
			public void onClick(View v) {
				if (upvoted == false) {
					question.upVote();
					TextView rating_value = (TextView) findViewById(R.id.overallRating);
					rating_value.setTextColor(R.color.blue);
					upvoted = true;
				} else {
					question.downVote();
					TextView rating_value = (TextView) findViewById(R.id.overallRating);
					rating_value.setTextColor(R.color.blue);
					upvoted = false;
				}
			}
		});
		*/
		
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
	
	public void favorited(View v){
		//http://stackoverflow.com/questions/12249495/android-imagebutton-change-image-onclick-solved  -Tuesday October 28 2014
		if (favorited == false){
			ImageButton favorite_button = (ImageButton) findViewById(R.id.question_favorite_button);
			favorite_button.setImageResource(android.R.drawable.btn_star_big_on);
			account.addFavourite(question);
			favorited = true;
			
		}else if (favorited == true){
			ImageButton favorite_button = (ImageButton) findViewById(R.id.question_favorite_button);
			favorite_button.setImageResource(android.R.drawable.btn_star_big_off);
			account.removeFavourite(question);
			favorited = false;
			
		}

	}
	
	public void show_info(View v){
		
		
		
		
	}
	
	public void show_photo(View v){
		
	}
	public void upvote_question(View v){
		
		
		
	}
	

}
