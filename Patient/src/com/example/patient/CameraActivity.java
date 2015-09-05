package com.example.patient;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class CameraActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//Hide the window title.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//Hide the status bar.
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
		FragmentManager manager = getFragmentManager();
		Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
		
		if(fragment == null) {
			fragment = CameraFragment.newInstance();
			manager.beginTransaction()
			.add(R.id.fragmentContainer, fragment)
			.commit();
		}
	}
	
}
