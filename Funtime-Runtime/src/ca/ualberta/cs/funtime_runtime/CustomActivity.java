package ca.ualberta.cs.funtime_runtime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * An activity used as a starting point for all activities within the application.
 * 
 * @author Benjamin Holmwood
 *
 */
public class CustomActivity extends Activity
{
	
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
	public void openMyHome(){
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
	public void openLogin(){
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

	/**
	 * Open the Sort activity
	 */
	public void openSortList() {
		Intent intent = new Intent(this, Sort.class);
		startActivity(intent);
	}

}
