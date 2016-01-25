package com.medikeen.patient;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.medikeen.asyncTask.ImageUploadTask;
import com.medikeen.asyncTask.SaveImageDetailsTask;
import com.medikeen.dataModel.ImageUploadArgs;
import com.medikeen.dataModel.SaveImageDetailsModel;
import com.medikeen.datamodels.User;
import com.medikeen.datamodels.serialized.SaveImageDetailsResponse;
import com.medikeen.interfaces.IImageUploadedEventListener;
import com.medikeen.util.SessionManager;
import com.google.gson.Gson;
import com.medikeen.patient.R;

public class AddressPrescription extends Activity implements OnClickListener,
		OnCheckedChangeListener {

	public static final String GridViewDemo_ImagePath = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/GridView/";

	AutoCompleteTextView editTextDoctorAdd;
	Button buttonUpload;
	RadioButton radioButtonThirty, radioButtonFifteen;
	RadioGroup radioGroupOffer;
	SessionManager sessionManager;
	User user;
	ArrayList<IImageUploadedEventListener> listeners;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_address_prescription);

		init();
		
		listeners = new ArrayList<IImageUploadedEventListener>();

		// auto fill the address of the patient.
		editTextDoctorAdd.setText(sessionManager.getUserDetails().getAddress());

		editTextDoctorAdd.setAdapter(new PlacesAutoCompleteAdapter1(this,
				R.layout.list_item));
		editTextDoctorAdd
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> adapterView,
							View view, int i, long l) {

						String strLocations = (String) adapterView
								.getItemAtPosition(i);
						editTextDoctorAdd.setText(strLocations);

						String s = strLocations;
						if (s.contains(",")) {
							String parts[] = s.split("\\,");
							System.out.print(parts[0]);
							String s1 = parts[0];
						}
					}
				});
	}

	private void init() {

		editTextDoctorAdd = (AutoCompleteTextView) findViewById(R.id.editTextDoctorAdd);
		radioGroupOffer = (RadioGroup) findViewById(R.id.radioGroupOffer);
		radioButtonFifteen = (RadioButton) findViewById(R.id.homdeDeleiveryThree);
		radioButtonThirty = (RadioButton) findViewById(R.id.homdeDeleiveryeight);

		buttonUpload = (Button) findViewById(R.id.buttonUpload);

		buttonUpload.setOnClickListener(this);
		radioGroupOffer.setOnCheckedChangeListener(this);
		sessionManager = new SessionManager(this.getApplicationContext());
		user = sessionManager.getUserDetails();

		Typeface font = Typeface.createFromAsset(getAssets(),
				"SofiaProLight.otf");
		radioButtonFifteen.setTypeface(font);
		radioButtonThirty.setTypeface(font);
		editTextDoctorAdd.setTypeface(font);
	}

	private List<String> RetriveCapturedImagePath() {
		List<String> tFileList = new ArrayList<String>();
		File f = new File(GridViewDemo_ImagePath);
		if (f.exists()) {
			File[] files = f.listFiles();
			Arrays.sort(files);

			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file.isDirectory())
					continue;
				tFileList.add(file.getPath());
			}
		}
		return tFileList;
	}

	@Override
	public void onClick(View arg0) {

		int id = arg0.getId();
		if (id == R.id.buttonUpload) {
			if (!radioButtonFifteen.isChecked()
					&& !radioButtonThirty.isChecked()) {
				Toast.makeText(AddressPrescription.this,
						"Please select the offer", Toast.LENGTH_LONG).show();
				return;
			}

			if (editTextDoctorAdd.getText().toString().equals("")) {
				Toast.makeText(AddressPrescription.this,
						"Please enter the address", Toast.LENGTH_LONG).show();
				return;
			}

			String address = editTextDoctorAdd.getText().toString();
			String offer = radioButtonFifteen.isChecked() ? "3 hours delivery"
					: "8 hours delivery";

			// this is where we actually upload the prescription

			List<String> images = RetriveCapturedImagePath();

			for (String image : images) {

				if (image == null)
					continue;

				File file = new File(image);

				long imageSize = file.length();

				if (imageSize == 0) {
					file.delete();
					continue;
				}

				if (file.isDirectory())
					continue;

				SaveImageDetailsModel arguments = new SaveImageDetailsModel(
						"jpg", user.getPersonId(), user.getFirstName() + " "
								+ user.getLastName(), address,
						user.getPhoneNo(), offer);

				AsyncTask<SaveImageDetailsModel, String, String> saveImageDetailsTask = new SaveImageDetailsTask(
						AddressPrescription.this, file).execute(arguments);
				// String response = null;
				// try {
				// response = saveImageDetailsTask.get();
				// } catch (InterruptedException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// } catch (ExecutionException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }
				//
				// Gson gson = new Gson();
				// SaveImageDetailsResponse imageDetails =
				// gson.fromJson(response, SaveImageDetailsResponse.class);
				//
				// if (imageDetails.success == 1) {
				// Log.d("filename", imageDetails.resourceid + ".jpg");
				// } else {
				// Toast.makeText(AddressPrescription.this, "Image upload
				// failed. Message: " + imageDetails.error_msg,
				// Toast.LENGTH_LONG).show();
				// return;
				// }
				//
				// ImageUploadArgs args = new ImageUploadArgs();
				// args.file = file;
				// args.filename = imageDetails.resourceid + ".jpg";
				// args.mimeType = "image/jpeg";
				// try {
				// args.url = new
				// URL("http://www.medikeen.com/android_api/prescription/upload.php");
				// } catch (MalformedURLException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				//
				// AsyncTask<ImageUploadArgs, String, String> task = new
				// ImageUploadTask(this).execute(args);
				// synchronized (task) {
				//
				// try {
				// task.get();
				//
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } catch (ExecutionException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// }
			}

			// ClearAllCapturedImages();
			//
			// Toast.makeText(AddressPrescription.this, "The prescription(s) has
			// been uploaded!", Toast.LENGTH_LONG)
			// .show();
			//
			// Intent newUploadPrescriptionIntent = new
			// Intent(AddressPrescription.this, LandingActivity.class);
			// newUploadPrescriptionIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// startActivity(newUploadPrescriptionIntent);
		}
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
					Log.d("file-deletion", "file-deletion failed for file "
							+ file.getAbsolutePath());
				}
			}
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		if (checkedId == R.id.homdeDeleiveryThree) {

			/**
			 * Save value as per off
			 */
			// objPrescriptionModel.setFifteenOff("");

		} else if (checkedId == R.id.homdeDeleiveryeight) {

			/**
			 * Save value as per off
			 */
			// objPrescriptionModel.setThirtyOff("");

		}
	}

}