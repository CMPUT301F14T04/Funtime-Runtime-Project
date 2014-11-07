package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ReplyListAdapter extends ArrayAdapter<Reply> {

	Context ctx;
	ArrayList<Reply> replyList;
	Reply reply;
	int res;
	LayoutInflater inflater;

	public ReplyListAdapter(Context context, int resource, ArrayList<Reply> objects) {
		super(context, resource, objects);
		ctx = context;
		res = resource;
		replyList = objects;
		inflater = LayoutInflater.from(context);
	}
	
	// Adapted from http://www.survivingwithandroid.com/2013/02/android-listview-adapter-imageview.html -- Accessed Oct 23, 2014
	// Adapted from http://www.framentos.com/en/android-tutorial/2012/07/16/listview-in-android-using-custom-listadapter-and-viewcache/ 
	//    -- Accessed Oct 23, 2014
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
