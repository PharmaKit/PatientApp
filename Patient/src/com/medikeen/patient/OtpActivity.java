package com.medikeen.patient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class OtpActivity extends Activity {

	public static EditText mOtp;
	Button mVerifyOtp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_otp);
		getActionBar().hide();

		mOtp = (EditText) findViewById(R.id.editTextotp);
		mVerifyOtp = (Button) findViewById(R.id.buttonOtp);

		mVerifyOtp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(OtpActivity.this, LandingActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);

				// String otpStr = mOtp.getText().toString();
				//
				// if (otpStr.isEmpty()) {
				//
				// } else {
				// OTPModel otpmodel = new OTPModel();
				// otpmodel.setTag("verifyOTP");
				// otpmodel.setOtp("" + otpStr);
				// otpmodel.setAuthorizationKey("123");
				//
				// new
				// OTPVerificationAsyncTask(OtpActivity.this).execute(otpmodel);
				// }

			}
		});
	}

	public void receivedSms(String message) {
		try {
			mOtp.setText("" + message);
		} catch (Exception e) {
			Log.e("OTP RECEIVE ERROR", "OTP RECEIVE ERROR" + e);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.otp, menu);
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
