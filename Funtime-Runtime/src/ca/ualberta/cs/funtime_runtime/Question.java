package ca.ualberta.cs.funtime_runtime;

import java.util.Date;

public class Question {

	private final String TITLE;
	private final String BODY;
	private final String USERNAME;
	private final Date DATE;
	public AnswerList answerList;
	private int rating;
	private int numberOfAnswers;
	
	public Question(String title, String body, String username) {
		this.TITLE = title;
		this.BODY = body;
		this.USERNAME = username;
		this.DATE = new Date();
		this.rating = 0;
		this.numberOfAnswers = 0;
		this.answerList = new AnswerList();
	}

	public String getTitle() {
		return this.TITLE;
	}

	public String getBody() {
		return this.BODY;
	}

	public String getUser() {
		return this.USERNAME;
	}
	
	public Date getDate() {
		return this.DATE;
	}

	public void upVote() {
		this.rating++;
	}

	public int getRating() {
		return this.rating;
	}

	public void downVote() {
		this.rating--;	
	}

	public void addAnswer(Answer answer) {
		this.answerList.add(answer);
		this.numberOfAnswers++;
	}

	public int getAnswerCount() {
		return this.numberOfAnswers;
	}

	public Answer getAnswer(int i) {
		Answer answer = this.answerList.get(i);
		return answer;
	}

}
