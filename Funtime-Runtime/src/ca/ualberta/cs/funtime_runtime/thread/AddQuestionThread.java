package ca.ualberta.cs.funtime_runtime.thread;

import android.app.Activity;
import android.content.Context;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.elastic.ESQuestionManager;
/**
 * Thread used to add a question created locally to the server
 * @author bsmolley
 *
 */
public class AddQuestionThread extends Thread {
	
	private Activity activity;
	private Question question;
	private ESQuestionManager manager = new ESQuestionManager();

	public AddQuestionThread(Question question, Context context) {
		this.question = question;
		this.activity = (Activity) context;
	}

	/**
	 * Run the thread to add a server question, used the ESQuestionManager 
	 * to add the question
	 */
	@Override
	public void run() {
		manager.addQuestion(question);
		
		// Give some time to get updated info
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		activity.runOnUiThread(doFinishAdd);
	}
	
	private Runnable doFinishAdd = new Runnable() {
		public void run() {
			
		}
	};
}
	

