package com.AndroidExamApp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.AndroidExamApp.data.MyDBHandler;
import com.AndroidExamApp.data.Party;

/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment2 extends Fragment {
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	static TextView plusSign;
	static TextView equalsSign;
	static TextView minusSign;
	static EditText editName;
	static EditText editPartyDescription;
	static ListView listView;
	static Party currentparty;
	Button buttonSave;
	Button buttonAdd;
	
	static Context context;
	
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
		buttonSave = (Button) rootView.findViewById(R.id.button2Save);
		buttonAdd = (Button) rootView.findViewById(R.id.button2Add);
		
		plusSign.setText("+0");
		equalsSign.setText("=0");
		minusSign.setText("-0");
		
		buttonAdd.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Party tempparty = new Party(editName.getText().toString(),editPartyDescription.getText().toString());
				MyDBHandler db = new MyDBHandler(context, null, null, 1);
				db.addParty(tempparty);	
				db.close();
				Fragment3.setCurrentPromises(tempparty.get_name(),"");
			}
		});
		
		buttonSave.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				currentparty.set_name(editName.getText().toString());
				currentparty.set_description(editPartyDescription.getText().toString());
				
				MyDBHandler db = new MyDBHandler(context, null, null, 1);
				db.alterParty(currentparty);
				db.close();
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener()
		   {
		      @Override
		      public void onItemClick(AdapterView<?> adapter, View v, int position,
		            long arg3) 
		      {
		            String value = (String)adapter.getItemAtPosition(position); 
		            Toast.makeText(context, value, Toast.LENGTH_SHORT).show();
		            Fragment3.setCurrentPromises(currentparty.get_name(),value);
		      }
		});
		
		return rootView;
	}	
		
	public static void setCurrentParty(String partyname){
		MyDBHandler db = new MyDBHandler(context, null, null, 1);
		currentparty = db.findParty(partyname);
        
		plusSign.setText("+"+Integer.toString(currentparty.get_plus_sign()));
		equalsSign.setText("="+Integer.toString(currentparty.get_equals_sign()));
		minusSign.setText("-"+Integer.toString(currentparty.get_minus_sign()));

		editName.setText(currentparty.get_name());
        editPartyDescription.setText(currentparty.get_description());
        String[] values = new String[currentparty.get_promises().keySet().size()];
        values = currentparty.get_promises().keySet().toArray(values);
		ArrayAdapter<String> adapter_temp = new ArrayAdapter<String>(context,R.layout.fragment2_row, R.id.labelfragment2, values);
		listView.setAdapter(adapter_temp);
	}
}
