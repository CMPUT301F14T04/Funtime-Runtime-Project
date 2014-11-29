package ca.ualberta.cs.funtime_runtime.classes;

import java.util.ArrayList;
import java.util.Queue;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;
import ca.ualberta.cs.funtime_runtime.AuthorQuestionActivity;
import ca.ualberta.cs.funtime_runtime.QuestionPageActivity;
import ca.ualberta.cs.funtime_runtime.adapter.QuestionListAdapter;
import ca.ualberta.cs.funtime_runtime.elastic.ESAccountManager;
import ca.ualberta.cs.funtime_runtime.elastic.ESQuestionManager;
import ca.ualberta.cs.funtime_runtime.thread.AddAccountThread;
import ca.ualberta.cs.funtime_runtime.thread.AddQuestionThread;
import ca.ualberta.cs.funtime_runtime.thread.SearchAccountThread;
import ca.ualberta.cs.funtime_runtime.thread.SearchActivityThread;
import ca.ualberta.cs.funtime_runtime.thread.SearchQuestionThread;
import ca.ualberta.cs.funtime_runtime.thread.UpdateAccountThread;
import ca.ualberta.cs.funtime_runtime.thread.UpdateQuestionThread;

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
	private static ArrayList<Question> cachedQuestions = new ArrayList<Question>();
	private static ArrayList<Question> offlineQuestions = new ArrayList<Question>();
	
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
	
	public static boolean firstLaunch = true;
	public static boolean lastKnownNetworkStatus = false;
	//private static boolean online = false;
	
	private static final String USERACCOUNT = "UserAccount.sav";
	private static final String CACHEDACCOUNT = "CachedAccount.sav";
	private static final String CACHEDQUESTIONS = "CachedQuestions.sav";
	private static final String OFFLINEQUESTIONS = "OfflineQuestions.sav";
	
	private static ESQuestionManager questionManager = new ESQuestionManager();
	private static ESAccountManager accountManager = new ESAccountManager();
	private static SaveManager saveManager = new SaveManager();
	
	public static void startup(Context ctx) {
		
		saveManager = new SaveManager();
		
		loadCachedQuestions(ctx);
		questionList = new ArrayList<Question>();
		
		if ( (ApplicationState.isOnline(ctx)) ) {
			questionManager = new ESQuestionManager();
			accountManager = new ESAccountManager();
			loadServerQuestions(ctx);
		} else {
			String offlineNotice;
			offlineNotice = "No Connection Available";
			Toast.makeText(ctx, offlineNotice, Toast.LENGTH_SHORT).show();
			setCachedQuestions(ctx);
		}

		checkLogin(ctx);
		
		firstLaunch = false;
		
	
	}
	
	public static boolean isFirstLaunch() {
		return firstLaunch;
	}
	
	public static void loadServerQuestions(Context context) {
		if ( (ApplicationState.isOnline(context)) ) {
			Log.i("ApplicationState", "online passed");
			Thread loadThread = new SearchQuestionThread("*");
			//Thread loadThread = new LoadHomeThread("*", homeQuestionList, adapter);
			loadThread.start();	
			
			try {
				loadThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			Log.i("ApplicationState", "loadServer online failed ");
		}
	}
	
	public static void loadCachedQuestions(Context context) {
		Object obj;
		try {
			obj = saveManager.load(CACHEDQUESTIONS, context);
			if (obj != null) {
				cachedQuestions = (ArrayList<Question>) obj;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void setCachedQuestions(Context context) {
		Toast.makeText(context, "Loaded Chached Questions", Toast.LENGTH_LONG).show();
		questionList = cachedQuestions;
	}
	
	public static void loadServerAccounts() {
		if (lastKnownOnlineStatus()) {
			Thread accountThread = new SearchAccountThread("*");
			accountThread.start();
			try {
				accountThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private static void checkLogin(Context context) {
		Object nameObj;
		Object accObj;
		if ( (ApplicationState.isOnline(context)) ) {
			try {
				nameObj = saveManager.load(USERACCOUNT, context);
				accObj = saveManager.load(CACHEDACCOUNT, context);
				
				if (nameObj != null) {
					loadServerAccounts();
					String name = (String) nameObj;
					for (Account a: accountList) {
						String aName = a.getName();
						if (name.equals(aName)) {
							if (accObj != null) {
								Account account = (Account) accObj;
								a = account;
								setAccount(account, context);
								updateAccount(context);
							} else {
								setAccount(a, context);
							}
							break;
						}
					}
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				accObj = saveManager.load(CACHEDACCOUNT, context);
				if (accObj != null) {
					Account account = (Account) accObj;
					setAccount(account, context);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void loadBySearch(String search) {
		if (lastKnownOnlineStatus()) {
			ApplicationState.questionList.clear();
			ApplicationState.questionList.addAll(questionManager.searchQuestions(search, null));	
		}
	}
	
	public static void loadAccounts(String search) {
		if (lastKnownOnlineStatus()) {
			ApplicationState.accountList.clear();
			ApplicationState.accountList.addAll(accountManager.searchAccounts(search, null));	
		}
	}
	
	public static void refresh(Context context) {
		
		loadCachedQuestions(context);
		updateAccount(context);
		
		if ( (ApplicationState.isOnline(context)) ) {
			loadOfflineQuestions(context);
			pushOfflineQuestions(context);
			loadServerQuestions(context);
		} else {
			questionList = cachedQuestions;
		}
	}
	
	public static void pushOfflineQuestions(Context context) {
//		while ( !(offlineQuestions.isEmpty()) ) {
//			addServerQuestions(offlineQuestions.remove(0), context);
//		}
		Log.i("Offline Push", "offline push, elements: " + offlineQuestions.size());
		for (int i = 0; i < offlineQuestions.size(); i++) {
			addServerQuestions(offlineQuestions.get(0), context);
		}
		Log.i("Offline Push", "offline push done: " + offlineQuestions.size());

	}
	
	
	/**
	 * Sets the currently logged in account
	 * @param newAccount	an account to be logged in
	 */
	public static void setAccount(Account newAccount, Context ctx) {
		saveManager.save(USERACCOUNT, newAccount.getName(), ctx);
		saveManager.save(CACHEDACCOUNT, newAccount, ctx);
		Toast.makeText(ctx, "Logged in as " + newAccount.getName(), Toast.LENGTH_LONG).show();
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
			lastKnownNetworkStatus = false;
			return false;
		}
		lastKnownNetworkStatus = true;
		return netInfo.isConnected();
	}
	
	public static boolean lastKnownOnlineStatus() {
		// Adapted from http://stackoverflow.com/questions/2789612/how-can-i-check-whether-an-android-device-is-connected-to-the-web - 2014-11-21
		return lastKnownNetworkStatus;
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
	public static ArrayList<Question> getQuestionList(Context context) {
		//TODO grab the master question list from the sever before returning
		refresh(context);
		/*
		if ( !(isOnline(context)) ) {
			String offlineCacheNotice = "Cached questions loaded.";
			Toast.makeText(context, offlineCacheNotice, Toast.LENGTH_LONG).show();
			loadCachedQuestions(context);
		} else {
			ApplicationState.loadServerQuestions();
		}
		*/
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
	
	public static void addServerQuestions(Question question, Context context) {
		if ( (ApplicationState.isOnline(context)) ) {
			Thread addThread = new AddQuestionThread(question, context);
			addThread.start();
			try {
				addThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			if ( !(offlineQuestions.contains(question)) ) {
				offlineQuestions.add(question);
				saveManager.save(OFFLINEQUESTIONS, offlineQuestions, context);
			}
			saveManager.save(OFFLINEQUESTIONS, offlineQuestions, context);
		}
		
	}
	
	public static void loadOfflineQuestions(Context context) {
		offlineQuestions = new ArrayList<Question>();
		Object obj;
		try {
			obj = saveManager.load(OFFLINEQUESTIONS, context);
			if (obj != null) {
				offlineQuestions = (ArrayList<Question>) obj;
				Log.i("Offline Load", "offline loaded : " + offlineQuestions.get(0).getTitle());
			} else {
				Log.i("Offline Load", "not loaded " + offlineQuestions.size());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void updateServerQuestion(Question question) {
		Thread updateThread = new UpdateQuestionThread(question);
		updateThread.start();
		try {
			updateThread.join();
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
	
	public static void updateAccount(Context context) {
		// TODO Add checks for online vs offline
		saveManager.save(CACHEDACCOUNT, account, context);
		
		
		if ( (ApplicationState.isOnline(context)) ) {
			Log.i("OnlinePass", "Online check passed");
			UpdateAccountThread accountThread = new UpdateAccountThread(account);
			accountThread.start();
			try {
				accountThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void searchQuery(String query, ArrayList<Question> list, QuestionListAdapter adapter, Activity act) {
		if (lastKnownOnlineStatus()) {
			Thread thread = new SearchActivityThread(query, list, adapter, act);
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void cacheQuestion(Question question, Context context) {
		if ( !(cachedQuestions.contains(question) ) ) {
			cachedQuestions.add(question);
			saveManager.save(CACHEDQUESTIONS, cachedQuestions, context);
			Toast.makeText(context, "Cached " + question.getTitle(), Toast.LENGTH_LONG).show();
		}
		saveManager.save(CACHEDQUESTIONS, cachedQuestions, context);
	}

	public static Question refreshQuestion(Question question, Context context) {
		ArrayList<Question> newestQuestions = getQuestionList(context);
		for (Question q: newestQuestions) {
			if (q.equals(question)) {
				return q;
			}
		}
		return question;
	}
	
	public static Question getQuestionById(Integer qId) {
		for (Question q: questionList) {
			if (qId.equals(q.getId())) {
				return q;
			}
		}
		return null;
	}
	
	public static Answer refreshAnswer(Answer answer, Context context) {
		Integer parentID = answer.getParentQuestionId();
		Question parentQuestion = ApplicationState.getQuestionById(parentID);
		parentQuestion = ApplicationState.refreshQuestion(parentQuestion, context);
		ArrayList<Answer> newestAnswers = parentQuestion.getAnswerList();
		for (Answer a: newestAnswers) {
			if (a.equals(answer)) {
				return a;
			}
		}
		return answer;
	}
	
	
}
