package ca.ualberta.cs.funtime_runtime;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccountActivity extends Activity
{
	protected void onCreate(Bundle savedInstanceState) 	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);
		
		Button createAccountButton = (Button) findViewById(R.id.createAccountButton);
		
		final Context ctx = this;
		
        OnClickListener createAccountListener = new OnClickListener() {
            public void onClick(View v) {
                EditText createUsernameText = (EditText) findViewById(R.id.createUsernameField);
                String newUsername = createUsernameText.getText().toString();
                if (newUsername != "") {
                	Account newAccount = new Account(createUsernameText.getText().toString());
                	AccountList.add(newAccount);
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
	
	
}
