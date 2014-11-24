package ca.ualberta.cs.funtime_runtime.classes;

import java.io.Serializable;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class Geolocation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8995370689907474524L;
	
	Context mContext;
	public Geolocation(Context mContext){
		this.mContext = mContext;
	}
	
	TextView textLat;
	TextView textLong;
	
	public void getLocation(){
		//textLat = (TextView)findViewById(R.id.);
		//textLong = (TextView)findViewbyId(R.id.);
		
		LocationManager lm = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
		LocationListener ll = new myLocationListener();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
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
		
	}	


