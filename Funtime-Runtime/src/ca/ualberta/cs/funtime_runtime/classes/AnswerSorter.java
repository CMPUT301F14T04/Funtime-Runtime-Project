package ca.ualberta.cs.funtime_runtime.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AnswerSorter {

	ArrayList<Answer> answerList;
	
	public AnswerSorter(ArrayList<Answer> answerList) {
		this.answerList = answerList;
	}
	
	public ArrayList<Answer> sortByVotes() {
		Collections.sort(answerList, new Comparator<Answer>() {
			  public int compare(Answer a1, Answer a2) {
				  return a2.getRating() - a2.getRating();
			  }
		});
		return answerList;
	}
	
}
