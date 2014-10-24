package ca.ualberta.cs.funtime_runtime;

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

	public QuestionListAdapter(Context context, int resource, List<Question> objects) {
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
		
		ImageView star = (ImageView) theView.findViewById(R.id.fav_star);

		TextView titleTextView = (TextView) theView.findViewById(R.id.question_title_textview);
		titleTextView.setText(question.getTitle());
		
		TextView ratingTextView = (TextView) theView.findViewById(R.id.question_rating_textview);
		String ratingString = ratingTextView.getText().toString();
		ratingTextView.setText(ratingString + " " + question.getRating());
		
		TextView answerTextView = (TextView) theView.findViewById(R.id.question_answer_textview);
		String answerString = answerTextView.getText().toString();
		answerTextView.setText(answerString + " " + question.getAnswerCount());
		
		TextView authorTextView = (TextView) theView.findViewById(R.id.question_author_textview);
		String authorString = authorTextView.getText().toString();
		authorTextView.setText(authorString + " " + question.getUser());
		
		
		
		return theView;
	}

}
