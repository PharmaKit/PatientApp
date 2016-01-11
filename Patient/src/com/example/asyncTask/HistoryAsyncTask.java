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

import com.google.gson.Gson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;

@SuppressWarnings("deprecation")
public class HistoryAsyncTask extends AsyncTask<String[], String, String> {

	Activity _passwordReset;
	ProgressDialog pd;
	String jsonResposnseString;
	String[] historyInputParams;

	SharedPreferences sp;
	Editor edit;

	private static final String RESET_PASSWORD = "RESET_PASSWORD";

	public HistoryAsyncTask(Activity historyFragment) {
		// TODO Auto-generated constructor stub

		_passwordReset = historyFragment;

		sp = _passwordReset.getSharedPreferences(RESET_PASSWORD, _passwordReset.MODE_PRIVATE);
		edit = sp.edit();
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
	protected String doInBackground(String[]... params) {
		// TODO Auto-generated method stub

		historyInputParams = params[0];

		Log.e("TAG: ", "TAG: " + historyInputParams[0]);

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

		HttpPost httpPost = new HttpPost(
				"http://www.medikeen.com/android_api/prescription/prescriptions.php?id=" + historyInputParams[0]);
//		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
//		nameValuePair.add(new BasicNameValuePair("id", historyInputParams[0]));

		// URl Encoding the POST parametrs
		try {
//			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
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

		// Gson gson = new Gson();
		//
		// ResetPasswordResponse response = gson.fromJson(result,
		// ResetPasswordResponse.class);
		//
		// if (response.success == 1) {
		//
		// edit.putString("RESET_EMAIL", response.email);
		// edit.putString("VERIFICATION_CODE", objResetModel.getReset_code());
		// edit.commit();
		//
		// Intent resetPasswordIntent = new Intent(_passwordReset,
		// NewPasswordActivity.class);
		// _passwordReset.startActivity(resetPasswordIntent);
		// _passwordReset.finish();
		// } else {
		// Toast.makeText(_passwordReset, "Something went wrong",
		// Toast.LENGTH_SHORT).show();
		// }

		pd.dismiss();
	}
}