package ca.ualberta.cs.funtime_runtime.test;

import java.util.ArrayList;
import java.util.Collections;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.QuestionPageActivity;
import ca.ualberta.cs.funtime_runtime.classes.Answer;
import ca.ualberta.cs.funtime_runtime.classes.AnswerSorter;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.classes.QuestionSorter;

public class AnswerSorterTest extends ActivityInstrumentationTestCase2<QuestionPageActivity> {
	
	public AnswerSorterTest() {
		super(QuestionPageActivity.class);
	}
	
	public void testSortByDate() {
		ArrayList<Answer> answerList = new ArrayList<Answer>();
		ArrayList<Answer> reversedList = new ArrayList<Answer>();
		AnswerSorter sorter = new AnswerSorter(answerList);
		
		for (int i = 0; i < 10; i++) {
			Answer answer = new Answer(i, "Body" + i, "User" + i);
			answerList.add(answer);
			reversedList.add(answer);
		}
		
		Collections.reverse(reversedList);
		sorter.sortByDate();
		assertEquals(answerList, reversedList);
		
	}
	
	public void testSortByVotes() {
		ArrayList<Answer> answerList = new ArrayList<Answer>();
		ArrayList<Answer> reversedList = new ArrayList<Answer>();
		AnswerSorter sorter = new AnswerSorter(answerList);
		
		for (int i = 0; i < 10; i++) {
			Answer answer = new Answer(i, "Body" + i, "User" + i);
			for (int j = 0; j < i; j++) {
				answer.upVote();
			}
			answerList.add(answer);
			reversedList.add(answer);
		}
		
		Collections.reverse(reversedList);
		sorter.sortByVotes();
		assertEquals(answerList, reversedList);

	}
	
	public void testSortByPhotos() {
		ArrayList<Answer> answerList = new ArrayList<Answer>();
		ArrayList<Answer> reversedList = new ArrayList<Answer>();
		AnswerSorter sorter = new AnswerSorter(answerList);
		byte[] photo = new byte[10];
		
		for (int i = 0; i < 5; i++) {
			Answer answer = new Answer(i, "Body" + i, "User" + i);
			answer.setPhoto(photo);
			answerList.add(answer);
			reversedList.add(answer);
		}
		
		for (Answer a: answerList) {
			assertTrue(a.getPhotoStatus());
		}
		
		Collections.reverse(reversedList);
		sorter.sortByPhoto();
		assertEquals(answerList, reversedList);	
	}
	
//	public void testSortByLocation() {
//		ArrayList<Answer> answerList = new ArrayList<Answer>();
//		AnswerSorter sorter = new AnswerSorter(answerList);
//		String loc1 = "Edmonton";
//		String loc2 = "N/A";
//		
//		for (int i = 0; i < 10; i++) {
//			Answer answer = new Answer(i, "Body" + i, "User" + i);
//			answer.setId(i);
//			if (i % 2 == 0) {
//				answer.setLocation(loc1);
//			} else {
//				answer.setLocation(loc2);
//			}
//			
//			answerList.add(answer);
//		}
//		
//		sorter.sortByLocation(getActivity());
//		
//		for (int i = 0; i < 5; i++) {
//			assertEquals(answerList.get(i).getLocation(), loc1);
//		}
//		
//		for (int i = 5; i < 10; i ++) {
//			assertEquals(answerList.get(i).getLocation(), loc2);
//		}
//	}

}
