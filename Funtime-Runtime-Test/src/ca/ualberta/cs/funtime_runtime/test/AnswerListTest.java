package ca.ualberta.cs.funtime_runtime.test;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.AnswerList;
import ca.ualberta.cs.funtime_runtime.AuthorAnswerActivity;

public class AnswerListTest extends ActivityInstrumentationTestCase2<AuthorAnswerActivity> {

	public AnswerListTest() {
		super(AuthorAnswerActivity.class);
	}
	
	public void testMakeList(){
		AnswerList answerList = new AnswerList();
		assertNotNull(answerList);
	}
	
	public void testAddAnswer(){
		AnswerList answerList = new AnswerList();
		answerList.add("test one");
		answerList.add("test two");
		assertNotNull(answerList);
		
	}
	
	public void testRemoveAnswer(){
		AnswerList answerList = new AnswerList();
		answerList.add("test one");
		assertNotNull(answerList);
		answerList.remove("test one");
		int size = answerList.size();
		if (size == 0)
			assertNotNull(answerList);
		
	}
};
