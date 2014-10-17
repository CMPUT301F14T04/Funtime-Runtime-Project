package ca.ualberta.cs.funtime_runtime.test;

import android.test.ActivityInstrumentationTestCase2;
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
		Answer answer = new Answer("Test Answer Title", "Test answer text", "TestAuthorUsername");
		assertNotNull(answerList);
		assertTrue(answerList.size(), 1);
		assertEquals(answer, answerList.get(0));
		
		Answer answer = new Answer("Test Answer 0 Title", "Test answer 0 text", "TestAuthorUsername0");
		Answer answer = new Answer("Test Answer 1 Title", "Test answer 1 text", "TestAuthorUsername1");
		Answer answer = new Answer("Test Answer 2 Title", "Test answer 2 text", "TestAuthorUsername2");
		assertTrue(answerList.size(), 3);
	
	}
	
	public void testRemoveAnswer(){
		AnswerList answerList = new AnswerList();
		Answer answer = new Answer("Test Answer Title", "Test answer text", "TestAuthorUsername");
		assertNotNull(answerList);
		assertTrue(answerList.size(), 1);
		answerList.remove(0);
		assertTrue(answerList.size(), 0);		
		
		Answer answer0 = new Answer("Test Answer 0 Title", "Test answer 0 text", "TestAuthorUsername0");
		Answer answer1 = new Answer("Test Answer 1 Title", "Test answer 1 text", "TestAuthorUsername1");
		Answer answer2 = new Answer("Test Answer 2 Title", "Test answer 2 text", "TestAuthorUsername2");
		assertNotNull(answerList);
		assertTrue(answerList.size(), 3);
		answerList.remove(1);
		assertTrue(answerList.size(), 2);
		assertEquals(answerList.get(0), answer0);
		assertEquals(answerList.get(1), answer2);
		
	}
};
