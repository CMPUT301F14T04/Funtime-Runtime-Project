package ca.ualberta.cs.funtime_runtime.classes;

import android.annotation.SuppressLint;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is our model for replies
 * replies consists of a body, author, and date implemented.
 * 
 * @author Kieran Boyle
 *
 */
public class Reply implements Serializable {

	private static final long serialVersionUID = 4346891943333516504L;
	private final String body;
	private final String username;
	private final Date date;
	private String location;
	
	public int id; //used to elastic search (see getId and setId methods) 
	
	/**
	 * This function initializes a reply. 
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
	 * @return returns the string value of a body
	 */
	public String getBody() {
		return body;
	}
	
	/**
	 * this function returns  the author of the username 
	 * @return a string containing the name of the author
	 */
	public String getUser() {
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
	
	
	/**
	 * this function gets the ID associated with a certain reply
	 * @return int id
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * this function sets the ID for a certain reply
	 * @param id
	 */
	public void setId(int id){
		this.id=id;
	}
	
	/**
	 * Sets the location the reply was made at
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * Returns the location the reply was made at
	 * @return
	 */
	public String getLocation() {
		return location;
	}

}
