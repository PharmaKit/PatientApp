package com.medikeen.asyncTask;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.medikeen.dataModel.LoginModel;
import com.medikeen.datamodels.serialized.LoginResponse;
import com.medikeen.datamodels.serialized.UserResponse;
import com.medikeen.patient.LandingActivity;
import com.medikeen.patient.Login;
import com.medikeen.util.Constants;
import com.medikeen.util.SessionManager;
import com.google.gson.Gson;
import com.medikeen.patient.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class LoginTask extends AsyncTask<LoginModel, String, String> {

	Activity mLogin;
	ProgressDialog pd;
	String jsonResponseString;
	LoginModel objLoginModel;
	SharedPreferences sp;

	public LoginTask(Login login) {
		mLogin = login;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		Log.e("response json is ", "" + result);
		pd.dismiss();

		Gson gson = new Gson();

		LoginResponse response = gson.fromJson(result, LoginResponse.class);

		if (response.success == 1) {

			SessionManager sessionManager = new SessionManager(
					mLogin.getApplicationContext());
			UserResponse user = response.user;

			sessionManager.createLoginSession(true, user.person_id, user.name,
					"", user.email, response.address.house_no, user.telephone);

			// Show a welcome message to the user through toast
			Toast.makeText(mLogin, "Welcome " + user.name, Toast.LENGTH_SHORT)
					.show();

			Intent intent = new Intent(mLogin, LandingActivity.class);
			mLogin.startActivity(intent);
			mLogin.finish();
		} else {

			EditText mPassword = (EditText) mLogin
					.findViewById(R.id.editTextPasswordLogin);
			mPassword.setError(response.error_msg);
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pd = new ProgressDialog(mLogin);
		pd.setMessage("Please wait while we are signing you in..");
		pd.setTitle("Signing in");
		pd.setCancelable(false);
		pd.show();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected String doInBackground(LoginModel... params) {

		objLoginModel = params[0];

		Log.d("username", "" + objLoginModel.getmUsername());
		Log.d("password", "" + objLoginModel.getmPassword());

		Gson gson = new Gson();
		String request = gson.toJson(params[0]);
		Log.d("gson is", "" + request);

		HttpResponse response;

		// Creating HTTP client
		HttpClient httpClient = new DefaultHttpClient();

		// Creating HttpPost
		// Modify your url
		HttpPost httpPost = new HttpPost(Constants.SERVER_URL
				+ Constants.PATIENT_EXTENSION);

		Log.d("Call to servlet", "Call  servlet");

		// Building post parameters, key and value pair
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("tag", "login"));
		nameValuePairs.add(new BasicNameValuePair("email", objLoginModel
				.getmUsername()));
		nameValuePairs.add(new BasicNameValuePair("password", objLoginModel
				.getmPassword()));

		Log.d("cac", "NameValuePair" + nameValuePairs);
		// Url Encoding the POST parameters
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (UnsupportedEncodingException e) {
			// writing error to Log
			e.printStackTrace();
		}
		try {

			System.out.println("Executing...");
			response = httpClient.execute(httpPost);

			HttpEntity entity = response.getEntity();
			jsonResponseString = EntityUtils.toString(entity);
			Log.e("Http Response:", jsonResponseString);

		} catch (ClientProtocolException e) {
			// writing exception to log
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonResponseString;
	}

}