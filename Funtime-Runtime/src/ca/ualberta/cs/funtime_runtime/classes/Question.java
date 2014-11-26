package ca.ualberta.cs.funtime_runtime.classes;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ca.ualberta.cs.funtime_runtime.R;


/**
 * This is a model class for questions
 * Question consists of a title, body, username, list of replies, list of answers, date and rating
 * Question can also consist of a Photo, if a photo is attached
 * 
 * @author Pranjali Pokharel
 *
 */

public class Question implements Serializable {

	private static final long serialVersionUID = -5060679300357466161L;
	private final String title;
	private final String body;
	private final String username;
	private final Date date;
	public ArrayList<Answer> answerList;
	public ArrayList<Reply> replyList;
	private int rating;
	//private  Bitmap photoBitmap;
	private byte[] photo = null;
	private Boolean hasPhoto = false;
	private String location;
	
	public int photoId;
	public int id; //used for elastic search (see setId and getId methods)
	/**
	 * 
	 * This function initializes a question
	 * 
	 * @param title - title of the question
	 * @param body - description of the question
	 * @param username - author of the question
	 */
	public Question(String title, String body, String username) {
		this.title = title;
		this.body = body;
		this.username = username;
		//photoBitmap = null;
		date = new Date();
		rating = 0;
		answerList = new ArrayList<Answer>();
		replyList = new ArrayList<Reply>();
	}

	/**
	 * This function gets the title of a question
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * This function gets the body (description) of the question
	 * @return body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * This function gets the username of the author of the question
	 * @return username
	 */
	public String getUser() {
		return username;
	}
	
	
	/**
	 * This function gets the date and time at which a particular question was posted
	 * @return date string with the format: "dd/MM/yyyy  HH:mm"
	 */
	@SuppressLint("SimpleDateFormat")
	public String getStringDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
		String questionDateString = dateFormat.format(date);
		return questionDateString;
	}
	
	/**
	 * This function gets the date and time at which a particular question was posted
	 * @return date 
	 */
	@SuppressLint("SimpleDateFormat")
	public Date getDate() {
		return date;
	}

	
	/**
	 * This function adds to the total number of ratings on a question 
	 */
	public void upVote() {
		rating++;
	}

	/**
	 * This function subtracts from the total number of ratings on an question 
	 */
	public void downVote() {
		rating--;	
	}

	
	/**
	 * This function gets the rating on a particular question
	 * @return returns current rating on that question
	 */
	public int getRating() {
		return rating;
	}
	
	/**
	 * This function adds an answer of type Answer at index 0 for a question 
	 * Index 0 makes it so that the most recent answer is displayed at the top of the list
	 * @param answer
	 */
	public void addAnswer(Answer answer) {
		answerList.add(0, answer);
	}

	
	/**
	 * This function gets the total count of the number of answers on a particular question
	 * @return size of List of answers for that question
	 */
	public int getAnswerCount() {
		return answerList.size();
	}

	
	/**
	 * This function gets an answer of type Answer from a particular index i of the answer list
	 * @param i
	 * @return answer
	 */
	public Answer getAnswer(int i) {
		Answer answer = answerList.get(i);
		return answer;
	}
	
	/**
	 * This function gets the array list of answers associated with a question
	 * @return answer list
	 */
	public ArrayList<Answer> getAnswerList() {
		return answerList;
	}

	
	/**
	 * This function adds a reply of type Reply to a particular question at index 0 of the list
	 * Index 0 makes it so that the most recent answer is displayed at the top of the list
	 * @param reply
	 */
	public void addReply(Reply reply) {
		replyList.add(0, reply);
	}
	
	
	/**
	 * This function gets the reply of type Reply for a question at the index i
	 * @param i
	 * @return reply
	 */
	public Reply getReply(int i) {
		Reply reply= replyList.get(i);
		return reply;
	}
	
	
	/**
	 * This function gets the number of replies in the list of replies for a question
	 * @return size of reply list
	 */
	public int getReplyCount() {
		return replyList.size();
	}
	
	/**
	 * This function gets the array list of replies associated with a question
	 * @return reply list
	 */
	public ArrayList<Reply> getReplyList() {
		return replyList;
	}
	
	/**
	 * this function gets the ID associated with a certain question
	 * @return int id
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * this function sets the ID for a certain question
	 * @param id
	 */
	public void setId(int id){
		this.id=id;
	}
	
	public void setPhoto(byte[] questionPhoto){
		photo = questionPhoto;
		hasPhoto = true;
	}
	
	public byte[] getPhoto(){
		return photo;
	}
	
	public Boolean getPhotoStatus(){
		return hasPhoto;
		
	}
	
	/**
	 * Sets the location the question was made at
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * Returns a the location the question was made at
	 * @return location
	 */
	public String getLocation() {
		return location;
	}
}
