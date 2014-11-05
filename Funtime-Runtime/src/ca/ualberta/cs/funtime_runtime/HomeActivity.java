package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HomeActivity extends CustomActivity {
	
	ListView homeListView;
	ArrayList<Question> homeQuestionList;
	QuestionListAdapter adapter;
	Account account;
	
	static boolean first = true;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		homeQuestionList = ApplicationState.getQuestionList();
		homeListView = (ListView) findViewById(R.id.questionListView);
		// TODO: retrieve homeQuestionList from server
		testHome();  // temporary test code
		account = ApplicationState.getAccount();

		//adapter = new QuestionListAdapter(this, R.layout.question_list_adapter, homeQuestionList);
		adapter = new QuestionListAdapter(this, R.layout.question_list_adapter2, homeQuestionList);
		
		
		homeListView.setAdapter(adapter);	
		adapter.notifyDataSetChanged();
		
		homeListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				openQuestion(position);	
			}
		});

		registerForContextMenu(homeListView);
	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		adapter.notifyDataSetChanged();
	
	}
	
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
	    Question selectedQuestion = adapter.getItem(info.position);
	    
	    if (account.getReadingList().contains(selectedQuestion)) {
	    	menu.add("Remove from reading list");
	    } else {
	    	menu.add("Add to reading list");
	    }
	}

	public boolean onContextItemSelected(MenuItem item) {

	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    int itemIndex = info.position;
	    Question selectedQuestion = adapter.getItem(itemIndex);
	    
		if (item.getTitle() == "Remove from reading list") {
		    account.removeReadLater(selectedQuestion);
		}
		else if (item.getTitle() == "Add to reading list") {
		    account.readLater(selectedQuestion);
            
	    } 
		else {
	        return false;
	    }
		
	    return true;

	}
		
	private void testHome() {
		
		if (first) {
			account = new Account("TestUser1");
			ApplicationState.setAccount(account);		
			Question question1 = new Question("What is the meaning of life?", "body 1 test", "user1");
			Question question2 = new Question("Why does Computing Science homework take so long to do?", "body 2 test", "user2");
			Question question3 = new Question("In what world does gravity push you away at a faster rate than it pulls you in?", "body 3 test", "user3");
			Question question4 = new Question("Why is this question so long? How it is going to look? What should the character count restriction be on question titles?", 
						"This is a really really really really really really really really really really long question. It's going to take up a whooooooooooooooooooooooooooooooooooooooooooooole lot of space. What should the character limit be on question bodies? Right now I'm testing with a maximum of 8 lines of text before the textview becomes scrollable. Is that bad? I feel like question descriptions should be allowed to be pretty long, but we can't take up the whole screen or else the answers will be basically unviewable. Well let's see how this looks.", "LongQuestionAsker");
			homeQuestionList.add(0, question1);
			homeQuestionList.add(0, question2);
			homeQuestionList.add(0, question3);
			homeQuestionList.add(0, question4);
			question4.addReply(new Reply("This is a test reply", "testuser1"));
			question4.addReply(new Reply("This is another test reply", "testuser2"));
			question4.addReply(new Reply("This is also a test reply", "testuser3"));
			account.addFavourite(question1);
			account.addFavourite(question2);
			account.readLater(question1);
			account.readLater(question2);
			question1.upVote();
			for (int i = 0; i < 1000; i++)
				question2.upVote();
			for (int i = 0; i < 999; i++) {
				question4.upVote();
			}
			Answer answer1 = new Answer("Sweet", "user1");
			Answer answer2 = new Answer("Question", "user2");
			Answer answer3 = new Answer("Bro do you even lift????????????????????????????????????????", "user3");
			answer3.addReply(new Reply("This is a test reply on an answer!", "userman"));
			answer3.addReply(new Reply("This is also a test reply on an answer!", "womanman"));
			answer3.addReply(new Reply("This is not a test reply on an answer! (Ha! I tricked you, it totally is! I bet you fell for it!", "catman"));
			question1.addAnswer(answer1);
			question1.addAnswer(answer2);
			question1.addAnswer(answer3);
			Answer answer0 = new Answer("This is a really long anser jkdslf;j;dsklfjkdls;ajfklds;ajfklds;jafkld;sjaklf;djskalf;jdskla;fjdskla;jfklds;ajfkld;sjafkl;dsjaklf;djsakfjds", "blaman");
			question4.addAnswer(answer0);
			for (int i = 0; i < 15; i++) {
				Answer answer = new Answer("This is answer " + i, "user " + i);
				for (int j = 0; j < 123; j++){
					answer.upVote();
				}	
				question4.addAnswer(answer);
			}
			first = false;
		}
	}
	

	private void openQuestion(int position) {
		Question question = (Question) adapter.getItem(position);
		account.addToHistory(question); // Add question clicked to history
		Bundle bundle = new Bundle();
		bundle.putSerializable("Question", question);
		Intent intent = new Intent(HomeActivity.this, QuestionPageActivity.class);
		intent.putExtras(bundle);
		
		ApplicationState.setPassableQuestion(question);
		
		startActivity(intent);	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds i		account = ApplicationState.getAccount();tems to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// switch case to choose an item from the menu
		//IntentSwitcher switcher = new IntentSwitcher(HomeActivity.this);
		
//-------------------------------------------
// Menu Items Switch Case
//-------------------------------------------
		
		switch (item.getItemId()) {
			case R.id.searchQuestionsList:
				//switcher.openSearch(this);
				openSearch();
				return true;
			case R.id.login_menu_item:
				//switchigner.openLogin();
				openLogin();
				return true;
			case R.id.my_questions_menu_item:
				//switcher.openMyQuestions(this);
				openMyQuestions();
				return true;
			case R.id.my_answers_menu_item:
				//switcher.openMyAnswers(this);
				openMyAnswers();
				return true;
			case R.id.my_favorites_menu_item:
				//switcher.openMyFavourites(this);
				openMyFavourites();
				return true;
			case R.id.my_reading_list_item:
				//switcher.openMyReadingList(this);
				openMyReadingList();
				return true;
			case R.id.my_history_list_item:
				//switcher.openMyHistory(this);
				openMyHistory();
				return true;
			case R.id.sort_list_item:
				//switcher.openSortList(this);
				openSortList();
				return true;
			default:
				return true;
		}	
	}
	
//-------------------------------------------
//-------------------------------------------	
	
	//start the AuthorQuestionActivity --> Author Question Page to ask a new Question
	public void askQuestion(View v)
	{
		Intent authorQuestion = new Intent(HomeActivity.this, AuthorQuestionActivity.class);
		startActivity(authorQuestion); 
		
//		Boolean loggedIn = checkLoggedIn();
//		if (loggedIn) {
//			Intent authorQuestion = new Intent(HomeActivity.this, AuthorQuestionActivity.class);
//			startActivity(authorQuestion); 
//		}
//		else {
//			Toast.makeText(this, "Please login to post a question", Toast.LENGTH_SHORT).show();
//			//Intent createAccount = new Intent(this, LoginActivity.class);
//			//startActivity(createAccount);
//		}
	}
	
	public Boolean checkLoggedIn() {
		if (account == null) {
			return false;
		}
		else {
			return true;
		}	
	}

}
