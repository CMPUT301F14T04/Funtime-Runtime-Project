package ca.ualberta.cs.funtime_runtime.test;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.QuestionPageActivity;
import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.classes.Answer;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.classes.Reply;

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
			String answerBody = answer.getBody();
			String authorName = answer.getUser();
			assertEquals(answerBody, "Test answer body");
			assertEquals(authorName, "TestAuthorUsername");
		}
		
		/*
		public void testEditAnswer() {
			// Consider implementing
		}AnswerPageActivity
		*/
		
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
			
			Account account = new Account("TestUser1");
			account.upvoteAnswer(answer, getActivity());
			assertEquals(answer.getRating(), 11);
			ArrayList<Integer> upvotedAnswers = account.getUpvotedAnswers();
			assertTrue(upvotedAnswers.contains(answer));
			
			
		}
		
		public void testReply() {
			Reply reply = new Reply("Test reply body", "TestReplier1");
			
			Question question = new Question("Is this a test question?", "I think this is a question, but I can't tell. HELP!", "TestAsker");
			Answer answer = new Answer("This is a test answer.", "TestAnswerer");
			
			question.addAnswer(answer);
			
			answer.addReply(reply);
			
			Reply testReply = answer.getReply(0);
			
			assertEquals(reply, testReply);
			
			ArrayList<Reply> replyList = answer.getReplyList();
		
			assertEquals(answer.getReplyCount(), 1);
			assertTrue(replyList.contains(reply));
		
		}
		
		
		
		
		/*
		public void testAddPhoto() {
			Answer answer = new Answer("Test answer body", "TestAuthorUsername");	
			create bitmap testPhoto
			Bitmap testPhoto; //implement!
			answer.addPhoto(testPhoto);
			Bitmap retreivedPhoto = answer.getPhoto();
			assertEquals(testPhoto, retreivedPhoto);
		}
		*/
		
		/*
		public void testDeleteAnswer() {
			// Consider implementing
		}
		*/
		
}
