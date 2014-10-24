package ca.ualberta.cs.funtime_runtime;

import java.io.Serializable;
import java.util.Date;

public class Question implements Serializable{

	private static final long serialVersionUID = -6697837907262756771L;
	private final String TITLE;
	private final String BODY;
	private final String USERNAME;
	private final Date DATE;
	public AnswerList answerList;
	private int rating;
	private int numberOfAnswers;
	
	public Question(String title, String body, String username) {
		TITLE = title;
		BODY = body;
		USERNAME = username;
		DATE = new Date();
		rating = 0;
		numberOfAnswers = 0;
		answerList = new AnswerList();
	}

	public String getTitle() {
		return TITLE;
	}

	public String getBody() {
		return BODY;
	}

	public String getUser() {
		return USERNAME;
	}
	
	public Date getDate() {
		return DATE;
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

}
