package ca.ualberta.cs.funtime_runtime;

import java.io.Serializable;
import java.util.Date;

public class Reply implements Serializable {

	private static final long serialVersionUID = 4346891943333516504L;
	private final String BODY;
	private final String USERNAME;
	private final Date DATE;
	
	public Reply(String body, String username) {
		BODY = body;
		USERNAME = username;
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
	
	
	public Date getDate() {
		return DATE;
	}

}
