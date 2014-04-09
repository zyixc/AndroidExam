package com.AndroidExamApp;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.AndroidExamApp.data.MyDBHandler;
import com.AndroidExamApp.data.Party;

/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment2 extends Fragment {
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	TextView plusSign;
	TextView equalsSign;
	TextView minusSign;
	EditText editName;
	EditText editPartyDescription;
	ListView listView;
	
	Context context;
	
	public static Fragment2 newInstance() {
		Fragment2 fragment = new Fragment2();				
		return fragment;
	}

	public Fragment2() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment2, container,	false);
		context = this.getActivity().getApplicationContext();
		plusSign = (TextView) rootView.findViewById(R.id.textViewPlus);
		equalsSign = (TextView) rootView.findViewById(R.id.textViewEquals);
		minusSign = (TextView) rootView.findViewById(R.id.textViewMinus);
		editName = (EditText) rootView.findViewById(R.id.editName);
		editPartyDescription = (EditText) rootView.findViewById(R.id.editPartyDescription);
		listView = (ListView) rootView.findViewById(R.id.listView2);
	
		return rootView;
	}	
		
	public void setCurrentParty(String partyname){
		MyDBHandler db = new MyDBHandler(context, null, null, 1);
		Party currentparty = db.findParty(partyname);
        editName.setText(currentparty.get_name());
        editPartyDescription.setText(currentparty.get_description());
        String[] values = (String[]) currentparty.get_promises().keySet().toArray();
		ArrayAdapter<String> adapter_temp = new ArrayAdapter<String>(context,R.layout.fragment2_row, R.id.labelfragment2, values);
		listView.setAdapter(adapter_temp);
	}
}
