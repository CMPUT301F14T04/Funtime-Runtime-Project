package ca.ualberta.cs.funtime_runtime.test;

import junit.framework.TestCase;
import ca.ualberta.cs.funtime_runtime.classes.Question;
import ca.ualberta.cs.funtime_runtime.elastic.ESQuestionManager;

public class AddQuestionThreadTest extends TestCase
{

	public void testAddQuestionThread()
	{
		Question question = new Question("Test Question Title", "Test question body", "TestAuthorUsername");
		question.setId(3456);
		
		ESQuestionManager manager = new ESQuestionManager();
		manager.addQuestion(question);
	}
}
