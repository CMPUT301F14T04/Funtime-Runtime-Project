package ca.ualberta.cs.funtime_runtime.test;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.HomeActivity;
import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.classes.Answer;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.classes.Reply;

public class QuestionTest extends ActivityInstrumentationTestCase2<HomeActivity> {

	public QuestionTest() {
		super(HomeActivity.class);
	}
	
	public void testMakeQuestion(){
		Question question = new Question("Test Question Title", "Test question body", "TestAuthorUsername");
		assertNotNull(question);
	}
	
	public void testQuestionContent() {
		Question question = new Question("Test Question Title", "Test question body", "TestAuthorUsername");
		String questionTitle = question.getTitle();
		String questionBody = question.getBody();
		String authorName = question.getUser();
		assertEquals(questionTitle, "Test Question Title");
		assertEquals(questionBody, "Test question body");
		assertEquals(authorName, "TestAuthorUsername");
	}
	
	public void testVoting() {
		Question question = new Question("Test Question Title", "Test question body", "TestAuthorUsername");
		
		question.upVote();
		int rating = question.getRating();
		assertEquals(rating, 1);
		
		question.downVote();
		rating = question.getRating();
		assertEquals(rating, 0);
		
		for (int i = 0; i < 10; i++) {
			question.upVote();
		}
		rating = question.getRating();
		assertEquals(rating, 10);
		
		Account account = new Account("TestUser1");
		account.upvoteQuestion(question, getActivity());
		assertEquals(question.getRating(), 11);
		ArrayList<Integer> upvotedQuestions = account.getUpvotedQuestions();
		assertTrue(upvotedQuestions.contains(question));
	}
	
	public void testAnswers() {
		Question question = new Question("Test Question Title", "Test question body", "TestAuthorUsername");
		Answer answer0 = new Answer(1, "Test answer body", "TestAuthorUsername");
		assertNotNull(answer0);
		question.addAnswer(answer0);
		Answer answer1 = new Answer(2, "Test answer 0 body", "TestAuthorUsername0");
		Answer answer2 = new Answer(3, "Test answer 1 body", "TestAuthorUsername1");
		Answer answer3 = new Answer(4, "Test answer 2 body", "TestAuthorUsername2");
		
		question.addAnswer(answer1);
		question.addAnswer(answer2);
		question.addAnswer(answer3);
		
		int answer_count = question.getAnswerCount();
				
		assertEquals(answer_count, 4);
		
		assertEquals(question.getAnswer(0), answer3);
		assertEquals(question.getAnswer(1), answer2);
		assertEquals(question.getAnswer(2), answer1);
		assertEquals(question.getAnswer(3), answer0);
	}
	
	public void testReply() {
		Reply reply = new Reply("Test reply body", "TestReplier1");
		
		Question question = new Question("Is this a test question?", "I think this is a question, but I can't tell. HELP!", "TestAsker");
		
		question.addReply(reply);
		
		Reply testReply = question.getReply(0);
		
		assertEquals(reply, testReply);
		
		ArrayList<Reply> replyList = question.getReplyList();
	
		assertEquals(question.getReplyCount(), 1);
		assertTrue(replyList.contains(reply));
	
	}
	
	/*
	public void testAddPhoto() {
		Question question = new Question("Test Question Title", "Test question body", "TestAuthorUsername");
		
		//create bitmap testPhoto
		bitmap testPhoto = //implement!
		question.addPhoto(testPhoto);
		bitmap retreivedPhoto = question.getPhoto();
		assertEquals(testPhoto, retreivedPhoto);
	}
	*/
	
	/*
	public void testDeleteQuestion() {
		// Consider implementing
	}
	*/
}
