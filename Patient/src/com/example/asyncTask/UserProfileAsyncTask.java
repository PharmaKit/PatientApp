package com.example.asyncTask;

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

import com.example.dataModel.UserProfileModel;
import com.example.patient.UserProfileActivity;
import com.example.util.Constants;
import com.example.util.SessionManager;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class UserProfileAsyncTask extends
		AsyncTask<UserProfileModel, String, String> {

	Activity userProfile;
	ProgressDialog pd;
	String jsonResponseString;
	UserProfileModel objUserProfileModel;
	SharedPreferences sp;

	SessionManager sessionManager;

	public UserProfileAsyncTask(UserProfileActivity userProfile) {
		this.userProfile = userProfile;
		this.sessionManager = new SessionManager(userProfile);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pd = new ProgressDialog(userProfile);
		pd.setMessage("Please wait while we are updating your profile");
		pd.setTitle("Updating Profile");
		pd.setCancelable(false);
		pd.show();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected String doInBackground(UserProfileModel... params) {

		objUserProfileModel = params[0];

		JSONStringer userProfileJsonStringer = new JSONStringer();

		try {
			userProfileJsonStringer.object().key("personId")
					.value(objUserProfileModel.getPersonId()).key("email")
					.value(objUserProfileModel.getEmail()).key("firstName")
					.value(objUserProfileModel.getFirstName()).key("lastName")
					.value(objUserProfileModel.getLastName()).key("phone")
					.value(objUserProfileModel.getPhone()).key("address")
					.value(objUserProfileModel.getAddress()).endObject();
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
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
				+ "/android_api/userprofile/updateprofile.php");
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
			String userJson = userProfileJsonResponse.getString("user");
			String addressJson = userProfileJsonResponse.getString("address");
			String errorMessage = userProfileJsonResponse
					.getString("errorMessage");

			if (success.contains("true")) {

				// USER
				JSONObject userJsonResponse = new JSONObject(userJson);

				String personId = userJsonResponse.getString("person_id");
				String name = userJsonResponse.getString("name");
				String email = userJsonResponse.getString("email");
				String patient_id = userJsonResponse.getString("patient_id");
				String phone = userJsonResponse.getString("telephone");

				String[] nameSplit = name.split(" ");
				String firstName = nameSplit[0];
				String lastName = nameSplit[1];

				JSONObject addressJsonResponse = new JSONObject(addressJson);

				String address = addressJsonResponse.getString("1");

				sessionManager.createLoginSession(true,
						Long.parseLong(personId), firstName, lastName, email,
						address, phone);

				Toast.makeText(userProfile, "User profile updated",
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
