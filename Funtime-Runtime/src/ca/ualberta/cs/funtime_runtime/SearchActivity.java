package ca.ualberta.cs.funtime_runtime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SearchActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
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
	
	public void openLogin(){
		Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
		startActivity(intent);
	}
	
	public void openMyQuestions() {
		Toast.makeText(this, "My Questions", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(SearchActivity.this, MyQuestionsActivity.class);
		startActivity(intent);
	}
	
	public void openMyAnswers() {
		Toast.makeText(this, "My Answers", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(SearchActivity.this, MyAnswersActivity.class);
		startActivity(intent);
	}
	
	public void openMyFavourites() {
		Toast.makeText(this, "My Favourites", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(SearchActivity.this, MyFavouritesActivity.class);
		startActivity(intent);
	}
	
	public void openMyReadingList() {
		Toast.makeText(this, "My Reading List", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(SearchActivity.this, ReadingListActivity.class);
		startActivity(intent);
	}
	
	public void openMyHistory() {
		Toast.makeText(this, "My History", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(SearchActivity.this, MyHistoryActivity.class);
		startActivity(intent);
	}

	public void openSortList() {
		Toast.makeText(this, "Choose A Sorting Method", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(SearchActivity.this, Sort.class);
		startActivity(intent);
	}
//-------------------------------------------
//-------------------------------------------

}
