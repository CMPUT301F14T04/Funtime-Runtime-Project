package ca.ualberta.cs.funtime_runtime;


public class Answer {
	
	private final String BODY;
	private final String USERNAME;
	public ReplyList replyList;
	private int rating;
	private int numberOfReplies;

	public Answer(String body, String username)
	{
		BODY = body;
		USERNAME = username;
		rating = 0;
		numberOfReplies = 0;
		replyList = new ReplyList();
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

//	public bitmap getPhoto()
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}

}
