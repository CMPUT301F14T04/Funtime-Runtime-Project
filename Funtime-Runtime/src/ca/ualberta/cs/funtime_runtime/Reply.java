package ca.ualberta.cs.funtime_runtime;

import android.annotation.SuppressLint;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * This class is our model for replies
 * replies conssts of a body, author, and date implemented.
 * @author Kieran Boyle
 *
 */
public class Reply implements Serializable {

	private static final long serialVersionUID = 4346891943333516504L;
	private final String body;
	private final String username;
	private final Date date;
	
	/**
	 * This funtion intializes a reply. 
	 * @param body a string that is the content of the reply
	 * @param username a string that serves as the author of the question.
	 */
	
	public Reply(String body, String username) {
		this.body = body;
		this.username = username;
		this.date= new Date();
	}
	
	/**
	 * This function returns the body of the text 
	 * @return retursn the string value of a body
	 */

	public String getBody()
	{
		return body;
	}
	
	/**
	 * this function returns  the author of the username 
	 * @return a string containing the name of the author
	 */

	public String getUser()
	{
		return username;
	}
	
	/**
	 * this function displays the date that the question was created on.
	 * @return the date in string form
	 */
	@SuppressLint("SimpleDateFormat")
	public String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
		String replyDateString = dateFormat.format(date);
		return replyDateString;
	}

}
