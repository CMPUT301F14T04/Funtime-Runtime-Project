package ca.ualberta.cs.funtime_runtime;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;

public class SaveManager {

	public void save(String FILENAME, Object object, Context ctx) {
		{
			/*	Save an object in the file FILENAME.
			 */
			
			FileOutputStream fos;
			ObjectOutputStream os;

			try {
			  fos = ctx.openFileOutput(FILENAME, 0);
			  os = new ObjectOutputStream(fos);
			  os.writeObject(object);
			  os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public Object load(String FILENAME, Context ctx) throws ClassNotFoundException {
		/*	Load the TODOList from the file FILENAME
		 */
		
		ObjectInputStream ois = null;
		Object loadedObject = null;
		
		try {
			FileInputStream fis = ctx.openFileInput(FILENAME);
			ois = new ObjectInputStream(fis);
			loadedObject = ois.readObject();
		    
		    try {
		        if(ois != null) {
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