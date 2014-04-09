package com.AndroidExamApp;

import java.util.ArrayList;

import com.AndroidExamApp.data.MyDBHandler;
import com.AndroidExamApp.data.Party;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
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
	Context context;
	ArrayAdapter<String> adapter;
	ListView lv;
	
	public static Fragment1 newInstance() {
		Fragment1 fragment = new Fragment1();
		return fragment;
	}

	public Fragment1() {
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment1, container,	false);
		context = this.getActivity().getApplicationContext();
		
		
		adapter = loadData();
		lv = (ListView) rootView.findViewById(R.id.listView1);
		lv.setOnItemClickListener(new OnItemClickListener()
		   {
		      @Override
		      public void onItemClick(AdapterView<?> adapter, View v, int position,
		            long arg3) 
		      {
		            String value = (String)adapter.getItemAtPosition(position); 
		            Toast.makeText(context, value, Toast.LENGTH_SHORT).show();
		            Fragment2.setCurrentParty(value);
		            Fragment3.setCurrentPromises(value, null);
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
	
	public ArrayAdapter<String> loadData(){
		MyDBHandler dbHandler = new MyDBHandler(context, null, null, 1);
		ArrayList<Party> products = dbHandler.getAll();
		ArrayList<String> listvalues = new ArrayList<String>();
		
		for(int i = 0; i<products.size(); i++){
			listvalues.add(products.get(i).get_name());
		}
		
		String[] values = listvalues.toArray(new String[listvalues.size()]);
		ArrayAdapter<String> adapter_temp = new ArrayAdapter<String>(context,R.layout.fragment1_row, R.id.labelfragment1, values);
		dbHandler.close();
		
		return adapter_temp;
	}
}
