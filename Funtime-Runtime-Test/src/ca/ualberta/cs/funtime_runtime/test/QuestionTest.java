package ca.ualberta.cs.funtime_runtime.test;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.HomeActivity;

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
		assertEquals(questionTitle, "Test Answser Title");
		assertEquals(questionBody, "Test question body");
		assertEquals(authorName, "TestAuthorUsername");
	}
	
	public void testEditQuestion() {
		// Consider implementing
	}
	
	
	public void testVoting() {
		Question question = new Question("Test Question Title", "Test question body", "TestAuthorUsername");
		
		question.upVote();
		int rating = question.getRating();
		assertEquals(rating, 1);
		
		question.downVote();
		int rating = question.getRating();
		assertEquals(rating, 0);
		
		for (int i = 0; i < 10; i++) {
				question.upVote();
		}
		int rating = question.getRating();
		assertEquals(rating, 10);
		
	}
	
	public void testAnswers() {
		Question question = new Question("Test Question Title", "Test question body", "TestAuthorUsername");
		Answer answer = new Answer("Test Answer Title", "Test answer body", "TestAuthorUsername");
		assertNotNull(answerList);
		question.addAnswer(answer);
		int answer_count = question.getAnswerCount();
		assertEquals(answer_count, 1);
		
		answerList.remove(0);
		assertEquals(answer_count, 0);
		
		Answer answer0 = new Answer("Test Answer 0 Title", "Test answer 0 body", "TestAuthorUsername0");
		Answer answer1 = new Answer("Test Answer 1 Title", "Test answer 1 body", "TestAuthorUsername1");
		Answer answer2 = new Answer("Test Answer 2 Title", "Test answer 2 body", "TestAuthorUsername2");
		
		question.addAnswer(answer0);
		question.addAnswer(answer1);
		question.addAnswer(answer2);
		
		assertEquals(question.getAnswerCount(), 3);
		
		assertEquals(question.getAnswer(1), answer1);
		
		question.removeAnswer(1);
		
		assertEquals(question.getAnswerCount, 2);
		assertEquals(question.getAnswer(0), answer0);
		assertEquals(question.getAnswer(1), answer2);
		
		question.removeAnswer(0);
		question.removeAnswer(1);
		
		assertEquals(question.getAnswerCount(), 0);
	
	}
	
	
	public void testAddPhoto() {
		Question question = new Question("Test Question Title", "Test question body", "TestAuthorUsername");
		
		// create bitmap testPhoto
		//bitmap testPhoto = //implement!
		question.addPhoto(testPhoto);
		bitmap retreivedPhoto = question.getPhoto();
		assertEquals(testPhoto, retreivedPhoto);
	}
	
	public void testDeleteQuestion() {
		// Consider implementing
	}
	
}
