package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import ca.ualberta.cs.funtime_runtime.adapter.QuestionListAdapter;
import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.classes.Answer;
import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.classes.QuestionSorter;
import ca.ualberta.cs.funtime_runtime.classes.Reply;
import ca.ualberta.cs.funtime_runtime.elastic.ESQuestionManager;

/**
 * 
 * This class handles the Activity of the main page which acts as a hub for
 * displaying questions taken from the server and displaying the button to ask a
 * question.
 * 
 * @author Steven Cherfan (authored the comments)
 */

public class HomeActivity extends CustomActivity {

	ListView homeListView;
	ArrayList<Question> homeQuestionList;
	QuestionListAdapter adapter;
	Account account;
	ESQuestionManager questionManager;
	QuestionSorter sorter;

	static boolean first = true;
	boolean loggedIn;

	/**
	 * Initializes the listview, ArrayList that holds the questions, the adapter
	 * and an instance of an account. Also has a listener that opens a question
	 * when a question is clicked.
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		loggedIn = ApplicationState.isLoggedIn();
		if (loggedIn) {
			account = ApplicationState.getAccount();
		} 
		//account = new Account("TestUser1");
		//ApplicationState.setAccount(account);
		questionManager = new ESQuestionManager();
		homeQuestionList = ApplicationState.getQuestionList();
		homeListView = (ListView) findViewById(R.id.questionListView);

		loadServerQuestions();
		sorter = new QuestionSorter(homeQuestionList);
		//testHome(); // temporary test code

		account = ApplicationState.getAccount();
		adapter = new QuestionListAdapter(this, R.layout.question_list_adapter,
				homeQuestionList);

		homeListView.setAdapter(adapter);
		sorter.sortByDate();
		adapter.notifyDataSetChanged();

		homeListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				openQuestion(position);
			}
		});

		registerForContextMenu(homeListView);
	}

	private void loadServerQuestions() {
		Thread loadThread = new SearchThread("*");
		//Thread loadThread = new LoadHomeThread("*", homeQuestionList, adapter);
		loadThread.start();	
		
		try {
			loadThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	

	/**
	 * Refreshes the adapter when the activity restarts
	 * in case anything changed.
	 */
	@Override
	public void onRestart() {
		super.onRestart();
		loadServerQuestions();
		adapter.notifyDataSetChanged();
	}

	/**
	 * On a long click if the question item is in the reading list
	 * it will give the option to remove it or if it is not in the
	 * reading list it will give the option to add the question to the
	 * reading list. This is all displayed via a context menu.
	 * 
	 * Adapted from
	 * http://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long
	 * -clicks-in-an-android-listview/ 2014-09-21
	 */
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		Question selectedQuestion = adapter.getItem(info.position);

		if (account.getReadingList().contains(selectedQuestion)) {
			menu.add("Remove from reading list");
		} else {
			menu.add("Add to reading list");
		}
	}
	
	/**
	 * This function simply redirects to another activity when a certain menu 
	 * item is selected by the user. It operates a switch statement to transition 
	 * between different activities
	 * 
	 * @param item is a menuItem signifying location within the menu that users 
	 * wish to visit
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {	
			case android.R.id.home:
				openMyHome();
				return true;
				
			case R.id.refresh:
				refresh();
				return true;
				
			case R.id.home_menu_item:
				openMyHome();
				return true;
				
			case R.id.searchQuestionsList :
				openSearch();
				return true;
				
			case R.id.login_menu_item :
				openLogin();
				return true;
				
			case R.id.my_questions_menu_item :
				openMyQuestions();
				return true;
				
			case R.id.my_answers_menu_item :
				openMyAnswers();
				return true;
				
			case R.id.my_favorites_menu_item :
				openMyFavourites();
				return true;
				
			case R.id.my_reading_list_item :
				openMyReadingList();
				return true;
				
			case R.id.my_history_list_item :
				openMyHistory();
				return true;
				
			case R.id.sort_list_item :
				return true;
				
			case R.id.sort_date_menu:
				homeQuestionList = sorter.sortByDate();
				adapter.notifyDataSetChanged();
				return true;
				
			case R.id.sort_votes_menu:
				homeQuestionList = sorter.sortByVotes();
				adapter.notifyDataSetChanged();
				return true;
				
			case R.id.sort_photo_menu:
				homeQuestionList = sorter.sortByPhoto();
				adapter.notifyDataSetChanged();
				return true;
				
			default :
				return true;
		}
	}

	private void refresh() {
		String sortType = sorter.getSortType();
		loadServerQuestions();
		if (sortType.equals("Date")){
			sorter.sortByDate();
		} else if (sortType.equals("Votes")) {
			sorter.sortByVotes();
		} else if (sortType.equals("Photo")) {
			sorter.sortByPhoto();
		}
	}

	/**
	 * On a long click if the question item is in the reading list
	 * it will give the option to remove it or if it is not in the
	 * reading list it will give the option to add the question to the
	 * reading list. This is all displayed via a context menu.
	 */
	public boolean onContextItemSelected(MenuItem item) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int itemIndex = info.position;
		Question selectedQuestion = adapter.getItem(itemIndex);

		if (item.getTitle() == "Remove from reading list") {
			account.removeReadLater(selectedQuestion);
		} else if (item.getTitle() == "Add to reading list") {
			account.readLater(selectedQuestion);

		} else {
			return false;
		}

		return true;

	}

	/**
	 * Populates the list with questions and answers for the sake of demos and
	 * testing.
	 */
	private void testHome() {

		if (first) {
			account = new Account("TestUser1");
			ApplicationState.setAccount(account);
			Question question1 = new Question("What is the meaning of life?",
					"body 1 test", "user1");
			Question question2 = new Question(
					"Why does Computing Science homework take so long to do?",
					"body 2 test", "user2");
			Question question3 = new Question(
					"In what world does gravity push you away at a faster rate than it pulls you in?",
					"body 3 test", "user3");
			Question question4 = new Question(
					"Why is this question so long? How it is going to look? What should the character count restriction be on question titles?",
					"This is a really really really really really really really really really really long question. It's going to take up a whooooooooooooooooooooooooooooooooooooooooooooole lot of space. What should the character limit be on question bodies? Right now I'm testing with a maximum of 8 lines of text before the textview becomes scrollable. Is that bad? I feel like question descriptions should be allowed to be pretty long, but we can't take up the whole screen or else the answers will be basically unviewable. Well let's see how this looks.",
					"LongQuestionAsker");
			homeQuestionList.add(0, question1);
			homeQuestionList.add(0, question2);
			homeQuestionList.add(0, question3);
			homeQuestionList.add(0, question4);
			question4.addReply(new Reply("This is a test reply", "testuser1"));
			question4.addReply(new Reply("This is another test reply",
					"testuser2"));
			question4.addReply(new Reply("This is also a test reply",
					"testuser3"));
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
			Answer answer3 = new Answer(
					"Bro do you even lift????????????????????????????????????????",
					"user3");
			answer3.addReply(new Reply("This is a test reply on an answer!",
					"userman"));
			answer3.addReply(new Reply(
					"This is also a test reply on an answer!", "womanman"));
			answer3.addReply(new Reply(
					"This is not a test reply on an answer! (Ha! I tricked you, it totally is! I bet you fell for it!",
					"catman"));
			question1.addAnswer(answer1);
			question1.addAnswer(answer2);
			question1.addAnswer(answer3);
			Answer answer0 = new Answer(
					"This is a really long anser jkdslf;j;dsklfjkdls;ajfklds;ajfklds;jafkld;sjaklf;djskalf;jdskla;fjdskla;jfklds;ajfkld;sjafkl;dsjaklf;djsakfjds",
					"blaman");
			question4.addAnswer(answer0);
			for (int i = 0; i < 15; i++) {
				Answer answer = new Answer("This is answer " + i, "user " + i);
				for (int j = 0; j < 123; j++) {
					answer.upVote();
				}
				question4.addAnswer(answer);
			}

			question1.upVote();
			question3.upVote();
			account.upvoteQuestion(question1);
			account.upvoteQuestion(question3);

			// test MyQuestions code

			Question myQuestion1 = new Question(
					"Why are there animals in my van?",
					"Test: there are animals in my van etc", account.getName());
			homeQuestionList.add(myQuestion1);
			account.authorQuestion(myQuestion1);
			Answer answer4 = new Answer("Because the devil put it there",
					"user2");
			Answer answer5 = new Answer(
					"Maybe you left the door open? Sometimes animals can also get in through the window",
					"user5");
			myQuestion1.addAnswer(answer4);
			myQuestion1.addAnswer(answer5);

			Question myQuestion2 = new Question(
					"How many colors can the people see?",
					"Test: like when it comes to skin color what if someone is color blind??",
					account.getName());
			homeQuestionList.add(myQuestion2);
			account.authorQuestion(myQuestion2);
			Answer answer6 = new Answer("Wow you're not very smart are you?",
					"user3");
			myQuestion2.addAnswer(answer6);

			// test MyAnsweredQuestions code
			account.answerQuestion(question1);
			Answer myAnswer = new Answer("THIS IS AN UNHELPFUL ANSWER!",
					account.getName());
			question1.addAnswer(myAnswer);

			first = false;

		}
	}

	/**
	 * This method is called when a question is clicked on. It takes in a
	 * integer of the position of the clicked question in the list. Then it
	 * passes the information about the question via an intent to the
	 * QuestionPageActivity and changes the application state. The clicked
	 * question is also added to the history list.
	 * 
	 * @param position
	 */
	private void openQuestion(int position) {
		Question question = (Question) adapter.getItem(position);
		if (loggedIn)
			account.addToHistory(question); // Add question clicked to history
		Bundle bundle = new Bundle();
		bundle.putSerializable("Question", question);
		Intent intent = new Intent(this, QuestionPageActivity.class);
		intent.putExtras(bundle);

		ApplicationState.setPassableQuestion(question);

		startActivity(intent);
	}

	/**
	 * Populates the menu with activities to move to.
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}


	/**
	 * Takes in a view to give information to start the AuthorQuestionActivity
	 * to allow the user to write an answer to a question.
	 * 
	 * @param v
	 */
	public void askQuestion(View v) {
		Intent authorQuestion = new Intent(HomeActivity.this, AuthorQuestionActivity.class);
		startActivity(authorQuestion);

		// Boolean loggedIn = checkLoggedIn();
		// if (loggedIn) {
		// Intent authorQuestion = new Intent(HomeActivity.this,
		// AuthorQuestionActivity.class);
		// startActivity(authorQuestion);
		// }
		// else {
		// Toast.makeText(this, "Please login to post a question",
		// Toast.LENGTH_SHORT).show();
		// //Intent createAccount = new Intent(this, LoginActivity.class);
		// //startActivity(createAccount);
		// }
	}

	/**
	 * Checks to see if there is a logged in account.
	 * 
	 * @return true
	 * @return false
	 */
	public Boolean checkLoggedIn() {
		if (account == null) {
			return false;
		} else {
			return true;
		}
	}
	
//	private void loadHomeList() {
//		int id = 0;
//		while (homeQuestionList.size() != sortList.size()) {
//			for (Question q: sortList) {
//				if (q.getId() == id) {
//					homeQuestionList.add(0, q);
//					id++;
//				} 
//			}
//		}
//	}

	// Comparator code http://stackoverflow.com/questions/5927109/sort-objects-in-arraylist-by-date  - Nov 20/2014, by Domchi
	class SearchThread extends Thread {
		private String search;
		
		public SearchThread(String s){		
			search = s;
		}
		
		@Override
		public void run() {
			homeQuestionList.clear();
			//sortList.clear();
			//sortList.addAll(questionManager.searchQuestions(search, null));
			
			homeQuestionList.addAll(questionManager.searchQuestions(search, null));
//			Collections.sort(homeQuestionList, new Comparator<Question>() {
//				  public int compare(Question q1, Question q2) {
//				      return q1.getDate().compareTo(q2.getDate());
//				  }
//			});
//			Collections.reverse(homeQuestionList);
			
			runOnUiThread(updateHomeUI);	
		}
		
		private Runnable updateHomeUI = new Runnable() {
			public void run() {
				adapter.notifyDataSetChanged();
			}
		};
	}


}
