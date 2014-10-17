package ca.ualberta.cs.funtime_runtime.test;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.HomeActivity;
import ca.ualberta.cs.funtime_runtime.Question;
import ca.ualberta.cs.funtime_runtime.QuestionList;

public class QuestionListTest extends ActivityInstrumentationTestCase2<HomeActivity> {

	public QuestionListTest() {
		super(HomeActivity.class);
	}
	
	public void testMakeList(){
		QuestionList questionList = new QuestionList();
		assertNotNull(questionList);
	}
	
	public void testAddQuestion(){
		QuestionList questionList = new QuestionList();
		Question question = new Question("Test Question Title", "Test question body", "TestAuthorUsername");
		assertNotNull(questionList);
		questionList.add(question);
		assertEquals(questionList.size(), 1);
		assertEquals(question, questionList.get(0));
		
		Question question0 = new Question("Test Question 0 Title", "Test question 0 body", "TestAuthorUsername0");
		Question question1 = new Question("Test Question 1 Title", "Test question 1 body", "TestAuthorUsername1");
		Question question2 = new Question("Test Question 2 Title", "Test question 2 body", "TestAuthorUsername2");
		questionList.add(question0);
		questionList.add(question1);
		questionList.add(question2);
		assertEquals(questionList.size(), 4);
	
	}
	
	public void testRemoveQuestion(){
		QuestionList questionList = new QuestionList();
		Question question = new Question("Test Question Title", "Test question body", "TestAuthorUsername");
		assertNotNull(questionList);
		questionList.add(question);
		assertEquals(questionList.size(), 1);
		questionList.remove(0);
		assertEquals(questionList.size(), 0);		
		
		Question question0 = new Question("Test Question 0 Title", "Test question 0 body", "TestAuthorUsername0");
		Question question1 = new Question("Test Question 1 Title", "Test question 1 body", "TestAuthorUsername1");
		Question question2 = new Question("Test Question 2 Title", "Test question 2 body", "TestAuthorUsername2");
		assertNotNull(questionList);
		questionList.add(question0);
		questionList.add(question1);
		questionList.add(question2);
		assertEquals(questionList.size(), 3);
		questionList.remove(1);
		assertEquals(questionList.size(), 2);
		assertEquals(questionList.get(0), question0);
		assertEquals(questionList.get(1), question2);
		
	}
}
