package com.example.adapters;



import java.util.ArrayList;
import java.util.List;

import com.example.dataModel.PatientDescriptionDTO;
import com.example.dataModel.UploadedPrescriptionData;
import com.example.patient.DoctorProfile;
import com.pharmakit.patient.R;
import com.example.patient.UploadDescription;
import com.example.patient.ViewPrescription;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UploadedPrescriptionAdapter extends BaseAdapter{

	LayoutInflater inflator;
	Activity mContext;
	ArrayList<UploadedPrescriptionData> objPrescriptionData;

	public UploadedPrescriptionAdapter(Activity activity,ArrayList<UploadedPrescriptionData> objArrayList) {

		mContext = activity;
		inflator = LayoutInflater.from(mContext);
		this.objPrescriptionData= objArrayList;
	}

	public class ViewHolderMainHome {
		TextView textViewAddress,textViewOrderId,textViewOrderAmount,textViewPlacedDate,textViewStatus;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.objPrescriptionData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return this.objPrescriptionData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub



		final ViewHolderMainHome holderMain;
		if(arg1 == null)
		{

			holderMain = new ViewHolderMainHome();
			arg1 = inflator.inflate(R.layout.custom_layout_add_prescription, null);

			Typeface roboto = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf" );

			holderMain.textViewAddress = (TextView)arg1.findViewById(R.id.textViewAddress);
			holderMain.textViewOrderAmount = (TextView)arg1.findViewById(R.id.textViewOrderAmount);
			holderMain.textViewOrderId= (TextView)arg1.findViewById(R.id.textViewOrderId);
			holderMain.textViewPlacedDate = (TextView)arg1.findViewById(R.id.textViewPlacedDate);
			holderMain.textViewStatus = (TextView)arg1.findViewById(R.id.textViewStatusValue);

			arg1.setTag(holderMain);
		}else
		{
			holderMain = (ViewHolderMainHome)arg1.getTag();
		}

		holderMain.textViewAddress.setText(this.objPrescriptionData.get(arg0).getDeliveryAddress());
		holderMain.textViewOrderAmount.setText("Rs. "+this.objPrescriptionData.get(arg0).getOrderAmount());
		holderMain.textViewOrderId.setText(this.objPrescriptionData.get(arg0).getOrderId());
		holderMain.textViewStatus.setText(this.objPrescriptionData.get(arg0).getOrderStatus());
		holderMain.textViewPlacedDate.setText("Placed on "+this.objPrescriptionData.get(arg0).getOrderPlaceDate());

		
		return arg1;
	}

}








