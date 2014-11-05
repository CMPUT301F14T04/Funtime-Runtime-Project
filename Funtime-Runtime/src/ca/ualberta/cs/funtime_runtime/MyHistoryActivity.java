
package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MyHistoryActivity extends CustomActivity {

    ArrayList<Question> myHistoryList;
    Account account;
    ListView myHistoryListView;
    QuestionListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
        
        myHistoryListView = (ListView) findViewById(R.id.myHistory_ListView);
        account = ApplicationState.getAccount();

        myHistoryList = account.getHistoryList();

        ArrayList<Question> historyList = new ArrayList<Question>();
        historyList = myHistoryList;
        adapter = new QuestionListAdapter(this, R.layout.question_list_adapter2,
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
                
                ApplicationState.setPassableQuestion(question);
                
                startActivity(intent);

            }
        });
    }
    
	@Override
	public void onRestart() {
		super.onRestart();
		adapter.notifyDataSetChanged();
	
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
        	case android.R.id.home:
				openMyHome();
				return true;
        	case R.id.home_menu_item:
        		openMyHome();
        		return true;
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

    // -------------------------------------------
    // -------------------------------------------
}
