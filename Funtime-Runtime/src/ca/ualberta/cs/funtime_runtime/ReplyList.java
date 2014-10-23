package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

public class ReplyList {

	ArrayList<Reply> replyList;
		
	public ReplyList() 
	{	
		this.replyList = new ArrayList<Reply>();
	}

	public void add(Reply reply)
	{
		this.replyList.add(reply);
	}

	public int size()
	{
		return this.replyList.size();
	}

	public Reply get(int i)
	{
		Reply reply = this.replyList.get(i);
		return reply;
	}

	public void remove(Reply reply)
	{
		this.replyList.remove(reply);
	}
}
