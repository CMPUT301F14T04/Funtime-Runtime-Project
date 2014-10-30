
package ca.ualberta.cs.funtime_runtime.test;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.Account;
import ca.ualberta.cs.funtime_runtime.ApplicationState;
import ca.ualberta.cs.funtime_runtime.MyHistoryActivity;
import ca.ualberta.cs.funtime_runtime.Question;

public class MyHistoryTest extends
        ActivityInstrumentationTestCase2<MyHistoryActivity>
{

    Instrumentation instrumentation;
    Activity activity;
    Account account;

    public MyHistoryTest()
    {
        super(MyHistoryActivity.class);
    }

    protected void setUp() throws Exception
    {
        super.setUp();
        instrumentation = getInstrumentation();
        activity = getActivity();

    }

    public void testAddQuestion()
    {
        account = new Account("TestUser1");
        ApplicationState.setAccount(account);

        Question question1 = new Question("What is the meaning of life?",
                "body 1 test", "user1");
        account.addToHistory(question1);
        //assertNotNull(historyList);
    }

}
