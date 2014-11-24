package ca.ualberta.cs.funtime_runtime.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ca.ualberta.cs.funtime_runtime.R;
import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;
import ca.ualberta.cs.funtime_runtime.classes.Question;

/**
 * A class that is an extension of the basic Array Adapter but allows for more specific formatting
 * inside a list view.
 * @author bsmolley
 *
 */

public class QuestionListAdapter extends ArrayAdapter<Question> {
	Context ctx;
	Question question;
	List<Question> questionList;
	int res;
	LayoutInflater inflater;
	Account account;
	ArrayList<Question> favouritesList;
	ArrayList<Question> upvotedList;	
	boolean loggedIn;
	
	public QuestionListAdapter(Context context, int resource, ArrayList<Question> objects) {
		super(context, resource, objects);
		ctx = context;
		res = resource;
		questionList = objects;
		inflater = LayoutInflater.from(context);
	}

	/**
	 * When called returns the size of the question list
	 * @return the size of the question list
	 */
	@Override
	public int getCount() {
		return questionList.size();
	}

	/**
	 * Returns a question from a list of questions
	 * @return Returns the question at the given position
	 */
	@Override
	public Question getItem(int position) {
		return questionList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	// Adapted from http://www.survivingwithandroid.com/2013/02/android-listview-adapter-imageview.html -- Accessed Oct 23, 2014
	// Adapted from http://www.framentos.com/en/android-tutorial/2012/07/16/listview-in-android-using-custom-listadapter-and-viewcache/ 
	//    -- Accessed Oct 23, 2014
	/**
	 * Method that when called on a list view object formats the contents
	 * @return Returns the generated adapter view
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View theView = convertView;
		
		if (theView == null) {
			theView = (RelativeLayout) inflater.inflate(res, null);
		}
		
		question = questionList.get(position);
		
		TextView titleTextView = (TextView) theView.findViewById(R.id.adapterTitle);
		titleTextView.setText(question.getTitle());
		
		TextView ratingTextView = (TextView) theView.findViewById(R.id.adapterRating);
		String rating = Integer.toString(question.getRating());
		ratingTextView.setText(rating);
		
		TextView answerTextView = (TextView) theView.findViewById(R.id.adapterAnswers);
		String answerString = "Answers:";
		answerTextView.setText(answerString + " " + question.getAnswerCount());
		
		TextView authorTextView = (TextView) theView.findViewById(R.id.adapterAuthor);
		String authorString = "Author:";
		authorTextView.setText(authorString + " " + question.getUser());
		
		TextView questionDateTextView = (TextView) theView.findViewById(R.id.adapterDate);
		String questionDateString = question.getStringDate();
		questionDateTextView.setText("Posted: " + questionDateString);
		
		TextView questionLocationTextView = (TextView) theView.findViewById(R.id.adapterLocation);
		//Log.i("Location", question.getLocation());
		questionLocationTextView.setText(question.getLocation());
		//String questionLocationString = question.getStringLocation();
		//questionLocationTextView.setText("Location: " + questionLocationString);
		
		
		loggedIn = ApplicationState.isLoggedIn();
		if (loggedIn) {
			account = ApplicationState.getAccount();
			favouritesList = account.getFavouritesList();
			upvotedList = account.getUpvotedQuestions();
			
			
			ImageView star = (ImageView) theView.findViewById(R.id.adapterStar);
			if (favouritesList.contains(question)) {
				star.setImageResource(android.R.drawable.btn_star_big_on);
			} else {
				star.setImageResource(android.R.drawable.btn_star_big_off);
			}
			
			if (upvotedList.contains(question)) {
				ratingTextView.setTextColor(Color.parseColor("#e77619"));
			} else {
				ratingTextView.setTextColor(Color.parseColor("#000000"));
			}
		}




		

		

		
		return theView;
	}

}
