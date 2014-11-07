package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;


public interface IQuestionManager {
	public ArrayList<Question> searchQuestions(String searchString, String field);
	public Question getQuestion(int id);
	public void addQuestion(Question question); //pushes question to the server
	public void deleteQuestion(int id); //deletes question from the server
}
