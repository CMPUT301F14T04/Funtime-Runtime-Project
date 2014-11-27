package ca.ualberta.cs.funtime_runtime.classes;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import ca.ualberta.cs.funtime_runtime.elastic.ESAccountManager;
import ca.ualberta.cs.funtime_runtime.elastic.ESQuestionManager;
import ca.ualberta.cs.funtime_runtime.thread.AddAccountThread;
import ca.ualberta.cs.funtime_runtime.thread.AddQuestionThread;
import ca.ualberta.cs.funtime_runtime.thread.SearchAccountThread;
import ca.ualberta.cs.funtime_runtime.thread.SearchQuestionThread;
import ca.ualberta.cs.funtime_runtime.thread.UpdateAccountThread;

/**
 * A static class used for managing the application's data in its current state.
 * 
 * @author Benjamin Holmwood
 *
 */
public class ApplicationState extends Application {
	// adapted from http://stackoverflow.com/questions/708012/how-to-declare-global-variables-in-android - Accessed Oct 22 2014
	
	private static Account account;
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
	//private static boolean online = false;
	
	private static final String USERACCOUNT = "UserAccount.sav";
	
	private static ESQuestionManager questionManager;
	private static ESAccountManager accountManager;
	
	public static void startup(Context ctx) {
		
		/*
		SaveManager saveManager = new SaveManager();
		Object obj;
		try {
			obj = saveManager.load(USERACCOUNT, ctx);
			if (obj != null) {
				account = (Account) obj;
				loggedIn = true;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		questionManager = new ESQuestionManager();
		accountManager = new ESAccountManager();
		questionList = new ArrayList<Question>();
		loadServerQuestions();
		
	}
	
	public static void loadServerQuestions() {
		
		
		Thread loadThread = new SearchQuestionThread("*");
		//Thread loadThread = new LoadHomeThread("*", homeQuestionList, adapter);
		loadThread.start();	
		
		try {
			loadThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		//questionList.clear();
		//questionList.addAll(questionManager.searchQuestions("*", null));	
		
	}
	
	public static void loadServerAccounts() {
		Thread accountThread = new SearchAccountThread("*");
		accountThread.start();
		try {
			accountThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void loadBySearch(String search) {
		ApplicationState.questionList.clear();
		ApplicationState.questionList.addAll(questionManager.searchQuestions(search, null));	
	}
	
	public static void loadAccounts(String search) {
		ApplicationState.accountList.clear();
		ApplicationState.accountList.addAll(accountManager.searchAccounts(search, null));	
		
	}
	
	public static void refresh() {
		loadServerQuestions();
	}
	
	
	/**
	 * Sets the currently logged in account
	 * @param newAccount	an account to be logged in
	 */
	public static void setAccount(Account newAccount, Context ctx) {
		SaveManager saveManager = new SaveManager();
		saveManager.save(USERACCOUNT, newAccount, ctx);
		account = newAccount;
		loggedIn = true;
	}
	
	/**
	 * @return		a boolean value indicating whether an account is logged in or not
	 */
	public static boolean isLoggedIn() {
		return loggedIn;
	}
	
	public static boolean isOnline(Context ctx) {
		// Adapted from http://stackoverflow.com/questions/2789612/how-can-i-check-whether-an-android-device-is-connected-to-the-web - 2014-11-21
		ConnectivityManager connManager = (ConnectivityManager) ctx.getSystemService(ctx.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connManager.getActiveNetworkInfo();
		if (netInfo == null) {
		    // There are no active networks.
		    return false;
		}
		return netInfo.isConnected();
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
		loadServerAccounts();
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
		//refresh();
		return questionList;
	}
	
	public static String notLoggedIn() {
		String msg = "Please Login or Create an Account"; 
		return msg;	
	}
	
	public static String notFunctional() {
		String msg = "Please Login to use";
		return msg;
	}
	
	public static void addServerQuestions(Question question, Activity activity) {
		Thread addThread = new AddQuestionThread(question, activity);
		addThread.start();
		try {
			addThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void addServerAccount(Account account) {
		Thread accountThread = new AddAccountThread(account);
		accountThread.start();
		try {
			accountThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void updateAccount() {
		// TODO Add checks for online vs offline
		UpdateAccountThread accountThread = new UpdateAccountThread(account);
		accountThread.start();
		try {
			accountThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}


	
	
}
