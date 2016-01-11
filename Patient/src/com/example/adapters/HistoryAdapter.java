package com.example.adapters;

import java.util.ArrayList;

import com.example.dataModel.HistoryModel;
import com.pharmakit.patient.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HistoryAdapter extends ArrayAdapter<HistoryModel> {

	ArrayList<HistoryModel> list;
	LayoutInflater inflater;
	Context context;

	public HistoryAdapter(Context context, ArrayList<HistoryModel> list) {
		super(context, R.layout.history_list_item, list);

		this.context = context;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = list;
	}

	class ViewHolder {
		ImageView historyImage;
		TextView name, address, number, offer, date;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.history_list_item, parent, false);

			holder.historyImage = (ImageView) convertView.findViewById(R.id.history_thumbnail);
			holder.name = (TextView) convertView.findViewById(R.id.rec_name);
			holder.address = (TextView) convertView.findViewById(R.id.rec_address);
			holder.number = (TextView) convertView.findViewById(R.id.rec_number);
			holder.offer = (TextView) convertView.findViewById(R.id.rec_offer);
			holder.date = (TextView) convertView.findViewById(R.id.rec_date);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Picasso.with(context).load("http://www.medikeen.com/android_api/prescription/thumbnail.php?image="
				+ list.get(position).getResourceId()).into(holder.historyImage);

		holder.name.setText(list.get(position).getRecepientName());
		holder.address.setText(list.get(position).getRecepientAddress());
		holder.number.setText(list.get(position).getRecepientNumber());
		holder.offer.setText(list.get(position).getOfferType());
		holder.date.setText(list.get(position).getCreated_Date());

		return convertView;
	}

}
