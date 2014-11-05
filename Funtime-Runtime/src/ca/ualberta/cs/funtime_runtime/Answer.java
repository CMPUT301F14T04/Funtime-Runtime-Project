package ca.ualberta.cs.funtime_runtime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class Answer implements Serializable {
	
	private static final long serialVersionUID = 7328501038270376718L;
	private final String body;
	private final String username;
	public ArrayList<Reply> replyList;
	private int rating;
	private int numberOfReplies;
	private final Date date;
	private int id;

	public Answer(String text, String user)
	{
		body = text;
		username = user;
		rating = 0;
		numberOfReplies = 0;
		replyList = new ArrayList<Reply>();
		date = new Date();
	}

	public String getBody()
	{
		return body;
	}

	public String getUser()
	{
		return username;
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
		return date;
	}
	
	public void setId(int num) {
		id = num;
	}

	public int getId() {
		return id;
	}
//	public bitmap getPhoto()
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}

}
