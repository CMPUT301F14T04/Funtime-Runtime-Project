package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


/*TODO
 *  FIGURE OUT WHY GSON LIBRARY WONT IMPORT????? SO CONFUSED???
 */


import android.util.Log;

//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;

/**
 * not implemented yet-- Saved for Part-4
 * @author Pranjali Pokharel
 *
 */
public class ESQuestionManager implements IQuestionManager {
	private static final String URL="http://cmput301.softwareprocess.es:8080/cmput301f14t04/";
	private static final String TAG= "QuestionsSearch";
	private int idTracker;

	//private Gson gson;
	
	public ESQuestionManager() 	{
		//gson = new Gson();
	}
	
	@Override
	public ArrayList<Question> searchQuestions(String searchString, String field) {
		ArrayList<Question> result = new ArrayList<Question>();
		return null;
	}

	@Override
	public Question getQuestion(int id) {
		// TODO Auto-generated method stub
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URL + id);

		HttpResponse response;

		try {
			response = httpClient.execute(httpGet);
			
			SearchHit<Question> sr = parseQuestionHit(response);
			//return sr.getSource();

		} catch (Exception e) {
			e.printStackTrace();
		} 

		return null;
	}

	@Override
	public void addQuestion(Question question) {
		// TODO Auto-generated method stub
		// push (save) question to server with idTracker
	    // increment idTracker 
	}

	@Override
	public void deleteQuestion(int id) 	{
		// TODO Auto-generated method stub
	}
	
	
	/**
	 * Creates a search request from a search string and a field
	 */
	private HttpPost createSearchRequest(String searchString, String field)	throws UnsupportedEncodingException {
		//TODO
		
		return null;
	}
	
	private SearchHit<Question> parseQuestionHit(HttpResponse response) {
		
		try {
			String json = getEntityContent(response);
			//Type searchHitType = new TypeToken<SearchHit<Question>>() {}.getType();
			
		//	SearchHit<Question> sr = gson.fromJson(json, searchHitType);
			//return sr;
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	

	
	
	/**
	 * Gets content from an HTTP response
	 */
	public String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		return result.toString();
	}
	
	public void incrementId() {
		idTracker++;
	}
	
	public void decrementId() {
		idTracker--;
	}

}
