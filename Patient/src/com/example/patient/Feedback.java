package com.example.patient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Feedback extends Fragment {

	EditText editTextTitle, editTextDesc;
	Button btnSend;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_feedback, container,
				false);

		init(view);

		return view;
	}

	private void init(View view) {

		editTextDesc = (EditText) view
				.findViewById(R.id.editTextFeedbackDescription);
		editTextTitle = (EditText) view
				.findViewById(R.id.editTextFeedbackTitle);

		btnSend = (Button) view.findViewById(R.id.buttonSend);

	}
}
