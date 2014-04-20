package com.AndroidExamApp;

import java.util.ArrayList;

import com.AndroidExamApp.data.MyDBHandler;
import com.AndroidExamApp.data.Party;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment1 extends Fragment {
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	static Context mycontext;
	ArrayAdapter<String> adapter;
	ListView lv;
	ViewPager mViewPager;
	
	public static Fragment1 newInstance() {
		Fragment1 fragment = new Fragment1();
		return fragment;
	}

	public Fragment1() {
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment1, container,	false);
		mycontext = this.getActivity().getApplicationContext();
		mViewPager = (ViewPager) container.findViewById(R.id.pager);
		
		adapter = loadData();
		lv = (ListView) rootView.findViewById(R.id.listView1);
		lv.setOnItemClickListener(new OnItemClickListener()
		   {
		      @Override
		      public void onItemClick(AdapterView<?> adapter, View v, int position,long arg3) {
		            String value = (String)adapter.getItemAtPosition(position); 
		            Toast.makeText(mycontext, value, Toast.LENGTH_SHORT).show();
		            Fragment2.setCurrentParty(value);
		            mViewPager.setCurrentItem(1);
		      }
		   });
		lv.setAdapter(adapter);
		
		Button b1 = (Button) rootView.findViewById(R.id.buttonReload);
		b1.setOnClickListener(myhandler1);
		
		return rootView;
	}
		
	View.OnClickListener myhandler1 = new View.OnClickListener() {
	    public void onClick(View v) {
	    	adapter = loadData();
	    	adapter.notifyDataSetChanged();
	    	lv.setAdapter(adapter);
	    }
	 };
	
	public static ArrayAdapter<String> loadData(){
		MyDBHandler dbHandler = new MyDBHandler(mycontext, null, null, 1);
		ArrayList<Party> parties = dbHandler.getAll();
		ArrayList<String> listvalues = new ArrayList<String>();
		
		for(int i = 0; i<parties.size(); i++){
			listvalues.add(parties.get(i).get_name());
		}
		
		String[] values = listvalues.toArray(new String[listvalues.size()]);
//		ArrayAdapter<String> adapter_temp = new ArrayAdapter<String>(context,R.layout.fragment1_row, R.id.labelfragment1, values);
		dbHandler.close();
		LoadDataArrayAdapter adapter_temp = new LoadDataArrayAdapter(mycontext,parties,values);
		return adapter_temp;
	} 
	
}
