
package ca.ualberta.cs.funtime_runtime;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class HistoryActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // switch case to choose an item from the menu
        // IntentSwitcher switcher = new IntentSwitcher(HomeActivity.this);

        switch (item.getItemId())
        {
            case R.id.searchQuestionsList:
                // switcher.openSearch(this);
                openSearch();
                return true;
            case R.id.login_menu_item:
                // switchigner.openLogin();
                openLogin();
                return true;
            case R.id.my_questions_menu_item:
                // switcher.openMyQuestions(this);
                openMyQuestions();
                return true;
            case R.id.my_answers_menu_item:
                // switcher.openMyAnswers(this);
                openMyAnswers();
                return true;
            case R.id.my_favorites_menu_item:
                // switcher.openMyFavourites(this);
                openMyFavourites();
                return true;
            case R.id.my_reading_list_item:
                // switcher.openMyReadingList(this);
                openMyReadingList();
                return true;
            case R.id.sort_list_item:
                // switcher.openSortList(this);
                openSortList();
                return true;
            default:
                return true;
        }
    }

    public void openSearch()
    {
        Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(HistoryActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    public void openLogin()
    {
        Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(HistoryActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void openMyQuestions()
    {
        Toast.makeText(this, "My Questions", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(HistoryActivity.this,
                MyQuestionsActivity.class);
        startActivity(intent);
    }

    public void openMyAnswers()
    {
        Toast.makeText(this, "My Answers", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(HistoryActivity.this,
                MyAnswersActivity.class);
        startActivity(intent);
    }

    public void openMyFavourites()
    {
        Toast.makeText(this, "My Favourites", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(HistoryActivity.this,
                MyFavouritesActivity.class);
        startActivity(intent);
    }

    public void openMyReadingList()
    {
        Toast.makeText(this, "My Reading List", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(HistoryActivity.this,
                ReadingListActivity.class);
        startActivity(intent);
    }

    public void openSortList()
    {
        Toast.makeText(this, "Choose A Sorting Method", Toast.LENGTH_SHORT)
                .show();
        Intent intent = new Intent(HistoryActivity.this, Sort.class);
        startActivity(intent);
    }

}
