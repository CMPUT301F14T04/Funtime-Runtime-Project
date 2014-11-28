package ca.ualberta.cs.funtime_runtime.test;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.CreateAccountActivity;
import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.classes.Answer;
import ca.ualberta.cs.funtime_runtime.classes.Question;

public class AccountTest extends ActivityInstrumentationTestCase2<CreateAccountActivity> {
	
	public AccountTest(){
		super(CreateAccountActivity.class);
	}
	
	public void testCreateAccount(){
		Account account = new Account("Test Account Username");
		assertNotNull(account);
	}
	
	public void testGetAccountName(){
		String name1 = "TestName";
		String name2;
		Account account = new Account(name1);
		account.setName(name1);
		name2 = account.getName();
		assertEquals(name2, name1);
		
	}
	
	public void testAuthorQuestion() {
		Account account = new Account("TestAccountUsername");
		Question question = new Question("Test Question Title", "Test question body", "TestAccountUsername");
		Integer id = 7841;
		question.setId(id);
		account.authorQuestion(question, getActivity());
		assertEquals(account.questionsCount(), 1);
		assertEquals(id, account.getQuestionId(0));
		
		Question question0 = new Question("Test Question 0 Title", "Test question 0 body", "TestAccountUsername0");
		Question question1 = new Question("Test Question 1 Title", "Test question 1 body", "TestAccountUsername1");
		Question question2 = new Question("Test Question 2 Title", "Test question 2 body", "TestAccountUsername2");
		account.authorQuestion(question0, getActivity());
		account.authorQuestion(question1, getActivity());
		account.authorQuestion(question2, getActivity());	
		assertEquals(account.questionsCount(), 4);
	}
	
	public void testAuthorAnswer() {
		Account account = new Account("TestAccountUsername");
		Question question = new Question("test question 1", "test body 1", "iAmTest");
		Answer answer = new Answer("Test answer body", "TestAccountUsername");
		Integer id = 7841;
		answer.setId(id);
		question.addAnswer(answer);
		account.answerQuestion(question);
		assertEquals(account.answeredCount(), 1);
		assertEquals(id, account.getAnsweredQuestionId(0));
		
		Answer answer0 = new Answer("Test answer 0 body", "TestAccountUsername0");
		Answer answer1 = new Answer("Test answer 1 body", "TestAccountUsername1");
		Answer answer2 = new Answer("Test answer 2 body", "TestAccountUsername2");
		question.addAnswer(answer0);
		account.answerQuestion(question);
		question.addAnswer(answer1);
		account.answerQuestion(question);
		question.addAnswer(answer2);
		account.answerQuestion(question);
		assertEquals(account.answeredCount(), 4);
	}
	
	public void testFavorites() {
		Account account = new Account("TestAccountUsername");
		ArrayList<Integer> testList;
		testList = account.getFavouritesList();
		assertNotNull(testList);
		
		Question question = new Question("Test Question Title", "Test question body", "TestAccountUsername");
		
		account.addFavourite(question);
		
		assertTrue(testList.contains(question.getId()));
		
		account.removeFavourite(question);
		
		assertFalse(testList.contains(question.getId()));
		
	}
	
	public void testReadingList() {
		Account account = new Account("TestAccountUsername");
		ArrayList<Integer> testList;
		testList = account.getReadingList();
		assertNotNull(testList);
		
		Question question = new Question("Test Question Title", "Test question body", "TestAccountUsername");
		
		account.readLater(question);
		
		assertTrue(testList.contains(question.getId()));
		
		account.removeFavourite(question);
		
		assertFalse(testList.contains(question.getId()));
	}
	
	public void testHistory() {
		Account account = new Account("TestAccountUsername");
		ArrayList<Integer> testList;
		testList = account.getHistoryList();
		assertNotNull(testList);
		
		Question question = new Question("Test Question Title", "Test question body", "TestAccountUsername");
		
		account.addToHistory(question);
		
		assertTrue(testList.contains(question.getId()));
	}
	
	public void testQuestionVoting() {
		Account account = new Account("TestAccountUsername");
		ArrayList<Integer> testList;
		testList = account.getUpvotedQuestions();
		assertNotNull(testList);
		
		Question question = new Question("Test Question Title", "Test question body", "TestAccountUsername");
		
		account.upvoteQuestion(question);
		
		assertTrue(testList.contains(question));
		
		account.downvoteQuestion(question);
		
		assertFalse(testList.contains(question));
		
	}
	
	public void testAnswerVoting() {
		Account account = new Account("TestAccountUsername");
		ArrayList<Integer> testList;
		testList = account.getUpvotedAnswers();
		assertNotNull(testList);
		
		Answer answer = new Answer("Test answer body", "TestAccountUsername");
		
		account.upvoteAnswer(answer);
		
		assertTrue(testList.contains(answer.getId()));
		
		account.downvoteAnswer(answer);
		
		assertFalse(testList.contains(answer.getId()));
		
	}
	
}
