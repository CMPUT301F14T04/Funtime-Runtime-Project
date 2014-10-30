package ca.ualberta.cs.funtime_runtime;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;
//import android.content.Context;
//import android.content.Intent;
//import android.widget.Toast;


public class CustomActivity extends Activity
{
	/*
	private Context context;
	
	public IntentSwitcher(Context context){
		this.context = context;
	}
	*/
	
	public void openSearch() {
		Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, SearchActivity.class);
		startActivity(intent);
	}
	
	public void openLogin(){
		Toast.makeText(this, "LoginTEST", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}
	
	public void openMyQuestions() {
		Toast.makeText(this, "My Questions", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, MyQuestionsActivity.class);
		startActivity(intent);
	}
	
	public void openMyAnswers() {
		Toast.makeText(this, "My Answers", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, MyAnswersActivity.class);
		startActivity(intent);
	}
	
	public void openMyFavourites() {
		Toast.makeText(this, "My Favourites", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, MyFavouritesActivity.class);
		startActivity(intent);
	}
	
	public void openMyReadingList() {
		Toast.makeText(this, "My Reading List", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, ReadingListActivity.class);
		startActivity(intent);
	}
	
	public void openMyHistory() {
		Toast.makeText(this, "My History", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, MyHistoryActivity.class);
		startActivity(intent);
	}

	public void openSortList() {
		Toast.makeText(this, "Choose A Sorting Method", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, Sort.class);
		startActivity(intent);
	}
}
