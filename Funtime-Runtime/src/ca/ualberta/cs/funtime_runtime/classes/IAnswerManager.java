package ca.ualberta.cs.funtime_runtime.classes;

import java.util.List;

public interface IAnswerManager {
	public List<Answer> searchAnswer(String searchString, String field);
	public Answer getAnswer(int id);
	public void addAnswer(Answer answer);
	public void deleteAnswer(int id);
}
