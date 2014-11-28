package ca.ualberta.cs.funtime_runtime.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class AnswerSorter {

	ArrayList<Answer> answerList;
	
	public AnswerSorter(ArrayList<Answer> answerList) {
		this.answerList = answerList;
	}
	
	public ArrayList<Answer> sortByVotes() {
		Collections.sort(answerList, new Comparator<Answer>() {
			  public int compare(Answer a1, Answer a2) {
				  Integer r1 = a1.getRating();
				  Integer r2 = a2.getRating();
				  int rStatus = r1.compareTo(r2);
				  
				  if (rStatus != 0) {
					  return rStatus;
				  } else {
					  Date d1 = a1.getDate();
					  Date d2 = a2.getDate();
					  return d1.compareTo(d2);
				  }
			  }
		});
		Collections.reverse(answerList);
		return answerList;
	}
	
}
