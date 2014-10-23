package ca.ualberta.cs.funtime_runtime;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QuestionListAdapter extends ArrayAdapter<Question> {

	Context context;
	List<Question> questionList;
	private int resource;
	LayoutInflater inflater;

	public QuestionListAdapter(Context context, int resource, List<Question> objects) {
		super(context, resource, objects);
		this.context = context;
		this.resource = resource;
		this.questionList = objects;
		this.inflater = LayoutInflater.from(context);
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
			theView = (RelativeLayout) inflater.inflate(resource, null);
		}
		
		Question question = questionList.get(position);

		TextView titleTextView = (TextView) theView.findViewById(R.id.question_title_textview);
		titleTextView.setText(question.getTitle());
		
		TextView ratingTextView = (TextView) theView.findViewById(R.id.question_rating_textview);
		ratingTextView.setText("Rating: " + question.getRating());
		
		TextView answersTextView = (TextView) theView.findViewById(R.id.question_answer_textview);
		answersTextView.setText("Answers: " + question.getAnswerCount());
		
		//ImageButton star = (ImageButton) theView.findViewById(R.id.question_star);
		
		return theView;
	}

}
