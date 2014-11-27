package ca.ualberta.cs.funtime_runtime;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import ca.ualberta.cs.funtime_runtime.classes.Answer;
import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;
//http://nebula.wsimg.com/fd3fb0dbd502dd558787bbb2af6f3861?AccessKeyId=F951FDF451EBE1B34A66&disposition=0&alloworigin=1
public class ShowPhoto extends Activity {
	ImageView questionPhoto;
	boolean hasPhoto = false;
	boolean hasAnswerPhoto = false;
	byte[] photoByteArray;
	Bitmap photoBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_photo);
		questionPhoto = (ImageView) findViewById(R.id.photoView);
		//Intent intent = getIntent();
		hasPhoto = getIntent().getExtras().getBoolean("hasPhoto?");
		hasAnswerPhoto = getIntent().getExtras().getBoolean("hasAnswerPhoto?");
		
		//
//		Answer answer = ApplicationState.getPassableAnswer();
//		photoByteArray = answer.getPhoto();
//		photoBitmap = BitmapFactory.decodeByteArray(photoByteArray, 0, photoByteArray.length);
//		questionPhoto.setImageBitmap(photoBitmap);
		
		if (hasPhoto == true){
			photoByteArray = getIntent().getExtras().getByteArray("Photo");
			photoBitmap = BitmapFactory.decodeByteArray(photoByteArray, 0, photoByteArray.length);
			questionPhoto.setImageBitmap(photoBitmap);
		} else if (hasAnswerPhoto == true){
			//String answerId = getIntent().getExtras().getString("answerId");
			Answer answer = ApplicationState.getPassableAnswer();
			photoByteArray = answer.getPhoto();
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
