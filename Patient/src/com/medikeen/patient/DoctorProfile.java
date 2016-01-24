package com.medikeen.patient;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.medikeen.patient.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DoctorProfile extends Activity{

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	Button showMap;
	TextView textViewName,textViewAddress,textViewSpec,textViewContact;
	ImageView imageDoc;
	String Docname,DocSpec,DocAddress,DocContact,DocImage;

	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	ImageLoader imageLoader;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		init();

		Intent getIntet = getIntent();
		Docname = getIntet.getStringExtra("Docname");
		DocSpec = getIntet.getStringExtra("Docspec");
		DocImage = getIntet.getStringExtra("DocImage");
		DocAddress = getIntet.getStringExtra("DocAddress");
		DocContact = getIntet.getStringExtra("DocContact");

		showMap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent objMap = new Intent(DoctorProfile.this,ShowMap.class);
				startActivity(objMap);
			}
		});

		//---------Manupulate when you got the image from string-----------------
		/*if(DocImage.equalsIgnoreCase("")){
			imageLoader.displayImage("", imageDoc, options, animateFirstListener);
		}*/
	}

	private void init() {

		imageLoader = ImageLoader.getInstance();

		showMap = (Button)findViewById(R.id.buttonFindMap);
		textViewName = (TextView)findViewById(R.id.textViewDocName);
		textViewAddress = (TextView)findViewById(R.id.textViewAddress);
		textViewSpec = (TextView)findViewById(R.id.textViewDocSpec);
		imageDoc = (ImageView)findViewById(R.id.ImageviewPhysician);

		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.images)
		.showImageForEmptyUri(R.drawable.images)
		.showImageOnFail(R.drawable.images)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.considerExifParams(true)
		.build();

	}

	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:


		}
		return true;
	}

}
