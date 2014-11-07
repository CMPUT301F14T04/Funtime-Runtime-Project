package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * This class is a controller class that displays the various reply list items in proper format
 * It displays all the components associated with a Reply object view
 * Reply, username of author and date are displayed in the list for each reply using this adapter
 * 
 * @author Pranjali Pokharel
 */
public class ReplyListAdapter extends ArrayAdapter<Reply> {

	Context ctx;
	ArrayList<Reply> replyList;
	Reply reply;
	int res;
	LayoutInflater inflater;

	/**
	 * This function initializes the adapter. 
	 * The objects being displayed on screen are Array List Reply objects
	 * @param context
	 * @param resource
	 * @param objects
	 */
	public ReplyListAdapter(Context context, int resource, ArrayList<Reply> objects) {
		super(context, resource, objects);
		ctx = context;
		res = resource;
		replyList = objects;
		inflater = LayoutInflater.from(context);
	}
	
	/**
	 * This function gets and displays the view consisting of reply body, username of author and date/time of reply
	 * A reply object of the list is displayed at the position corresponding to its position in the reply list
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 * 
	 * @return theView
	 * 
	 * Adapted from http://www.survivingwithandroid.com/2013/02/android-listview-adapter-imageview.html -- Accessed Oct 23, 2014
	 * Adapted from http://www.framentos.com/en/android-tutorial/2012/07/16/listview-in-android-using-custom-listadapter-and-viewcache/ 
	 * -- Accessed Oct 23, 2014
	 */

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View theView = convertView;
		
		if (theView == null) {
			theView = (RelativeLayout) inflater.inflate(res, null);
		}
		
		reply = replyList.get(position);
		
		TextView replyTextView = (TextView) theView.findViewById(R.id.reply_textview);
		replyTextView.setText(reply.getBody());
		
		TextView replyAuthorTextView = (TextView) theView.findViewById(R.id.reply_author_textview);
		String replyAuthorString = "Author:";
		replyAuthorTextView.setText(replyAuthorString + " " +reply.getUser());

		TextView replyDateView = (TextView) theView.findViewById(R.id.reply_author_date);
		replyDateView.setText("Posted: " + reply.getDate());
		
		return theView;
	}

}
