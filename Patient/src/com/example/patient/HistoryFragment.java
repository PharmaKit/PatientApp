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

		View rootView = inflater.inflate(R.layout.history_layout_fragment, container, false);

		sessionManager = new SessionManager(HistoryFragment.this.getActivity());

		String personId = "" + sessionManager.getUserDetails().getPersonId();

		mHistoryListView = (ListView) rootView.findViewById(R.id.history_list);

		String[] historyRequestParams = new String[] { personId };

		historyList = new ArrayList<>();

		final AsyncTask<String[], String, String> task = new HistoryAsyncTask(HistoryFragment.this.getActivity())
				.execute(historyRequestParams);

		String result = "";
		try {
			result = task.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Gson gson = new Gson();

		HistoryResponse response = gson.fromJson(result, HistoryResponse.class);

		if (response.success == 1) {

			Log.e("TAG", "HISTORY RESPONSE: " + response.success);

			try {
				JSONObject historyJsonObject = new JSONObject(result);

				prescriptionsArray = historyJsonObject.getJSONArray("prescriptions");

				for (int i = 0; i < prescriptionsArray.length(); i++) {
					JSONObject prescriptionsObject = prescriptionsArray.getJSONObject(i);

					String resource_id = prescriptionsObject.getString("resource_id");
					String resource_type = prescriptionsObject.getString("resource_type");
					String person_id = prescriptionsObject.getString("person_id");
					String recepient_name = prescriptionsObject.getString("recepient_name");
					String recepient_address = prescriptionsObject.getString("recepient_address");
					String recepient_number = prescriptionsObject.getString("recepient_number");
					String offer_type = prescriptionsObject.getString("offer_type");
					String is_image_uploaded = prescriptionsObject.getString("is_image_uploaded");
					String is_email_sent = prescriptionsObject.getString("is_email_sent");
					String created_date = prescriptionsObject.getString("created_date");
					String updated_date = prescriptionsObject.getString("updated_date");

					historyList.add(new HistoryModel(person_id, resource_id, resource_type, recepient_name,
							recepient_address, recepient_number, offer_type, is_image_uploaded, is_email_sent,
							created_date, updated_date));
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (response.success == 0) {
			Log.e("TAG", "HISTORY RESPONSE: " + response.error);
		}

		adapter = new HistoryAdapter(HistoryFragment.this.getActivity(), historyList);
		mHistoryListView.setAdapter(adapter);

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
