package com.example.patient;

import java.io.File; 
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.asyncTask.ImageUploadTask;
import com.example.asyncTask.SaveImageDetailsTask;
import com.example.dataModel.ImageUploadArgs;
import com.example.dataModel.SaveImageDetailsModel;
import com.example.datamodels.User;
import com.example.datamodels.serialized.SaveImageDetailsResponse;
import com.example.util.ImageUploader;
import com.example.util.OnBackPressListener;
import com.example.util.SessionManager;
import com.google.gson.Gson;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;


public class AddressPrescription extends Fragment implements OnClickListener,OnCheckedChangeListener,OnBackPressListener {
	
	public static final String GridViewDemo_ImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GridView/";

	AutoCompleteTextView editTextDoctorAdd;
	Button buttonUpload;
	RadioButton radioButtonThirty,radioButtonFifteen;
	RadioGroup radioGroupOffer;
	SessionManager sessionManager;
	User user;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.new_address_prescription, container,
				false);

		init(view);
		
		//auto fill the address of the patient.
		editTextDoctorAdd.setText(sessionManager.getUserDetails().getAddress());

		editTextDoctorAdd.setAdapter(new PlacesAutoCompleteAdapter1(getActivity(), R.layout.list_item));
		editTextDoctorAdd.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

				String strLocations = (String) adapterView.getItemAtPosition(i);
				editTextDoctorAdd.setText(strLocations);

				String s = strLocations;
				if (s.contains(",")) {
					String parts[] = s.split("\\,");
					System.out.print(parts[0]);
					String s1 = parts[0];
				}
			}
		});
		return view;
	}

	private void init(View view) {

		editTextDoctorAdd = (AutoCompleteTextView)view.findViewById(R.id.editTextDoctorAdd);
		radioGroupOffer = (RadioGroup)view.findViewById(R.id.radioGroupOffer);
		radioButtonFifteen = (RadioButton)view.findViewById(R.id.homdeDeleiveryThree);
		radioButtonThirty = (RadioButton)view.findViewById(R.id.homdeDeleiveryeight);

		buttonUpload = (Button)view.findViewById(R.id.buttonUpload);
				
		buttonUpload.setOnClickListener(this);
		radioGroupOffer.setOnCheckedChangeListener(this);
		sessionManager = new SessionManager(getActivity().getApplicationContext());
		user = sessionManager.getUserDetails();
	}
	
	private List<String> RetriveCapturedImagePath() {
		List<String> tFileList = new ArrayList<String>();
		File f = new File(GridViewDemo_ImagePath);
		if (f.exists()) {
			File[] files=f.listFiles();
			Arrays.sort(files);

			for(int i=0; i<files.length; i++){
				File file = files[i];
				if(file.isDirectory())
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
			
			if(!radioButtonFifteen.isChecked() && !radioButtonThirty.isChecked()){
				Toast.makeText(getActivity(), "Please select the offer", Toast.LENGTH_LONG).show();
				return;
			}
			
			if(editTextDoctorAdd.getText().toString().equals(""))
			{
				Toast.makeText(getActivity(), "Please enter the address", Toast.LENGTH_LONG).show();
				return;
			}
			
			String address = editTextDoctorAdd.getText().toString();
			String offer = radioButtonFifteen.isChecked() ? "3 hours delivery" : "8 hours delivery";
			
			//this is where we actually upload the prescription
			
			List<String> images = RetriveCapturedImagePath();
			
			for(String image : images) {
				
				if(image == null)
					continue;
				
				File file = new File(image);
				
				if(file.isDirectory())
					continue;
				
				SaveImageDetailsModel arguments = new SaveImageDetailsModel("jpg", user.getPersonId(), user.getFirstName() + " " + user.getLastName(), address, offer);
				
				AsyncTask<SaveImageDetailsModel, String, String> saveImageDetailsTask = new SaveImageDetailsTask(this.getActivity()).execute(arguments);
				String response = null;
				try {
					response = saveImageDetailsTask.get();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				Gson gson = new Gson();
				SaveImageDetailsResponse imageDetails = gson.fromJson(response, SaveImageDetailsResponse.class);
				
				if(imageDetails.success == 1)
				{
					Log.d("filename", imageDetails.resourceid + ".jpg");
				}
				else{
					Toast.makeText(getActivity(), "Image upload failed. Message: " + imageDetails.error_msg, Toast.LENGTH_LONG).show();
					return;
				}
				
				ImageUploadArgs args = new ImageUploadArgs();
				args.file = file;
				args.filename = imageDetails.resourceid + ".jpg";
				args.mimeType = "image/jpeg";
				try {
					args.url = new URL("http://pharmakit.co/android_api/prescription/upload.php");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				AsyncTask<ImageUploadArgs, String, String> task = new ImageUploadTask(this.getActivity()).execute(args);
				synchronized(task){
				
				try {
					task.get();
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
			
			ClearAllCapturedImages();
			
			Toast.makeText(getActivity().getApplicationContext(), "The prescription(s) has been uploaded!", Toast.LENGTH_LONG).show();
			
			getActivity().finish();
		}
	}

	private void ClearAllCapturedImages() {
		File f = new File(GridViewDemo_ImagePath);
		if (f.exists()) {
			File[] files=f.listFiles();
			Arrays.sort(files);

			for(int i=0; i<files.length; i++){
				File file = files[i];
				if(file.isDirectory())
					continue;
				
				if(!file.delete())
				{
					Log.d("file-deletion", "file-deletion failed for file " + file.getAbsolutePath());
				}
			}
		}
	}

	@Override
	public boolean onBackPressed() {

		/*// currently visible tab Fragment
		OnBackPressListener currentFragment = (OnBackPressListener)new AttachPrescription();

		if (currentFragment != null) {
			// lets see if the currentFragment or any of its childFragment can handle onBackPressed
			return currentFragment.onBackPressed();
		}

		// this Fragment couldn't handle the onBackPressed call
*/		return false;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		if(checkedId==R.id.homdeDeleiveryThree){

			/**
			 * Save value as per off
			 */
			//objPrescriptionModel.setFifteenOff("");

		}else if(checkedId==R.id.homdeDeleiveryeight){

			/**
			 * Save value as per off
			 */
			//objPrescriptionModel.setThirtyOff("");

		}
	}

	
}
