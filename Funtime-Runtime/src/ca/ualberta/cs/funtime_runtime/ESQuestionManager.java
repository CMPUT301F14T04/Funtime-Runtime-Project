package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
//import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * not implemented yet-- Saved for Part-4
 * NEED TO HAVE SEARCH AND RESOURSE URLS need two not ONE
 * 
 * 
 * TODO Implement same methods for answers or create a new class for answer search
 * TODO check the search URL and resource URL-- probably will need two urls for this to work
 * TODO test this class to make sure elastic search works for question
 * 
 * @see createSearchRequest
 * @author Pranjali Pokharel
 *
 */
public class ESQuestionManager implements IQuestionManager {
	private static final String URL="http://cmput301.softwareprocess.es:8080/cmput301f14t04/";
	private static final String TAG= "QuestionsSearch";
	private int idTracker;

	private Gson gson;
	
	public ESQuestionManager() 	{
		gson = new Gson();
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
			return sr.getSource();

		} catch (Exception e) {
			e.printStackTrace();
		} 

		return null;
	}

	
	
	/**
	 * Get questions with the specified search string. If the search does not
	 * specify fields, it searches on all the fields.
	 */
	public List<Question> searchQuestions(String searchString, String field) {
		List<Question> result = new ArrayList<Question>();

		// TODO: Implement search questions using ElasticSearch
		if (searchString == null || "".equals(searchString)) {
			searchString = "*";
		}
		
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			HttpPost searchRequest = createSearchRequest(searchString, field);
			
			HttpResponse response = httpClient.execute(searchRequest);
			
			String status = response.getStatusLine().toString();
			Log.i(TAG, status);
			
			SearchResponse<Question> esResponse = parseSearchResponse(response);
			Hits<Question> hits = esResponse.getHits();
			
			if (hits != null) {
				if (hits.getHits() != null) {
					for (SearchHit<Question> sesr : hits.getHits()) {
						result.add(sesr.getSource());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return result;
	}
	
	
	/**
	 * Adds a new question
	 */
			
	@Override
	public void addQuestion(Question question) {
		// TODO Auto-generated method stub
		// STILL LEFT TO DO THESE:
		// push (save) question to server with idTracker
	    // increment idTracker 

		
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPost addRequest = new HttpPost(URL + question.getId());

			StringEntity stringEntity = new StringEntity(gson.toJson(question));
			addRequest.setEntity(stringEntity);
			addRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			Log.i(TAG, status);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletes the question with the specified id
	 * @param id
	 */
	
	@Override
	public void deleteQuestion(int questionId) 	{
		// TODO Auto-generated method stub
		//SAME AS ABOVE NEED --decrement Id
		//ALSO CHECK RESOURSE URL AND SEARCH URL
		
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpDelete deleteRequest = new HttpDelete(URL + questionId);
			deleteRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(deleteRequest);
			String status = response.getStatusLine().toString();
			Log.i(TAG, status);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Creates a search request from a search string and a field
	 * @param searchString
	 * @param field
	 */
	private HttpPost createSearchRequest(String searchString, String field)	throws UnsupportedEncodingException {
		
		// TODO HTTP POST NEED TO HAVE A SEARCH URL TO IT NOT JUST URL. 
		HttpPost searchRequest = new HttpPost(URL);

		String[] fields = null;
		if (field != null) {
			fields = new String[1];
			fields[0] = field;
		}
		
		SimpleSearchCommand command = new SimpleSearchCommand(searchString,	fields);
		
		String query = command.getJsonCommand();
		Log.i(TAG, "Json command: " + query);

		StringEntity stringEntity;
		stringEntity = new StringEntity(query);

		searchRequest.setHeader("Accept", "application/json");
		searchRequest.setEntity(stringEntity);

		return searchRequest;
	}
	
	private SearchHit<Question> parseQuestionHit(HttpResponse response) {
		
		try {
			String json = getEntityContent(response);
			Type searchHitType = new TypeToken<SearchHit<Question>>() {}.getType();
			
			SearchHit<Question> sr = gson.fromJson(json, searchHitType);
			return sr;
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * Parses the response of a search
	 */
	private SearchResponse<Question> parseSearchResponse(HttpResponse response) throws IOException {
		String json;
		json = getEntityContent(response);
		
		Type searchResponseType = new TypeToken<SearchResponse<Question>>() {
		}.getType();
		
		SearchResponse<Question> esResponse = gson.fromJson(json, searchResponseType);

		return esResponse;
		
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
