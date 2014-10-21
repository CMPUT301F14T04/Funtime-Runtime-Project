package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;


public class AnswerList{
	
	private ArrayList<Answer> answerList;

	public AnswerList(){	
		this.answerList = new ArrayList<Answer>();
	}

	public void add(Answer answer) {
		this.answerList.add(answer);
	}

	public void remove(int i) {
		this.answerList.remove(i);	
	}

	public int size() {
		return this.answerList.size();
	}

	public Answer get(int i)
	{
		return this.answerList.get(i);
	}

}
