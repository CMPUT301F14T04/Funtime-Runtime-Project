package ca.ualberta.cs.funtime_runtime.test;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.funtime_runtime.Account;
import ca.ualberta.cs.funtime_runtime.ApplicationState;
import ca.ualberta.cs.funtime_runtime.HomeActivity;


public class ApplicationStateTest extends ActivityInstrumentationTestCase2<HomeActivity> {
	
	public ApplicationStateTest() {
		super(HomeActivity.class);
	}
	
	public void testLogin() {
		assertFalse(ApplicationState.isLoggedIn());
		Account newAccount = new Account("TestUser");
		ApplicationState.setAccount(newAccount);
		Account foundAccount = ApplicationState.getAccount();
		assertEquals(newAccount, foundAccount);
		assertTrue(ApplicationState.isLoggedIn());
	}
	
	public void testAccountList() {
		Account account0 = new Account("TestUser0");
		ApplicationState.addAccount(account0);
		ArrayList<Account> accounts = ApplicationState.getAccountList();
		assertTrue(accounts.contains(account0));
	}
	
}
