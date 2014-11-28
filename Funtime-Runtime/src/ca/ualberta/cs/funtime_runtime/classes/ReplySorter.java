package ca.ualberta.cs.funtime_runtime.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ReplySorter {
	private ArrayList<Reply> sortList;
	protected String sortType;
	
	public ReplySorter (ArrayList<Reply> sortList) {
		this.sortList = sortList;
		sortType = "Date";
	}

	public ArrayList<Reply> sortByDate() {	
		sortType = "Date";
		Collections.sort(sortList, new Comparator<Reply>() {
			  public int compare(Reply r1, Reply r2) {
			      return r1.getDate().compareTo(r2.getDate());
			  }
		});
		Collections.reverse(sortList);
		return sortList;
	}
}
