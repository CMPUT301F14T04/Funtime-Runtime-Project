package ca.ualberta.cs.funtime_runtime;


public class Account {

	private String username;
	private MyFavourites favourites;
	private AnswerList answerList;
	private QuestionList questionList;
	private ReadingList readingList;
	private HistoryList history;
	
	public Account(String username) {
		this.username = username;
		this.favourites = new MyFavourites();
		this.answerList = new AnswerList();
		this.questionList = new QuestionList();
		this.readingList = new ReadingList();
		this.history = new HistoryList();
	}

	public void setName(String username) {
		this.username = username;
	}

	public String getName() {
		return this.username;
	}

	public void authorQuestion(Question question) {
		this.questionList.add(question);
	}

	public int questionsCount() { 
		return this.questionList.size();
	}

	public Question getQuestion(int i) {
		return this.questionList.get(i);
	}

	public void authorAnswer(Answer answer) {
		this.answerList.add(answer);
	}

	public int answersCount() {
		return this.answerList.size();
	}

	public Answer getAnswer(int i) {
		return this.answerList.get(i);
	}

	public MyFavourites getFavouritesList() {
		return this.favourites;
	}

	public ReadingList getReadingList() {
		return this.readingList;
	}

	public HistoryList getHistoryList() {
		return this.history;
	}

}
