package com.example.patient;

import com.example.datamodels.User;
import com.example.util.SessionManager;
import com.pharmakit.patient.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class UserProfileActivity extends Activity {

	EditText mFirstName, mLastName, mEmailId, mContactNo, mPassword, mConfirmPassword;
	AutoCompleteTextView mAddress;
	Button mSave;

	SessionManager sessionManager;
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);

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
			}
		});

	}

	private void init() {

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
		getMenuInflater().inflate(R.menu.user_profile, menu);
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
