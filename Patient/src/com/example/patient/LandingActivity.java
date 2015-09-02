package com.example.patient;

import java.util.ArrayList;
import java.util.List;

import com.example.adapters.CustomDrawerAdapter;
import com.example.adapters.DrawerItem;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

@SuppressLint("NewApi")
public class LandingActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	CustomDrawerAdapter adapter;

	List<DrawerItem> dataList;
	Fragment fragment;

	/*@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { 
			// do something on back.
			Intent startMain = new Intent(Intent.ACTION_MAIN); 
			startMain.addCategory(Intent.CATEGORY_HOME); 
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			startActivity(startMain); 
			return true; 
		}
		return super.onKeyDown(keyCode, event);
	}*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initializing
		dataList = new ArrayList<DrawerItem>();
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// Add Drawer Item to dataList
		dataList.add(new DrawerItem("Add Prescription", R.drawable.ic_briefcase_upload_grey600_36dp));
		//dataList.add(new DrawerItem("Find Doctor", R.drawable.ic_account_search_grey600_36dp));
		//dataList.add(new DrawerItem("Saved Prescrption", R.drawable.ic_content_save_all_grey600_36dp));
		//dataList.add(new DrawerItem("Prescrption History", R.drawable.ic_content_save_all_grey600_36dp));
		//dataList.add(new DrawerItem("Settings", R.drawable.ic_settings_grey600_36dp));
		dataList.add(new DrawerItem("Feedback", R.drawable.ic_comment_alert_outline_grey600_36dp));
		//	dataList.add(new DrawerItem("Privacy Policy", R.drawable.ic_marker_check_grey600_36dp));
		//	dataList.add(new DrawerItem("About Us", R.drawable.ic_human_male_female_grey600_36dp));

		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
				dataList);

		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
				// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
				// onPrepareOptionsMenu()
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			SelectItem(0);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



	public void SelectItem(int possition) {

		Bundle args = new Bundle();
		switch (possition) {
		case 0:
			fragment = new NewUploadPrescription();
			args.putString(UploadDescription.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(UploadDescription.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());

			break;
			
		case 1:
			fragment = new Feedback();
			args.putString(UploadDescription.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(UploadDescription.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			
			//todo: we are hiding all other cases which are currently not used.
			
//		case 1:
//			fragment = new FindDoctor();
//			args.putString(UploadDescription.ITEM_NAME, dataList.get(possition)
//					.getItemName());
//			args.putInt(UploadDescription.IMAGE_RESOURCE_ID, dataList.get(possition)
//					.getImgResID());
//
//			break;
//		case 2:
//			fragment = new SavedPrescription();
//			args.putString(UploadDescription.ITEM_NAME, dataList.get(possition)
//					.getItemName());
//			args.putInt(UploadDescription.IMAGE_RESOURCE_ID, dataList.get(possition)
//					.getImgResID());
//
//
//			break;
//
//		case 3:
//			fragment = new UploadedPrescription();
//			args.putString(UploadDescription.ITEM_NAME, dataList.get(possition)
//					.getItemName());
//			args.putInt(UploadDescription.IMAGE_RESOURCE_ID, dataList.get(possition)
//					.getImgResID());
//
//
//			break;
//
//		case 4:
//			fragment = new Feedback();
//			args.putString(UploadDescription.ITEM_NAME, dataList.get(possition)
//					.getItemName());
//			args.putInt(UploadDescription.IMAGE_RESOURCE_ID, dataList.get(possition)
//					.getImgResID());



			break;

		default:
			break;
		}

		fragment.setArguments(args);
		FragmentManager frgManager = getFragmentManager();
		frgManager.beginTransaction().replace(R.id.content_frame, fragment)
		.addToBackStack(null)
		.commit();

		mDrawerList.setItemChecked(possition, true);
		setTitle(dataList.get(possition).getItemName());
		mDrawerLayout.closeDrawer(mDrawerList);

	}


	@Override
	public void onBackPressed() {

		System.out.println("back press");

		/*if(!((NewUploadPrescription)fragment).onBackPressed()){
			super.onBackPressed();
		}*/

		try{
		if (!fragment.
				getChildFragmentManager().
				popBackStackImmediate())
		{
			super.onBackPressed();
		}
		}catch(Exception e){
			
			super.onBackPressed();
			Intent startMain = new Intent(Intent.ACTION_MAIN); 
			startMain.addCategory(Intent.CATEGORY_HOME); 
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			startActivity(startMain); 
			
		}
	}



	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}


	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		Fragment fragment = null;

		int itemId = item.getItemId();
		if (itemId == R.id.action_settings) {
			setTitle("Settings");
		} else if (itemId == R.id.action_privacy) {
			fragment = new TermsNCondition();
			setTitle("Terms and Conditions");
		} else if (itemId == R.id.action_aboutus) {
			fragment = new AboutUs();
			setTitle("About Us");
		}
		FragmentManager frgManager = getFragmentManager();
		frgManager.beginTransaction().replace(R.id.content_frame, fragment)
		.commit();

		return super.onOptionsItemSelected(item);
	}

	private class DrawerItemClickListener implements
	ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			SelectItem(position);
		}
	}

}
