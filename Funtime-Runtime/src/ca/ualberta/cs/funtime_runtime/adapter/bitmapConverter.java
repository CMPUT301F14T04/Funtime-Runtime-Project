package ca.ualberta.cs.funtime_runtime.adapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class bitmapConverter implements Serializable{
	private static final long serialVersionUID = -5228835919664263905L;
	private Bitmap bitmap; 
	public bitmapConverter(Bitmap b) {
	    bitmap = b; 
	}
	
	// Converts the Bitmap into a byte array for serialization
	private void writeObject(ObjectOutputStream out) throws IOException {
	    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
	    boolean success = bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteStream);
	    byte bitmapBytes[] = byteStream.toByteArray();
	    if (success){
	    	out.write(bitmapBytes, 64, bitmapBytes.length);
	    }
	}
	
	// Deserializes a byte array representing the Bitmap and decodes it
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
	    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
	    int b;
	    while((b = in.read()) != -1)
	        byteStream.write(b);
	    byte bitmapBytes[] = byteStream.toByteArray();
	    bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 64, bitmapBytes.length);
	}
	
	public Bitmap getBitmap() {
	    return this.bitmap;
	}

}
