package com.example.asyncTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapters.HistoryAdapter;
import com.example.dataModel.HistoryModel;
import com.example.datamodels.serialized.HistoryResponse;
import com.example.patient.HistoryFragment;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

@SuppressWarnings("deprecation")
public class HistoryAsyncTask extends AsyncTask<String[], String, String> {

	Activity historyFragment;
	ProgressDialog pd;
	String jsonResposnseString;
	String[] historyInputParams;

	SharedPreferences sp;
	Editor edit;

	// TEST
	ListView mHistoryListView;
	JSONArray prescriptionsArray;
	ArrayList<HistoryModel> historyList;
	HistoryAdapter adapter;

	private static final String RESET_PASSWORD = "RESET_PASSWORD";

	public HistoryAsyncTask(Activity historyFragment, ListView mHistoryListView) {
		// TODO Auto-generated constructor stub

		this.mHistoryListView = mHistoryListView;
		historyList = new ArrayList<>();
		this.historyFragment = historyFragment;

		sp = historyFragment.getSharedPreferences(RESET_PASSWORD, historyFragment.MODE_PRIVATE);
		edit = sp.edit();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = new ProgressDialog(historyFragment);
		pd.setMessage("Please wait while we are loading your history");
		pd.setTitle("History");
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
				// List<NameValuePair> nameValuePair = new
				// ArrayList<NameValuePair>();
				// nameValuePair.add(new BasicNameValuePair("id",
				// historyInputParams[0]));

		// URl Encoding the POST parametrs
		try {
			// httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
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

		Gson gson = new Gson();

		HistoryResponse response = gson.fromJson(result, HistoryResponse.class);

		if (response.success == 1) {

			Log.e("TAG", "HISTORY RESPONSE: " + response.success);

			try {
				JSONObject historyJsonObject = new JSONObject(result);

				prescriptionsArray = historyJsonObject.getJSONArray("prescriptions");

				for (int i = 0; i < prescriptionsArray.length(); i++) {
					JSONObject prescriptionsObject = prescriptionsArray.getJSONObject(i);

					String resource_id = prescriptionsObject.getString("resource_id");
					String resource_type = prescriptionsObject.getString("resource_type");
					String person_id = prescriptionsObject.getString("person_id");
					String recepient_name = prescriptionsObject.getString("recepient_name");
					String recepient_address = prescriptionsObject.getString("recepient_address");
					String recepient_number = prescriptionsObject.getString("recepient_number");
					String offer_type = prescriptionsObject.getString("offer_type");
					String is_image_uploaded = prescriptionsObject.getString("is_image_uploaded");
					String is_email_sent = prescriptionsObject.getString("is_email_sent");
					String created_date = prescriptionsObject.getString("created_date");
					String updated_date = prescriptionsObject.getString("updated_date");

					historyList.add(new HistoryModel(person_id, resource_id, resource_type, recepient_name,
							recepient_address, recepient_number, offer_type, is_image_uploaded, is_email_sent,
							created_date, updated_date));
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (response.success == 0) {
			Log.e("TAG", "HISTORY RESPONSE: " + response.error);
		}

		adapter = new HistoryAdapter(historyFragment, historyList);
		mHistoryListView.setAdapter(adapter);

		pd.dismiss();
	}
}