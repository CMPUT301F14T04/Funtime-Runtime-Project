package ca.ualberta.cs.funtime_runtime;


public class Answer {
	
	private final String BODY;
	private final String USERNAME;
	public ReplyList replyList;
	private int rating;
	private int numberOfReplies;

	public Answer(String body, String username)
	{
		this.BODY = body;
		this.USERNAME = username;
		this.rating = 0;
		this.numberOfReplies = 0;
		this.replyList = new ReplyList();
	}

	public String getBody()
	{
		return this.BODY;
	}

	public String getUser()
	{
		return this.USERNAME;
	}

	public void upVote()
	{
		this.rating++;
		
	}

	public int getRating()
	{
		return this.rating;
	}

	public void downVote()
	{
		this.rating--;
	}
	
	public void addReply(Reply reply) 
	{
		this.replyList.add(reply);
		this.numberOfReplies++;
	}

	public int getReplyCount() 
	{
		return this.numberOfReplies;
	}

	public Reply getReply(int i) 
	{
		Reply reply = this.replyList.get(i);
		return reply;
	}

//	public bitmap getPhoto()
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}

}
