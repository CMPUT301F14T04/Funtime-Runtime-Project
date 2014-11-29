package ca.ualberta.cs.funtime_runtime;

import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

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
		if (ApplicationState.isLoggedIn()) {
			Intent intent = new Intent(this, MyQuestionsActivity.class);
			startActivity(intent);
		} else {
			String msg = ApplicationState.notLoggedIn();
			Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * Open the MyAnswers activity
	 */
	public void openMyAnswers() {
		if (ApplicationState.isLoggedIn()) {
			Intent intent = new Intent(this, MyAnswersActivity.class);
			startActivity(intent);
		} else {
			String msg = ApplicationState.notLoggedIn();
			Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * Open the MyFavourites activity
	 */
	public void openMyFavourites() {
		if (ApplicationState.isLoggedIn()) {
			Intent intent = new Intent(this, MyFavouritesActivity.class);
			startActivity(intent);
		} else {
			String msg = ApplicationState.notLoggedIn();
			Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * Open the MyReadingList activity
	 */
	public void openMyReadingList() {
		if (ApplicationState.isLoggedIn()) {
			Intent intent = new Intent(this, ReadingListActivity.class);
			startActivity(intent);
		} else {
			String msg = ApplicationState.notLoggedIn();
			Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * Open the MyHistory activity
	 */
	public void openMyHistory() {
		if (ApplicationState.isLoggedIn()) {
			Intent intent = new Intent(this, MyHistoryActivity.class);
			startActivity(intent);
		} else {
			String msg = ApplicationState.notLoggedIn();
			Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
		}
	}
	
	public void refresh() {}
	public void sortDate() {}
	public void sortVotes() {}
	public void sortPhoto() {}
	public void sortLocation() {}
	

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
			
			case R.id.refresh:
				refresh();
				
			case R.id.sort_date_menu:
				sortDate();
				return true;
				
			case R.id.sort_votes_menu:
				sortVotes();
				return true;
				
			case R.id.sort_photo_menu:
				sortPhoto();
				return true;
				
			case R.id.sort_location_menu:
				sortLocation();
				return true;
				
			default :
				return true;
		}
	}

}
