package ca.ualberta.cs.funtime_runtime.test;

import junit.framework.TestCase;
import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.classes.Answer;
import ca.ualberta.cs.funtime_runtime.elastic.ESAccountManager;


public class AddAccountThreadTest extends TestCase
{

	public void testAddAccountThread(){
		Account account = new Account("Test Account Username");
		
		ESAccountManager manager = new ESAccountManager();
		
		manager.addAccount(account);
		
	}
}
