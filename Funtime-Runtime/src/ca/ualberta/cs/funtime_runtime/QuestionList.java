package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

public class QuestionList {
	
	ArrayList<Question> questionList;

	public QuestionList() {
		this.questionList = new ArrayList<Question>();
	}

	public void add(Question question)
	{
		this.questionList.add(question);
	}

	public int size()
	{
		return this.questionList.size();
	}

	public Question get(int i)
	{
		Question question = this.questionList.get(i);
		return question;
	}

	public void remove(int i)
	{
		this.questionList.remove(i);
	}

}
