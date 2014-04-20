package com.AndroidExamApp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.AndroidExamApp.data.MyDBHandler;
import com.AndroidExamApp.data.Party;

/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment2 extends Fragment {
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	ViewPager mViewPager;
	static TextView plusSign;
	static TextView equalsSign;
	static TextView minusSign;
	static EditText editName;
	static EditText editPartyDescription;
	static ListView listView;
	static Party currentparty;
	static Button buttonSave;
	static Button buttonAdd;
	static Button buttonAddPr;
	static ImageView imageView;
	static ListPopupWindow listPopupWindow;
	
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
		mViewPager = (ViewPager) container.findViewById(R.id.pager);
		
		final String[] listitems={"cda", "cu", "d66","gl","pvda","pvdd","pvv","sgp","sp","vijftigplus","vvd"};
		
		context = this.getActivity().getApplicationContext();
		plusSign = (TextView) rootView.findViewById(R.id.textViewPlus);
		equalsSign = (TextView) rootView.findViewById(R.id.textViewEquals);
		minusSign = (TextView) rootView.findViewById(R.id.textViewMinus);
		editName = (EditText) rootView.findViewById(R.id.editName);
		editPartyDescription = (EditText) rootView.findViewById(R.id.editPartyDescription);
		listView = (ListView) rootView.findViewById(R.id.listView2);
		buttonSave = (Button) rootView.findViewById(R.id.button2Save);
		buttonAdd = (Button) rootView.findViewById(R.id.button2Add);
		buttonAddPr = (Button) rootView.findViewById(R.id.button2AddPr);
		imageView = (ImageView) rootView.findViewById(R.id.imageView2);
		
		listPopupWindow = new ListPopupWindow(context);
		//ArrayAdapter<String> testadapter = new ArrayAdapter<String>(context, R.layout.fragment1_row, R.id.labelfragment1, listitems);
		LoadImageArrayAdapter testadapter = new LoadImageArrayAdapter(context,listitems);
		listPopupWindow.setAdapter(testadapter);
		listPopupWindow.setAnchorView(imageView);
        listPopupWindow.setWidth(400);
        listPopupWindow.setHeight(600);
        listPopupWindow.setModal(true);
        imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                listPopupWindow.show();
            }
        });  
        listPopupWindow.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				String result = "@drawable/"+listitems[arg2];
				int imageResource = getResources().getIdentifier(result, null, context.getPackageName());
				if(imageResource!=0){
					imageView.setImageResource(imageResource);
					currentparty.set_filename(listitems[arg2]);
					
					MyDBHandler db = new MyDBHandler(context, null, null, 1);
					db.alterParty(currentparty);
					db.close();
				}
				listPopupWindow.dismiss();
			}
        });
		
		plusSign.setText("+0");
		equalsSign.setText("=0");
		minusSign.setText("-0");
		
		buttonSave.setEnabled(false);
		buttonAddPr.setEnabled(false);
		
		buttonAdd.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Party tempparty = new Party(editName.getText().toString(),editPartyDescription.getText().toString());
				MyDBHandler db = new MyDBHandler(context, null, null, 1);
				db.addParty(tempparty);	
				db.close();
				//Fragment1.loadData(); //no time to fix this kind things ..prob some working version of onfocuslistener;blal;bal
				mViewPager.setCurrentItem(0);
			}
		});
		
		buttonSave.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				currentparty.set_name(editName.getText().toString());
				currentparty.set_description(editPartyDescription.getText().toString());
				currentparty.set_filename(context.getResources().getResourceEntryName(imageView.getId()));
				
				MyDBHandler db = new MyDBHandler(context, null, null, 1);
				db.alterParty(currentparty);
				db.close();
			}
		});
		
		buttonAddPr.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				currentparty.set_name(editName.getText().toString());
				currentparty.set_description(editPartyDescription.getText().toString());
				Fragment3.setCurrentPromises(currentparty.get_name(),"2");
				mViewPager.setCurrentItem(2);
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
		            mViewPager.setCurrentItem(2);
		      }
		});
		
		return rootView;
	}	
		
	public static void reset(){
		buttonSave.setEnabled(false);
		buttonAdd.setEnabled(true);
		buttonAddPr.setEnabled(false);
		editName.setText("");
		editPartyDescription.setText("");
		listView.setAdapter(null);
	}
	
	public static void setCurrentParty(String partyname){
		MyDBHandler db = new MyDBHandler(context, null, null, 1);
		currentparty = db.findParty(partyname);
		
		buttonSave.setEnabled(true);
		buttonAddPr.setEnabled(true);
		buttonAdd.setEnabled(false);
		
		plusSign.setText("+"+Integer.toString(currentparty.get_plus_sign()));
		equalsSign.setText("="+Integer.toString(currentparty.get_equals_sign()));
		minusSign.setText("-"+Integer.toString(currentparty.get_minus_sign()));

		editName.setText(currentparty.get_name());
        editPartyDescription.setText(currentparty.get_description());
        
        String result = "@drawable/"+currentparty.get_filename();
	    int imageResource = context.getResources().getIdentifier(result, null, context.getPackageName());
        imageView.setImageResource(imageResource);
        
        String[] values = new String[currentparty.get_promises().keySet().size()];
        values = currentparty.get_promises().keySet().toArray(values);
		ArrayAdapter<String> adapter_temp = new ArrayAdapter<String>(context,R.layout.fragment2_row, R.id.labelfragment2, values);
		listView.setAdapter(adapter_temp);
		db.close();
	}
}
