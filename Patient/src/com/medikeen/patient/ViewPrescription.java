package com.medikeen.patient;

import java.util.ArrayList;

import com.medikeen.adapters.PrescriptionAdapter;
import com.medikeen.adapters.PrescriptionTabAdapter;
import com.medikeen.dataModel.PatientDescriptionDTO;
import com.medikeen.dataModel.PrescriptionDTO;
import com.medikeen.patient.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

public class ViewPrescription extends Activity{

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */

	ListView listTabdescription;
	ArrayList<PrescriptionDTO>patientslist = new ArrayList<PrescriptionDTO>();

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_prescription);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		//------------------------Find History----------
		listTabdescription  =(ListView)findViewById(R.id.listView1Tabdesc);

		//------------Manipulate if any histoy found in historytask onPostExecute----//
		//------------Else shows image only--------------------

		PrescriptionDTO objDescriptionDTO = new PrescriptionDTO();

		for(int i=0;i<5;i++){
			objDescriptionDTO.setMatchUserDetails(new PrescriptionDTO("Tab1", "Morning"));
		}

		String []patientList = new String[PatientDescriptionDTO.prescriptionList.size()];

		for(int i =0;i<patientList.length;i++){

			patientList[i] = PatientDescriptionDTO.prescriptionList.get(i).patientName;

			PrescriptionDTO group = new PrescriptionDTO(patientList[i],patientList[i]);
			patientslist.add(group);
		} 

		PrescriptionTabAdapter tripadapter = new PrescriptionTabAdapter(this,patientslist);
		listTabdescription.setAdapter(tripadapter);

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:

		}
		return true;
	}
}
