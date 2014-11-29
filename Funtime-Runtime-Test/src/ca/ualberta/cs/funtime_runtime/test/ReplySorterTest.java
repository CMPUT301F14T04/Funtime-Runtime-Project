package ca.ualberta.cs.funtime_runtime.test;

import java.util.ArrayList;
import java.util.Collections;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.SearchActivity;
import ca.ualberta.cs.funtime_runtime.classes.Reply;
import ca.ualberta.cs.funtime_runtime.classes.ReplySorter;

public class ReplySorterTest extends ActivityInstrumentationTestCase2<SearchActivity> {

	public ReplySorterTest() {
		super(SearchActivity.class);
	}
	
	public void testSortByDate() {
		ArrayList<Reply> replyList = new ArrayList<Reply>();
		ArrayList<Reply> reversedList = new ArrayList<Reply>();
		ReplySorter sorter = new ReplySorter(replyList);
		
		for (int i = 0; i < 10; i++) {
			Reply reply = new Reply(i, i, "Body" + i, "User" + i);
			replyList.add(reply);
			reversedList.add(reply);
		}
		
		Collections.reverse(reversedList);
		sorter.sortByDate();
		assertEquals(replyList, reversedList);
	}
	
	public void testSortByLocation() {
		ArrayList<Reply> replyList = new ArrayList<Reply>();
		ReplySorter sorter = new ReplySorter(replyList);
		String loc1 = "Edmonton";
		String loc2 = "N/A";
		
		for (int i = 0; i < 10; i++) {
			Reply reply = new Reply(i, i, "Body" + i, "User" + i);
			if (i % 2 == 0) {
				reply.setLocation(loc1);
			} else {
				reply.setLocation(loc2);
			}
			
			replyList.add(reply);
		}
		
		sorter.sortByLocation(getActivity());
		
		for (int i = 0; i < 5; i++) {
			assertEquals(replyList.get(i).getLocation(), loc2);
		}
		
		for (int i = 5; i < 10; i ++) {
			assertEquals(replyList.get(i).getLocation(), loc1);
		}
	}
	
	

}
