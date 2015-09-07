package com.example.patient;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asyncTask.ForgotPasswordTask;
import com.example.asyncTask.LoginTask;
import com.example.asyncTask.RegisterTask;
import com.example.dataModel.LoginModel;
import com.example.dataModel.RegisterModel;
import com.example.datamodels.serialized.LoginResponse;
import com.example.datamodels.serialized.RegistrationResponse;
import com.example.datamodels.serialized.UserResponse;
import com.example.util.SessionManager;
import com.google.gson.Gson;

public class Login extends Activity implements OnClickListener {

	EditText mUsername, mPassword,mFirstName,mLastName,mEmailId,mContactNo,mpass,muser,mEmailForgot;
	AutoCompleteTextView mAddress;
	TextView textViewCreateAccount,textViewforgot;
	Button mLogin, mRegister,mLoginRegister,mforgot;
	ImageButton buttonSearch;
	FrameLayout framelLayoutlogin,framelLayoutregister,framLayoutForgot;
	SharedPreferences sp;

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { 
			// do something on back.
			Intent startMain = new Intent(Intent.ACTION_MAIN); 
			startMain.addCategory(Intent.CATEGORY_HOME); 
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			startActivity(startMain); 
			return true; 
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		init();

		textViewCreateAccount.setOnClickListener(this);
		mLoginRegister.setOnClickListener(this);
		mLogin.setOnClickListener(this);
		mRegister.setOnClickListener(this);
		textViewforgot.setOnClickListener(this);
		mforgot.setOnClickListener(this);
	}

	private void init() {

		mUsername = (EditText)findViewById(R.id.editTextUsernameLogin);
		mPassword = (EditText)findViewById(R.id.editTextPasswordLogin);
		mLogin = (Button)findViewById(R.id.buttonLoginLogin);
		textViewCreateAccount = (TextView)findViewById(R.id.textViewCreate);
		framelLayoutlogin = (FrameLayout)findViewById(R.id.frameLayoutLogin);
		framelLayoutregister=(FrameLayout)findViewById(R.id.frameLayoutRegister); 
		mRegister = (Button)findViewById(R.id.buttonRegister);
		mLoginRegister = (Button)findViewById(R.id.buttonLogin);

		mFirstName = (EditText)findViewById(R.id.editTextFirstName);
		mLastName = (EditText)findViewById(R.id.editTextLastName);
		mEmailId = (EditText)findViewById(R.id.editTextEmailId);
		mContactNo = (EditText)findViewById(R.id.editTextContactNo);
		mAddress = (AutoCompleteTextView)findViewById(R.id.editTextAddress);
		mpass = (EditText)findViewById(R.id.editTextPassword);
		muser = (EditText)findViewById(R.id.editTextUserName);
		textViewforgot = (TextView)findViewById(R.id.textViewforgot);
		framLayoutForgot = (FrameLayout)findViewById(R.id.frameLayoutForgot);
		mforgot = (Button)findViewById(R.id.buttonForgot);
		mEmailForgot = (EditText)findViewById(R.id.editTextEmailForgot);
		
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
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.buttonLoginLogin) {
			final LoginModel loginModel = new LoginModel();
			String userName = mUsername.getText().toString();
			String password = mPassword.getText().toString();
			
			loginModel.setmPassword(password);
			loginModel.setmUsername(userName);
			
			if(userName.compareTo("") == 0 || password.compareTo("") == 0)
			{
				if(password.compareTo("") == 0)
				{
					mPassword.setError("Please Enter a password");
				}
				if(userName.compareTo("") == 0 )
				{
					mUsername.setError("Please Enter a username");					
				}
				return;
			}			
	        
	        AsyncTask<LoginModel,String,String> task = new LoginTask(Login.this).execute(loginModel);
	        
			
		} else if (id == R.id.textViewCreate) {
			framelLayoutlogin.setVisibility(View.GONE);
			framelLayoutregister.setVisibility(View.VISIBLE);
		} else if (id == R.id.buttonLogin) {
			framelLayoutregister.setVisibility(View.GONE);
			framelLayoutlogin.setVisibility(View.VISIBLE);
		} else if (id == R.id.buttonRegister) {
			if(mFirstName.getText().toString().equalsIgnoreCase("")){
				showdialog();
			}else if(mLastName.getText().toString().equalsIgnoreCase("")){
				showdialog();

			}else if(mEmailId.getText().toString().equalsIgnoreCase("")){
				showdialog();

			}else if(mpass.getText().toString().equalsIgnoreCase("")){
				showdialog();

			}else if(mContactNo.getText().toString().equalsIgnoreCase("")){
				showdialog();

			}else if(mAddress.getText().toString().equalsIgnoreCase("")){
				showdialog();

			}else if(muser.getText().toString().equalsIgnoreCase("")){
				showdialog();

			}else{

				RegisterModel objRegisterModel  =new RegisterModel();
				objRegisterModel.setFirstName(mFirstName.getText().toString());
				objRegisterModel.setLastName(mLastName.getText().toString());
				objRegisterModel.setEmailId(mEmailId.getText().toString());
				objRegisterModel.setPassword(mpass.getText().toString());
				objRegisterModel.setContactNo(mContactNo.getText().toString());
				objRegisterModel.setAddress(mAddress.getText().toString());
				objRegisterModel.setUserName(muser.getText().toString());

				final AsyncTask<RegisterModel, String, String> task = new RegisterTask(Login.this).execute(objRegisterModel);

				new Thread(new Runnable() {
					
		            @Override
		            public void run() {
		    			String result = "";
		    			try {
		    				result = task.get();
		    			} catch (InterruptedException e) {
		    				// TODO Auto-generated catch block
		    				e.printStackTrace();
		    			} catch (ExecutionException e) {
		    				// TODO Auto-generated catch block
		    				e.printStackTrace();
		    			}
		    			
		    			Gson gson = new Gson();

		    			RegistrationResponse response = gson.fromJson(result, RegistrationResponse.class);
		    			
		    			if (response.success == 1) {
		    				
		    				SessionManager session = new SessionManager(getApplicationContext());
		    				session.createLoginSession(true, response.user.person_id, mFirstName.getText().toString(), mLastName.getText().toString(), mEmailId.getText().toString(), mAddress.getText().toString(), mContactNo.getText().toString());
		    				
		    				Intent intent = new Intent(Login.this,LandingActivity.class);
		    				startActivity(intent);
		    			} else {
		    			}
		            }
		
		        }).start();
				
				/**
				 * Create Sesson manager
				 */
				

				//todo: password should not be sent from the cellphone itself. it should be sent forom the server.
				
				//sendOTP(mContactNo.getText().toString());
			}
		} else if (id == R.id.textViewforgot) {
			framelLayoutlogin.setVisibility(View.GONE);
			framLayoutForgot.setVisibility(View.VISIBLE);
		} else if (id == R.id.buttonForgot) {
			LoginModel objLoginModel = new LoginModel();
			objLoginModel.setmUsername(mEmailForgot.getText().toString());
			new ForgotPasswordTask(Login.this).execute(objLoginModel);
			//-------------manipulate these lines when password send Successfully-----------
			framLayoutForgot.setVisibility(View.GONE);
			framelLayoutlogin.setVisibility(View.VISIBLE);
		}
	}

	private void sendOTP(String stringPhone) {

		/**
		 * Send Message with OTP
		 */
		int randomInt=0;
		Random randomGenerator = new Random();
		for (int idx = 1; idx <= 10; ++idx){
			randomInt = randomGenerator.nextInt(9000000) + 1000000;
		}

		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";

		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
				new Intent(SENT), 0);

		PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
				new Intent(DELIVERED), 0);

		//---when the SMS has been sent---
		registerReceiver(new BroadcastReceiver(){
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode())
				{
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS sent", 
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(getBaseContext(), "Generic failure", 
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(getBaseContext(), "No service", 
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(getBaseContext(), "Null PDU", 
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast.makeText(getBaseContext(), "Radio off", 
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}, new IntentFilter(SENT));

		//---when the SMS has been delivered---
		registerReceiver(new BroadcastReceiver(){
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode())
				{
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS delivered", 
							Toast.LENGTH_SHORT).show();
					break;
				case Activity.RESULT_CANCELED:
					Toast.makeText(getBaseContext(), "SMS not delivered", 
							Toast.LENGTH_SHORT).show();
					break;                        
				}
			}
		}, new IntentFilter(DELIVERED));        

		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(stringPhone, null, "Your OTP is: "+randomInt, sentPI, deliveredPI);        

		createDialogForOTP(randomInt);
	}

	private void createDialogForOTP(final int OTP) {

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login.this);
		alertDialog.setTitle("One Time Password");
		alertDialog.setMessage("Enter OTP");

		final EditText input = new EditText(Login.this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);

		input.setLayoutParams(lp);

		alertDialog.setView(input);

		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				int OTPpassword = Integer.parseInt(input.getText().toString());

				Log.d("OTP", ""+OTPpassword);

				if (OTPpassword != -1) {

					Log.d("OTP1", ""+OTPpassword);

					if (OTP == OTPpassword) {
						Toast.makeText(getApplicationContext(),
								"OTP Matched", Toast.LENGTH_SHORT).show();
						Intent objLanding = new Intent(Login.this,LandingActivity.class);
						startActivity(objLanding);
					}

					else {
						Toast.makeText(getApplicationContext(),
								"Wrong OTP!", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		alertDialog.show();
	}

	private void showdialog() {

		AlertDialog alertDialog = new AlertDialog.Builder(
				Login.this).create();

		// Setting Dialog Title
		alertDialog.setTitle("Warning");

		// Setting Dialog Message
		alertDialog.setMessage("Please enter all the information");


		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Write your code here to execute after dialog closed
			}
		});
		alertDialog.show();
	}

}
