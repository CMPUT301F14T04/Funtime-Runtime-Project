package ca.ualberta.cs.funtime_runtime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
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

	public QuestionListAdapter(Context context, int resource, ArrayList<Question> objects) {
		super(context, resource, objects);
		ctx = context;
		res = resource;
		questionList = objects;
		inflater = LayoutInflater.from(context);
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

		//TextView titleTextView = (TextView) theView.findViewById(R.id.question_title_textview);
		TextView titleTextView = (TextView) theView.findViewById(R.id.adapterTitle);
		titleTextView.setText(question.getTitle());
		
		//TextView ratingTextView = (TextView) theView.findViewById(R.id.question_rating_textview);
		TextView ratingTextView = (TextView) theView.findViewById(R.id.adapterRating);
		//String ratingString = "Rating:";
		//ratingTextView.setText(ratingString + " " + question.getRating());
		ratingTextView.setText("10000");
		
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
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy  HH:mm");
		String questionDateString = dateFormat.format(question.getDate());
		questionDateTextView.setText("Date Posted: " + questionDateString);
		
		
		return theView;
	}

}
