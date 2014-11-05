package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;
import java.util.List;

//import com.google.gson.Gson;

public class ESQuestionManager implements IQuestionManager
{
	private static final String URL="http://cmput301.softwareprocess.es:8080/cmput301f14t04/";
	private static final String TAG= "QuestionsSearch";
	private int idTracker;

	//private Gson gson;
	
	public ESQuestionManager()
	{
		//gson = new Gson();
	}
	@Override
	public ArrayList<Question> searchQuestions(String searchString, String field)
	{

		ArrayList<Question> result = new ArrayList<Question>();

		return null;
	}

	@Override
	public Question getQuestion(int id)
	{

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addQuestion(Question question)
	{

		// TODO Auto-generated method stub
		// push (save) question to server with idTracker
	    // increment idTracker 
		
	}

	@Override
	public void deleteQuestion(int id)
	{

		// TODO Auto-generated method stub
		
	}
	
	public void incrementId() {
		idTracker++;
	}
	
	public void decrementId() {
		idTracker--;
	}

}
