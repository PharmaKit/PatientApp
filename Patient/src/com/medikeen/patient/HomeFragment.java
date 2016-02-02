package com.medikeen.patient;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class HomeFragment extends Fragment {

	ViewSwitcher viewSwitcher;
	FrameLayout newPres, history;
	TextView new_pres_text, history_text;

	Fragment newPresFragment, historyFragment;

	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.home_fragment, container,
				false);

		new_pres_text = (TextView) rootView.findViewById(R.id.new_pres_text);
		history_text = (TextView) rootView.findViewById(R.id.history_text);

		viewSwitcher = (ViewSwitcher) rootView.findViewById(R.id.viewSwitcher);
		newPres = (FrameLayout) rootView.findViewById(R.id.frame_new_pres);
		history = (FrameLayout) rootView.findViewById(R.id.frame_history);

		// NEW PRES
		newPresFragment = new NewUploadPrescription();

		android.support.v4.app.FragmentTransaction ftNewPres = getActivity()
				.getSupportFragmentManager().beginTransaction();
		ftNewPres.replace(R.id.frame_new_pres, newPresFragment);
		ftNewPres.commit();

		// HISTORY
		historyFragment = new HistoryFragment();

		android.support.v4.app.FragmentTransaction ftHistory = getActivity()
				.getSupportFragmentManager().beginTransaction();
		ftHistory.replace(R.id.frame_history, historyFragment);
		ftHistory.commit();

		changeColor(0);

		new_pres_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				viewSwitcher.showNext();

				changeColor(0);

			}
		});

		history_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				viewSwitcher.showPrevious();

				changeColor(1);
			}
		});

		return rootView;
	}

	private void changeColor(int pos) {
		if (pos == 0) {

			new_pres_text.setClickable(false);
			new_pres_text.setEnabled(false);
			history_text.setClickable(true);
			history_text.setEnabled(true);

			new_pres_text.setTextColor(Color.parseColor("#1E88E5"));
			new_pres_text.setBackgroundColor(Color.parseColor("#ffffff"));

			history_text.setTextColor(Color.parseColor("#ffffff"));
			history_text.setBackgroundColor(Color.parseColor("#1E88E5"));
		} else if (pos == 1) {

			new_pres_text.setClickable(true);
			new_pres_text.setEnabled(true);
			history_text.setClickable(false);
			history_text.setEnabled(false);

			new_pres_text.setTextColor(Color.parseColor("#ffffff"));
			new_pres_text.setBackgroundColor(Color.parseColor("#1E88E5"));

			history_text.setTextColor(Color.parseColor("#1E88E5"));
			history_text.setBackgroundColor(Color.parseColor("#ffffff"));
		}
	}

}