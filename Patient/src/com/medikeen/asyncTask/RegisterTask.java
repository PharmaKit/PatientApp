package com.medikeen.asyncTask;

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

import com.google.gson.Gson;
import com.medikeen.dataModel.RegisterModel;
import com.medikeen.patient.RegistrationActivity;
import com.medikeen.util.Constants;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class RegisterTask extends AsyncTask<RegisterModel, String, String> {

	Activity _login;
	ProgressDialog pd;
	String jsonResposnseString;

	RegisterModel objRegisterModel;

	public RegisterTask(RegistrationActivity registrationActivity) {
		_login = registrationActivity;
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
		Log.d("response json is ", "" + result);
		pd.dismiss();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = new ProgressDialog(_login);
		pd.setTitle("Registering");
		pd.setMessage("Please Wait..");
		pd.setCancelable(false);
		pd.show();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected String doInBackground(RegisterModel... params) {
		// TODO Auto-generated method stub

		objRegisterModel = params[0];

		Gson objGson = new Gson();
		String request = objGson.toJson(params[0]);

		HttpResponse response;

		// Creating Http client
		HttpClient httpclient = new DefaultHttpClient();

		// Building post parametrs key and value pair

		// ------Modify your server url in Constants in util package-------

		// URL url = new URL(Constants.SERVER_URL
		// + Constants.PATIENT_EXTENSION);
		//
		// HttpURLConnection urlConnection = (HttpURLConnection)
		// url.openConnection();
		//
		// try{
		// urlConnection.setDoOutput(true);
		// urlConnection.setChunkedStreamingMode(0);
		//
		// OutputStream out = new
		// BufferedOutputStream(urlConnection.getOutputStream());
		//
		// writeStream(out);
		// }

		HttpPost httpPost = new HttpPost(Constants.SERVER_URL
				+ Constants.PATIENT_EXTENSION);

		Log.d("Call to servlet", "Call servlet");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);

		nameValuePairs.add(new BasicNameValuePair("tag", "register"));
		nameValuePairs.add(new BasicNameValuePair("name", objRegisterModel
				.getFirstName()));
		nameValuePairs.add(new BasicNameValuePair("email", objRegisterModel
				.getEmailId()));
		nameValuePairs.add(new BasicNameValuePair("password", objRegisterModel
				.getPassword()));
		nameValuePairs.add(new BasicNameValuePair("address", objRegisterModel
				.getAddress()));
		nameValuePairs.add(new BasicNameValuePair("telephone", objRegisterModel
				.getContactNo()));

		// URl Encoding the POST parametrs

		try {

			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// making http request
		try {
			Log.d("RegisterTask", "Sending NameValuePair" + nameValuePairs);
			response = httpclient.execute(httpPost);
			Log.d("RegisterTask", "check response" + response.toString());
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