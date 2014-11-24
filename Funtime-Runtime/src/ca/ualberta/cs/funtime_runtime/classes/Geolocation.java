package ca.ualberta.cs.funtime_runtime.classes;

import java.io.IOException;
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
import android.widget.TextView;
import android.widget.Toast;

public class Geolocation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8995370689907474524L;
	
	Context mContext;
	public Geolocation(Context mContext){
		this.mContext = mContext;
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
}


