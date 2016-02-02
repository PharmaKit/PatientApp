package com.medikeen.patient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medikeen.util.ButtonFloat;
import com.medikeen.patient.R;

public class NewUploadPrescription extends Fragment implements OnClickListener {

	ButtonFloat buttonAddPrescription;
	public static int tabIndex = 0;
	AttachPrescription newFragment = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_uploadaprescription,
				container, false);

		TextView medikeen = (TextView) view.findViewById(R.id.medikeen);
		medikeen.setText(Html
				.fromHtml("<font color=\"#1E88E5\">M</font>edi<font color=\"#E53935\">K</font>een"));

		init(view);

		return view;
	}

	private void init(View view) {

		buttonAddPrescription = (ButtonFloat) view
				.findViewById(R.id.buttonFloat);

		buttonAddPrescription.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {

		int id = arg0.getId();
		if (id == R.id.buttonFloat) {
			String backStateName = "NewUpload";
			// newFragment = new AttachPrescription();
			// Bundle bundle = new Bundle();
			// newFragment.setArguments(bundle);
			// FragmentTransaction transaction = getFragmentManager()
			// .beginTransaction();
			// // Replace whatever is in the fragment_container view with this
			// // fragment,
			// // and add the transaction to the back stack so the user can
			// // navigate back
			// transaction.replace(R.id.content_frame, newFragment);
			// transaction.addToBackStack(null);
			// // Commit the transaction
			// transaction.commit();

			Intent attachPrescriptionsIntent = new Intent(
					NewUploadPrescription.this.getActivity(),
					AttachPrescription.class);
			startActivity(attachPrescriptionsIntent);

			tabIndex = 1;
		}
	}

	/**
	 * Retrieve the currently visible Tab Fragment and propagate the
	 * onBackPressed callback
	 * 
	 * @return true = if this fragment and/or one of its associates Fragment can
	 *         handle the backPress
	 */
	// public boolean onBackPressed() {
	//
	// Log.d("New", "new upload" + tabIndex);
	//
	// if (tabIndex == 1) {
	//
	// Log.d("in", "tabinse" + tabIndex);
	//
	// } else if (tabIndex == 2) {
	//
	// }
	// return false;
	//
	// }

}
