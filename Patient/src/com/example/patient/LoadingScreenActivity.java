package com.example.patient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class LoadingScreenActivity extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	System.out.println("LoadingScreenActivity  screen started");
	setContentView(R.layout.loading_screen);
	findViewById(R.id.mainSpinner1).setVisibility(View.VISIBLE);
	}
}
