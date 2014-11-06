package ca.ualberta.cs.funtime_runtime;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * An activity that allows the user to log in to an account.
 * 
 * @author Benjamin Holmwood
 *
 */
public class LoginActivity extends CustomActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		Button loginButton = (Button) findViewById(R.id.loginButton);
		
		final Context ctx = this;
		
		
        OnClickListener loginListener = new OnClickListener() {
            /* The on-click listener for the login button.
             * @see android.view.View.OnClickListener#onClick(android.view.View)
             */
            public void onClick(View v) {
            	// Retrieve the username to be logged in from the edit text field
                EditText usernameText = (EditText) findViewById(R.id.loginField);
                String username = usernameText.getText().toString();
                //Make sure the username field is not empty
                if (username != "") {
                	// Get account list
                	ArrayList<Account> accountList = ApplicationState.getAccountList();
                	
                	// Check if username matches existing account
                	boolean accountExists = false;
                	Account account = null;
                	for (int i = 0; i < accountList.size(); ++i) {
                		account = accountList.get(i);
                		if (account.getName().equals(username)) {
                			accountExists = true;
                			break;
                		}
                	}
                	if (accountExists) {
                		// Login the user and return to previous screen
                		ApplicationState.setAccount(account);
                		finish();
                	} else {
                		// Notify the user that there is no existing account with that username.
                		Toast.makeText(ctx, "Account does not exist. Please login to an existing account or create a a new one.", Toast.LENGTH_LONG).show();
                	}
                	usernameText.setText(""); 

                }
                else {
           		    Toast.makeText(ctx, "Please enter a username to login.", Toast.LENGTH_SHORT).show();
                }
            }
        };
        
        loginButton.setOnClickListener(loginListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
			default:
				return true;
			}	
		}
	
//------------------------------------
//-------------------------------------------	
			
	/**
	 * The onClick function for create account button.
	 * Takes the user to the create account page. 
	 */
	public void createAccount(View v)	{
		Intent createNewAccount = new Intent(LoginActivity.this, CreateAccountActivity.class);
		startActivity(createNewAccount);
		finish();
	}
}