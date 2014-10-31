package ca.ualberta.cs.funtime_runtime;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ReplyListAdapter extends ArrayAdapter<Reply> {

	Context ctx;
	List<Reply> replyList;
	int res;
	LayoutInflater inflater;

	public ReplyListAdapter(Context context, int resource, List<Reply> objects) {
		super(context, resource, objects);
		ctx = context;
		res = resource;
		replyList = objects;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return this.replyList.size();
	}

	@Override
	public Reply getItem(int position) {
		return this.replyList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
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
		
		Reply reply = replyList.get(position);
		
		TextView replyTextView = (TextView) theView.findViewById(R.id.reply_textview);
		replyTextView.setText(reply.getBody());
		
		TextView replyAuthorTextView = (TextView) theView.findViewById(R.id.reply_author_textview);
		//String replyAuthorString = replyAuthorTextView.getText().toString();
		String replyAuthorString = "Author:";
		replyAuthorTextView.setText(replyAuthorString + " " +reply.getUser());
		
		
		return theView;
	}

}
