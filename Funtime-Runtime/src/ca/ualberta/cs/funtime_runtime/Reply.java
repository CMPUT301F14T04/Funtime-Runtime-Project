package ca.ualberta.cs.funtime_runtime;

public class Reply {

	private final String BODY;
	private final String USERNAME;
	
	public Reply(String body, String username) {
		this.BODY = body;
		this.USERNAME = username;
	}

	public String getBody()
	{
		return this.BODY;
	}

	public String getUser()
	{
		return this.USERNAME;
	}

}
