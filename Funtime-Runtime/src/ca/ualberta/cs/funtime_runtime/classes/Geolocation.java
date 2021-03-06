package ca.ualberta.cs.funtime_runtime.classes;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
/**
 * Geolocation is used for locating the current postion of the user
 * @author bsmolley
 *
 */
public class Geolocation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8995370689907474524L;
	public String addressString;	
	
	Context mContext;
	String context = Context.LOCATION_SERVICE;
	
	public Geolocation(Context mContext){
		this.mContext = mContext;
	}

			
	/**
	 * Locations listener is used to find the last known location of the user 
	 * via the phones built in GPS.
	 */
	private final LocationListener ll = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras){
		}
		
		@Override
		public void onProviderEnabled(String provider) {
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			findLocation();
		}
		
		@Override
		public void onLocationChanged(Location location) {
			findLocation();
		}
	};
	
	/**
	 * Using the last known location listener this method converts the GPS coordinates into
	 * strings for City, Postal Code, Country, etc. Used for grabbing useful location
	 * information to display.
	 */
	public void findLocation(){
		
		LocationManager lm = (LocationManager)mContext.getSystemService(context);
		String provider = LocationManager.GPS_PROVIDER;
		Location location = lm.getLastKnownLocation(provider);
		
		if(location != null){
			
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			
			Geocoder gc = new Geocoder(mContext, Locale.getDefault());
			try {
				List <Address> addresses = gc.getFromLocation(latitude, longitude, 1);
				if(addresses.size() > 0){
					Address address = addresses.get(0);
					StringBuilder sb = new StringBuilder();
					sb.append(address.getLocality() + ", ");
					sb.append(address.getCountryName());
				
					addressString = sb.toString();
					Log.i("Address on create", addressString);
				}
			} catch(Exception e){	
			}
		} else {
			addressString = "N/A";
		}
	}
	
	/**
	 * Return the string made after calling findLocation()
	 * @return addressString  
	 */
	public String getLocation() {
		return addressString;
		
	}
}

