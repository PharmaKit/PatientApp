package com.medikeen.patient;

import com.medikeen.patient.R;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class HistoryDetailActivity extends Activity {

	String resourceId, resourceType;

	ImageView historyImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_detail);

		resourceId = getIntent().getStringExtra("resource_id");
		resourceType = getIntent().getStringExtra("resource_type");

		historyImage = (ImageView) findViewById(R.id.history_image);

		Picasso.with(HistoryDetailActivity.this)
				.load("http://www.medikeen.com/android_api/prescription/uploads/" + resourceId + "." + resourceType)
				.into(historyImage);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.history_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
