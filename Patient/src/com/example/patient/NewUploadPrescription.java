package com.example.patient;

import com.example.util.ButtonFloat;
import com.example.util.OnBackPressListener;
import com.pharmakit.patient.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class NewUploadPrescription extends Fragment implements OnClickListener {

	ButtonFloat buttonAddPrescription;
	public static int tabIndex= 0 ; 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_uploadaprescription, container,
				false);

		init(view);

		return view;
	}

	private void init(View view) {

		buttonAddPrescription =(ButtonFloat)view.findViewById(R.id.buttonFloat);

		buttonAddPrescription.setOnClickListener(this);

	}

	AttachPrescription newFragment=null;
	@Override
	public void onClick(View arg0) {


		int id = arg0.getId();
		if (id == R.id.buttonFloat) {
			String backStateName ="NewUpload";
			newFragment = new AttachPrescription();
			Bundle bundle = new Bundle();
			newFragment.setArguments(bundle);
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			// Replace whatever is in the fragment_container view with this fragment,
			// and add the transaction to the back stack so the user can navigate back
			transaction.replace(R.id.content_frame, newFragment);
			transaction.addToBackStack(null);
			// Commit the transaction
			transaction.commit();
			tabIndex=1;
		}
	}
	/**
	 * Retrieve the currently visible Tab Fragment and propagate the onBackPressed callback
	 *
	 * @return true = if this fragment and/or one of its associates Fragment can handle the backPress
	 */
	public boolean onBackPressed() {


		Log.d("New", "new upload"+tabIndex);

		if(tabIndex == 1){

			Log.d("in", "tabinse"+tabIndex);
			
			
		}else if(tabIndex==2){


			
		}
		return false;

	}

}
