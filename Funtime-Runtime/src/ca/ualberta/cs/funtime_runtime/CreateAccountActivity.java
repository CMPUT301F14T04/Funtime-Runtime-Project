package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;
import java.util.Random;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ca.ualberta.cs.funtime_runtime.classes.Account;
import ca.ualberta.cs.funtime_runtime.classes.ApplicationState;

/**
 * An activity that allows the user to create a new account and log into it.
 *
 * @author Benjamin Holmwood
 *
 */
public class CreateAccountActivity extends CustomActivity {
	
	ArrayList<Account> accountList;
	Random generator;
	private static final int RANDOM_NUMBER_CAP = 100000000;
	public int id;
	
	protected void onCreate(Bundle savedInstanceState) 	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);
		
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		Button createAccountButton = (Button) findViewById(R.id.createAccountButton);
		generator = new Random();
		
		final Context ctx = this;
		
        OnClickListener createAccountListener = new OnClickListener() {
            public void onClick(View v) {
                /* The on-click listener for the create account button.
                 * @see android.view.View.OnClickListener#onClick(android.view.View)
                 */
            	
            	EditText createUsernameText = (EditText) findViewById(R.id.loginField);
                String newUsername = createUsernameText.getText().toString();
                if (newUsername.length() > 0) {
                	// Get account list
                	//ArrayList<Account> accountList = ApplicationState.getAccountList();
                	//accountList = new ArrayList<Account>();
                	//SearchThread searchThread = new SearchThread("*");
                    //searchThread.start();
                	accountList = ApplicationState.getAccountList();
                    
                	// Check if account already exists
                	String username = createUsernameText.getText().toString();
                	Account account;

                	boolean accountExists = false;
                	if (accountList.size() > 0) {
                		for (int i = 0; i < accountList.size(); ++i) {
                			account = accountList.get(i);
                            String check = account.getName();
                			if (check.equals(username)) {
                				accountExists = true;
                				Toast.makeText(ctx, "Account already exists. Please use a unique username.", Toast.LENGTH_LONG).show();
                				break;
                			}
                		}
                	}
                	
                	if (!accountExists) {
                		// Create new account and add to AccountList
                		Account newAccount = new Account(createUsernameText.getText().toString());
                		id = generator.nextInt(RANDOM_NUMBER_CAP);
                		newAccount.setId(id);
                		ApplicationState.addAccount(newAccount);
                		ApplicationState.addServerAccount(newAccount);
                		//AddThread addThread = new AddThread(newAccount);
                		//addThread.start();

                    	// Login the new account
                    	ApplicationState.setAccount(newAccount, ctx); 
                    	
                    	// Return to previous activity
                    	finish();
                    	
                	}
                	createUsernameText.setText("");
                	
                }
                else {
           		    Toast.makeText(ctx, "Please enter a username to create an account.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        createAccountButton.setOnClickListener(createAccountListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_account, menu);
		return true;
	}
	
//	class SearchThread extends Thread {
//		private String search;
//		
//		public SearchThread(String s){		
//			search = s;
//		}
//		
//		@Override
//		public void run() {
//			accountList.clear();		
//			accountList.addAll(manager.searchAccounts(search, null));				
//		}
//	}
//	
//	 class AddThread extends Thread {
//		private Account account;
//
//		public AddThread(Account account) {
//			this.account = account;
//		}
//
//		@Override
//		public void run() {
//			manager.addAccount(account);
//
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//	 }

	
}
