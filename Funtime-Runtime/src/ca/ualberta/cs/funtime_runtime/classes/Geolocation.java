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

public class Geolocation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8995370689907474524L;
	public String addressString;
	
	Context mContext;
	public Geolocation(Context mContext){
		this.mContext = mContext;
	}
	String context = Context.LOCATION_SERVICE;

//	LocationManager lm = (LocationManager)mContext.getSystemService(context);
//	String provider = LocationManager.GPS_PROVIDER;
//	Location location = lm.getLastKnownLocation(provider);
	
	//getLocation(location);
	//lm.requestLocationUpdates(provider, 0, 0, ll);
	
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
	
	public void findLocation(){
		
		String latLong;
		//TextView myLocation;
		//myLocation = (TextView)findViewById(R.id.myLocation);
		//String addressString = "no address found";
		
		LocationManager lm = (LocationManager)mContext.getSystemService(context);
		String provider = LocationManager.GPS_PROVIDER;
		Location location = lm.getLastKnownLocation(provider);
		
		if(location != null){
			double lat = location.getLatitude();
			double lon = location.getLongitude();
			latLong = "Lat:"+lat+"\nLong:"+lon;
			
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
					//}
					addressString = sb.toString();
					Log.i("Address on create", addressString);
				}
			} catch(Exception e){	
			}
		} else {
			latLong = "No Location Found!";
			addressString = "N/A";
		}
	}
	
	public String getLocation() {
		return addressString;
		
	}
}

	/*TextView textLat;
	TextView textLong;
	TextView myAddress;
	
	public void getLocation(){
		//textLat = (TextView)findViewById(R.id.);
		//textLong = (TextView)findViewbyId(R.id.);
		//myLocation = (TextView)findViewById(R.id.location);
		//myAddress = (TextView)findViewById(R.id.address);
		
		LocationManager lm = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
		LocationListener ll = new myLocationListener();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
		

		Geocoder geocoder = new Geocoder(this, Locale.CANADA);
		try{
			List<Address>addresses = geocoder.getFromLocation(latitude, longitude);
			if(addresses != null){
				Address fetchedAddress = addresses.get(0);
				StringBuilder strAddress = new StringBuilder();
				
				for(int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++){
					strAddress.append(fetchedAddress.getAddressLine(i)).append("\n");
				}
				myAddress.setText(strAddress.toString());
				}
			else
				myAddress.setText("No Location Found!");
			}
		catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
			
		}
		class myLocationListener implements LocationListener{

			@Override
			public void onLocationChanged(Location location) {
				if(location != null){
					double plong = location.getLongitude();
					double plat = location.getLatitude();
					
					textLat.setText(Double.toString(plat));
					textLong.setText(Double.toString(plong));
				}
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
		}
	}*/


