package com.example.asyncTask;

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

import com.example.dataModel.ResetPasswordModel;
import com.example.datamodels.serialized.ResetPasswordResponse;
import com.example.patient.ForgotPasswordActivity;
import com.example.patient.PasswordResetCodeVerificationActivity;
import com.example.util.Constants;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class ForgotPasswordTask extends AsyncTask<ResetPasswordModel, String, String> {

	Activity _forgotPassword;
	ProgressDialog pd;
	String jsonResposnseString;
	ResetPasswordModel objResetModel;

	SharedPreferences sp;
	Editor edit;

	private static final String RESET_PASSWORD = "RESET_PASSWORD";

	public ForgotPasswordTask(ForgotPasswordActivity frogotPasswordActivity) {
		// TODO Auto-generated constructor stub

		_forgotPassword = frogotPasswordActivity;

		sp = _forgotPassword.getSharedPreferences(RESET_PASSWORD, _forgotPassword.MODE_PRIVATE);
		edit = sp.edit();

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

		ResetPasswordResponse response = gson.fromJson(result, ResetPasswordResponse.class);

		if (response.success == 1) {

			edit.putString("RESET_EMAIL", response.email);
			edit.commit();

			Intent resetPasswordIntent = new Intent(_forgotPassword, PasswordResetCodeVerificationActivity.class);
			_forgotPassword.startActivity(resetPasswordIntent);
			_forgotPassword.finish();
		} else {
			Toast.makeText(_forgotPassword, "Something went wrong", Toast.LENGTH_SHORT).show();
		}

		pd.dismiss();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = new ProgressDialog(_forgotPassword);
		pd.setMessage("Please Wait..");
		pd.setCancelable(false);
		pd.show();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected String doInBackground(ResetPasswordModel... params) {
		// TODO Auto-generated method stub

		objResetModel = params[0];

		Log.e("TAG: ", "TAG: " + objResetModel.getTag());
		Log.e("EMAIL: ", "EMAIL: " + objResetModel.getEmail());

		Gson objGson = new Gson();
		String request = objGson.toJson(params[0]);

		HttpResponse response;

		// Creating Http client
		HttpClient httpclient = new DefaultHttpClient();

		// Building post parametrs key and value pair

		// ------Modify your server url in Constants in util package-------

		// HttpPost httpPost = new HttpPost(Constants.SERVER_URL + "/urc2");
		// List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
		// nameValuePair.add(new BasicNameValuePair("jsondata", request));

		HttpPost httpPost = new HttpPost(Constants.SERVER_URL + "/android_api/userprofile/updatepassword.php");
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("tag", objResetModel.getTag()));
		nameValuePair.add(new BasicNameValuePair("email", objResetModel.getEmail()));

		// URl Encoding the POST parametrs

		try {

			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (Exception e) {
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