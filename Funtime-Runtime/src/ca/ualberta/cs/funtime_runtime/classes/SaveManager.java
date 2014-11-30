package ca.ualberta.cs.funtime_runtime.classes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;


/**
 * Handles local saving and loading.
 * 
 * @author Benjamin Holmwood
 *
 */
public class SaveManager {

	/**
	 * Saves an object to the device.
	 * 
	 * @param FILENAME		The name of the file to save to.
	 * @param object		The object to save.
	 * @param ctx			The context of the current activity.
	 */
	public void save(String FILENAME, Object object, Context ctx) {
		FileOutputStream fos;
		ObjectOutputStream os;
		try {
			fos = ctx.openFileOutput(FILENAME, 0);
			os = new ObjectOutputStream(fos);
			os.writeObject(object);
			os.close();
			//Toast.makeText(ctx, "saved", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * Loads an object from the device.
	 * 
	 * @param FILENAME		The name of the file to save to.
	 * @param object		The object to save.
	 * @param ctx			The context of the current activity.
	 */
	public Object load(String FILENAME, Context ctx) throws ClassNotFoundException {
		
		ObjectInputStream ois = null;
		Object loadedObject = null;
		try {
			FileInputStream fis = ctx.openFileInput(FILENAME);
			ois = new ObjectInputStream(fis);
			loadedObject = ois.readObject();
		    try {
		        if(ois != null) {
		        	//Toast.makeText(ctx, "loaded", Toast.LENGTH_LONG).show();
		            ois.close();
		        }
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		} 
		catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		return loadedObject;
	}
}
