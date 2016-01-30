package com.medikeen.asyncTask;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

import com.medikeen.dataModel.ImageUploadArgs;
import com.medikeen.dataModel.SaveImageDetailsModel;
import com.medikeen.datamodels.serialized.SaveImageDetailsResponse;
import com.medikeen.interfaces.IImageUploadedEventListener;
import com.medikeen.patient.AddressPrescription;
import com.medikeen.patient.LandingActivity;
import com.medikeen.util.Constants;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class SaveImageDetailsTask extends
		AsyncTask<SaveImageDetailsModel, String, String> {

	Activity mActivity;
	ProgressDialog pd;
	String jsonResponseString;
	SaveImageDetailsModel saveImageDetailsModel;
	SharedPreferences sp;
	IImageUploadedEventListener uploadedEventListener;

	File file;

	public SaveImageDetailsTask(AddressPrescription activity, File file, IImageUploadedEventListener uploadedEventListener) {
		mActivity = activity;
		this.file = file;
		this.uploadedEventListener = uploadedEventListener;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		Log.d("response json is ", "" + result);

		Gson gson = new Gson();
		SaveImageDetailsResponse imageDetails = gson.fromJson(result,
				SaveImageDetailsResponse.class);

		if (imageDetails.success == 1) {
			Log.d("filename", imageDetails.resourceid + ".jpg");
		} else {
			Toast.makeText(mActivity,
					"Image upload failed. Message: " + imageDetails.error_msg,
					Toast.LENGTH_LONG).show();
			return;
		}

		ImageUploadArgs args = new ImageUploadArgs();
		args.file = file;
		args.filename = imageDetails.resourceid + ".jpg";
		args.mimeType = "image/jpeg";
		try {
			args.url = new URL(Constants.SERVER_URL
					+ "/android_api/prescription/upload.php");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		AsyncTask<ImageUploadArgs, String, String> task = new ImageUploadTask(
				mActivity);
		((ImageUploadTask) task).setOnImageUploadedEventListener(uploadedEventListener);
		task.execute(args);
		pd.dismiss();

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pd = new ProgressDialog(mActivity);
		pd.setMessage("Uploading Prescription details..");
		pd.setTitle("Please wait");
		pd.setCancelable(false);
		pd.show();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected String doInBackground(SaveImageDetailsModel... params) {

		saveImageDetailsModel = params[0];

		Gson gson = new Gson();
		String request = gson.toJson(params[0]);
		Log.d("gson is", "" + request);

		HttpResponse response;

		// Creating HTTP client
		HttpClient httpClient = new DefaultHttpClient();

		// Creating HttpPost
		// Modify your url
		HttpPost httpPost = new HttpPost(Constants.SERVER_URL
				+ Constants.IMAGE_EXTENSION);

		Log.d("Call to servlet", "Call servlet");

		// Building post parameters, key and value pair
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("tag", "save"));
		nameValuePairs.add(new BasicNameValuePair("resourcetype",
				saveImageDetailsModel.getResourceType()));
		nameValuePairs.add(new BasicNameValuePair("personid", Long
				.toString(saveImageDetailsModel.getPersonId())));
		nameValuePairs.add(new BasicNameValuePair("recepientname",
				saveImageDetailsModel.getRecepientName()));
		nameValuePairs.add(new BasicNameValuePair("recepientaddress",
				saveImageDetailsModel.getRecepientAddress()));
		nameValuePairs.add(new BasicNameValuePair("recepientnumber",
				saveImageDetailsModel.getRecepientNumber()));
		nameValuePairs.add(new BasicNameValuePair("offertype",
				saveImageDetailsModel.getOfferType()));

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
			Log.d("Http Response:", jsonResponseString);

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
