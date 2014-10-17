package ca.ualberta.cs.funtime_runtime.test;

import ca.ualberta.cs.funtime_runtime.Account;
import junit.framework.TestCase;

public class AccountTest extends TestCase {
	
	public AccountTest(){
		
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
