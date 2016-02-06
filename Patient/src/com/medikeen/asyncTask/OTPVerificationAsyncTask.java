package com.medikeen.asyncTask;

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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONStringer;

import com.google.gson.Gson;
import com.medikeen.dataModel.OTPModel;
import com.medikeen.datamodels.serialized.OTPResponse;
import com.medikeen.datamodels.serialized.ResetPasswordResponse;
import com.medikeen.patient.LandingActivity;
import com.medikeen.patient.Login;
import com.medikeen.patient.NewPasswordActivity;
import com.medikeen.patient.OtpActivity;
import com.medikeen.util.Constants;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class OTPVerificationAsyncTask extends
		AsyncTask<OTPModel, String, String> {

	Activity _passwordReset;
	ProgressDialog pd;
	String jsonResposnseString;
	OTPModel objOTPModel;

	private static final String RESET_PASSWORD = "RESET_PASSWORD";

	public OTPVerificationAsyncTask(OtpActivity otpAsyncTask) {
		// TODO Auto-generated constructor stub

		_passwordReset = otpAsyncTask;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		pd.dismiss();

		Gson gson = new Gson();

		OTPResponse response = gson.fromJson(result, OTPResponse.class);

		if (response.success == 1) {

			Intent resetPasswordIntent = new Intent(_passwordReset,
					Login.class);
			resetPasswordIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			_passwordReset.startActivity(resetPasswordIntent);
			_passwordReset.finish();
		} else {
			Toast.makeText(_passwordReset, "Something went wrong",
					Toast.LENGTH_SHORT).show();
		}

		pd.dismiss();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = new ProgressDialog(_passwordReset);
		pd.setMessage("Please Wait..");
		pd.setCancelable(false);
		pd.show();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected String doInBackground(OTPModel... params) {
		// TODO Auto-generated method stub

		objOTPModel = params[0];

		Gson objGson = new Gson();
		String request = objGson.toJson(params[0]);

		HttpResponse response;

		// Creating Http client
		HttpClient httpclient = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost(Constants.SERVER_URL
				+ "/android_api/patient.php");
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("tag", objOTPModel.getTag()));
		nameValuePair.add(new BasicNameValuePair("otp", objOTPModel.getOtp()));
		nameValuePair.add(new BasicNameValuePair("authorizationKey",
				objOTPModel.getAuthorizationKey()));

		// URl Encoding the POST parametrs

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (UnsupportedEncodingException e) {
			// writing error to Log
			e.printStackTrace();
		}

		// making http request
		try {
			System.out.println("Executing");
			response = httpclient.execute(httpPost);
			System.out.println("check response" + response.toString());
			HttpEntity entity = response.getEntity();
			jsonResposnseString = EntityUtils.toString(entity);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return jsonResposnseString;
	}
}