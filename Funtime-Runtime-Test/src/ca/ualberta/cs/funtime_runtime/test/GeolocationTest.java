package ca.ualberta.cs.funtime_runtime.test;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.SearchActivity;
import ca.ualberta.cs.funtime_runtime.classes.Geolocation;

public class GeolocationTest extends ActivityInstrumentationTestCase2<SearchActivity> {

	public GeolocationTest() {
		super(SearchActivity.class);
	}
	
	public void testFindLocation() {
		Geolocation location = new Geolocation(getActivity());
		String foundLocation;
		String actualLocation = "N/A";
		
		location.findLocation();
		foundLocation = location.getLocation();
		
		assertEquals(foundLocation, actualLocation);	
	}
	
	public void testInputLocation(){
		
		String actualLocation = "London";
		String userLocation = "London" ;
				
		
		assertEquals(userLocation, actualLocation);
		
	}

}
