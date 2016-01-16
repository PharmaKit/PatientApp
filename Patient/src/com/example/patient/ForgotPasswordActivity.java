package com.example.patient;

import com.example.asyncTask.ForgotPasswordTask;
import com.example.dataModel.ResetPasswordModel;
import com.pharmakit.patient.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordActivity extends Activity {

	EditText mEmailForgot;
	Button mforgot;
	String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_frogot_password);

		mEmailForgot = (EditText) findViewById(R.id.editTextEmailForgot);
		mforgot = (Button) findViewById(R.id.buttonForgot);

		mforgot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (isValidEmail(mEmailForgot.getText().toString())) {

					ResetPasswordModel objResetModel = new ResetPasswordModel();
					objResetModel.setTag("generatecode");
					objResetModel.setEmail(mEmailForgot.getText().toString());

					new ForgotPasswordTask(ForgotPasswordActivity.this).execute(objResetModel);

				} else {
					Toast.makeText(ForgotPasswordActivity.this, "Please enter a valid email address",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public final static boolean isValidEmail(CharSequence target) {
		return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.frogot_password, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
