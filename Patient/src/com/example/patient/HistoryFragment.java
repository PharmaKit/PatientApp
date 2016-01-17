package com.example.patient;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapters.HistoryAdapter;
import com.example.asyncTask.HistoryAsyncTask;
import com.example.dataModel.HistoryModel;
import com.example.datamodels.serialized.HistoryResponse;
import com.example.util.SessionManager;
import com.google.gson.Gson;
import com.pharmakit.patient.HistoryDetailActivity;
import com.pharmakit.patient.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HistoryFragment extends Fragment {

	ListView mHistoryListView;
	JSONArray prescriptionsArray;

	ArrayList<HistoryModel> historyList;

	HistoryAdapter adapter;

	SessionManager sessionManager;

	public HistoryFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		historyList = new ArrayList<HistoryModel>();

		View rootView = inflater.inflate(R.layout.history_layout_fragment, container, false);

		sessionManager = new SessionManager(HistoryFragment.this.getActivity());

		String personId = "" + sessionManager.getUserDetails().getPersonId();

		mHistoryListView = (ListView) rootView.findViewById(R.id.history_list);

		final String[] historyRequestParams = new String[] { personId };

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				final AsyncTask<String[], String, String> task = new HistoryAsyncTask(
						HistoryFragment.this.getActivity(), mHistoryListView, historyList).execute(historyRequestParams);

			}
		}, 500);

		mHistoryListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent historyDetailIntent = new Intent(HistoryFragment.this.getActivity(),
						HistoryDetailActivity.class);
				historyDetailIntent.putExtra("resource_id", historyList.get(position).getResourceId());
				historyDetailIntent.putExtra("resource_type", historyList.get(position).getResourceType());
				startActivity(historyDetailIntent);
			}
		});

		return rootView;
	}

}
