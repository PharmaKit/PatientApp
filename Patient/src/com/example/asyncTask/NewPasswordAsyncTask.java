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
import com.example.patient.Login;
import com.example.patient.NewPasswordActivity;
import com.example.patient.PasswordResetCodeVerificationActivity;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class NewPasswordAsyncTask extends AsyncTask<ResetPasswordModel, String, String> {

	Activity _newPassword;
	ProgressDialog pd;
	String jsonResposnseString;
	ResetPasswordModel objResetModel;

	public NewPasswordAsyncTask(NewPasswordActivity NewPasswordAsyncTask) {
		// TODO Auto-generated constructor stub

		_newPassword = NewPasswordAsyncTask;
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
			Intent resetPasswordIntent = new Intent(_newPassword, Login.class);
			resetPasswordIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			_newPassword.startActivity(resetPasswordIntent);
			_newPassword.finish();
		} else {
			Toast.makeText(_newPassword, "Something went wrong", Toast.LENGTH_SHORT).show();
		}

		pd.dismiss();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = new ProgressDialog(_newPassword);
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
		Log.e("RESET CODE: ", "RESET CODE: " + objResetModel.getReset_code());
		Log.e("NEW PASSWORD: ", "NEW PASSWORD: " + objResetModel.getNew_password());

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

		HttpPost httpPost = new HttpPost("http://pharmakit.co/android_api/userprofile/updatepassword.php");
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("tag", objResetModel.getTag()));
		nameValuePair.add(new BasicNameValuePair("email", objResetModel.getEmail()));
		nameValuePair.add(new BasicNameValuePair("reset_code", objResetModel.getReset_code()));
		nameValuePair.add(new BasicNameValuePair("new_password", objResetModel.getNew_password()));

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