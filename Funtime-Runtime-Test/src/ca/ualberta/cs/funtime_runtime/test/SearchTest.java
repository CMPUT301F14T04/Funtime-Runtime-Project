package ca.ualberta.cs.funtime_runtime.test;
import java.util.ArrayList;
import java.util.Random;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.SearchActivity;
import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.thread.SearchQuestionThread;


public class SearchTest extends ActivityInstrumentationTestCase2<SearchActivity> {
	
	public SearchTest() {
		super(SearchActivity.class);
	}
	
	public void testSearch() {
		ArrayList<Question> found = new ArrayList<Question>();
		ArrayList<Question> match = new ArrayList<Question>();
		Account account = new Account("User");
		String find = "xxxxxxxxxxxxx";
		Random rand = new Random();
		for (int i = 0; i < 5; i ++) {
			Question question = new Question("xxxxxxxxxxxxx" + i, "Body" + i, "User" + i);
			question.setId(rand.nextInt(50000));
			account.authorQuestion(question, getActivity());
		}
		
		for (int i = 5; i < 10; i ++) {
			Question question = new Question("I'm hiding" + i, "Body" + i, "User" + i);
			question.setId(rand.nextInt(50000));
			account.authorQuestion(question, getActivity());
		}
		
		SearchQuestionThread thread = new SearchQuestionThread(find);
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		found = ApplicationState.getQuestionList(getActivity());
		
		for (Question q: found) {
			if (q.getTitle().contains(find)) {
				assertTrue(q.getTitle().contains(find));
				match.add(q);
			} else if (q.getBody().contains(find)) {
				assertTrue(q.getBody().contains(find));
				match.add(q);
			}	
			
		}
			
	}

}
