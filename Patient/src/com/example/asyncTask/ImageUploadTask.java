package com.example.asyncTask;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.example.dataModel.ImageUploadArgs;
import com.example.dataModel.LoginModel;
import com.example.util.ImageUploader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class ImageUploadTask extends AsyncTask<ImageUploadArgs, String, String> {

	LoginModel logiinModel;
	Context mContext;
	ProgressDialog mDialog;
	String jsonResposnseString;

	public ImageUploadTask(Context c) {
		mContext = c;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		// mDialog = new ProgressDialog(mContext);
		// mDialog.setTitle("Please wait");
		// mDialog.setMessage("Uploading prescription image..");
		// mDialog.setCancelable(false);
		// mDialog.show();
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
		// mDialog.dismiss();
	}
}
