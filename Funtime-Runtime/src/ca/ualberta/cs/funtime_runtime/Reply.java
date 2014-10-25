package ca.ualberta.cs.funtime_runtime;

import java.io.Serializable;

public class Reply implements Serializable {

	private static final long serialVersionUID = 4346891943333516504L;
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
