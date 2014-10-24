package ca.ualberta.cs.funtime_runtime;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		Button loginButton = (Button) findViewById(R.id.loginButton);
		
		final Context ctx = this;
		
        OnClickListener loginListener = new OnClickListener() {
            public void onClick(View v) {
                EditText usernameText = (EditText) findViewById(R.id.loginField);
                String username = usernameText.getText().toString();
                if (username != "") {
                	// Get application state and account list
            	    
                	ApplicationState appState = (ApplicationState) getApplicationContext();
                	// AccountList accountList = appState.getAccountList();
                	
                	AccountList accountList = ApplicationState.getAccountList();
                	// Check if username matches existing account
                	boolean accountExists = false;
                	Account account = null;
                	for (int i = 0; i < accountList.size(); ++i) {
                		account = accountList.get(i);
                		if (account.getName() == username) {
                			accountExists = true;
                			break;
                		}
                	}
                	if (accountExists) {
                		appState.setAccount(account);
                	} else {
                		Toast.makeText(ctx, "Account does not exist. Please login to an existing account or create a a new one.", Toast.LENGTH_LONG).show();
                	}
                	usernameText.setText("");
                	
                	// TODO return to page before login page	

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

	public void createAccount(View v)	{
		Intent createNewAccount = new Intent(LoginActivity.this, CreateAccountActivity.class);
		startActivity(createNewAccount);
	}
}
