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
	
	public static void startup(Context context) {

//		saveManager = new SaveManager();
//		questionList = new ArrayList<Question>();
//		questionManager = new ESQuestionManager();
//		accountManager = new ESAccountManager();
		
		loadCachedQuestions(context);
		loadOfflineData(context);
		
		if ( (ApplicationState.isOnline(context)) ) {
			pushOfflineQuestions(context);
			loadServerQuestions(context);
			syncCachedQuestions(context);
			pushOfflineData(context);
		} else {
			setCachedQuestions(context);
		}

		checkLogin(context);
		
		firstLaunch = false;
		
	
	}
	
	public static void refresh(Context context) {
		
		updateAccount(context);
		loadCachedQuestions(context);
		
		loadOfflineData(context);
		
		if ( (ApplicationState.isOnline(context)) ) {
			pushOfflineQuestions(context);
			loadServerQuestions(context);
			syncCachedQuestions(context);
			pushOfflineData(context);
		} else {
			setCachedQuestions(context);
		}
		
	}
	
	public static boolean isFirstLaunch() {
		return firstLaunch;
	}
	
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
//		if (lastKnownOnlineStatus()) {
			ApplicationState.questionList.clear();
			ApplicationState.questionList.addAll(questionManager.searchQuestions(search, null));	
//		}
	}
	
	public static void loadAccounts(String search) {
//		if (lastKnownOnlineStatus()) {
			ApplicationState.accountList.clear();
			ApplicationState.accountList.addAll(accountManager.searchAccounts(search, null));	
//		}
	}
	
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
	
	public static void pushOfflineData(Context context) {

		//pushOfflineQuestions(context);
		pushOfflineAnswers(context);
		pushOfflineQuestionReplies(context);
		pushOfflineAnswerReplies(context);
		pushOfflineQuestionUpvotes(context);
		pushOfflineQuestionDownvotes(context);
		pushOfflineAnswerUpvotes(context);
		pushOfflineAnswerDownvotes(context);
		
	}
	
	public static void pushOfflineQuestions(Context context) {
//		while ( !(offlineQuestions.isEmpty()) ) {
//			addServerQuestions(offlineQuestions.remove(0), context);
//		}
		Log.i("Offline Push", "offline push, elements: " + offlineQuestions.size());
		for (int i = 0; i < offlineQuestions.size(); i++) {
			addServerQuestions(offlineQuestions.get(i), context);
		}
		offlineQuestions.clear();
		saveManager.save(OFFLINEQUESTIONS, offlineQuestions, context);
		Log.i("Offline Push", "offline push done");

	}

	public static void pushOfflineAnswers(Context context) {
		for (int i = 0; i < offlineAnswers.size(); i++) {
			Answer a = offlineAnswers.get(i);
			Integer parentQId = a.getParentQuestionId();
			Question parentQ = ApplicationState.getQuestionById(parentQId);
			//if parentQ.getTitle()
			parentQ.addAnswer(a);
			updateServerQuestion(parentQ);
		}
		offlineAnswers.clear();
		saveManager.save(OFFLINEANSWERS, offlineAnswers, context);

	}
	
	public static void pushOfflineQuestionReplies(Context context) {
		for (int i = 0; i < offlineQuestionReplies.size(); i++) {
			Reply r = offlineQuestionReplies.get(i);
			Integer parentQId = r.getParentQuestionId();
			Question parentQ = ApplicationState.getQuestionById(parentQId);
			Log.i("PushOfflineQuestionReply", "got guestion " + parentQ.getTitle());
			Log.i("PushOfflineQuestionReply", "ID: " + parentQ.getId());
			parentQ.addReply(r);
			updateServerQuestion(parentQ);
			Log.i("PushOfflineQuestionReply", "updated server question " + parentQ.getTitle());
		}
		offlineQuestionReplies.clear();
		saveManager.save(OFFLINEQUESTIONREPLIES, offlineQuestionReplies, context);

	}
	
	public static void pushOfflineAnswerReplies(Context context) {
		Log.i("PushOfflineAnswerReplies", "Replies to push: " + offlineAnswerReplies.size());
		for (int i = 0; i < offlineAnswerReplies.size(); i++) {
			Reply r = offlineAnswerReplies.get(i);
			Integer parentQId = r.getParentQuestionId();
			Question parentQ = ApplicationState.getQuestionById(parentQId);
			Log.i("PushOfflineAnswerReplies", "got guestion " + parentQ.getTitle());
			Log.i("PushOfflineAnswerReplies", "ID: " + parentQ.getId());
			Integer parentAId = r.getParentAnswerId();
			Answer parentA = parentQ.getAnswerById(parentAId);
			Log.i("PushOfflineAnswerReplies", "got answer " + parentA.getBody());
			Log.i("PushOfflineAnswerRepliesS", "ID: " + parentA.getId());
			parentA.addReply(r);
			//parentQ.updateAnswer(parentA);
			updateServerQuestion(parentQ);
		}
		offlineAnswerReplies.clear();
		saveManager.save(OFFLINEANSWERREPLIES, offlineAnswerReplies, context);

	}
	
	public static void pushOfflineQuestionUpvotes(Context context) {
		for (int i = 0; i < offlineQuestionUpvotes.size(); i++) {	
			Question q = getQuestionById(offlineQuestionUpvotes.get(i));
			q.upVote();
			ApplicationState.updateServerQuestion(q);
		}
		offlineQuestionUpvotes.clear();
		saveManager.save(OFFLINEQUESTIONUPVOTES, offlineQuestionUpvotes, context);

	}
	
	public static void pushOfflineQuestionDownvotes(Context context) {
		for (int i = 0; i < offlineQuestionDownvotes.size(); i++) {
			Question q = getQuestionById(offlineQuestionDownvotes.get(i));
			q.downVote();
			ApplicationState.updateServerQuestion(q);
		}
		offlineQuestionDownvotes.clear();
		saveManager.save(OFFLINEQUESTIONDOWNVOTES, offlineQuestionDownvotes, context);
	}
	
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
		} else {
			Question q = question;
			cachedQuestions.remove(question);
			cachedQuestions.add(q);
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
			Log.i("GetQById", "Comparing " + qId + " to " + q.getId());
			if (qId.equals(q.getId())) {
				return q;
			}
		}
		Question bad = new Question("INVALID QID", "", "");
		bad.setId(qId);
		return bad;
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

	public static void addOfflineAnswer(Answer offA, Context context) {
		if ( !(offlineAnswers.contains(offA)) ) {
			offlineAnswers.add(offA);
		}
		saveManager.save(OFFLINEANSWERS, offlineAnswers, context);
	}
	
	public static void addOfflineQuestionReply(Reply offR, Context context) {
		if ( !(offlineQuestionReplies.contains(offR)) ) {
			offlineQuestionReplies.add(offR);
		}
		saveManager.save(OFFLINEQUESTIONREPLIES, offlineQuestionReplies, context);
	}
	
	public static void addOfflineAnswerReply(Reply offR, Context context) {
		if ( !(offlineAnswerReplies.contains(offR)) ) {
			offlineAnswerReplies.add(offR);
		}
		saveManager.save(OFFLINEANSWERREPLIES, offlineAnswerReplies, context);
	}
	
	public static void addOfflineQuestionUpvote(Integer id, Context context) {
		if ( !(offlineQuestionUpvotes.contains(id)) ) {
			Toast.makeText(context, "added to offline upvotes", Toast.LENGTH_LONG).show();
			offlineQuestionUpvotes.add(id);
		}
		saveManager.save(OFFLINEQUESTIONUPVOTES, offlineQuestionUpvotes, context);
		
		if ( (offlineQuestionDownvotes.contains(id)) ) {
			offlineQuestionDownvotes.remove( (Integer) id );
			saveManager.save(OFFLINEQUESTIONDOWNVOTES, offlineQuestionDownvotes, context);
		}
	}	
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
	
}
