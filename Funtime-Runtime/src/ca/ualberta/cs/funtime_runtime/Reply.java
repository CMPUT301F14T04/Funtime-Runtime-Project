package ca.ualberta.cs.funtime_runtime;

public class Reply {

	private final String BODY;
	private final String USERNAME;
	
	public Reply(String body, String username) {
		BODY = body;
		USERNAME = username;
	}

	public String getBody()
	{
		return BODY;
	}

	public String getUser()
	{
		return USERNAME;
	}

}
