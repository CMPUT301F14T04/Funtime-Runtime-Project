package ca.ualberta.cs.funtime_runtime.thread;

import android.app.Activity;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.elastic.ESQuestionManager;

public class AddQuestionThread extends Thread {
	
	private Activity activity;
	private Question question;
	private ESQuestionManager manager = new ESQuestionManager();

	public AddQuestionThread(Question question, Activity activity) {
		this.question = question;
		this.activity = activity;
	}

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
			activity.finish();
		}
	};
}
	

