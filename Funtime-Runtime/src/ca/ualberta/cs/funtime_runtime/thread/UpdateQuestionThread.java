package ca.ualberta.cs.funtime_runtime.thread;

import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.elastic.ESQuestionManager;

public class UpdateQuestionThread extends Thread {
	private Question question;
	private ESQuestionManager manager = new ESQuestionManager();
	
	public UpdateQuestionThread(Question q){		
		question = q;
	}
	
	@Override
	public void run() {
		updateQuestion(question);
	}

	private void updateQuestion(Question question) {
		manager.deleteQuestion(question.getId());
		manager.addQuestion(question);	
	}
}