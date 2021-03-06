package com.medikeen.asyncTask;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import com.medikeen.dataModel.ImageUploadArgs;
import com.medikeen.dataModel.LoginModel;
import com.medikeen.interfaces.IImageUploadedEventListener;
import com.medikeen.patient.AddressPrescription;
import com.medikeen.patient.LandingActivity;
import com.medikeen.util.ImageUploader;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class ImageUploadTask extends AsyncTask<ImageUploadArgs, String, String> {

	LoginModel logiinModel;
	Context mContext;
	ProgressDialog mDialog;
	String jsonResposnseString;
	ArrayList<IImageUploadedEventListener> listeners = new ArrayList<IImageUploadedEventListener> ();

	public static final String GridViewDemo_ImagePath = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/GridView/";

	public ImageUploadTask(Context c) {
		mContext = c;
	}
	
	public void setOnImageUploadedEventListener(IImageUploadedEventListener listener) {
		this.listeners.add(listener);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mDialog = new ProgressDialog(mContext);
		mDialog.setTitle("Please wait");
		mDialog.setMessage("Uploading prescription image..");
		mDialog.setCancelable(false);
		mDialog.show();
	}

	@Override
	protected String doInBackground(ImageUploadArgs... params) {

		try {
			int returnCode = ImageUploader.postFileToURL(params[0].file, params[0].filename, params[0].mimeType,
					params[0].url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(String result) {

		// TODO Auto-generated method stub
		super.onPostExecute(result);

		//ClearAllCapturedImages();
		
		for(IImageUploadedEventListener listener : listeners) {
			if(listener != null) {
				listener.onImageUploaded();
			}
		}

		//Toast.makeText(mContext, "The prescription(s) has been uploaded!", Toast.LENGTH_LONG).show();

		//Intent newUploadPrescriptionIntent = new Intent(mContext, LandingActivity.class);
		//newUploadPrescriptionIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//mContext.startActivity(newUploadPrescriptionIntent);

		mDialog.dismiss();
	}

	private void ClearAllCapturedImages() {
		File f = new File(GridViewDemo_ImagePath);
		if (f.exists()) {
			File[] files = f.listFiles();
			Arrays.sort(files);
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file.isDirectory())
					continue;
				if (!file.delete()) {
					Log.d("file-deletion", "file-deletion failed for file " + file.getAbsolutePath());
				}
			}
		}
	}
}
