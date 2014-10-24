package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

public class QuestionList {
	
	ArrayList<Question> questionList;

	public QuestionList() {
		questionList = new ArrayList<Question>();
	}

	public void add(Question question)
	{
		questionList.add(question);
	}

	public int size()
	{
		return questionList.size();
	}

	public Question get(int i)
	{
		Question question = questionList.get(i);
		return question;
	}

	public void remove(int i)
	{
		questionList.remove(i);
	}

}
