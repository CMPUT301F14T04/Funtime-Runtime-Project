package ca.ualberta.cs.funtime_runtime.elastic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;
import ca.ualberta.cs.funtime_runtime.classes.IReplyManager;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.classes.Reply;

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
 * TODO write method that returns a list of all questions, to populate home page
 * 
 * @see createSearchRequest
 * @author Pranjali Pokharel
 *
 */
public class ESReplyManager implements IReplyManager {
	private static final String RESOURCE_URL="http://cmput301.softwareprocess.es:8080/cmput301f14t04/reply/";
	private static final String SEARCH_URL="http://cmput301.softwareprocess.es:8080/cmput301f14t04/reply/_search";
	private static final String TAG= "AddReply";
	private int id;

	private Gson gson;
	
	public ESReplyManager() 	{
		gson = new Gson();
	}
	
	
	
	@Override
	public Reply getReply(int id) {
		// TODO Auto-generated method stub
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(RESOURCE_URL + id);

		HttpResponse response;

		try {
			response = httpClient.execute(httpGet);
			
			SearchHit<Reply> sr = parseReplyHit(response);
			return sr.getSource();
			

		} catch (Exception e) {
			e.printStackTrace();
		} 

		return null;
	}

	
	
	/**
	 * Adds a new reply to the server
	 * @param Reply
	 */			
	@Override
	public void addReply(Reply reply) {
		// TODO Auto-generated method stub
		// STILL LEFT TO DO THESE:
	    // increment idTracker 
		
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPost addRequest = new HttpPost(RESOURCE_URL + reply.getId());

			StringEntity stringEntity = new StringEntity(gson.toJson(reply));
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
	 * Deletes the reply with the specified id
	 * @param id
	 */
	
	@Override
	public void deleteReply(int replyId) 	{
		// TODO Auto-generated method stub
		//SAME AS ABOVE NEED --decrement Id
		
		
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpDelete deleteRequest = new HttpDelete(RESOURCE_URL + replyId);
			deleteRequest.setHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(deleteRequest);
			String status = response.getStatusLine().toString();
			Log.i(TAG, status);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	private SearchHit<Reply> parseReplyHit(HttpResponse response) {
		
		try {
			String json = getEntityContent(response);
			Type searchHitType = new TypeToken<SearchHit<Reply>>() {}.getType();
			
			SearchHit<Reply> sr = gson.fromJson(json, searchHitType);
			return sr;
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
		id++;
	}



	public List<Reply> searchReplies(String searchString, String field) {
		List<Reply> result = new ArrayList<Reply>();

		if (searchString == null || "".equals(searchString)) {
			searchString = "*";
		}
		
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			HttpPost searchRequest = createSearchRequest(searchString, field);
			
			HttpResponse response = httpClient.execute(searchRequest);
			String status = response.getStatusLine().toString();
			Log.i(TAG, status);
			
			SearchResponse<Reply> esResponse = parseSearchResponse(response);
			Hits<Reply> hits = esResponse.getHits();
			
			if (hits != null) {
				if (hits.getHits() != null) {
					for (SearchHit<Reply> sesr : hits.getHits()) {
						result.add(sesr.getSource());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return result;
	}
	
	private HttpPost createSearchRequest(String searchString, String field)	throws UnsupportedEncodingException {
		
		// TODO HTTP POST NEED TO HAVE A SEARCH URL TO IT NOT JUST URL. 
		HttpPost searchRequest = new HttpPost(SEARCH_URL);

		String[] fields = null;
		if (field != null) {
			fields = new String[1];
			fields[0] = field;
		}
		
		SimpleSearchCommand command = new SimpleSearchCommand(searchString,	fields);
		
		//String query = command.getJsonCommand();
		String query = command.getAll();
		Log.i(TAG, "Json command: " + query);

		StringEntity stringEntity;
		stringEntity = new StringEntity(query);

		searchRequest.setHeader("Accept", "application/json");
		searchRequest.setEntity(stringEntity);

		return searchRequest;
	}
	
	private SearchResponse<Reply> parseSearchResponse(HttpResponse response) throws IOException {
		String json;
		json = getEntityContent(response);
		
		Type searchResponseType = new TypeToken<SearchResponse<Reply>>() {
		}.getType();
		
		SearchResponse<Reply> esResponse = gson.fromJson(json, searchResponseType);

		return esResponse;
		
	}
	

}
