package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;


public class Account {

	private String username;
	private ArrayList<Question> favourites;
	private ArrayList<Answer> answerList;
	private ArrayList<Question> questionList;
	private ArrayList<Question> readingList;
	private ArrayList<Question> history;
	
	public Account(String username) {
		this.username = username;
		favourites = new ArrayList<Question>();
		answerList = new ArrayList<Answer>();
		questionList = new ArrayList<Question>();
		readingList = new ArrayList<Question>();
		history = new ArrayList<Question>();
	}

	public void setName(String username) {
		this.username = username;
	}

	public String getName() {
		return username;
	}

	public void authorQuestion(Question question) {
		questionList.add(question);
	}

	public int questionsCount() { 
		return questionList.size();
	}

	public Question getQuestion(int i) {
		return questionList.get(i);
	}

	public void authorAnswer(Answer answer) {
		answerList.add(answer);
	}

	public int answersCount() {
		return answerList.size();
	}

	public Answer getAnswer(int i) {
		return answerList.get(i);
	}

	public ArrayList<Question> getFavouritesList() {
		return favourites;
	}
	
	public void addFavourite(Question question) {
		favourites.add(question);
	}
	
	public void removeFavourite(Question question) {
		favourites.remove(question);
	}

	public ArrayList<Question> getReadingList() {
		return readingList;
	}

	public void readLater(Question question) {
		readingList.add(question);
	}
	
	public ArrayList<Question> getHistoryList() {
		return history;
	}
	
	public void addToHistory(Question question) {
		history.add(question);
	}
	

}
