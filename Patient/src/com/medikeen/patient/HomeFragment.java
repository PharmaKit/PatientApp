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
import android.widget.TextView;

public class HomeFragment extends Fragment {

	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.home_fragment, container, false);

		final TextView new_pres_text = (TextView) rootView.findViewById(R.id.new_pres_text);
		final TextView history_text = (TextView) rootView.findViewById(R.id.history_text);

		final ViewPager pager = (ViewPager) rootView.findViewById(R.id.viewPager);
		pager.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager()));

		pager.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				if (arg0 == 0) {
					new_pres_text.setTextColor(Color.parseColor("#ffffff"));
					new_pres_text.setBackgroundColor(Color.parseColor("#000000"));

					history_text.setTextColor(Color.parseColor("#000000"));
					history_text.setBackgroundColor(Color.parseColor("#ffffff"));
				} else if (arg0 == 1) {
					new_pres_text.setTextColor(Color.parseColor("#000000"));
					new_pres_text.setBackgroundColor(Color.parseColor("#ffffff"));

					history_text.setTextColor(Color.parseColor("#ffffff"));
					history_text.setBackgroundColor(Color.parseColor("#000000"));
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		new_pres_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pager.setCurrentItem(0);
			}
		});

		history_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pager.setCurrentItem(1);
			}
		});

		return rootView;
	}

	private class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int pos) {
			switch (pos) {

			case 0:
				return new NewUploadPrescription();
			case 1:
				return new HistoryFragment();
			}
			return null;
		}

		@Override
		public int getCount() {
			return 2;
		}
	}

}