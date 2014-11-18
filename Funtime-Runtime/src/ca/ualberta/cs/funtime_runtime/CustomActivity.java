package ca.ualberta.cs.funtime_runtime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * An activity used as a starting point for all activities within the application.
 * 
 * @author Benjamin Holmwood
 * @author Deborsi Hazarika
 *
 */
public class CustomActivity extends Activity {
	
	/**
	 * Default Activity onResume()
	 */
	public void onResume(Bundle savedInstanceState) {
		super.onResume();
	}

	/**
	 * Default Activity onStart()
	 */
	public void onStart(Bundle savedInstanceState) {
		super.onStart();
	}
	
	/**
	 * Default Activity onRestart()
	 */
	public void onRestart(Bundle savedInstanceState) {
		super.onRestart();
		
	}
	
	/**
	 * Open the home activity
	 */
	public void openMyHome() {
		Intent intent = new Intent (this, HomeActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Open the search activity
	 */
	public void openSearch() {
		Intent intent = new Intent(this, SearchActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Open the login activity
	 */
	public void openLogin() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Open the MyQuestions activity
	 */
	public void openMyQuestions() {
		Intent intent = new Intent(this, MyQuestionsActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Open the MyAnswers activity
	 */
	public void openMyAnswers() {
		Intent intent = new Intent(this, MyAnswersActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Open the MyFavourites activity
	 */
	public void openMyFavourites() {
		Intent intent = new Intent(this, MyFavouritesActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Open the MyReadingList activity
	 */
	public void openMyReadingList() {
		Intent intent = new Intent(this, ReadingListActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Open the MyHistory activity
	 */
	public void openMyHistory() {
		Intent intent = new Intent(this, MyHistoryActivity.class);
		startActivity(intent);
	}

	public void openAuthorAnswerPage() {
		Intent intent = new Intent(this, AuthorAnswerActivity.class);
		startActivity(intent);
	}
	
	public void refreshPage() {
		finish();
		startActivity(getIntent());
	}
	
	/**
	 * Open the Sort activity
	 */
	public void openSortList() {

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
				
			case R.id.home_menu_item:
				openMyHome();
				return true;
				
			case R.id.refresh:
				//refreshPage();
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
				openSortList();
				return true;
				
			default :
				return true;
		}
	}

}
