package ca.ualberta.cs.funtime_runtime.test;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.QuestionPageActivity;
import ca.ualberta.cs.funtime_runtime.Reply;

public class ReplyListTest extends ActivityInstrumentationTestCase2<QuestionPageActivity> {

	public ReplyListTest() {
		super(QuestionPageActivity.class);
	}
	
	public void testMakeList(){
		ArrayList<Reply> replyList = new ArrayList<Reply>();
		assertNotNull(replyList);
	}
	
	public void testAddReply(){
		ArrayList<Reply> replyList = new ArrayList<Reply>();
		Reply reply = new Reply("Test reply body", "TestAuthorUsername");
		assertNotNull(replyList);
		replyList.add(reply);
		assertEquals(replyList.size(), 1);
		assertEquals(reply, replyList.get(0));
		
		Reply reply0 = new Reply("Test reply 0 body", "TestAuthorUsername0");
		Reply reply1 = new Reply("Test reply 1 body", "TestAuthorUsername1");
		Reply reply2 = new Reply("Test reply 2 body", "TestAuthorUsername2");
		replyList.add(reply0);
		replyList.add(reply1);
		replyList.add(reply2);
		assertEquals(replyList.size(), 4);
	
	}

}
