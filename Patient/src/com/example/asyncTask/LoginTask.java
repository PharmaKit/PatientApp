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

import com.example.dataModel.LoginModel;
import com.example.patient.HomePage;
import com.example.util.Constants;
import com.google.gson.Gson;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class LoginTask extends AsyncTask<LoginModel, String, String> {

	LoginModel logiinModel;
	Context mContext;
	ProgressDialog mDialog;
	String jsonResposnseString;

	public LoginTask(Context c)
	{
		mContext = c;
	}


	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mDialog = new ProgressDialog(mContext);
		mDialog.setTitle("Loading");
		mDialog.setCancelable(false);
		//	mDialog.show();
	}

	@Override
	protected String doInBackground(LoginModel... arg0) {


		Gson objGson = new Gson();
		String request  = objGson.toJson(arg0[0]);

		HttpResponse response;

		//Creating Http client
		HttpClient httpclient = new DefaultHttpClient();

		//Building post parametrs key and value pair

		//------Modify your server url in Constants in util package-------

		HttpPost httpPost = new HttpPost(Constants.SERVER_URL+"/urc2");		
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
		nameValuePair.add(new BasicNameValuePair("jsondata", request));

		//URl Encoding the POST parametrs

		try{

			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		}
		catch(Exception e){
			e.printStackTrace();
		}

		//making http request
		try{
			System.out.println("Executing");
			response = httpclient.execute(httpPost);
			System.out.println("check response"+response.toString());
			HttpEntity entity= response.getEntity();
			jsonResposnseString = EntityUtils.toString(entity);


			/**
			 * In 'jsonResposnseString' you will get response that you sent form server.
			 * 
			 */

		}catch(ClientProtocolException e){
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;

	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		//mDialog.dismiss();

		//--------------Manipulate according to your response----------------

		//		Intent intent = new Intent(mContext,HomePage.class);
		//		mContext.startActivity(intent);
	}
}
