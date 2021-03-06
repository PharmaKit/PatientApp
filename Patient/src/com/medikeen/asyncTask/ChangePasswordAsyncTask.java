package com.medikeen.asyncTask;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.medikeen.dataModel.ChangePasswordModel;
import com.medikeen.dataModel.UserProfileModel;
import com.medikeen.patient.UserProfileActivity;
import com.medikeen.util.Constants;
import com.medikeen.util.SessionManager;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class ChangePasswordAsyncTask extends
		AsyncTask<ChangePasswordModel, String, String> {

	Activity userProfile;
	ProgressDialog pd;
	String jsonResponseString;
	ChangePasswordModel objChangePasswordModel;
	SharedPreferences sp;

	SessionManager sessionManager;

	public ChangePasswordAsyncTask(UserProfileActivity userProfile) {
		this.userProfile = userProfile;
		this.sessionManager = new SessionManager(userProfile);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pd = new ProgressDialog(userProfile);
		pd.setMessage("Please wait while we are updating your password");
		pd.setTitle("Updating Profile");
		pd.setCancelable(false);
		pd.show();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected String doInBackground(ChangePasswordModel... params) {

		objChangePasswordModel = params[0];

		JSONStringer userProfileJsonStringer = new JSONStringer();

		try {
			userProfileJsonStringer.object().key("email")
					.value(objChangePasswordModel.getEmail())
					.key("currentPassword")
					.value(objChangePasswordModel.getCurrentPassword())
					.key("newPassword")
					.value(objChangePasswordModel.getNewPassword()).endObject();
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		Gson gson = new Gson();
		String request = gson.toJson(params[0]);

		// String response = null;
		HttpResponse response;

		// Creating HTTP client
		HttpClient httpClient = new DefaultHttpClient();

		// Creating HttpPost
		// Modify your url
		HttpPost httpPost = new HttpPost(Constants.SERVER_URL
				+ "/android_api/userprofile/changepassword.php");
		httpPost.setHeader("Content-type", "application/json");

		try {
			StringEntity stringEntity = new StringEntity(
					userProfileJsonStringer.toString());

			httpPost.setEntity(stringEntity);
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

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		try {
			JSONObject userProfileJsonResponse = new JSONObject(
					jsonResponseString);

			String success = userProfileJsonResponse.getString("success");
			String error = userProfileJsonResponse.getString("error");
			String errorMessage = userProfileJsonResponse
					.getString("errorMessage");

			if (success.contains("true")) {

				Toast.makeText(userProfile, "Password changed",
						Toast.LENGTH_SHORT).show();

			} else {
				Toast.makeText(userProfile, errorMessage, Toast.LENGTH_SHORT)
						.show();
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pd.dismiss();

	}
}