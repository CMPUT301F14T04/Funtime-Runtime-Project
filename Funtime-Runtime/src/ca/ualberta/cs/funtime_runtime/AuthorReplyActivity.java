package ca.ualberta.cs.funtime_runtime;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class AuthorReplyActivity extends IntentSwitcher
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_author_reply);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.author_reply, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// switch case to choose an item from the menu
			
	//-------------------------------------------
	// Menu Items Switch Case
	//-------------------------------------------
		 switch (item.getItemId()) {
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
		
		public void openMyQuestions() {
			Toast.makeText(this, "My Questions", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(AuthorReplyActivity.this, MyQuestionsActivity.class);
			startActivity(intent);
		}
		
		public void openMyAnswers() {
			Toast.makeText(this, "My Answers", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(AuthorReplyActivity.this, MyAnswersActivity.class);
			startActivity(intent);
		}
		
		public void openMyFavourites() {
			Toast.makeText(this, "My Favourites", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(AuthorReplyActivity.this, MyFavouritesActivity.class);
			startActivity(intent);
		}
		
		public void openMyReadingList() {
			Toast.makeText(this, "My Reading List", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(AuthorReplyActivity.this, ReadingListActivity.class);
			startActivity(intent);
		}
		
		public void openMyHistory() {
			Toast.makeText(this, "My History", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(AuthorReplyActivity.this, MyHistoryActivity.class);
			startActivity(intent);
		}

		public void openSortList() {
			Toast.makeText(this, "Choose A Sorting Method", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(AuthorReplyActivity.this, Sort.class);
			startActivity(intent);
		}
//-------------------------------------------
//-------------------------------------------

}
