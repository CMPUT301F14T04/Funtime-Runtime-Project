package ca.ualberta.cs.funtime_runtime.classes;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;


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
public class Account implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1130304605213585452L;
	
	private String username;
	
	/*
	private ArrayList<Question> favourites;
	private ArrayList<Question> answeredList;
	private ArrayList<Question> questionList;
	private ArrayList<Question> readingList;
	private ArrayList<Question> history;
	private ArrayList<Question> upvotedQuestions;
	private ArrayList<Answer> upvotedAnswers;
	*/
	
	private ArrayList<Integer> favourites;
	private ArrayList<Integer> answeredList;
	private ArrayList<Integer> questionList;
	private ArrayList<Integer> readingList;
	private ArrayList<Integer> history;
	private ArrayList<Integer> upvotedQuestions;
	private ArrayList<Integer> upvotedAnswers;
	
	private Integer id;
	
	
	/**
	 * Constructor for a new account. Initializes a new account with a empty lists
	 * for favourites, user answers, user questions, read later, history, and upvoted 
	 * questions and answers lists
	 * @param username		a string used as a unique identifier for the user
	 */
	public Account(String username) {
		this.username = username;
		favourites = new ArrayList<Integer>();
		answeredList = new ArrayList<Integer>();
		questionList = new ArrayList<Integer>();
		readingList = new ArrayList<Integer>();
		history = new ArrayList<Integer>();
		upvotedQuestions = new ArrayList<Integer>();
		upvotedAnswers = new ArrayList<Integer>();
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
	public void authorQuestion(Question question, Context context) {
		questionList.add(question.getId());
		ApplicationState.updateAccount(context);
		ApplicationState.addServerQuestions(question, context);
		ApplicationState.cacheQuestion(question, context);
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
	public Integer getQuestionId(int i) {
		return questionList.get(i);
	}
	
	/**
	 * @return		the list of questions the user has authored
	 */
	public ArrayList<Integer> getQuestionList() {
		return questionList;
	}

	/**
	 * Adds an answer to the account's list of authored answers.
	 * @param answer		An answer posted by the user that has 
	 * 						been added to the answer list of its 
	 * 						parent question
	 */
	public void answerQuestion(Question question, Answer answer, Context context) {
		if (!answeredList.contains(question.getId())){
			answeredList.add(question.getId());
		}
		if ( (ApplicationState.isOnline(context)) ) {
			ApplicationState.updateServerQuestion(question);
		} else {
			ApplicationState.addOfflineAnswer(answer, context);
		}
		
		ApplicationState.updateAccount(context);
		
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
	public Integer getAnsweredQuestionId(int i) {
		return answeredList.get(i);
	}

	/**
	 * @return		the list of answers the question has authored
	 */
	public ArrayList<Integer> getAnsweredList() {
		return answeredList;
	}
	
	/**
	 * Adds a question to the account's favourited questions list
	 * @param question		a question the user has indicated they want to favourite
	 */
	public void addFavourite(Question question, Context context) {
		favourites.add(question.getId());
		ApplicationState.updateAccount(context);
	}
	
	/**
	 * Removes a question from the account's favourited questions list
	 * @param question		a question the user has indicated they want to remove from favourites
	 */
	public void removeFavourite(Question question, Context context) {
		favourites.remove( (Integer) question.getId());
		ApplicationState.updateAccount(context);
	}
	
	/**
	 * @return		the list of questions the user has favourited
	 */
	public ArrayList<Integer> getFavouritesList() {
		return favourites;
	}

	/**
	 * Adds a question to the account's read later list
	 * @param question		a question the user has indicated they want to save to read later
	 */
	public void readLater(Question question, Context context) {
		readingList.add(question.getId());
		ApplicationState.updateAccount(context);
		ApplicationState.cacheQuestion(question, context);
	}
	
	/**
	 * Removes a question from the account's read later list
	 * @param question		a question to be removed from the read later list
	 */
	public void removeReadLater(Question question, Context context) {
		readingList.remove( (Integer) question.getId());
		ApplicationState.updateAccount(context);
	}
	
	/**
	 * @return		the list of questions the user has saved for reading later
	 */
	public ArrayList<Integer> getReadingList() {
		return readingList;
	}
	
	/**
	 * Adds a question to the account's history list
	 * @param question		a question the user has viewed
	 */
	public void addToHistory(Question question, Context context) {
		if ( !(history.contains(question.getId()) ) ) {
			history.add(question.getId());
		}
		ApplicationState.updateAccount(context);
		ApplicationState.cacheQuestion(question, context);
	}
	
	/**
	 * @return		the account's history list
	 */
	public ArrayList<Integer> getHistoryList() {
		return history;
	}
	
	/**
	 * Adds a question to the account's upvoted questions list
	 * @param question		a question the user has upvoted
	 */
	public void upvoteQuestion(Question question, Context context) {
		Question q;
		if ( (ApplicationState.isOnline(context)) ) {
			q = ApplicationState.refreshQuestion(question, context);
		} else {
			q = question;
		}
		q.upVote();
		upvotedQuestions.add( (Integer) q.getId() );
		//q.upVote();
		if ( (ApplicationState.isOnline(context)) ) {
			ApplicationState.updateServerQuestion(q);
		} else {
			ApplicationState.addOfflineQuestionUpvote(q.getId(), context);
			//ApplicationState.cacheQuestion(question, context);
			//ApplicationState.syncCachedQuestions(context);
		}
		//ApplicationState.cacheQuestion(question, context);
		ApplicationState.updateAccount(context);
	}
	
	/**
	 * Removed a question from the account's upvoted questions list
	 * @param question		a question the user has un-upvoted
	 */
	public void downvoteQuestion(Question question, Context context) {
		Question q;
		if ( (ApplicationState.isOnline(context)) ) {
			q = ApplicationState.refreshQuestion(question, context);
		} else {
			q = question;
		}
		q.downVote();
		upvotedQuestions.remove( (Integer) q.getId() );
		if ( (ApplicationState.isOnline(context)) ) {
			ApplicationState.updateServerQuestion(q);
		} else {
			ApplicationState.addOfflineQuestionDownvote(q.getId(), context);
			//ApplicationState.cacheQuestion(q, context);
		}
		//ApplicationState.cacheQuestion(question, context);
		ApplicationState.updateAccount(context);
	}
	
	/**
	 * @return		the list of questions the account has upvoted
	 */
	public ArrayList<Integer> getUpvotedQuestions() {
		return upvotedQuestions;
	}

	/**
	 * Adds an answer to the account's upvoted answers list
	 * @param answer	an answer the user has upvoted
	 */
	public void upvoteAnswer(Answer answer, Context context) {
//		answer = ApplicationState.refreshAnswer(answer, context);
		
		
		Answer a;
		if ( (ApplicationState.isOnline(context)) ) {
			a = ApplicationState.refreshAnswer(answer, context);
		} else {
			a = answer;
		}
		a.upVote();
		upvotedAnswers.add( (Integer) a.getId() );
		//q.upVote();
		if ( (ApplicationState.isOnline(context)) ) {
			ApplicationState.updateServerQuestion(ApplicationState.getQuestionById(a.getParentQuestionId()));
		} else {
			ApplicationState.addOfflineAnswerUpvote(new OfflineAnswer(a.getId(), a.getParentQuestionId()), context);
			//ApplicationState.cacheQuestion(question, this);
			//ApplicationState.syncCachedQuestions(context);
		}
		//ApplicationState.cacheQuestion(ApplicationState.getQuestionById(answer.getParentQuestionId()), context);
		ApplicationState.updateAccount(context);
		
	}
	
	/**
	 * Removes an answer from the account's upvoted answers list
	 * @param answer	an answer the user has un-upvoted
	 */
	public void downvoteAnswer(Answer answer, Context context) {
		Answer a;
		if ( (ApplicationState.isOnline(context)) ) {
			a = ApplicationState.refreshAnswer(answer, context);
		} else {
			a = answer;
		}
		a.downVote();
		upvotedAnswers.remove( (Integer) a.getId() );
		//q.upVote();
		if ( (ApplicationState.isOnline(context)) ) {
			ApplicationState.updateServerQuestion(ApplicationState.getQuestionById(a.getParentQuestionId()));
		} else {
			ApplicationState.addOfflineQuestionUpvote(a.getId(), context);
			ApplicationState.syncCachedQuestions(context);
		}
		//ApplicationState.cacheQuestion(ApplicationState.getQuestionById(answer.getParentQuestionId()), context);
		ApplicationState.updateAccount(context);
	}
	
	/**
	 * @return		the list of answers the user has upvoted
	 */
	public ArrayList<Integer> getUpvotedAnswers() {
		return upvotedAnswers;
	}

	/**
	 * Returns the server id of the account
	 * @return id
	 */
	public Integer getId() {
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
