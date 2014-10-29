package ca.ualberta.cs.funtime_runtime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class Answer implements Serializable {
	
	private static final long serialVersionUID = 7328501038270376718L;
	private final String BODY;
	private final String USERNAME;
	public ArrayList<Reply> replyList;
	private int rating;
	private int numberOfReplies;
	private final Date DATE;

	public Answer(String body, String username)
	{
		BODY = body;
		USERNAME = username;
		rating = 0;
		numberOfReplies = 0;
		replyList = new ArrayList<Reply>();
		DATE = new Date();
	}

	public String getBody()
	{
		return BODY;
	}

	public String getUser()
	{
		return USERNAME;
	}

	public void upVote()
	{
		rating++;
		
	}

	public int getRating()
	{
		return rating;
	}

	public void downVote()
	{
		rating--;
	}
	
	public void addReply(Reply reply) 
	{
		replyList.add(reply);
		numberOfReplies++;
	}

	public int getReplyCount() 
	{
		return numberOfReplies;
	}

	public Reply getReply(int i) 
	{
		Reply reply = replyList.get(i);
		return reply;
	}
	
	public Date getDate() {
		return DATE;
	}

//	public bitmap getPhoto()
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}

}
