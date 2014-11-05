package ca.ualberta.cs.funtime_runtime;

import android.annotation.SuppressLint;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reply implements Serializable {

	private static final long serialVersionUID = 4346891943333516504L;
	private final String body;
	private final String username;
	private final Date date;
	
	public Reply(String body, String username) {
		this.body = body;
		this.username = username;
		this.date= new Date();
	}

	public String getBody()
	{
		return body;
	}

	public String getUser()
	{
		return username;
	}
	
	
	@SuppressLint("SimpleDateFormat")
	public String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
		String replyDateString = dateFormat.format(date);
		return replyDateString;
	}

}
