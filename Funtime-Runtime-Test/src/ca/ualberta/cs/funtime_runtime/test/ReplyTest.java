package ca.ualberta.cs.funtime_runtime.test;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.QuestionPageActivity;
import junit.framework.TestCase;

public class ReplyTest extends ActivityInstrumentationTestCase2<QuestionPageActivity> {

	public ReplyTest() {
		super(QuestionPageActivity.class);
	}

	public void testMakeReply() {
		Reply reply = new Reply("Test reply body", "TestAuthorUsername");
		assertNotNull(reply);
	}
	
	public void testReplyContent() {
		Reply reply = new Reply("Test reply body", "TestAuthorUsername");
		String replyBody = reply.getBody();
		String authorName = reply.getUser();
		assertEquals(replyBody, "Test reply body");
		assertEquals(authorName, "TestAuthorUsername");
	}
	
	public void testEditReply() {
		// Consider implementing
	}

	public void testDeleteReply() {
		// Consider implementing
	}
	
}
