package com.AndroidExamApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import com.AndroidExamApp.data.DumpRItoF;
import com.AndroidExamApp.data.MyDBHandler;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v13.app.FragmentPagerAdapter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity implements ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	public void testfunction(){
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
			case R.id.action_addParty: 
				mViewPager.setCurrentItem(1);
				Fragment2.reset();
				return true;
			case R.id.action_settings: ;
				Intent intent = new Intent(this, DumpRItoF.class);
				startService(intent);				
				dumpDB();
				return true;
	        case R.id.action_resetDb: 
	        	MyDBHandler db = new MyDBHandler(this, null, null, 1);
	        	db.dropTable();
	        	return true;
	        case R.id.action_info:
	        	String url = "http://nl.m.wikipedia.org/wiki/GNU-licentie_voor_vrije_documentatie";
	        	Intent i = new Intent(Intent.ACTION_VIEW);
	        	i.setData(Uri.parse(url));
	        	startActivity(i);
	        	return true;	        	
	        default: return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			switch (position) {
			case 0:
				return Fragment1.newInstance();
			case 1:
				return Fragment2.newInstance();
			case 2:
				return Fragment3.newInstance();
			}
			return null;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_Fragment1);
			case 1:
				return getString(R.string.title_Fragment2);
			case 2:
				return getString(R.string.title_Fragment3);
			}
			return null;
		}
	}
	
	private void dumpDB(){
  	  
  	  	File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();

        if (sd.canWrite()) {
            String currentDBPath = "//data//com.AndroidExamApp//databases//partyDB.db";
            String backupDBPath = "ppm//partyDB.db";
            File currentDB = new File(data, currentDBPath);
            File backupDB = new File(sd, backupDBPath);
            try{
            if (currentDB.exists()) {
                FileInputStream srcInput = new FileInputStream(currentDB);
            	FileOutputStream srcOutput = new FileOutputStream(backupDB);
            	FileChannel src = srcInput.getChannel();
                FileChannel dst = srcOutput.getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                srcInput.close();
                srcOutput.close();
            }}catch (Exception e){
          	  e.printStackTrace();
            }
        }
	}
}
