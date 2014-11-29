package ca.ualberta.cs.funtime_runtime.test;

import java.util.ArrayList;
import java.util.Collections;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.QuestionPageActivity;
import ca.ualberta.cs.funtime_runtime.classes.Answer;
import ca.ualberta.cs.funtime_runtime.classes.AnswerSorter;

public class AnswerSorterTest extends ActivityInstrumentationTestCase2<QuestionPageActivity> {
	
	public AnswerSorterTest() {
		super(QuestionPageActivity.class);
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

}
