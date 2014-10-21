package ca.ualberta.cs.funtime_runtime;

public class Question {

	public AnswerList answerList;
	private final String TITLE;
	private final String BODY;
	private final String USERNAME;
	private int rating;
	private int numberOfAnswers;
	
	
	public Question(String title, String body, String username) {
		this.TITLE = title;
		this.BODY = body;
		this.USERNAME = username;
		this.rating = 0;
		this.numberOfAnswers = 0;
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
		answerList.add(answer);
	}

	public int getAnswerCount() {
		return this.numberOfAnswers;
	}

	public Answer getAnswer(int i) {
		Answer answer = answerList.get(i);
		return answer;
	}

}
