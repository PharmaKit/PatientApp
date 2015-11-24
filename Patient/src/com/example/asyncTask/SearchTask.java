package com.example.asyncTask;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.dataModel.DoctorDTO;
import com.example.dataModel.SearchModel;
import com.example.patient.HomePage;
import com.example.patient.ShowMap;
import com.example.util.Constants;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class SearchTask extends AsyncTask<SearchModel, String, String>{

	Activity _homePage;
	ProgressDialog pd;
	String jsonResposnseString;
	ArrayList<DoctorDTO >objDtos =new ArrayList<DoctorDTO>();
	SearchModel objSearchResult;
	public SearchTask(Activity activity) {

		_homePage = activity;		

	}

	public SearchTask(HomePage homePage) {
		// TODO Auto-generated constructor stub
		_homePage=homePage;

	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(String result) {

		super.onPostExecute(result);

		//	pd.dismiss();

		String []doctorList = new String[5];
		DoctorDTO objDoctorDTO = new DoctorDTO();
		//SearchModel objSearchResult = new SearchModel();
		/*
		 * It will get arraylist of the result then manipulate 
		 * it like this to send to custom adapter
		 * 
		 */

		//----------------Instead of static value need to pass list size
		for(int i =0;i<5;i++){

			Log.d("pa", ""+i);
			//-------Manipulated Work----------------
			//-----Remove this line after doinbackground--------------
			//--------pass url for image in last paramenter
			objDoctorDTO.setMatchUserDetails(new DoctorDTO("Test", "MBBS", "Pune", "1234567", ""));

			DoctorDTO group = new DoctorDTO(doctorList[i],doctorList[i],doctorList[i],doctorList[i],doctorList[i]);
			objDtos.add(group);
		}

		String ress = objSearchResult.getFromActivity();

		Log.d("rss", ""+ress);
		if(ress.equalsIgnoreCase("1001")){
			//--------Pass result to ShowMap-----------
			Intent showmap = new Intent(_homePage,ShowMap.class);
			_homePage.startActivity(showmap);	

		}
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = new ProgressDialog(_homePage);
		pd.setMessage("Please Wait..");
		pd.setCancelable(false);
		//pd.show();

	}

	@Override
	protected String doInBackground(SearchModel... params) {
		// TODO Auto-generated method stub

		Log.d("doInBackground", "doInBackground");
		Gson objGson = new Gson();
		String request  = objGson.toJson(params[0]);

		objSearchResult =params[0];

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
			//			response = httpclient.execute(httpPost);
			//			System.out.println("check response"+response.toString());
			//			HttpEntity entity= response.getEntity();
			//			jsonResposnseString = EntityUtils.toString(entity);

			Log.d("search", "Search");
			//--------User response Arraylist to go through loop and pass parameter to constuctor------------
			/*DoctorDTO objDoctorDTO = new DoctorDTO();
			for(int i=0;i<5;i++){
				objDoctorDTO.setMatchUserDetails(new DoctorDTO("Test", "MBBS", "Pune", "1234567", ""));
			}*/

		}
		//		catch(ClientProtocolException e){
		//			e.printStackTrace();
		//		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
