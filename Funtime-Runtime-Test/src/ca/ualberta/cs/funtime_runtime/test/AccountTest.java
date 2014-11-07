package ca.ualberta.cs.funtime_runtime.test;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.Account;
import ca.ualberta.cs.funtime_runtime.Answer;
import ca.ualberta.cs.funtime_runtime.CreateAccountActivity;
import ca.ualberta.cs.funtime_runtime.Question;

public class AccountTest extends ActivityInstrumentationTestCase2<CreateAccountActivity> {
	
	public AccountTest(){
		super(CreateAccountActivity.class);
	}
	
	public void testCreateAccount(){
		Account account = new Account("Test Account Username");
		assertNotNull(account);
	}
	
	public void testGetAccountName(){
		String name1 = "Kappa";
		String name2;
		Account account = new Account(name1);
		account.setName(name1);
		name2 = account.getName();
		assertEquals(name2, name1);
		
	}
	
	public void testAuthorQuestion() {
		Account account = new Account("TestAccountUsername");
		Question question = new Question("Test Question Title", "Test question body", "TestAccountUsername");
		account.authorQuestion(question);
		assertEquals(account.questionsCount(), 1);
		assertEquals(question, account.getQuestion(0));
		
		Question question0 = new Question("Test Question 0 Title", "Test question 0 body", "TestAccountUsername0");
		Question question1 = new Question("Test Question 1 Title", "Test question 1 body", "TestAccountUsername1");
		Question question2 = new Question("Test Question 2 Title", "Test question 2 body", "TestAccountUsername2");
		account.authorQuestion(question0);
		account.authorQuestion(question1);
		account.authorQuestion(question2);	
		assertEquals(account.questionsCount(), 4);
	}
	
	public void testAuthorAnswer() {
		Account account = new Account("TestAccountUsername");
		Answer answer = new Answer("Test answer body", "TestAccountUsername");
		account.authorAnswer(answer);
		assertEquals(account.answersCount(), 1);
		assertEquals(answer, account.getAnswer(0));
		
		Answer answer0 = new Answer("Test answer 0 body", "TestAccountUsername0");
		Answer answer1 = new Answer("Test answer 1 body", "TestAccountUsername1");
		Answer answer2 = new Answer("Test answer 2 body", "TestAccountUsername2");
		account.authorAnswer(answer0);
		account.authorAnswer(answer1);
		account.authorAnswer(answer2);
		assertEquals(account.answersCount(), 4);
	}
	
	public void testFavorites() {
		Account account = new Account("TestAccountUsername");
		ArrayList<Question> testList;
		testList = account.getFavouritesList();
		assertNotNull(testList);
		
		Question question = new Question("Test Question Title", "Test question body", "TestAccountUsername");
		
		account.addFavourite(question);
		
		assertTrue(testList.contains(question));
		
		account.removeFavourite(question);
		
		assertFalse(testList.contains(question));
		
	}
	
	public void testReadingList() {
		Account account = new Account("TestAccountUsername");
		ArrayList<Question> testList;
		testList = account.getReadingList();
		assertNotNull(testList);
		
		Question question = new Question("Test Question Title", "Test question body", "TestAccountUsername");
		
		account.readLater(question);
		
		assertTrue(testList.contains(question));
		
		account.removeReadLater(question);
		
		assertFalse(testList.contains(question));
	}
	
	public void testHistory() {
		Account account = new Account("TestAccountUsername");
		ArrayList<Question> testList;
		testList = account.getHistoryList();
		assertNotNull(testList);
		
		Question question = new Question("Test Question Title", "Test question body", "TestAccountUsername");
		
		account.addToHistory(question);
		
		assertTrue(testList.contains(question));
	}
	
	public void testQuestionVoting() {
		Account account = new Account("TestAccountUsername");
		ArrayList<Question> testList;
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
		ArrayList<Answer> testList;
		testList = account.getUpvotedAnswers();
		assertNotNull(testList);
		
		Answer answer = new Answer("Test answer body", "TestAccountUsername");
		
		account.upvoteAnswer(answer);
		
		assertTrue(testList.contains(answer));
		
		account.downvoteAnswer(answer);
		
		assertFalse(testList.contains(answer));
		
	}
	
}
