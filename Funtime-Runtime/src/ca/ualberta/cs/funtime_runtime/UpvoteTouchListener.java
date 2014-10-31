package ca.ualberta.cs.funtime_runtime;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

// adapted from http://stackoverflow.com/questions/4468380/android-textview-change-text-color-on-click - Accessed Oct 30 2014

@SuppressLint("ResourceAsColor")
public class UpvoteTouchListener implements OnTouchListener {     
    public boolean onTouch(View view, MotionEvent motionEvent) {
    	switch(motionEvent.getAction()){            
    		case MotionEvent.ACTION_DOWN:
    			if ( ((TextView)view).getCurrentTextColor() == R.color.black) {
    				((TextView)view).setTextColor(R.color.blue); //blue
    			} else {
                	((TextView)view).setTextColor(R.color.black); //black
    			}
    			break;          
            case MotionEvent.ACTION_CANCEL:       
            	break;
            case MotionEvent.ACTION_UP:
            	break;
    	} 
        return false;   
    }
}