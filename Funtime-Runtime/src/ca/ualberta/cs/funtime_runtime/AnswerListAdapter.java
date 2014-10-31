package ca.ualberta.cs.funtime_runtime;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AnswerListAdapter extends ArrayAdapter<Answer> {

	Context ctx;
	List<Answer> answerList;
	int res;
	LayoutInflater inflater;

	public AnswerListAdapter(Context context, int resource, List<Answer> objects) {
		super(context, resource, objects);
		ctx = context;
		res = resource;
		answerList = objects;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return this.answerList.size();
	}

	@Override
	public Answer getItem(int position) {
		return this.answerList.get(position);
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
		
		Answer answer = answerList.get(position);
		
		TextView answerTextView = (TextView) theView.findViewById(R.id.answer_textview);
		answerTextView.setText(answer.getBody());
		
		TextView answerRatingTextView = (TextView) theView.findViewById(R.id.answer_rating_textview);
		//String answerRatingString = answerRatingTextView.getText().toString();
		String answerRatingString = "Rating:";
		answerRatingTextView.setText(answerRatingString + " " + answer.getRating());
		
		TextView answerAuthorTextView = (TextView) theView.findViewById(R.id.answer_author_textview);
		//String answerAuthorString = answerAuthorTextView.getText().toString();
		String answerAuthorString = "Author:";
		answerAuthorTextView.setText(answerAuthorString + " " + answer.getUser());
		
		TextView answerReplyTextView = (TextView) theView.findViewById(R.id.answer_reply_textview);
		//String answerReplyString = answerReplyTextView.getText().toString();
		String answerReplyString = "Replies:";
		answerReplyTextView.setText(answerReplyString + " " + answer.getReplyCount());
		
		TextView answerDateTextView = (TextView) theView.findViewById(R.id.answer_date);
		//String answerDateString = answerDateTextView.getText().toString();
		String answerDateString = "Date Posted:";
		answerDateTextView.setText(answerDateString + " " + answer.getDate());
		
		
		return theView;
	}

}
