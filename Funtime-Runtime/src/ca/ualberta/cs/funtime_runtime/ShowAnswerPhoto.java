package ca.ualberta.cs.funtime_runtime;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.widget.ImageView;

public class ShowAnswerPhoto extends Activity {
	ImageView answerPhoto;
	boolean hasPhoto;
	byte[] photoByteArray;
	Bitmap photoBitmap;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_answer_photo);
		answerPhoto = (ImageView) findViewById(R.id.answerPhotoView);
		hasPhoto = getIntent().getExtras().getBoolean("hasAnwserPhoto?");
		if(hasPhoto == true){
			photoByteArray = getIntent().getExtras().getByteArray("answerPhoto");
			photoBitmap = BitmapFactory.decodeByteArray(photoByteArray, 0, photoByteArray.length);
			answerPhoto.setImageBitmap(photoBitmap);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_answer_photo, menu);
		return true;
	}

}
