package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

/**
 * Not implemented yet. 
 * Will be implemented in Part4 
 * @author pranjali
 *
 */

public interface IQuestionManager {
	public ArrayList<Question> searchQuestions(String searchString, String field);
	public Question getQuestion(int id);
	public void addQuestion(Question question); //pushes (saves) question to the server
	public void deleteQuestion(int id); //deletes question from the server
}
