package ca.ualberta.cs.funtime_runtime.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.content.Context;
import android.widget.Toast;


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
				  Integer r1 = q1.getRating();
				  Integer r2 = q2.getRating();
				  
				  int pStatus = r1.compareTo(r2);
				  if (pStatus != 0) {
					  return pStatus;
				  } else {
					  Date d1 = q1.getDate();
					  Date d2 = q2.getDate();
					  return d1.compareTo(d2);
				  }
			  }
		});
		Collections.reverse(sortList);
		return sortList;
	}
	
	// http://stackoverflow.com/questions/4805606/java-sort-problem-by-two-fields - Nov 25, 2014 - Richard H
	public ArrayList<Question> sortByPhoto() {
		sortType = "Photo";
		Collections.sort(sortList, new Comparator<Question>() {
			  public int compare(Question q1, Question q2) {
				  Boolean p1 = q1.getPhotoStatus();
				  Boolean p2 = q2.getPhotoStatus();
				  
				  int pStatus = p1.compareTo(p2);
				  if (pStatus != 0) {
					  return pStatus;
				  } else {
					  Date d1 = q1.getDate();
					  Date d2 = q2.getDate();
					  return d1.compareTo(d2);
				  }
			  }
		});
		Collections.reverse(sortList);
		return sortList;
	}
	
	public ArrayList<Question> sortByLocation(Context context) {
		sortType = "Location";
		
		ArrayList<Question> nearMeList = new ArrayList<Question>();
		ArrayList<Question> farList = new ArrayList<Question>();
		QuestionSorter sorterNear; 
		QuestionSorter sorterFar;
		
		Geolocation geolocation = new Geolocation(context);
		geolocation.findLocation();
		String loc = geolocation.getLocation();
		
		Collections.sort(sortList, new Comparator<Question>() {
			  public int compare(Question q1, Question q2) {
				  String s1 = q1.getLocation();
				  String s2 = q2.getLocation();
				  
				  int pStatus = s1.compareTo(s2);
				  if (pStatus != 0) {
					  return pStatus;
				  } else {
					  Date d1 = q1.getDate();
					  Date d2 = q2.getDate();
					  return d1.compareTo(d2);
				  }
			  }
		});
		Collections.reverse(sortList);
		
		for (Question q: sortList) {
			if (q.getLocation().equals(loc)) {
				nearMeList.add(q);
			} else {
				farList.add(q);
			}
		}

		sorterNear = new QuestionSorter(nearMeList);
		sorterFar = new QuestionSorter(farList);
		
		sorterNear.sortByDate();
		sorterFar.sortByDate();
		
		ArrayList<Question> newList = new ArrayList<Question>();
		for (Question q: farList) {
			newList.add(0, q);
		}
		for (Question q: nearMeList) {
			newList.add(0, q);
		}
		

		return newList;
		
	}

	public String getSortType() {
		return sortType;
	}
	
	
}

