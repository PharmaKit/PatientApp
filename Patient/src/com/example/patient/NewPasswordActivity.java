package com.example.patient;

import com.example.asyncTask.NewPasswordAsyncTask;
import com.example.asyncTask.PasswordResetCodeVerificationAsyncTask;
import com.example.dataModel.ResetPasswordModel;
import com.pharmakit.patient.R;
import com.pharmakit.patient.R.id;
import com.pharmakit.patient.R.layout;
import com.pharmakit.patient.R.menu;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewPasswordActivity extends Activity {

	EditText mNewPassword, mConfirmPassword;
	Button mSubmitButton;

	SharedPreferences sp;
	Editor edit;
	String emailStr, verificationCodeStr;

	private static final String RESET_PASSWORD = "RESET_PASSWORD";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_password);

		sp = getSharedPreferences(RESET_PASSWORD, MODE_PRIVATE);
		edit = sp.edit();

		emailStr = sp.getString("RESET_EMAIL", null);
		verificationCodeStr = sp.getString("VERIFICATION_CODE", null);

		mNewPassword = (EditText) findViewById(R.id.editTextNewPassword);
		mConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);

		mSubmitButton = (Button) findViewById(R.id.buttonSubmitPassword);

		mSubmitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String newPasswordString = mNewPassword.getText().toString();
				String confirmPasswordString = mConfirmPassword.getText().toString();

				if (newPasswordString.equals("") || newPasswordString.isEmpty() || confirmPasswordString.equals("")
						|| confirmPasswordString.isEmpty()) {
					Toast.makeText(NewPasswordActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
				} else {
					if (newPasswordString.compareTo(confirmPasswordString) == 0) {

						ResetPasswordModel objResetModel = new ResetPasswordModel();
						objResetModel.setTag("resetpassword");
						objResetModel.setEmail(emailStr);
						objResetModel.setReset_code(verificationCodeStr);

						new NewPasswordAsyncTask(NewPasswordActivity.this).execute(objResetModel);

					} else {
						mConfirmPassword.setError("Passwords do not match");
					}
				}

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_password, menu);
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
