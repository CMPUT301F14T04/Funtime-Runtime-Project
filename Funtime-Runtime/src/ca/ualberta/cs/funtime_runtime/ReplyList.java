package ca.ualberta.cs.funtime_runtime;

import java.io.Serializable;
import java.util.ArrayList;

public class ReplyList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3619450391434064548L;
	ArrayList<Reply> replyList;
		
	public ReplyList() 
	{	
		replyList = new ArrayList<Reply>();
	}

	public void add(Reply reply)
	{
		replyList.add(reply);
	}

	public int size()
	{
		return replyList.size();
	}

	public Reply get(int i)
	{
		Reply reply = replyList.get(i);
		return reply;
	}

	public void remove(Reply reply)
	{
		replyList.remove(reply);
	}
}
