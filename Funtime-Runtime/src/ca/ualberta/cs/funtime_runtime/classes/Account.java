package ca.ualberta.cs.funtime_runtime.classes;

import java.util.ArrayList;


/**
 * A class for storing the user's account information.
 * This allows the user to have lists specific to them, 
 * such as favourites, read later, history, etc.
 * Also allows us to indicate that a user has upvoted or 
 * favourited a question on the question page
 * 
 * @author Benjamin Holmwood
 * 
 */
public class Account {
	private String username;
	private ArrayList<Question> favourites;
	private ArrayList<Question> answeredList;
	private ArrayList<Question> questionList;
	private ArrayList<Question> readingList;
	private ArrayList<Question> history;
	private ArrayList<Question> upvotedQuestions;
	private ArrayList<Answer> upvotedAnswers;
	private int id;
	
	
	/**
	 * Constructor for a new account. Initializes a new account with a empty lists
	 * for favourites, user answers, user questions, read later, history, and upvoted 
	 * questions and answers lists
	 * @param username		a string used as a unique identifier for the user
	 */
	public Account(String username) {
		this.username = username;
		favourites = new ArrayList<Question>();
		answeredList = new ArrayList<Question>();
		questionList = new ArrayList<Question>();
		readingList = new ArrayList<Question>();
		history = new ArrayList<Question>();
		upvotedQuestions = new ArrayList<Question>();
		upvotedAnswers = new ArrayList<Answer>();
	}

	/**
	 * Sets the account's username
	 * @param username		a string used as a unique identifier for the user
	 */
	public void setName(String username) {
		this.username = username;
	}

	/**
	 * Returns the account's username
	 * @return		the string used as a unique identifier for the user
	 */
	public String getName() {
		return username;
	}

	/**
	 * Adds a question to the account's list of authored questions
	 * @param question		A question asked by the user that has 
	 * 						been added to the master questsion list
	 */
	public void authorQuestion(Question question) {
		questionList.add(0, question);
	}

	/**
	 * @return	the number of questions a user has answered
	 */
	public int questionsCount() { 
		return questionList.size();
	}

	/**
	 * @param i		the index of the question to be returned
	 * @return		the question at the given index
	 */
	public Question getQuestion(int i) {
		return questionList.get(i);
	}
	
	/**
	 * @return		the list of questions the user has authored
	 */
	public ArrayList<Question> getQuestionList() {
		return questionList;
	}

	/**
	 * Adds an answer to the account's list of authored answers.
	 * @param answer		An answer posted by the user that has 
	 * 						been added to the answer list of its 
	 * 						parent question
	 */
	public void answerQuestion(Question question) {
		answeredList.add(0, question);
	}

	/**
	 * @return		the number of answers the user has authored.
	 */
	public int answeredCount() {
		return answeredList.size();
	}

	/**
	 * @param i		the index of the answer to be returned
	 * @return		the answer at that index
	 */
	public Question getAnsweredQuestion(int i) {
		return answeredList.get(i);
	}

	/**
	 * @return		the list of answers the question has authored
	 */
	public ArrayList<Question> getAnsweredList() {
		return answeredList;
	}
	
	/**
	 * Adds a question to the account's favourited questions list
	 * @param question		a question the user has indicated they want to favourite
	 */
	public void addFavourite(Question question) {
		favourites.add(0, question);
	}
	
	/**
	 * Removes a question from the account's favourited questions list
	 * @param question		a question the user has indicated they want to remove from favourites
	 */
	public void removeFavourite(Question question) {
		favourites.remove(question);
	}
	
	/**
	 * @return		the list of questions the user has favourited
	 */
	public ArrayList<Question> getFavouritesList() {
		return favourites;
	}

	/**
	 * Adds a question to the account's read later list
	 * @param question		a question the user has indicated they want to save to read later
	 */
	public void readLater(Question question) {
		readingList.add(0, question);
	}
	
	/**
	 * Removes a question from the account's read later list
	 * @param question		a question to be removed from the read later list
	 */
	public void removeReadLater(Question question) {
		readingList.remove(question);
	}
	
	/**
	 * @return		the list of questions the user has saved for reading later
	 */
	public ArrayList<Question> getReadingList() {
		return readingList;
	}
	
	/**
	 * Adds a question to the account's history list
	 * @param question		a question the user has viewed
	 */
	public void addToHistory(Question question) {
		history.add(question);
	}
	
	/**
	 * @return		the account's history list
	 */
	public ArrayList<Question> getHistoryList() {
		return history;
	}
	
	/**
	 * Adds a question to the account's upvoted questions list
	 * @param question		a question the user has upvoted
	 */
	public void upvoteQuestion(Question question) {
		question.upVote();
		upvotedQuestions.add(0, question);
	}
	
	/**
	 * Removed a question from the account's upvoted questions list
	 * @param question		a question the user has un-upvoted
	 */
	public void downvoteQuestion(Question question) {
		question.downVote();
		upvotedQuestions.remove(question);
	}
	
	/**
	 * @return		the list of questions the account has upvoted
	 */
	public ArrayList<Question> getUpvotedQuestions() {
		return upvotedQuestions;
	}

	/**
	 * Adds an answer to the account's upvoted answers list
	 * @param answer	an answer the user has upvoted
	 */
	public void upvoteAnswer(Answer answer) {
		answer.upVote();
		upvotedAnswers.add(0, answer);
	}
	
	/**
	 * Removes an answer from the account's upvoted answers list
	 * @param answer	an answer the user has un-upvoted
	 */
	public void downvoteAnswer(Answer answer) {
		answer.downVote();
		upvotedAnswers.remove(answer);
	}
	
	/**
	 * @return		the list of answers the user has upvoted
	 */
	public ArrayList<Answer> getUpvotedAnswers() {
		return upvotedAnswers;
	}

	/**
	 * Returns the server id of the account
	 * @return id
	 */
	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}
	
	/**
	 * Set the account id for the server
	 * @param id
	 * @return
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Adds an answer that the user made to a list of their created answers
	 * @param answer
	 */


}
