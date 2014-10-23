package ca.ualberta.cs.funtime_runtime;

public class Account {

	private String username;
	private MyFavourites favourites;
	private AnswerList answerList;
	private QuestionList questionList;
	private ReadingList readingList;
	private HistoryList history;
	
	public Account(String username_) {
		username = username_;
	}

	public void setName(String username_) {
		username = username_;
	}

	public String getName() {
		// TODO Auto-generated method stub
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

	public ReadingList getReadingList() {
		return readingList;
	}

	public HistoryList getHistoryList() {
		return history;
	}

}
