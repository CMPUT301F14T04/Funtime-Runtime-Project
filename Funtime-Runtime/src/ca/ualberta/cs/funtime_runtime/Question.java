package ca.ualberta.cs.funtime_runtime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Question implements Serializable{

	private static final long serialVersionUID = -5060679300357466161L;
	private final String title;
	private final String body;
	private final String username;
	private final Date date;
	public ArrayList<Answer> answerList;
	private static int rating;
	private int numberOfAnswers;
	
	public Question(String title, String body, String username) {
		this.title = title;
		this.body = body;
		this.username = username;
		date = new Date();
		rating = 0;
		numberOfAnswers = 0;
		answerList = new ArrayList<Answer>();
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
	
	public Date getDate() {
		return date;
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
		answerList.add(answer);
		numberOfAnswers++;
	}

	public int getAnswerCount() {
		return numberOfAnswers;
	}

	public Answer getAnswer(int i) {
		Answer answer = answerList.get(i);
		return answer;
	}
	
	public ArrayList<Answer> getAnswerList() {
		return answerList;
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
