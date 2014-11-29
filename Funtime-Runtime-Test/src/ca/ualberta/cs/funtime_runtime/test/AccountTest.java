package ca.ualberta.cs.funtime_runtime.test;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.CreateAccountActivity;
import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.classes.Answer;
import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;
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
	}
	
	public void testAuthorAnswer() {
		Account account = new Account("TestAccountUsername");
		Question question = new Question("test question 1", "test body 1", "iAmTest");
		Answer answer = new Answer(10, "Test answer body", "TestAccountUsername");
		Integer id = 7841;
		question.setId(id);
		answer.setId(id);
		question.addAnswer(answer);
		account.answerQuestion(question, answer, getActivity());
		assertEquals(account.answeredCount(), 1);
		assertEquals(id, account.getAnsweredQuestionId(0));
		
	}
	
	public void testFavorites() {
		Account account = new Account("TestAccountUsername");
		ArrayList<Integer> testList;
		testList = account.getFavouritesList();
		assertNotNull(testList);
		
		Question question = new Question("Test Question Title", "Test question body", "TestAccountUsername");
		question.setId(55);
		
		account.addFavourite(question, getActivity());
		
		assertTrue(testList.contains(question.getId()));
		
		account.removeFavourite(question, getActivity());
		
		assertFalse(testList.contains(question.getId()));
		
	}
	
	public void testReadingList() {
		Account account = new Account("TestAccountUsername");
		ArrayList<Integer> testList;
		testList = account.getReadingList();
		assertNotNull(testList);
		
		Question question = new Question("Test Question Title", "Test question body", "TestAccountUsername");
		question.setId(0);
		
		account.readLater(question, getActivity());
		
		assertTrue(testList.contains(question.getId()));
		
		account.removeFavourite(question, getActivity());
		
		assertFalse(testList.contains(question.getId()));
	}
	
	public void testHistory() {
		Account account = new Account("TestAccountUsername");
		ArrayList<Integer> testList;
		testList = account.getHistoryList();
		assertNotNull(testList);
		
		Question question = new Question("Test Question Title", "Test question body", "TestAccountUsername");
		
		account.addToHistory(question, getActivity());
		
		assertTrue(testList.contains(question.getId()));
	}
	
	public void testQuestionVoting() {
		Account account = new Account("TestAccountUsername");
		ArrayList<Integer> testList;
		testList = account.getUpvotedQuestions();
		assertNotNull(testList);
		
		Question question = new Question("Test Question Title", "Test question body", "TestAccountUsername");
		question.setId(0);
		
		account.upvoteQuestion(question, getActivity());
		
		assertTrue(testList.contains(question));
		
		account.downvoteQuestion(question, getActivity());
		
		assertFalse(testList.contains(question));
		
	}
	
	public void testAnswerVoting() {
		Account account = new Account("TestAccountUsername");
		ArrayList<Integer> testList;
		testList = account.getUpvotedAnswers();
		assertNotNull(testList);
		Question question = new Question("Test Question Title", "Test question Body", "TestAccountUsername");
		Answer answer = new Answer(question.getId(), "Test answer body", "TestAccountUsername");
		
		account.upvoteAnswer(answer, getActivity());
		
		assertTrue(testList.contains(answer.getId()));
		
		account.downvoteAnswer(answer, getActivity());
		
		assertFalse(testList.contains(answer.getId()));
		
	}
	
}
