package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import ca.ualberta.cs.funtime_runtime.adapter.QuestionListAdapter;
import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.classes.QuestionSorter;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * This class handles displaying the populated list of every question that is
 * clicked on by the user in the main list of questions from the server. If a
 * question is clicked in this history list, it opens the question page for the
 * respective question.
 * 
 * @author cherfan
 * 
 */
public class MyHistoryActivity extends CustomActivity {

	ArrayList<Question> myHistoryList;
	ArrayList<Integer> myHistoryIdList;
	ArrayList<Question> appStateList;
	QuestionSorter sorter;
	
	Account account;
	ListView myHistoryListView;
	QuestionListAdapter adapter;

	/**
	 * Initializes the list, view and adapter for this activity. Gets the
	 * current Account information and listens for a click on an item.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);

		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);

		myHistoryListView = (ListView) findViewById(R.id.myHistory_ListView);
		account = ApplicationState.getAccount();

		myHistoryIdList = account.getHistoryList();
		myHistoryList = new ArrayList<Question>();
		appStateList = ApplicationState.getQuestionList(this);
		for (Integer id: myHistoryIdList) {
			for (Question q: appStateList) {
				Integer qId = q.getId();
				if (qId.equals(id)) {
					myHistoryList.add(q);
				}
			}
		}
		
		adapter = new QuestionListAdapter(this, R.layout.question_list_adapter,
				myHistoryList);
		myHistoryListView.setAdapter(adapter);
		sorter = new QuestionSorter(myHistoryList);
		sorter.sortByDate();
		adapter.notifyDataSetChanged();

		myHistoryListView.setOnItemClickListener(new OnItemClickListener() {

			/**
			 * When an item is clicked this method passes the relevant
			 * information to the QuestionPageActivity via an intent.
			 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Question question = (Question) adapter.getItem(position);
				Bundle bundle = new Bundle();
				bundle.putSerializable("Question", question);
				Intent intent = new Intent(MyHistoryActivity.this,
						QuestionPageActivity.class);
				intent.putExtras(bundle);

				ApplicationState.setPassableQuestion(question);

				startActivity(intent);

			}
		});

		registerForContextMenu(myHistoryListView);
		
		// TODO save history locally

	}

	/**
	 * Refreshes the adapter when the activity restarts
	 * in case anything changed. 
	 */
	@Override
	public void onRestart() {
		super.onRestart();
		adapter.notifyDataSetChanged();
		
		// TODO save history locally

	}

	/**
	 * On a long click if the question item is in the reading list it will give
	 * the option to remove it or if it is not in the reading list it will give
	 * the option to add the question to the reading list. This is all displayed
	 * via a context menu.
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
	 * On a long click if the question item is in the reading list it will give
	 * the option to remove it or if it is not in the reading list it will give
	 * the option to add the question to the reading list. This is all displayed
	 * via a context menu.
	 */
	public boolean onContextItemSelected(MenuItem item) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int itemIndex = info.position;
		Question selectedQuestion = adapter.getItem(itemIndex);

		if (item.getTitle() == "Remove from reading list") {
			account.removeReadLater(selectedQuestion, this);
		} else if (item.getTitle() == "Add to reading list") {
			account.readLater(selectedQuestion, this);

		} else {
			return false;
		}

		return true;

	}

	/**
	 * Populates the menu with options to switch activities.
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.history, menu);
		return true;
	}

}
