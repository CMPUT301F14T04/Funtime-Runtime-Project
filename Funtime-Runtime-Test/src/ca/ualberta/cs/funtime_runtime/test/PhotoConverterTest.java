package ca.ualberta.cs.funtime_runtime.test;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.AuthorQuestionActivity;
import ca.ualberta.cs.funtime_runtime.classes.Answer;
import ca.ualberta.cs.funtime_runtime.classes.Question;

public class PhotoConverterTest extends ActivityInstrumentationTestCase2<AuthorQuestionActivity> {


	public PhotoConverterTest() {
		super(AuthorQuestionActivity.class);
	}
	
	public void testPhotoCompression() {
		
	}
	
	public void testhasPhoto() {
		byte[] photo = new byte[10];
		Question question = new Question("Test Question Title", "Test question body", "TestAuthorUsername");
		question.setId(1111111111);
		question.setPhoto(photo);
		assertTrue(question.getPhotoStatus());
		}
	
//	public void testAnswerhasPhoto() {
//		byte[] photo = new byte[10];
//		Answer answer = new Answer(null, "Test question body", "TestAuthorUsername");
//
//		assertTrue(answer.getPhotoStatus());
//		}
//	
	
}
