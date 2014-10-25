package ca.ualberta.cs.funtime_runtime;

import java.io.Serializable;
import java.util.ArrayList;


public class AnswerList implements Serializable {
	
	private static final long serialVersionUID = -3373854528010789820L;
	private ArrayList<Answer> answerList;

	public AnswerList(){	
		answerList = new ArrayList<Answer>();
	}

	public void add(Answer answer) {
		answerList.add(answer);
	}

	public void remove(int i) {
		answerList.remove(i);	
	}

	public int size() {
		return answerList.size();
	}

	public Answer get(int i)
	{
		return answerList.get(i);
	}
	
	public ArrayList<Answer> getAnswerList() {
		return answerList;
	}
}
