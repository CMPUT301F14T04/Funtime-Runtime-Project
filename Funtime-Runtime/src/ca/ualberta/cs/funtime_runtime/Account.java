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
		favourites = new MyFavourites();
		answerList = new AnswerList();
		questionList = new QuestionList();
		readingList = new ReadingList();
		history = new HistoryList();
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

	public MyFavourites getFavouritesList() {
		return favourites;
	}
	
	public void addFavourite(Question question) {
		favourites.add(question);
	}
	
	public void removeFavourite(Question question) {
		favourites.remove(question);
	}

	public ReadingList getReadingList() {
		return readingList;
	}

	public void readLater(Question question) {
		readingList.add(question);
	}
	
	public HistoryList getHistoryList() {
		return history;
	}
	
	public void addToHistory(Question question) {
		history.add(question);
	}
	

}
