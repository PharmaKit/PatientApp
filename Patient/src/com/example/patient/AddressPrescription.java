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
import com.example.dataModel.ImageUploadArgs;
import com.example.util.ImageUploader;
import com.example.util.OnBackPressListener;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.new_address_prescription, container,
				false);

		init(view);

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
			//this is where we actually upload the prescription
			
			List<String> images = RetriveCapturedImagePath();
			
			for(String image : images) {
				
				if(image == null)
					continue;
				
				File file = new File(image);
				
				if(file.isDirectory())
					continue;
				
				ImageUploadArgs args = new ImageUploadArgs();
				args.file = file;
				args.mimeType = "image/jpeg";
				try {
					args.url = new URL("http://pharmakit.co/android_api/prescription/upload.php");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				AsyncTask<ImageUploadArgs, String, String> task = new ImageUploadTask(this.getActivity().getApplicationContext()).execute(args);
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
