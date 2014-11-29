package ca.ualberta.cs.funtime_runtime.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.content.Context;

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
	
	public ArrayList<Reply> sortByLocation(Context context) {
		
		ArrayList<Reply> nearMeList = new ArrayList<Reply>();
		ArrayList<Reply> farList = new ArrayList<Reply>();
		ReplySorter sorterNear; 
		ReplySorter sorterFar;
		
		Geolocation geolocation = new Geolocation(context);
		geolocation.findLocation();
		String loc = geolocation.getLocation();
		
		Collections.sort(sortList, new Comparator<Reply>() {
			  public int compare(Reply r1, Reply r2) {
				  String s1 = r1.getLocation();
				  String s2 = r2.getLocation();
				  
				  int pStatus = s1.compareTo(s2);
				  if (pStatus != 0) {
					  return pStatus;
				  } else {
					  Date d1 = r1.getDate();
					  Date d2 = r2.getDate();
					  return d1.compareTo(d2);
				  }
			  }
		});
		Collections.reverse(sortList);
		
		for (Reply r: sortList) {
			if (r.getLocation().equals(loc)) {
				nearMeList.add(r);
			} else {
				farList.add(r);
			}
		}

		sorterNear = new ReplySorter(nearMeList);
		sorterFar = new ReplySorter(farList);
		
		sorterNear.sortByDate();
		sorterFar.sortByDate();
		
		ArrayList<Reply> newList = new ArrayList<Reply>();
		for (Reply r: farList) {
			newList.add(0, r);
		}
		for (Reply r: nearMeList) {
			newList.add(0, r);
		}
		

		return newList;
		
	}
}
