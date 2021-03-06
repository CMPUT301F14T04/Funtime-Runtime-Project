package ca.ualberta.cs.funtime_runtime.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;
import ca.ualberta.cs.funtime_runtime.R;
import ca.ualberta.cs.funtime_runtime.ShowPhoto;
import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.classes.Answer;
import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;
import ca.ualberta.cs.funtime_runtime.thread.UpdateQuestionThread;

/**
 * This class is a controller class that displays the various answer list items in proper format
 * It displays all the components associated with an Answer object view 
 * answer body, username of author, rating, and date are displayed in the list for each answer using this adapter
 * 
 * @author Pranjali Pokharel
 */
public class AnswerListAdapter extends ArrayAdapter<Answer> {

	Context ctx;
	ArrayList<Answer> answerList;
	Answer answer;
	int res;
	LayoutInflater inflater;
	Account account;
	ArrayList<Integer> upvotedList;
	UpdateQuestionThread updateThread;
	int has_photo_color = Color.parseColor("#228b22");
	boolean loggedIn;

	/**
	 * This function initializes the adapter. 
	 * The objects being displayed on screen are Array List Answer objects
	 * @param context
	 * @param resource
	 * @param objects
	 */
	public AnswerListAdapter(Context context, int resource, ArrayList<Answer> objects) {
		super(context, resource, objects);
		ctx = context;
		res = resource;
		answerList = objects;
		inflater = LayoutInflater.from(context);
	}

	/**
	 * This function gets the size of the answer list
	 * @return this.answerList.size()
	 */
	@Override
	public int getCount() {
		return answerList.size();
	}

	/**
	 * This function gets an answer item at the specified position
	 * @param position
	 * @return this.answerList.get(position)
	 */
	@Override
	public Answer getItem(int position) {
		return answerList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/**
	 * 
	 * This function gets and displays the view consisting of answer body, username of author, 
	 * rating, number of replies and date/time of answer
	 * An answer object of the list is displayed at the position corresponding to its position in the answer list
	 * It also contains a method for an OnClickListener to take the user to the reply page when an answer object is selected
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
		
		account = ApplicationState.getAccount();
		loggedIn = ApplicationState.isLoggedIn();
		if (loggedIn) {
			upvotedList = account.getUpvotedAnswers();
		}
		
		answer = answerList.get(position);
		
		TextView answerTextView = (TextView) theView.findViewById(R.id.answer_textview);
		answerTextView.setText(answer.getBody());
		
		final Button answerRating = (Button) theView.findViewById(R.id.answerAdapterRating);
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
		answerDateTextView.setText(answerDateString + " " + answer.getStringDate());
		
		TextView answerLocation = (TextView) theView.findViewById(R.id.answer_adapter_location);
		answerLocation.setText("Location: " + answer.getLocation());
		
		ImageButton answerViewPhotoButton = (ImageButton) theView.findViewById(R.id.answer_view_photo_button);
		if (loggedIn) {
			if (upvotedList.contains(answer.getId())) {
				answerRating.setTextColor(Color.parseColor("#e77619"));
			} else {
				answerRating.setTextColor(Color.parseColor("#000000"));
			}
		} 

		if(answer.getPhotoStatus()){
			answerViewPhotoButton.setColorFilter(has_photo_color);
		}
		
		
		answerRating.setTag(position);
		answerViewPhotoButton.setTag(position);
		
		/**
		 * This function is used to set an OnClickListner on an answer list object which takes the user to replies page if selected
		 * 
		 * @see android.view.View.OnClickListener(android.view.ViewGroup)
		 * adapted from http://syedasaraahmed.wordpress.com/2013/02/08/make-a-custom-listview-row-with-clickable-buttons-in-it-selectable-using-a-custom-cursoradapter/ 
		 * -- accessed Nov 5 2014
		 */
		answerRating.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				Question question = ApplicationState.getPassableQuestion();
				Answer answer = getItem((Integer) v.getTag());
				
				int rating = answer.getRating();
				
				Account account = ApplicationState.getAccount();
				if (ApplicationState.isLoggedIn()) {
					ArrayList<Integer> upvotedAnswers = account.getUpvotedAnswers();
					if (upvotedAnswers.contains(answer.getId())) {
						account.downvoteAnswer(answer, getContext());
						answer.downVote();
						rating--;
						answerRating.setTextColor(Color.parseColor("#000000"));
						
					} else {
						account.upvoteAnswer(answer, getContext());
						answer.upVote();
						rating++;
						answerRating.setTextColor(Color.parseColor("#e77619"));
					}
				} else {
					String msg = "Please Login to use";
					Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
				}

				String ratingText = Integer.toString(rating);
				answerRating.setText(ratingText);
				
			}
		});
		
		answerViewPhotoButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Answer answer = getItem((Integer) v.getTag());	
				Context context = getContext();
				if ( answer.getPhotoStatus() ) {
					Intent bootPhoto = new Intent(context, ShowPhoto.class);
					bootPhoto.putExtra("Photo", answer.getPhoto());
					bootPhoto.putExtra("hasAnswerPhoto?", answer.getPhotoStatus());

					context.startActivity(bootPhoto);
				} else {
					Toast.makeText(context, "No photo available", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
			
		return theView;
	}
	
}
