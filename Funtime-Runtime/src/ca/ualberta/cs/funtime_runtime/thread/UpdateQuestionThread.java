package ca.ualberta.cs.funtime_runtime.thread;

import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.elastic.ESQuestionManager;
/**
 * Thread that updates a question, done after an answer or reply is added, 
 * uses ESQuestionManager to do these updates
 * @author bsmolley
 *
 */
public class UpdateQuestionThread extends Thread {
	private Question question;
	private ESQuestionManager manager = new ESQuestionManager();
	
	public UpdateQuestionThread(Question q){		
		question = q;
	}
	
	/**
	 * Uses the ESQuestionManager to update the server with most recent
	 * information
	 */
	@Override
	public void run() {
		updateQuestion(question);
	}

	private void updateQuestion(Question question) {
		manager.deleteQuestion(question.getId());
		manager.addQuestion(question);	
	}
}