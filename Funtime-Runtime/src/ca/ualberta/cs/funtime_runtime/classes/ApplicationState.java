package ca.ualberta.cs.funtime_runtime.classes;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;
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
	private static ArrayList<Answer> offlineAnswers = new ArrayList<Answer>();
	private static ArrayList<Integer> offlineQuestionUpvotes = new ArrayList<Integer>();
	private static ArrayList<Integer> offlineQuestionDownvotes = new ArrayList<Integer>();
	private static ArrayList<OfflineAnswer> offlineAnswerUpvotes = new ArrayList<OfflineAnswer>();
	private static ArrayList<OfflineAnswer> offlineAnswerDownvotes = new ArrayList<OfflineAnswer>();
	private static ArrayList<Reply> offlineQuestionReplies = new ArrayList<Reply>();	
	private static ArrayList<Reply> offlineAnswerReplies = new ArrayList<Reply>();
	
	
	/* Questions and answers that are passable between activities.
	* This will save the reference, while passing via intent will not.
	* This allows the new activity to modify the data and have it be
	* the same accross all activities.
	*/
	private static Question passableQuestion;
	private static Answer passableAnswer;

	private static ArrayList<Account> accountList = new ArrayList<Account>();
	private static boolean loggedIn = false;
	
	public static boolean firstLaunch = true;
	public static boolean lastKnownNetworkStatus = false;
	//private static boolean online = false;
	
	private static final String USERACCOUNT = "UserAccount.sav";
	private static final String CACHEDACCOUNT = "CachedAccount.sav";
	private static final String CACHEDQUESTIONS = "CachedQuestions.sav";
	private static final String OFFLINEQUESTIONS = "OfflineQuestions.sav";
	private static final String OFFLINEANSWERS = "OfflineAnswers.sav";
	private static final String OFFLINEQUESTIONREPLIES = "OfflineQuestionReplies.sav";
	private static final String OFFLINEANSWERREPLIES = "OfflineAnswerReplies.sav";
	private static final String OFFLINEQUESTIONUPVOTES = "OfflineQuestionUpvotes.sav";
	private static final String OFFLINEQUESTIONDOWNVOTES = "OfflineQuestionDownvotes.sav";
	private static final String OFFLINEANSWERUPVOTES = "OfflineAnswerUpvotes.sav";
	private static final String OFFLINEANSWERDOWNVOTES = "OfflineAnswerDownvotes.sav";
	
	
	private static ESQuestionManager questionManager = new ESQuestionManager();
	private static ESAccountManager accountManager = new ESAccountManager();
	private static SaveManager saveManager = new SaveManager();
	
	/**
	 * Run on startup of the application. 
	 * Loads user's locally saved data and pushes any data authored offline to the server.
	 * 
	 * @param context	The context of the current activity.
	 */
	public static void startup(Context context) {
		//Refresh application state
		refresh(context);
		firstLaunch = false;
		if ( isLoggedIn() ) {
			Toast.makeText(context, "Logged in as " + account.getName(), Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * Refreshes the application data. Depending on connectivity,
	 * this function will set the question data for the application
	 * to the most recent data from the server, or from the user's
	 * locally cached data.
	 * 
	 * Pushes any data authored offline once the user reconnects.
	 * 
	 * @param context	The context of the current activity.
	 */
	public static void refresh(Context context) {
		//Load the user's cached data
		loadCachedQuestions(context);
		loadOfflineData(context);
		if ( (ApplicationState.isOnline(context)) ) {
			// If the user is online, push any questions authored while offline
			pushOfflineQuestions(context);
			// Load the latest question data from the server
			loadServerQuestions(context);
			// Push any data authored while offline
			pushOfflineData(context);
			// Sync the cache to the latest server data
			syncCachedQuestions(context);
		} else {
			// If the user is offline, set the question data to the user's cached data
			setCachedQuestions(context);
		}
		// Update the user account information
		checkLogin(context);
		updateAccount(context);
	}
	
	/**
	 * @return firstLaunch 	Whether this is the first launch of the application.
	 */
	public static boolean isFirstLaunch() {
		return firstLaunch;
	}
	
	/**
	 * @return		a boolean value indicating whether an account is logged in or not
	 */
	public static boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * @param context		The context of the current activity.
	 * @return				A boolean indicating whether the device is connected to a network.
	 */
	public static boolean isOnline(Context context) {
		// Adapted from http://stackoverflow.com/questions/2789612/how-can-i-check-whether-an-android-device-is-connected-to-the-web - 2014-11-21
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connManager.getActiveNetworkInfo();
		if (netInfo == null) {
		    // There are no active networks.
			lastKnownNetworkStatus = false;
			return false;
		}
		lastKnownNetworkStatus = true;
		return netInfo.isConnected();
	}

	/**
	 * @return		A message indicating the user must be logged in to user the selected feature.
	 */
	public static String notLoggedIn() {
		String msg = "Please Login or Create an Account"; 
		return msg;	
	}

	/**
	 * @return		The last known connectivity status
	 */
	public static boolean lastKnownOnlineStatus() {
		return lastKnownNetworkStatus;
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
		refresh(context);
		return questionList;
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
		loadServerAccounts();
		return accountList;
	}

	/**
	 * Adds an account to the list of registered accounts
	 * 
	 * @param newAccount	an account to be added to registered accounts
	 */
	public static void addAccount(Account newAccount) {
		accountList.add(newAccount);
	}

	/**
	 * Sets the currently logged in account
	 * 
	 * @param newAccount	an account to be logged in
	 */
	public static void setAccount(Account newAccount, Context ctx) {
		saveManager.save(USERACCOUNT, newAccount.getName(), ctx);
		saveManager.save(CACHEDACCOUNT, newAccount, ctx);
		account = newAccount;
		loggedIn = true;
	}

	/**
	 * Check whether the user is logged in. The user's account data
	 * will be saved locally after logging in, but they are required
	 * to be online to initially log in or create an account.
	 * 
	 * @param context		The context of the current activity.
	 */
	private static void checkLogin(Context context) {
		//Check if the user is online.
		Object nameObj;
		Object accObj;
		if ( (ApplicationState.isOnline(context)) ) {
			// If the user is online, load account from server
			try {
				// Load the locally saved account information
				nameObj = saveManager.load(USERACCOUNT, context);
				accObj = saveManager.load(CACHEDACCOUNT, context);
				// If the user has previously logged in
				if (nameObj != null) {
					// Load accounts from the server
					loadServerAccounts();
					String name = (String) nameObj;
					for (Account a: accountList) {
						// Check each account against the saved information
						String aName = a.getName();
						if (name.equals(aName)) {
							if (accObj != null) {
								// Account found
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
				// Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// The user is offline, load account data from device.
			try {
				accObj = saveManager.load(CACHEDACCOUNT, context);
				if (accObj != null) {
					Account account = (Account) accObj;
					setAccount(account, context);
				}
			} catch (ClassNotFoundException e) {
				// Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Loads the account data from the server.
	 */
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

	/**
	 * Loads the latest questions from the server
	 * 
	 * @param context		The context of the current activity.
	 */
	public static void loadServerQuestions(Context context) {
		Thread loadThread = new SearchQuestionThread("*");
		//Thread loadThread = new LoadHomeThread("*", homeQuestionList, adapter);
		loadThread.start();	
		
		try {
			loadThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads the questions saved on the user's device.
	 * 
	 * @param context 		The context of the current activity.
	 */
	public static void loadCachedQuestions(Context context) {
		Object obj;
		try {
			obj = saveManager.load(CACHEDQUESTIONS, context);
			if (obj != null) {
				cachedQuestions = (ArrayList<Question>) obj;
			}
		} catch (ClassNotFoundException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the question data to the user's cached questions for
	 * offline browsing.
	 * 
	 * @param context		The context of the current activity.
	 */
	public static void setCachedQuestions(Context context) {
		questionList = cachedQuestions;
	}
	
	/**
	 * Adds a question to the server if their is internet connectivity,
	 * or 
	 * 
	 * @param question		A question to be added to the server
	 * @param context		The context of the current activity.
	 */
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

	/**
	 * @param question		A question to update on the server.
	 */
	public static void updateServerQuestion(Question question) {
		Thread updateThread = new UpdateQuestionThread(question);
		updateThread.start();
		try {
			updateThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param account		An account to add to the server.
	 */
	public static void addServerAccount(Account account) {
		Thread accountThread = new AddAccountThread(account);
		accountThread.start();
		try {
			accountThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Update the user's account locally and on the server.
	 * 
	 * @param context			The context of the current activity.
	 */
	public static void updateAccount(Context context) {
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

	/**
	 * Syncs the user's cached questions to the most recent data on
	 * the server.
	 * 
	 * @param context		The context of the current activity.
	 */
	public static void syncCachedQuestions(Context context) {
		ArrayList<Question> newCache = new ArrayList<Question>();
		for (Question cacheQuestion: cachedQuestions) {
			Question q = ApplicationState.getQuestionById(cacheQuestion.getId());
			if ( !(q.getTitle().equals("INVALID QUID")) ) {
				newCache.add(q);
			}
		}
		cachedQuestions.clear();
		cachedQuestions.addAll(newCache);
		saveManager.save(CACHEDQUESTIONS, cachedQuestions, context);
	}
	
	/**
	 * @param question		A question to add to the cache.
	 * @param context		The context of the current activity.
	 */
	public static void cacheQuestion(Question question, Context context) {
		loadCachedQuestions(context);
		if ( !(cachedQuestions.contains(question) ) ) {
			cachedQuestions.add(question);
		} 
		else {
			try {
				for (Question q: cachedQuestions) {
					if (q.equals(question)) {
						cachedQuestions.remove(q);
						cachedQuestions.add(question);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();				
			}
		}
		saveManager.save(CACHEDQUESTIONS, cachedQuestions, context);
	}

	/**
	 * @param question		A question to refresh locally from newest server data.
	 * @param context		The context of the current activity.
	 * @return				The updated question.
	 */
	public static Question refreshQuestion(Question question, Context context) {
		ArrayList<Question> newestQuestions = getQuestionList(context);
		for (Question q: newestQuestions) {
			if (q.equals(question)) {
				return q;
			}
		}
		return question;
	}

	/**
	 * @param question		An answer to refresh locally from newest server data.
	 * @param context		The context of the current activity.
	 * @return				The updated answer.
	 */
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

	/**
	 * Searches for a question by ID.
	 * 
	 * @param qId		The Id of the question to search for.
	 * @return			The question with the matching Id.
	 */
	public static Question getQuestionById(Integer qId) {
		for (Question q: questionList) {
			Log.i("GetQById", "Comparing " + qId + " to " + q.getId());
			if (qId.equals(q.getId())) {
				return q;
			}
		}
		return questionList.get(0);
	}

	/**
	 * Load server questions by a search string.
	 * 
	 * @param search		A value to search for.
	 */
	public static void loadBySearch(String search) {
		ApplicationState.questionList.clear();
		ApplicationState.questionList.addAll(questionManager.searchQuestions(search, null));	
	}
	
	/**
	 * Load accounts from the server by a search value.
	 * 
	 * @param search		A value to search for.
	 */
	public static void loadAccounts(String search) {
		ApplicationState.accountList.clear();
		ApplicationState.accountList.addAll(accountManager.searchAccounts(search, null));	
	}
	
	/**
	 * Called by search activity when the search button is clicked, begins a search of the 
	 * elastic search server for questions and answer that match or contain
	 * the given query.	
	 */
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

	/**
	 * @param offA			An answer to be pushed to the server upon reconnection.
	 * @param context		The context of the current activity.
	 * @param question		A question to refresh locally from newest server data.
	 */
	public static void addOfflineAnswer(Answer offA, Context context) {
		if ( !(offlineAnswers.contains(offA)) ) {
			offlineAnswers.add(offA);
		}
		saveManager.save(OFFLINEANSWERS, offlineAnswers, context);
	}

	/**
	 * @param offR			A question reply to be pushed to the server upon reconnection.
	 * @param context		The context of the current activity.
	 */
	public static void addOfflineQuestionReply(Reply offR, Context context) {
		if ( !(offlineQuestionReplies.contains(offR)) ) {
			offlineQuestionReplies.add(offR);
		}
		saveManager.save(OFFLINEQUESTIONREPLIES, offlineQuestionReplies, context);
	}

	/**
	 * @param offR			An answer reply to be pushed to the server upon reconnection.
	 * @param context		The context of the current activity.
	 */
	public static void addOfflineAnswerReply(Reply offR, Context context) {
		if ( !(offlineAnswerReplies.contains(offR)) ) {
			offlineAnswerReplies.add(offR);
		}
		saveManager.save(OFFLINEANSWERREPLIES, offlineAnswerReplies, context);
	}

	/**
	 * @param id			The id of a question to be upvoted upon reconnection to the server.
	 * @param context		The context of the current activity.
	 */
	public static void addOfflineQuestionUpvote(Integer id, Context context) {
		if ( !(offlineQuestionUpvotes.contains(id)) ) {
			offlineQuestionUpvotes.add(id);
		}
		saveManager.save(OFFLINEQUESTIONUPVOTES, offlineQuestionUpvotes, context);
		
		if ( (offlineQuestionDownvotes.contains(id)) ) {
			offlineQuestionDownvotes.remove( (Integer) id );
			saveManager.save(OFFLINEQUESTIONDOWNVOTES, offlineQuestionDownvotes, context);
		}
	}

	/**
	 * @param id			The id of a question to be downvoted upon reconnection to the server.
	 * @param context		The context of the current activity.
	 */
	public static void addOfflineQuestionDownvote(Integer id, Context context) {
		if ( !(offlineQuestionDownvotes.contains(id)) ) {
			offlineQuestionDownvotes.add(id);
		}
		saveManager.save(OFFLINEQUESTIONDOWNVOTES, offlineQuestionDownvotes, context);
		
		if ( (offlineQuestionUpvotes.contains(id)) ) {
			offlineQuestionUpvotes.remove( (Integer) id );
			saveManager.save(OFFLINEQUESTIONUPVOTES, offlineQuestionUpvotes, context);
		}
	}

	/**
	 * @param offA			A custom class for an answer to be upvoted upon reconnection to the server.
	 * @param context		The context of the current activity.
	 */
	public static void addOfflineAnswerUpvote(OfflineAnswer offA, Context context) {
		if ( !(offlineAnswerUpvotes.contains(offA)) ) {
			offlineAnswerUpvotes.add(offA);
		}
		saveManager.save(OFFLINEANSWERUPVOTES, offlineAnswerUpvotes, context);
		
		if ( (offlineAnswerDownvotes.contains(offA)) ) {
			offlineAnswerDownvotes.remove(offA);
			saveManager.save(OFFLINEANSWERDOWNVOTES, offlineAnswerDownvotes, context);
		}
	}

	/**
	 * @param offA			A custom class for an answer to be downvoted upon reconnection to the server.
	 * @param context		The context of the current activity.
	 */
	public static void addOfflineAnswerDownvote(OfflineAnswer offA, Context context) {
		if ( !(offlineAnswerDownvotes.contains(offA)) ) {
			offlineAnswerDownvotes.add(offA);
		}
		saveManager.save(OFFLINEANSWERDOWNVOTES, offlineAnswerDownvotes, context);
		
		if ( (offlineAnswerUpvotes.contains(offA)) ) {
			offlineAnswerUpvotes.remove(offA);
			saveManager.save(OFFLINEANSWERUPVOTES, offlineAnswerUpvotes, context);
		}
	}

	/**
	 * Load the questions that were authored while the user did not have network connection.
	 * 
	 * @param context		The context of the current activity.
	 */
	public static void loadOfflineData(Context context) {
		loadOfflineQuestions(context);
		loadOfflineAnswers(context);
		loadOfflineQuestionReplies(context);
		loadOfflineAnswerReplies(context);
		loadOfflineQuestionUpvotes(context);
		loadOfflineQuestionDownvotes(context);
		loadOfflineAnswerUpvotes(context);
		loadOfflineAnswerDownvotes(context);
	}
	
	/**
	 * Load the questions that were authored while the user did not have network connection.
	 * 
	 * @param context		The context of the current activity.
	 */
	public static void loadOfflineQuestions(Context context) {
		Object obj;
		try {
			obj = saveManager.load(OFFLINEQUESTIONS, context);
			if (obj != null) {
				offlineQuestions = (ArrayList<Question>) obj;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load the answers that were authored while the user did not have network connection.
	 * 
	 * @param context		The context of the current activity.
	 */
	public static void loadOfflineAnswers(Context context) {
		Object obj;
		try {
			obj = saveManager.load(OFFLINEANSWERS, context);
			if (obj != null) {
				offlineAnswers = (ArrayList<Answer>) obj;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load the question replies that were authored while the user did not have network connection.
	 * 
	 * @param context		The context of the current activity.
	 */
	public static void loadOfflineQuestionReplies(Context context) {
		Object obj;
		try {
			obj = saveManager.load(OFFLINEQUESTIONREPLIES, context);
			if (obj != null) {
				offlineQuestionReplies = (ArrayList<Reply>) obj;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load the answer replies that were authored while the user did not have network connection.
	 * 
	 * @param context		The context of the current activity.
	 */
	public static void loadOfflineAnswerReplies(Context context) {
		Object obj;
		try {
			obj = saveManager.load(OFFLINEANSWERREPLIES, context);
			if (obj != null) {
				offlineAnswerReplies = (ArrayList<Reply>) obj;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load the questions that were upvoted while the user did not have network connection.
	 * 
	 * @param context		The context of the current activity.
	 */
	public static void loadOfflineQuestionUpvotes(Context context) {
		Object obj;
		try {
			obj = saveManager.load(OFFLINEQUESTIONUPVOTES, context);
			if (obj != null) {
				offlineQuestionUpvotes = (ArrayList<Integer>) obj;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load the questions that were downvoted while the user did not have network connection.
	 * 
	 * @param context		The context of the current activity.
	 */
	public static void loadOfflineQuestionDownvotes(Context context) {
		Object obj;
		try {
			obj = saveManager.load(OFFLINEQUESTIONDOWNVOTES, context);
			if (obj != null) {
				offlineQuestionDownvotes = (ArrayList<Integer>) obj;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load the answers that were upvoted while the user did not have network connection.
	 * 
	 * @param context		The context of the current activity.
	 */
	public static void loadOfflineAnswerUpvotes(Context context) {
		Object obj;
		try {
			obj = saveManager.load(OFFLINEANSWERUPVOTES, context);
			if (obj != null) {
				offlineAnswerUpvotes = (ArrayList<OfflineAnswer>) obj;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load the answers that were downvoted while the user did not have network connection.
	 * 
	 * @param context		The context of the current activity.
	 */
	public static void loadOfflineAnswerDownvotes(Context context) {
		Object obj;
		try {
			obj = saveManager.load(OFFLINEANSWERDOWNVOTES, context);
			if (obj != null) {
				offlineAnswerDownvotes = (ArrayList<OfflineAnswer>) obj;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update the server questions with data from the user's device.
	 * 
	 * @param context		The context of the current activity.
	 */
	public static void pushOfflineData(Context context) {
		pushOfflineAnswers(context);
		pushOfflineQuestionReplies(context);
		pushOfflineAnswerReplies(context);
		pushOfflineQuestionUpvotes(context);
		pushOfflineQuestionDownvotes(context);
		pushOfflineAnswerUpvotes(context);
		pushOfflineAnswerDownvotes(context);
	}
	
	/**
	 * Push questions authored while offline to the server.
	 * 
	 * @param context		The context of the current activity.
	 */
	public static void pushOfflineQuestions(Context context) {
		for (int i = 0; i < offlineQuestions.size(); i++) {
			addServerQuestions(offlineQuestions.get(i), context);
		}
		offlineQuestions.clear();
		saveManager.save(OFFLINEQUESTIONS, offlineQuestions, context);
	}

	/**
	 * Push answers authored while offline to the server.
	 * 
	 * @param context		The context of the current activity.
	 */
	public static void pushOfflineAnswers(Context context) {
		for (int i = 0; i < offlineAnswers.size(); i++) {
			Answer a = offlineAnswers.get(i);
			Integer parentQId = a.getParentQuestionId();
			Question parentQ = ApplicationState.getQuestionById(parentQId);
			parentQ.addAnswer(a);
			updateServerQuestion(parentQ);
		}
		offlineAnswers.clear();
		saveManager.save(OFFLINEANSWERS, offlineAnswers, context);

	}
	
	/**
	 * Push question replies authored while offline to the server.
	 * 
	 * @param context		The context of the current activity.
	 */
	public static void pushOfflineQuestionReplies(Context context) {
		for (int i = 0; i < offlineQuestionReplies.size(); i++) {
			Reply r = offlineQuestionReplies.get(i);
			Integer parentQId = r.getParentQuestionId();
			Question parentQ = ApplicationState.getQuestionById(parentQId);
			parentQ.addReply(r);
			updateServerQuestion(parentQ);
		}
		offlineQuestionReplies.clear();
		saveManager.save(OFFLINEQUESTIONREPLIES, offlineQuestionReplies, context);

	}
	
	/**
	 * Push answer replies authored while offline to the server.
	 * 
	 * @param context
	 */
	public static void pushOfflineAnswerReplies(Context context) {
		Log.i("PushOfflineAnswerReplies", "Replies to push: " + offlineAnswerReplies.size());
		for (int i = 0; i < offlineAnswerReplies.size(); i++) {
			Reply r = offlineAnswerReplies.get(i);
			Integer parentQId = r.getParentQuestionId();
			Question parentQ = ApplicationState.getQuestionById(parentQId);
			Integer parentAId = r.getParentAnswerId();
			Answer parentA = parentQ.getAnswerById(parentAId);
			parentA.addReply(r);
			updateServerQuestion(parentQ);
		}
		offlineAnswerReplies.clear();
		saveManager.save(OFFLINEANSWERREPLIES, offlineAnswerReplies, context);

	}
	
	/**
	 * Upvote questions the user upvoted while offline.
	 * 
	 * @param context		The context of the current activity.
	 */
	public static void pushOfflineQuestionUpvotes(Context context) {
		for (int i = 0; i < offlineQuestionUpvotes.size(); i++) {	
			Question q = getQuestionById(offlineQuestionUpvotes.get(i));
			q.upVote();
			ApplicationState.updateServerQuestion(q);
		}
		offlineQuestionUpvotes.clear();
		saveManager.save(OFFLINEQUESTIONUPVOTES, offlineQuestionUpvotes, context);

	}
	
	/**
	 * Downvote questions the user downvoted while offline.
	 * 
	 * @param context	The context of the current activity.
	 */
	public static void pushOfflineQuestionDownvotes(Context context) {
		for (int i = 0; i < offlineQuestionDownvotes.size(); i++) {
			Question q = getQuestionById(offlineQuestionDownvotes.get(i));
			q.downVote();
			ApplicationState.updateServerQuestion(q);
		}
		offlineQuestionDownvotes.clear();
		saveManager.save(OFFLINEQUESTIONDOWNVOTES, offlineQuestionDownvotes, context);
	}
	
	/**
	 * Upvote answers the user upvoted while offline.
	 * 
	 * @param context		The context of the current activity.
	 */
	public static void pushOfflineAnswerUpvotes(Context context) {
		for (int i = 0; i < offlineAnswerUpvotes.size(); i++) {
			Question parentQ = getQuestionById(offlineAnswerUpvotes.get(i).getParentQuestionId());
			Answer a = parentQ.getAnswerById(offlineAnswerUpvotes.get(i).getAnswerId());
			a.upVote();
			ApplicationState.updateServerQuestion(parentQ);
		}
		offlineAnswerUpvotes.clear();
		saveManager.save(OFFLINEANSWERUPVOTES, offlineAnswerUpvotes, context);
	}
	
	/**
	 * Downvote answers the user downvoted while offline.
	 * 
	 * @param context		The context of the current activity.
	 */
	public static void pushOfflineAnswerDownvotes(Context context) {
		for (int i = 0; i < offlineAnswerDownvotes.size(); i++) {
			Question parentQ = getQuestionById(offlineAnswerDownvotes.get(i).getParentQuestionId());
			Answer a = parentQ.getAnswerById(offlineAnswerDownvotes.get(i).getAnswerId());
			a.downVote();
			ApplicationState.updateServerQuestion(parentQ);
		}
		offlineAnswerDownvotes.clear();
		saveManager.save(OFFLINEANSWERDOWNVOTES, offlineAnswerDownvotes, context);
	}
	
}
