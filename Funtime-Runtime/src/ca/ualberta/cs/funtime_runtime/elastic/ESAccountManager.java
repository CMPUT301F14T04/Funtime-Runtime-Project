package ca.ualberta.cs.funtime_runtime.elastic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;
import ca.ualberta.cs.funtime_runtime.classes.Account;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * not implemented yet-- Saved for Part-4
 * NEED TO HAVE SEARCH AND RESOURSE URLS need two not ONE
 * 
 * 
 * TODO Implement same methods for answers or create a new class for answer search
 * TODO check the search URL and resource URL-- probably will need two urls for this to work
 * TODO test this class to make sure elastic search works for account
 * TODO write method that returns a list of all accounts, to populate home page
 * 
 * @see createSearchRequest
 * @author Pranjali Pokharel
 *
 */
public class ESAccountManager {
	private static final String RESOURCE_URL="http://cmput301.softwareprocess.es:8080/cmput301f14t04/account/";
	private static final String SEARCH_URL="http://cmput301.softwareprocess.es:8080/cmput301f14t04/account/_search/?size=50";
	private static final String TAG= "AccountSearch";

	private Gson gson;
	
	public ESAccountManager() 	{
		gson = new Gson();
	}
	
	
	public void updateAccount(Account account) {
		deleteAccount(account.getId());
		addAccount(account);
	}

	public Account getAccount(int id) {
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(RESOURCE_URL + id);

		HttpResponse response;

		try {
			response = httpClient.execute(httpGet);
			
			SearchHit<Account> sr = parseAccountHit(response);
			return sr.getSource();

		} catch (Exception e) {
			e.printStackTrace();
		} 

		return null;
	}

	
	
	/**
	 * Get accounts with the specified search string. If the search does not
	 * specify fields, it searches on all the fields.
	 */
	public List<Account> searchAccounts(String searchString, String field) {
		List<Account> result = new ArrayList<Account>();

		if (searchString == null || "".equals(searchString)) {
			searchString = "*";
		}
		
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			HttpPost searchRequest = createSearchRequest(searchString, field);
			
			HttpResponse response = httpClient.execute(searchRequest);
			String status = response.getStatusLine().toString();
			Log.i(TAG, status);
			
			SearchResponse<Account> esResponse = parseSearchResponse(response);
			Hits<Account> hits = esResponse.getHits();
			//Log.i("Acutal hits", ""+hits.getTotal());
			
			if (hits != null) {
				if (hits.getHits() != null) {
					for (SearchHit<Account> sesr : hits.getHits()) {
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
	 * Adds a new account
	 */
	public void addAccount(Account account) {	
		
		HttpClient httpClient = new DefaultHttpClient();
		
		try {
			HttpPost addRequest = new HttpPost(RESOURCE_URL + account.getId());

			StringEntity stringEntity = new StringEntity(gson.toJson(account));
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
	 * Deletes the account with the specified id
	 * @param id
	 */
	
	public void deleteAccount(int accountId) 	{
		// TODO Auto-generated method stub
		//SAME AS ABOVE NEED --decrement Id
		//ALSO CHECK RESOURSE URL AND SEARCH URL
		
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpDelete deleteRequest = new HttpDelete(RESOURCE_URL + accountId);
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
		HttpPost searchRequest = new HttpPost(SEARCH_URL);

		String[] fields = null;
		if (field != null) {
			fields = new String[1];
			fields[0] = field;
		}
		
		SimpleSearchCommand command = new SimpleSearchCommand(searchString,	fields);
		
		String query = command.getJsonCommand();
		//String query = command.getAll();
		Log.i(TAG, "Json command: " + query);

		StringEntity stringEntity;
		stringEntity = new StringEntity(query);

		searchRequest.setHeader("Accept", "application/json");
		searchRequest.setEntity(stringEntity);

		return searchRequest;
	}
	
	private SearchHit<Account> parseAccountHit(HttpResponse response) {
		
		try {
			String json = getEntityContent(response);
			Type searchHitType = new TypeToken<SearchHit<Account>>() {}.getType();
			
			SearchHit<Account> sr = gson.fromJson(json, searchHitType);
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
	private SearchResponse<Account> parseSearchResponse(HttpResponse response) throws IOException {
		String json;
		json = getEntityContent(response);
		
		Type searchResponseType = new TypeToken<SearchResponse<Account>>() {
		}.getType();
		
		SearchResponse<Account> esResponse = gson.fromJson(json, searchResponseType);

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
	
	

}
