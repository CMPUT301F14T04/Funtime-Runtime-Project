package ca.ualberta.cs.funtime_runtime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QuestionListAdapter extends ArrayAdapter<Question> {

	Context ctx;
	List<Question> questionList;
	int res;
	LayoutInflater inflater;
	Account account;
	ArrayList<Question> favouritesList;
	ArrayList<Question> upvotedList;
	
	
	public QuestionListAdapter(Context context, int resource, ArrayList<Question> objects) {
		super(context, resource, objects);
		ctx = context;
		res = resource;
		questionList = objects;
		inflater = LayoutInflater.from(context);
		account = ApplicationState.getAccount();
		favouritesList = account.getFavouritesList();
		upvotedList = account.getUpvotedQuestions();
	}

	@Override
	public int getCount() {
		return this.questionList.size();
	}

	@Override
	public Question getItem(int position) {
		return this.questionList.get(position);
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
		
		Question question = questionList.get(position);
		
		//ImageView star = (ImageView) theView.findViewById(R.id.fav_star);
		ImageView star = (ImageView) theView.findViewById(R.id.adapterStar);
		if (favouritesList.contains(question)) {
			star.setImageResource(android.R.drawable.btn_star_big_on);
		} else {
			star.setImageResource(android.R.drawable.btn_star_big_off);
		}

		//TextView titleTextView = (TextView) theView.findViewById(R.id.question_title_textview);
		TextView titleTextView = (TextView) theView.findViewById(R.id.adapterTitle);
		titleTextView.setText(question.getTitle());
		
		//TextView ratingTextView = (TextView) theView.findViewById(R.id.question_rating_textview);
		TextView ratingTextView = (TextView) theView.findViewById(R.id.adapterRating);
		//String ratingString = "Rating:";
		//ratingTextView.setText(ratingString + " " + question.getRating());
		String rating = Integer.toString(question.getRating());
		ratingTextView.setText(rating);
		
		if (upvotedList.contains(question)) {
			ratingTextView.setTextColor(Color.parseColor("#e77619"));
		} else {
			ratingTextView.setTextColor(Color.parseColor("#000000"));
		}
		
		//TextView answerTextView = (TextView) theView.findViewById(R.id.question_answer_textview);
		TextView answerTextView = (TextView) theView.findViewById(R.id.adapterAnswers);
		String answerString = "Answers:";
		answerTextView.setText(answerString + " " + question.getAnswerCount());
		
		//TextView authorTextView = (TextView) theView.findViewById(R.id.question_author_textview);
		TextView authorTextView = (TextView) theView.findViewById(R.id.adapterAuthor);
		String authorString = "Author:";
		authorTextView.setText(authorString + " " + question.getUser());
		
		//TextView questionDateTextView = (TextView) theView.findViewById(R.id.question_date_textview);
		TextView questionDateTextView = (TextView) theView.findViewById(R.id.adapterDate);
		String questionDateString = question.getDate();
		questionDateTextView.setText("Posted: " + questionDateString);
		
		
		return theView;
	}

}
