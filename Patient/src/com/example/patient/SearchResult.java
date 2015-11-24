package com.example.patient;

import java.util.ArrayList;

import com.example.adapters.GridViewCustomAdapter;
import com.example.asyncTask.SearchTask;
import com.example.dataModel.DoctorDTO;
import com.example.dataModel.SearchModel;
import com.pharmakit.patient.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;

public class SearchResult extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */

	GridView gridView;
	GridViewCustomAdapter grisViewCustomeAdapter;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_serach_doctor_result);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		SearchModel objSearchModel = new SearchModel();

		//----Which specialist is selected pass id by comma sepearated-----------
		objSearchModel.setSearchIds("1");
		objSearchModel.setFromActivity("1002");

		//new SearchTask(SearchResult.this).execute(objSearchModel);

		/**
		 * Manipulated Work for demo 
		 */
		String []doctorList = new String[5];
		DoctorDTO objDoctorDTO = new DoctorDTO();
		ArrayList<DoctorDTO >objDtos =new ArrayList<DoctorDTO>();

		for(int i =0;i<5;i++){

			Log.d("pa", ""+i);
			//-------Manipulated Work----------------
			//-----Remove this line after doinbackground--------------
			//--------pass url for image in last paramenter
			objDoctorDTO.setMatchUserDetails(new DoctorDTO("Test", "MBBS", "Pune", "1234567", ""));

			DoctorDTO group = new DoctorDTO(doctorList[i],doctorList[i],doctorList[i],doctorList[i],doctorList[i]);
			objDtos.add(group);
		}


		GridView gridView=(GridView)findViewById(R.id.gridViewCustom);
		// Create the Custom Adapter Object
		GridViewCustomAdapter grisViewCustomeAdapter = new GridViewCustomAdapter(this,objDtos);
		// Set the Adapter to GridView
		gridView.setAdapter(grisViewCustomeAdapter);

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:


		}
		return true;
	}



}
