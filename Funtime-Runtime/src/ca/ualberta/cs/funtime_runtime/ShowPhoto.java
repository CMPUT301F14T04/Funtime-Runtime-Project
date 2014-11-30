package ca.ualberta.cs.funtime_runtime;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
//http://nebula.wsimg.com/fd3fb0dbd502dd558787bbb2af6f3861?AccessKeyId=F951FDF451EBE1B34A66&disposition=0&alloworigin=1
/**
 * Class that is used to display the Photos of Questions or Answers
 * @author bsmolley
 *
 */
public class ShowPhoto extends Activity {
	ImageView questionPhoto;
	boolean hasPhoto = false;
	boolean hasAnswerPhoto = false;
	byte[] photoByteArray;
	Bitmap photoBitmap;

	/**Display the photo, depending on what object is opened 
	 * (Question/Answer)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_photo);
		questionPhoto = (ImageView) findViewById(R.id.photoView);
		hasPhoto = getIntent().getExtras().getBoolean("hasPhoto?");
		hasAnswerPhoto = getIntent().getExtras().getBoolean("hasAnswerPhoto?");
	
		if (hasPhoto == true){
			photoByteArray = getIntent().getExtras().getByteArray("Photo");
			photoBitmap = BitmapFactory.decodeByteArray(photoByteArray, 0, photoByteArray.length);
			questionPhoto.setImageBitmap(photoBitmap);
		} else if (hasAnswerPhoto == true){
			photoByteArray = getIntent().getExtras().getByteArray("Photo");
			photoBitmap = BitmapFactory.decodeByteArray(photoByteArray, 0, photoByteArray.length);
			questionPhoto.setImageBitmap(photoBitmap);
		}
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getMenuInflater().inflate(R.menu.show_photo, menu);
		return true;
	}

}
