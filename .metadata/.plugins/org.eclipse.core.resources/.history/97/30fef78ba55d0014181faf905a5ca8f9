
package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;
import java.util.List;

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

public class MyHistoryActivity extends Activity
{

    ListView myHistoryListView;

    QuestionListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        myHistoryListView = (ListView) findViewById(R.id.myHistory_ListView);

        myHistoryTest();

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

    private void myHistoryTest()
    {
        List<Question> questionList = new ArrayList<Question>();
        adapter = new QuestionListAdapter(this, R.layout.question_list_adapter,
                questionList);
        // Example 1
        Question question1 = new Question("Why are there animals in my van?",
                "Test: there are animals in my van etc", "user1");
        questionList.add(question1);
        question1.upVote();
        Answer answer1 = new Answer("Because the devil put it there", "user2");
        Answer answer2 = new Answer(
                "Maybe you left the door open? Sometimes animals can also get in through the window",
                "user5");
        question1.addAnswer(answer1);
        question1.addAnswer(answer2);

        // Example 2
        Question question2 = new Question(
                "How many colors can the people see?",
                "Test: like when it comes to skin color what if someone is color blind??",
                "user1");
        questionList.add(question2);
        question2.downVote();
        question2.downVote();
        Answer answer3 = new Answer("Wow you're not very smart are you?",
                "user2");
        answer3.upVote();
        question2.addAnswer(answer3);

        myHistoryListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
        Intent intent = new Intent(MyHistoryActivity.this, SearchActivity.class);
        startActivity(intent);
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

}
