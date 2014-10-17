package ca.ualberta.cs.funtime_runtime.test;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.HomeActivity;

public class ReadingListTest extends ActivityInstrumentationTestCase2<HomeActivity> {

	public ReadingListTest() {
		super(HomeActivity.class);
	}
	
	public void testAddQuestion() {
		// A question is added to the reading list upon long clicking the question title in the home page.
		// or long clicking the question title in the question's page.
	}
	
}
