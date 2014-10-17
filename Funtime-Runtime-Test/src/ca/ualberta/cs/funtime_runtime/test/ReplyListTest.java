package ca.ualberta.cs.funtime_runtime.test;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.ReplyList;
import ca.ualberta.cs.funtime_runtime.QuestionPageActivity;
import junit.framework.TestCase;

public class ReplyListTest extends ActivityInstrumentationTestCase2<QuestionPageActivity> {

	public ReplyListTest() {
		super(QuestionPageActivity.class);
	}
	
	public void testMakeList(){
		ReplyList replyList = new ReplyList();
		assertNotNull(replyList);
	}
	
	public void testAddReply(){
		ReplyList replyList = new ReplyList();
		Reply reply = new Reply("Test reply body", "TestAuthorUsername");
		assertNotNull(replyList);
		replyList.add(reply);
		assertTrue(replyList.size(), 1);
		assertEquals(reply, replyList.get(0));
		
		Reply reply0 = new Reply("Test reply 0 body", "TestAuthorUsername0");
		Reply reply1 = new Reply("Test reply 1 body", "TestAuthorUsername1");
		Reply reply2 = new Reply("Test reply 2 body", "TestAuthorUsername2");
		replyList.add(reply0);
		replyList.add(reply1);
		replyList.add(reply2);
		assertTrue(replyList.size(), 4);
	
	}
	
	public void testRemoveReply(){
		ReplyList replyList = new ReplyList();
		Reply reply = new Reply("Test reply body", "TestAuthorUsername");
		assertNotNull(replyList);
		assertTrue(replyList.size(), 1);
		replyList.remove(0);
		assertTrue(replyList.size(), 0);		
		
		Reply reply0 = new Reply("Test reply 0 body", "TestAuthorUsername0");
		Reply reply1 = new Reply("Test reply 1 body", "TestAuthorUsername1");
		Reply reply2 = new Reply("Test reply 2 body", "TestAuthorUsername2");
		assertNotNull(replyList);
		assertTrue(replyList.size(), 3);
		replyList.remove(1);
		assertTrue(replyList.size(), 2);
		assertEquals(replyList.get(0), reply(0);
		assertEquals(replyList.get(1), reply(2);
		
	}
}
