package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AnswerListAdapter extends ArrayAdapter<Answer> {

	Context ctx;
	ArrayList<Answer> answerList;
	Answer answer;
	int res;
	LayoutInflater inflater;
	Account account;
	ArrayList<Answer> upvotedList;

	public AnswerListAdapter(Context context, int resource, ArrayList<Answer> objects) {
		super(context, resource, objects);
		ctx = context;
		res = resource;
		answerList = objects;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return answerList.size();
	}

	@Override
	public Answer getItem(int position) {
		return answerList.get(position);
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
		
		account = ApplicationState.getAccount();
		upvotedList = account.getUpvotedAnswers();
		
		answer = answerList.get(position);
		
		TextView answerTextView = (TextView) theView.findViewById(R.id.answer_textview);
		answerTextView.setText(answer.getBody());
		
		Button answerRating = (Button) theView.findViewById(R.id.answerAdapterRating);
		String ratingText = Integer.toString(answer.getRating());
		answerRating.setText(ratingText);
		
		TextView answerAuthorTextView = (TextView) theView.findViewById(R.id.answer_author_textview);
		String answerAuthorString = "Author:";
		answerAuthorTextView.setText(answerAuthorString + " " + answer.getUser());
		
		TextView answerReplyTextView = (TextView) theView.findViewById(R.id.answer_reply_textview);
		String answerReplyString = "Replies:";
		answerReplyTextView.setText(answerReplyString + " " + answer.getReplyCount());
		
		TextView answerDateTextView = (TextView) theView.findViewById(R.id.answer_date);
		String answerDateString = "Posted:";
		answerDateTextView.setText(answerDateString + " " + answer.getDate());
		
		ImageButton answerViewPhotoButton = (ImageButton) theView.findViewById(R.id.answer_view_photo_button);
		
		if (upvotedList.contains(answer)) {
			answerRating.setTextColor(Color.parseColor("#e77619"));
		} else {
			answerRating.setTextColor(Color.parseColor("#000000"));
		}
		
		
		// adapted from http://syedasaraahmed.wordpress.com/2013/02/08/make-a-custom-listview-row-with-clickable-buttons-in-it-selectable-using-a-custom-cursoradapter/ - accessed Nov 5 2014
		answerRating.setTag(position);
		answerRating.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Answer answer = getItem((Integer) v.getTag());
				Account account = ApplicationState.getAccount();
				ArrayList<Answer> upvotedAnswers = account.getUpvotedAnswers();
				if (upvotedAnswers.contains(answer)) {
					//answer.downVote();
					account.downvoteAnswer(answer);
				} else {
					//answer.upVote();
					account.upvoteAnswer(answer);
				}
				notifyDataSetChanged();
			}
		});
		
		return theView;
	}

}
