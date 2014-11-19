package ca.ualberta.cs.funtime_runtime.classes;

import android.annotation.SuppressLint;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This is a model class for answers
 * Answers consists of a body, user, list of replies, rating, date and number of replies
 * Answer can also consist of a Photo, if it is attached
 * @author Pranjali Pokharel
 *
 */

public class Answer implements Serializable {
	
	private static final long serialVersionUID = 7328501038270376718L;
	private final String body;
	private final String username;
	public ArrayList<Integer> replyList;
	private int rating;
	private final Date date;
	private int id;

	
	/**
	 * This function initializes an answer 
	 * 
	 * @param body a string that is the content of the answer
	 * @param username a string that serves as the author of the question.
	 *
	 */
	
	public Answer(String text, String user) 	{
		body = text;
		username = user;
		rating = 0;
		replyList = new ArrayList<Integer>();
		date = new Date();
	}

	
	/**
	 * This function gets the content of the answer
	 * @return the answer body (text containing the answer)
	 */
	public String getBody() {
		return body;
	}

	
	/**
	 * This function gets the username of the answer author
	 * @return returns username
	 */
	public String getUser() {
		return username;
	}

	
	/**
	 * This function adds to the total number of ratings on an answer 
	 */
	public void upVote() {
		rating++;	
	}

	/**
	 * This function subtracts from the total number of ratings on an answer 
	 */
	public void downVote()	{
		rating--;
	}
	
	/**
	 * This function gets the rating on a particular answer
	 * @return returns current rating on that answer
	 */
	public int getRating() {
		return rating;
	}
	
	/**
	 * This function adds a reply to a particular answer
	 * It also adds to the total number of replies count
	 * @param reply of the type Reply
	 */
	public void addReply(Reply reply) {
		replyList.add(0, reply.getId());
	}

	
	/**
	 * This function gets the number of replies currently on an answer
	 * @return returns the number of replies
	 */
	public int getReplyCount() {
		return replyList.size();
	}

	
	/**
	 * This function gets a specific reply on an answer at the specified index
	 * @param i an index integer
	 * @return returns a reply of type Reply on an answer 
	 */
	public int getReply(int i) {
		int replyId = replyList.get(i);
		return replyId;
	}
	
	
	/**
	 * This function gets the reply list associated with a particular answer
	 * @return returns a list of reply
	 */
	public ArrayList<Integer> getReplyList() {
		return replyList;
	}
	
	
	/**
	 * This function gets the date and time at which a particular answer was posted
	 * @return an answer date string with the format: "dd/MM/yyyy  HH:mm"
	 */
	@SuppressLint("SimpleDateFormat")
	public String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
		String answerDateString = dateFormat.format(date);
		return answerDateString;
	}
	
	/**
	 * This function sets an id used to save the answer on the server
	 * This will be used in Elastic Search
	 * @param num 
	 */
	public void setId(int num) {
		id = num;
	}

	/**
	 * This function gets the id that was set by the setId function
	 * @see setId
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * This function will be implemented later to get a photo attached to an answer
	 */
//	public bitmap getPhoto()
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}

}
