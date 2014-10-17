package ca.ualberta.cs.funtime_runtime.test;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.Answer;
import ca.ualberta.cs.funtime_runtime.AnswerList;
import ca.ualberta.cs.funtime_runtime.QuestionPageActivity;

public class AnswerListTest extends ActivityInstrumentationTestCase2<QuestionPageActivity> {

	public AnswerListTest() {
		super(QuestionPageActivity.class);
	}
	
	public void testMakeList(){
		AnswerList answerList = new AnswerList();
		assertNotNull(answerList);
	}
	
	public void testAddAnswer(){
		AnswerList answerList = new AnswerList();
		Answer answer = new Answer("Test answer text", "TestAuthorUsername");
		assertNotNull(answerList);
		answerList.add(answer);
		assertEquals(answerList.size(), 1);
		assertEquals(answer, answerList.get(0));
		
		Answer answer0 = new Answer("Test answer 0 text", "TestAuthorUsername0");
		Answer answer1 = new Answer("Test answer 1 text", "TestAuthorUsername1");
		Answer answer2 = new Answer("Test answer 2 text", "TestAuthorUsername2");
		answerList.add(answer0);
		answerList.add(answer1);
		answerList.add(answer2);
		assertEquals(answerList.size(), 4);
	
	}
	
	public void testRemoveAnswer(){
		AnswerList answerList = new AnswerList();
		Answer answer = new Answer("Test answer text", "TestAuthorUsername");
		assertNotNull(answerList);
		answerList.add(answer);
		assertEquals(answerList.size(), 1);
		answerList.remove(0);
		assertEquals(answerList.size(), 0);		
		
		Answer answer0 = new Answer("Test answer 0 text", "TestAuthorUsername0");
		Answer answer1 = new Answer("Test answer 1 text", "TestAuthorUsername1");
		Answer answer2 = new Answer("Test answer 2 text", "TestAuthorUsername2");
		assertNotNull(answerList);
		answerList.add(answer0);
		answerList.add(answer1);
		answerList.add(answer2);
		assertEquals(answerList.size(), 3);
		answerList.remove(1);
		assertEquals(answerList.size(), 2);
		assertEquals(answerList.get(0), answer0);
		assertEquals(answerList.get(1), answer2);
		
	}
}
