package ca.ualberta.cs.funtime_runtime;

import android.annotation.SuppressLint;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Question implements Serializable {

	private static final long serialVersionUID = -5060679300357466161L;
	private final String title;
	private final String body;
	private final String username;
	private final Date date;
	public ArrayList<Answer> answerList;
	public ArrayList<Reply> replyList;
	private int rating;
	
	public Question(String title, String body, String username) {
		this.title = title;
		this.body = body;
		this.username = username;
		date = new Date();
		rating = 0;
		answerList = new ArrayList<Answer>();
		replyList = new ArrayList<Reply>();
	}

	public String getTitle() {
		return title;
	}
	

	public String getBody() {
		return body;
	}

	public String getUser() {
		return username;
	}
	
	@SuppressLint("SimpleDateFormat")
	public String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
		String questionDateString = dateFormat.format(date);
		return questionDateString;
	}

	public void upVote() {
		rating++;
	}

	public int getRating() {
		return rating;
	}

	public void downVote() {
		rating--;	
	}

	public void addAnswer(Answer answer) {
		answerList.add(0, answer);
	}

	public int getAnswerCount() {
		return answerList.size();
	}

	public Answer getAnswer(int i) {
		Answer answer = answerList.get(i);
		return answer;
	}
	
	public ArrayList<Answer> getAnswerList() {
		return answerList;
	}

	public void addReply(Reply reply) {
		replyList.add(0, reply);
	}
	
	public Reply getReply(int i) {
		Reply reply = replyList.get(i);
		return reply;
	}
	
	public int getReplyCount() {
		return replyList.size();
	}
	
	public ArrayList<Reply> getReplyList() {
		return replyList;
	}
	
	
	
	public boolean equals(Object o) {
	    // Adapted from http://stackoverflow.com/questions/10943836/if-arraylist-contains-doesnt-work - accessed 2 nov 2014
		if (o instanceof Question) {
	    	Question other = (Question) o;
	    	//TODO: change to check unique ID.
	        return title.equals(other.title) 
	             && username.equals(other.username) 
	             && date.equals(other.date);
	    } else {
	        return false;
	    }
	}

	
}
