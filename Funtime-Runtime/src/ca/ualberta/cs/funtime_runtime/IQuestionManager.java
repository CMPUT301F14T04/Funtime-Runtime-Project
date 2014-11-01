package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;


public interface IQuestionManager
{

	public ArrayList<Question> searchQuestions(String searchString, String field);
	public Question getQuestion(int id);
	public void addQuestion(Question question);
	public void deleteQuestion(int id);
	
}
