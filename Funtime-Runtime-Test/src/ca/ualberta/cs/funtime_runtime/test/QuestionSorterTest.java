package ca.ualberta.cs.funtime_runtime.test;

import java.util.ArrayList;
import java.util.Collections;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.SearchActivity;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.classes.QuestionSorter;

public class QuestionSorterTest extends ActivityInstrumentationTestCase2<SearchActivity> {

	public QuestionSorterTest() {
		super(SearchActivity.class);
	}
	
	public void testSortByDate() {
		ArrayList<Question> questionList = new ArrayList<Question>();
		ArrayList<Question> reversedList = new ArrayList<Question>();
		QuestionSorter sorter = new QuestionSorter(questionList);
		
		for (int i = 0; i < 10; i++) {
			Question question = new Question("Title" + i, "Body" + i, "User" + i);
			question.setId(i);
			questionList.add(question);
			reversedList.add(question);
		}
		
		Collections.reverse(reversedList);
		sorter.sortByDate();
		assertEquals(questionList, reversedList);
		
	}
	
	public void testSortByVotes() {
		ArrayList<Question> questionList = new ArrayList<Question>();
		ArrayList<Question> reversedList = new ArrayList<Question>();
		QuestionSorter sorter = new QuestionSorter(questionList);
		
		for (int i = 0; i < 10; i++) {
			Question question = new Question("Title" + i, "Body" + i, "User" + i);
			question.setId(i);
			for (int j = 0; j < i; j++) {
				question.upVote();
			}
			questionList.add(question);
			reversedList.add(question);
		}
		
		Collections.reverse(reversedList);
		sorter.sortByVotes();
		assertEquals(questionList, reversedList);
	}
	
	public void testSortByPhotos() {
		ArrayList<Question> questionList = new ArrayList<Question>();
		ArrayList<Question> reversedList = new ArrayList<Question>();
		QuestionSorter sorter = new QuestionSorter(questionList);
		byte[] photo = new byte[10];
		
		for (int i = 0; i < 5; i++) {
			Question question = new Question("Title" + i, "Body" + i, "User" + i);
			question.setId(i);
			question.setPhoto(photo);
			questionList.add(question);
			reversedList.add(question);
		}
		
		for (Question q: questionList) {
			assertTrue(q.getPhotoStatus());
		}
		
		Collections.reverse(reversedList);
		sorter.sortByPhoto();
		assertEquals(questionList, reversedList);	
	}
	
	public void testSortByLocation() {
		ArrayList<Question> questionList = new ArrayList<Question>();
		QuestionSorter sorter = new QuestionSorter(questionList);
		String loc1 = "Edmonton";
		String loc2 = "N/A";
		
		for (int i = 0; i < 10; i++) {
			Question question = new Question("Title" + i, "Body" + i, "User" + i);
			question.setId(i);
			if (i % 2 == 0) {
				question.setLocation(loc1);
			} else {
				question.setLocation(loc2);
			}
			
			questionList.add(question);
		}
		
		sorter.sortByLocation(getActivity());
		
		for (int i = 0; i < 5; i++) {
			assertEquals(questionList.get(i).getLocation(), loc2);
		}
		
		for (int i = 5; i < 10; i ++)
			assertEquals(questionList.get(i).getLocation(), loc1);
	}

}
