package ca.ualberta.cs.funtime_runtime.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.content.Context;

/**
 * This class is used to sort an Answer List by various fields,
 * it is primarily used for the Sort function on the app pages that
 * allow for sorting.
 * @author bsmolley
 *
 */

public class AnswerSorter {

	ArrayList<Answer> answerList;
	
	/**
	 * Upon creation of the sorter a list is initialized
	 * @param answerList
	 */
	public AnswerSorter(ArrayList<Answer> answerList) {
		this.answerList = answerList;
	}
	
	/**
	 * Sorts the answer list provided by the date of the object
	 * @return answerList  sorted by Date
	 */
	public ArrayList<Answer> sortByDate() {	
		Collections.sort(answerList, new Comparator<Answer>() {
			  public int compare(Answer a1, Answer a2) {
			      return a1.getDate().compareTo(a2.getDate());
			  }
		});
		Collections.reverse(answerList);
		return answerList;
	}
	
	/**
	 * Sorts the answer list provided by the number of votes of the object
	 * @return answerList  sorted by most votes
	 */
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
	
	/**
	 * Sorts the list by whether or not they have photos
	 * @return  answerList   sorted by if they have an attached photo
	 */
	public ArrayList<Answer> sortByPhoto() {
		Collections.sort(answerList, new Comparator<Answer>() {
			  public int compare(Answer a1, Answer a2) {
				  Boolean p1 = a1.getPhotoStatus();
				  Boolean p2 = a2.getPhotoStatus();
				  
				  int pStatus = p1.compareTo(p2);
				  if (pStatus != 0) {
					  return pStatus;
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
	
	
	/**
	 * Sorts the answer list by location, prioritizing those close to
	 * the users current location
	 * @param context
	 * @return answerList  sorted by location closest to current location
	 */
	public ArrayList<Answer> sortByLocation(Context context) {
		
		ArrayList<Answer> nearMeList = new ArrayList<Answer>();
		ArrayList<Answer> farList = new ArrayList<Answer>();
		AnswerSorter sorterNear; 
		AnswerSorter sorterFar;
		
		Geolocation geolocation = new Geolocation(context);
		geolocation.findLocation();
		String loc = geolocation.getLocation();
		
		Collections.sort(answerList, new Comparator<Answer>() {
			  public int compare(Answer a1, Answer a2) {
				  String s1 = a1.getLocation();
				  String s2 = a2.getLocation();
				  
				  int pStatus = s1.compareTo(s2);
				  if (pStatus != 0) {
					  return pStatus;
				  } else {
					  Date d1 = a1.getDate();
					  Date d2 = a2.getDate();
					  return d1.compareTo(d2);
				  }
			  }
		});
		Collections.reverse(answerList);
		
		for (Answer a: answerList) {
			if (a.getLocation().equals(loc)) {
				nearMeList.add(a);
			} else {
				farList.add(a);
			}
		}

		sorterNear = new AnswerSorter(nearMeList);
		sorterFar = new AnswerSorter(farList);
		
		sorterNear.sortByDate();
		sorterFar.sortByDate();
		
		ArrayList<Answer> newList = new ArrayList<Answer>();
		for (Answer a: farList) {
			newList.add(0, a);
		}
		for (Answer a: nearMeList) {
			newList.add(0, a);
		}
		

		return newList;
		
	}
	
}
