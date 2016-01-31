package com.medikeen.patient;

import com.medikeen.asyncTask.ChangePasswordAsyncTask;
import com.medikeen.asyncTask.UserProfileAsyncTask;
import com.medikeen.dataModel.ChangePasswordModel;
import com.medikeen.dataModel.UserProfileModel;
import com.medikeen.datamodels.User;
import com.medikeen.util.SessionManager;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserProfileActivity extends Activity {

	EditText mFirstName, mLastName, mEmailId, mContactNo, mPassword, mConfirmPassword;
	AutoCompleteTextView mAddress;
	Button mSave, mChangePassword;

	SessionManager sessionManager;
	User user;
	Dialog changePasswordDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);

		getActionBar().hide();

		init();

		mFirstName.setText(user.getFirstName());
		mLastName.setText(user.getLastName());
		mEmailId.setText(user.getEmailAddress());
		mContactNo.setText(user.getPhoneNo());
		mAddress.setText(user.getAddress());

		mSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sessionManager.createLoginSession(true, user.getPersonId(), mFirstName.getText().toString(),
						mLastName.getText().toString(), mEmailId.getText().toString(), mAddress.getText().toString(),
						mContactNo.getText().toString());

				UserProfileModel userProfile = new UserProfileModel("" + user.getPersonId(),
						mEmailId.getText().toString(), mFirstName.getText().toString(), mLastName.getText().toString(),
						mContactNo.getText().toString(), mAddress.getText().toString());

				new UserProfileAsyncTask(UserProfileActivity.this).execute(userProfile);
			}
		});

		mChangePassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				changePasswordDialog();
			}
		});
	}

	private void changePasswordDialog() {
		changePasswordDialog = new Dialog(UserProfileActivity.this);
		changePasswordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		changePasswordDialog.setContentView(R.layout.change_password_dialog);
		changePasswordDialog.setCancelable(false);
		changePasswordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

		final EditText currentPassword = (EditText) changePasswordDialog.findViewById(R.id.current_password_edit);
		final EditText newPassword = (EditText) changePasswordDialog.findViewById(R.id.new_password_edit);
		final EditText confirmNewPassword = (EditText) changePasswordDialog
				.findViewById(R.id.confirm_new_password_edit);

		Button mChangePasswordOk = (Button) changePasswordDialog.findViewById(R.id.change_password_ok);
		Button mChangePasswordCancel = (Button) changePasswordDialog.findViewById(R.id.change_password_cancel);

		mChangePasswordCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				changePasswordDialog.dismiss();
			}
		});

		// LOGIN BUTTON
		mChangePasswordOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String currentPasswordStr = currentPassword.getText().toString();
				String newPasswordStr = newPassword.getText().toString();
				String confirmNewPasswordStr = confirmNewPassword.getText().toString();

				if (currentPasswordStr.isEmpty() || newPasswordStr.isEmpty() || confirmNewPasswordStr.isEmpty()) {
					Toast.makeText(UserProfileActivity.this, "Please enter all the field", Toast.LENGTH_SHORT).show();
				} else {
					if (newPasswordStr.compareTo(confirmNewPasswordStr) == 0) {

						ChangePasswordModel changePasswordModel = new ChangePasswordModel(
								sessionManager.getUserDetails().getEmailAddress(), currentPasswordStr, newPasswordStr);

						new ChangePasswordAsyncTask(UserProfileActivity.this).execute(changePasswordModel);

					} else {
						confirmNewPassword.setError("Passwords do not match");
					}
				}

				changePasswordDialog.dismiss();
			}
		});

		changePasswordDialog.show();
	}

	private void init() {

		Typeface font = Typeface.createFromAsset(getAssets(), "SofiaProLight.otf");

		sessionManager = new SessionManager(UserProfileActivity.this);
		user = sessionManager.getUserDetails();

		mSave = (Button) findViewById(R.id.profile_buttonsave);
		mFirstName = (EditText) findViewById(R.id.profile_editTextFirstName);
		mLastName = (EditText) findViewById(R.id.profile_editTextLastName);
		mEmailId = (EditText) findViewById(R.id.profile_editTextEmailId);
		mContactNo = (EditText) findViewById(R.id.profile_editTextContactNo);
		mAddress = (AutoCompleteTextView) findViewById(R.id.profile_editTextAddress);
		mPassword = (EditText) findViewById(R.id.profile_editTextPassword);
		mConfirmPassword = (EditText) findViewById(R.id.profile_editTextConfirmPassword);
		mChangePassword = (Button) findViewById(R.id.profile_changePassword);

		mAddress.setTypeface(font);

		mAddress.setAdapter(new PlacesAutoCompleteAdapter1(this, R.layout.list_item));
		mAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

				String strLocations = (String) adapterView.getItemAtPosition(i);
				mAddress.setText(strLocations);

				String s = strLocations;
				if (s.contains(",")) {
					String parts[] = s.split("\\,");
					System.out.print(parts[0]);
					String s1 = parts[0];
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.user_profile, menu);
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
