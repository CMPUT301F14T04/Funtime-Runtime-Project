package ca.ualberta.cs.funtime_runtime.test;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.QuestionPageActivity;
import ca.ualberta.cs.funtime_runtime.classes.Reply;

public class ReplyTest extends ActivityInstrumentationTestCase2<QuestionPageActivity> {

	public ReplyTest() {
		super(QuestionPageActivity.class);
	}

	public void testMakeReply() {
		Reply questionReply = new Reply(-1, 0, "Test reply body question", "TestAuthorUsername");
		assertNotNull(questionReply);
		
		Reply answerReply = new Reply (0, 0, "Test reply body answer", "TestAuthorUsername1");
		assertNotNull(answerReply);
	}
	
	public void testReplyContent() {
		Reply reply = new Reply(-1, 0, "Test reply body", "TestAuthorUsername");
		String replyBody = reply.getBody();
		String authorName = reply.getUser();
		assertEquals(replyBody, "Test reply body");
		assertEquals(authorName, "TestAuthorUsername");
	}
	
	/*
	public void testEditReply() {
		// Consider implementing
	}
	*/

	/*
	public void testDeleteReply() {
		// Consider implementing
	}
	*/
}
