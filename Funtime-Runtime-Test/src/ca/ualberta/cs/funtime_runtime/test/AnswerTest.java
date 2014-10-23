package ca.ualberta.cs.funtime_runtime.test;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.Answer;
import ca.ualberta.cs.funtime_runtime.QuestionPageActivity;

public class AnswerTest extends ActivityInstrumentationTestCase2<QuestionPageActivity> {

		public AnswerTest() {
			super(QuestionPageActivity.class);
		}

		public void testMakeAnswer(){
			Answer answer = new Answer("Test answer body", "TestAuthorUsername");
			assertNotNull(answer);
		}
		
		public void testAnswerContent() {
			Answer answer = new Answer("Test answer body", "TestAuthorUsername");
			//String answerTitle = answer.getTitle();
			String answerBody = answer.getBody();
			String authorName = answer.getUser();
			//assertEquals(answerTitle, "Test Answser Title");
			assertEquals(answerBody, "Test answer body");
			assertEquals(authorName, "TestAuthorUsername");
		}
		
		public void testEditAnswer() {
			// Consider implementing
		}
		
		
		public void testVoting() {
			Answer answer = new Answer("Test answer body", "TestAuthorUsername");
			
			answer.upVote();
			int rating = answer.getRating();
			assertEquals(rating, 1);
			
			answer.downVote();
			rating = answer.getRating();
			assertEquals(rating, 0);
			
			for (int i = 0; i < 10; i++) {
					answer.upVote();
			}
			rating = answer.getRating();
			assertEquals(rating, 10);
			
		}
		
		
		public void testAddPhoto() {
			//Answer answer = new Answer("Test answer body", "TestAuthorUsername");	
			// create bitmap testPhoto
			//Bitmap testPhoto; //implement!
			//answer.addPhoto(testPhoto);
			//Bitmap retreivedPhoto = answer.getPhoto();
			//assertEquals(testPhoto, retreivedPhoto);
		}
		
		public void testDeleteAnswer() {
			// Consider implementing
		}
		
}
