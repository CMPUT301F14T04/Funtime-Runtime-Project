package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * An activity that allows the user to create a new account and log into it.
 *
 * @author Benjamin Holmwood
 *
 */
public class CreateAccountActivity extends CustomActivity {
	protected void onCreate(Bundle savedInstanceState) 	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);
		
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		Button createAccountButton = (Button) findViewById(R.id.createAccountButton);
		
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
                	ArrayList<Account> accountList = ApplicationState.getAccountList();
                    
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
                				Toast.makeText(ctx, "Account already exists. Please use a unique username.", Toast.LENGTH_SHORT).show();
                				break;
                			}
                		}
                	}
                	
                	if (!accountExists) {
                		// Create new account and add to AccountList
                		Account newAccount = new Account(createUsernameText.getText().toString());
                		ApplicationState.addAccount(newAccount);

                    	// Login the new account
                    	ApplicationState.setAccount(newAccount);
                    	
                    	// TODO update accountList on server
                    	
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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// switch case to choose an item from the menu
		//IntentSwitcher switcher = new IntentSwitcher(HomeActivity.this);
		
//-------------------------------------------
// Menu Items Switch Case
//-------------------------------------------
		
	switch (item.getItemId()) {
		case android.R.id.home:
			openMyHome();
			return true;
		case R.id.home_menu_item:
			openMyHome();
			return true;
		case R.id.login_menu_item:
			openLogin();
			return true;
		default:
			return true;
		}	
	}
	
//------------------------------------
//-------------------------------------------	

	
}
