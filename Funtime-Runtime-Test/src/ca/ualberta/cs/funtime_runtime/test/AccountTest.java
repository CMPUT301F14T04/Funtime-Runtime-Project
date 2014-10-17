package ca.ualberta.cs.funtime_runtime.test;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.Account;
import ca.ualberta.cs.funtime_runtime.CreateAccountActivity;

public class AccountTest extends ActivityInstrumentationTestCase2<CreateAccountActivity> {
	
	public AccountTest(){
		super(CreateAccountActivity.class);
	}
	
	public void testCreateAccount(){
		Account account = new Account();
		assertNotNull(account);
	}
	
	public void testGetAccountName(){
		Account account = new Account();
		String name1 = "Kappa";
		String name2;
		account.setName(name1);
		name2 = account.getName();
		assertEquals(name2, name1);
		
	}
		
	public void testGetMyQuestions(){
		
	}
	
	public void testGetMyAnswers(){
		
	}
	
	public void testGetFavorites(){
		
	}
	
	public void testGetReadingList(){
		
	}
	
	public void testGetHistory(){
		
	}
	
}
