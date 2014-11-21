package ca.ualberta.cs.funtime_runtime.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * Class not implemented for this release
 * Will be implemented for part-4
 * @author Pranjali Pokharel
 * @author Brandon Smolley
 *
 */
public class QuestionSorter {
	
	private ArrayList<Question> sortList;
	protected String sortType;
	
	public QuestionSorter (ArrayList<Question> sortList) {
		this.sortList = sortList;
		sortType = "Date";
	}

	public ArrayList<Question> sortByDate() {	
		sortType = "Date";
		Collections.sort(sortList, new Comparator<Question>() {
			  public int compare(Question q1, Question q2) {
			      return q1.getDate().compareTo(q2.getDate());
			  }
		});
		Collections.reverse(sortList);
		return sortList;
	}
	
	public ArrayList<Question> sortByVotes() {
		sortType = "Votes";
		Collections.sort(sortList, new Comparator<Question>() {
			  public int compare(Question q1, Question q2) {
			      return q1.getRating() - q2.getRating();
			  }
		});
		Collections.reverse(sortList);
		return sortList;
	}
	
	public ArrayList<Question> sortByPhoto() {
		sortType = "Photo";
		return sortList;
	}

	public String getSortType() {
		return sortType;
	}
	
	
}

