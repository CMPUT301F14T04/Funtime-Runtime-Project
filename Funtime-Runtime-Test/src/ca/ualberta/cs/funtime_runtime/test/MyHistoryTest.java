package ca.ualberta.cs.funtime_runtime.test;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.Account;
import ca.ualberta.cs.funtime_runtime.ApplicationState;
import ca.ualberta.cs.funtime_runtime.MyHistoryActivity;
import ca.ualberta.cs.funtime_runtime.Question;

public class MyHistoryTest extends
	ActivityInstrumentationTestCase2<MyHistoryActivity> {

    public MyHistoryTest() {
	super(MyHistoryActivity.class);
    }

    public void testAddQuestion() {
	 Account account = new Account("TestUser1");
	ApplicationState.setAccount(account);

	Question question = new Question("What is the meaning of life?",
		"body 1 test", "user1");
	account.addToHistory(question);
	assertNotNull(account.getHistoryList());
    }

}
