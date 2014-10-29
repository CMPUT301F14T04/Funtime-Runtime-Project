
package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MyHistoryActivity extends IntentSwitcher
{

    ArrayList<Question> myHistoryList;
    Account account;
    ListView myHistoryListView;
    QuestionListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        myHistoryListView = (ListView) findViewById(R.id.myHistory_ListView);
        account = ApplicationState.getAccount();

        myHistoryList = account.getHistoryList();

        ArrayList<Question> historyList = new ArrayList<Question>();
        historyList = myHistoryList;
        adapter = new QuestionListAdapter(this, R.layout.question_list_adapter,
                historyList);
        myHistoryListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        myHistoryListView.setOnItemClickListener(new OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id)
            {
                Question question = (Question) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Question", question);
                Intent intent = new Intent(MyHistoryActivity.this,
                        QuestionPageActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
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

        // -------------------------------------------
        // Menu Items Switch Case
        // -------------------------------------------
        switch (item.getItemId())
        {
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
            case R.id.sort_list_item:
                openSortList();
                return true;
            default:
                return true;
        }
    }

    public void openLogin()
    {
        Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MyHistoryActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void openMyQuestions()
    {
        Toast.makeText(this, "My Questions", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MyHistoryActivity.this,
                MyQuestionsActivity.class);
        startActivity(intent);
    }

    public void openMyAnswers()
    {
        Toast.makeText(this, "My Answers", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MyHistoryActivity.this,
                MyAnswersActivity.class);
        startActivity(intent);
    }

    public void openMyFavourites()
    {
        Toast.makeText(this, "My Favourites", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MyHistoryActivity.this,
                MyFavouritesActivity.class);
        startActivity(intent);
    }

    public void openMyReadingList()
    {
        Toast.makeText(this, "My Reading List", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MyHistoryActivity.this,
                ReadingListActivity.class);
        startActivity(intent);
    }

    public void openSortList()
    {
        Toast.makeText(this, "Choose A Sorting Method", Toast.LENGTH_SHORT)
                .show();
        Intent intent = new Intent(MyHistoryActivity.this, Sort.class);
        startActivity(intent);
    }

    // -------------------------------------------
    // -------------------------------------------
}
