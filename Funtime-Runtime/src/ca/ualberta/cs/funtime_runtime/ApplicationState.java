package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.app.Application;

public class ApplicationState extends Application {
	// adapted from http://stackoverflow.com/questions/708012/how-to-declare-global-variables-in-android - Accessed Oct 22 2014
	
	private static Account account;
	// TODO pull questionList from server
	private static ArrayList<Question> questionList = new ArrayList<Question>();
	
	private static Question passableQuestion;
	
	// TODO pull accountList from server
	private static ArrayList<Account> accountList = new ArrayList<Account>();
	private static boolean loggedIn = false;
	
	public static void setAccount(Account newAccount) {
		account = newAccount;
		loggedIn = true;
	}
	
	public static Account getAccount() {
		return account;
	}
	
	public static void setAccountList(ArrayList<Account> newList) {
		accountList = newList;
	}
	
	public static ArrayList<Account> getAccountList() {
		return accountList;
	}
	
	public static void addAccount(Account newAccount) {
		accountList.add(newAccount);
	}
	
	public static void setPassableQuestion(Question question) {
		passableQuestion = question;
	}
	
	public static Question getPassableQuestion() {
		return passableQuestion;
	}
	
	public static ArrayList<Question> getQuestionList() {
		return questionList;
	}
	
	public static boolean isLoggedIn() {
		return loggedIn;
	}
}
