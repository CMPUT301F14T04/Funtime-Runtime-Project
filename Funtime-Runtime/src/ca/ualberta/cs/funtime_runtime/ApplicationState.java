package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.app.Application;

/**
 * A static class used for managing the application's data in its current state.
 * 
 * @author Benjamin Holmwood
 *
 */
public class ApplicationState extends Application {
	// adapted from http://stackoverflow.com/questions/708012/how-to-declare-global-variables-in-android - Accessed Oct 22 2014
	
	private static Account account;
	// TODO pull questionList from server
	private static ArrayList<Question> questionList = new ArrayList<Question>();
	
	/* Questions and answers that are passable between activities.
	* This will save the reference, while passing via intent will not.
	* This allows the new activity to modify the data and have it be
	* the same accross all activities.
	*/
	private static Question passableQuestion;
	private static Answer passableAnswer;
	
	// TODO pull accountList from server
	private static ArrayList<Account> accountList = new ArrayList<Account>();
	private static boolean loggedIn = false;
	
	/**
	 * Sets the currently logged in account
	 * @param newAccount	an account to be logged in
	 */
	public static void setAccount(Account newAccount) {
		account = newAccount;
		loggedIn = true;
	}
	
	/**
	 * @return		a boolean value indicating whether an account is logged in or not
	 */
	public static boolean isLoggedIn() {
		return loggedIn;
	}
	
	/**
	 * @return		the currently logged in account
	 */
	public static Account getAccount() {
		return account;
	}
	
	/**
	 * @return		the list of all registered accounts
	 */
	public static ArrayList<Account> getAccountList() {
		// TODO pull updated list from server before returning
		return accountList;
	}
	
	/**
	 * Adds an account to the list of registered accounts
	 * @param newAccount	an account to be added to registered accounts
	 */
	public static void addAccount(Account newAccount) {
		//TODO pull updated list from server before adding
		accountList.add(newAccount);
		//TODO save new account to server
	}
	
	/**
	 * Set a question to be passed between activities
	 * @param question		a question to be passed between activities
	 */
	public static void setPassableQuestion(Question question) {
		passableQuestion = question;
	}
	
	/**
	 * @return		the question that has been set before starting a new activity
	 */
	public static Question getPassableQuestion() {
		return passableQuestion;
	}
	
	/**
	 * Set an answer to be passed between activities
	 * @param answer		an answer to be passed between activities
	 */
	public static void setPassableAnswer(Answer answer) {
		passableAnswer = answer;
	}
	
	/**
	 * @return		the answer that has been set before starting a new activity
	 */
	public static Answer getPassableAnswer() {
		return passableAnswer;
	}
	
	/**
	 * @return		return the master list of questions
	 */
	public static ArrayList<Question> getQuestionList() {
		//TODO grab the master question list from the sever before returning
		return questionList;
	}
	
	
	
}
