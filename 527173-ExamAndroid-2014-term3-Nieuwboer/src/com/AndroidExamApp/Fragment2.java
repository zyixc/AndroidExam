package com.AndroidExamApp;

import java.util.ArrayList;
import com.AndroidExamApp.data.MyDBHandler;
import com.AndroidExamApp.data.Product;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment2 extends Fragment {
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	Context context;
	ArrayAdapter<String> adapter;
	ListView lv;
	
	public static Fragment2 newInstance() {
		Fragment2 fragment = new Fragment2();
		return fragment;
	}

	public Fragment2() {
	}

//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//	    super.onCreate(savedInstanceState);
//	    context = this.getActivity().getApplicationContext();
//		loadData();
//	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment2, container,	false);
		context = this.getActivity().getApplicationContext();
		
		adapter = loadData();
		lv = (ListView) rootView.findViewById(R.id.listView1);
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
	    	Log.i("data", "data changed, should be updated!");
	    }
	 };
	
	public ArrayAdapter<String> loadData(){
		MyDBHandler dbHandler = new MyDBHandler(context, null, null, 1);
		ArrayList<Product> products = dbHandler.getAll();
		ArrayList<String> listvalues = new ArrayList<String>();
		
		for(int i = 0; i<products.size(); i++){
			listvalues.add(products.get(i).getProductName());
		}
		
		String[] values = listvalues.toArray(new String[listvalues.size()]);
		ArrayAdapter<String> adapter_temp = new ArrayAdapter<String>(context,R.layout.fragment2_row, R.id.label, values);
		dbHandler.close();
		
		return adapter_temp;
	}
}
