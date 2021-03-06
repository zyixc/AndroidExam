package com.AndroidExamApp;

import java.util.Map;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.AndroidExamApp.data.MyDBHandler;
import com.AndroidExamApp.data.Party;

public class Fragment3 extends Fragment {
	ViewPager mViewPager;
	static Context context;
	static TextView plusSign;
	static TextView equalsSign;
	static TextView minusSign;
	static Button buttonPlus;
	static Button buttonEquals;
	static Button buttonMinus;
	static Button button3Save;
	static Button button3Add;
	static EditText editPromise;
	static EditText editPromiseDescription;
	static TextView partyNameText;
	static ImageView imageView;
	static Party currentparty;
	
	public static Fragment3 newInstance() {
		Fragment3 fragment = new Fragment3();
		return fragment;
	}

	public Fragment3() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment3, container,	false);
		mViewPager = (ViewPager) container.findViewById(R.id.pager);
		
		context = this.getActivity().getApplicationContext();
		plusSign = (TextView) rootView.findViewById(R.id.textViewPlus3);
		equalsSign = (TextView) rootView.findViewById(R.id.textViewEquals3);
		minusSign = (TextView) rootView.findViewById(R.id.textViewMinus3);
		editPromise = (EditText) rootView.findViewById(R.id.editPromise);
		editPromiseDescription = (EditText) rootView.findViewById(R.id.editPromiseDescription);
		buttonPlus = (Button) rootView.findViewById(R.id.buttonPlus);
		buttonMinus = (Button) rootView.findViewById(R.id.buttonMinus);
		buttonEquals = (Button) rootView.findViewById(R.id.buttonEquals);
		button3Save = (Button) rootView.findViewById(R.id.button3Save);
		button3Add = (Button) rootView.findViewById(R.id.button3Add);
		partyNameText = (TextView) rootView.findViewById(R.id.textView3);
		imageView = (ImageView) rootView.findViewById(R.id.imageView3);
		
		plusSign.setText("+0");
		equalsSign.setText("=0");
		minusSign.setText("-0");
		
		partyNameText.setText("none selected");
		editPromise.setText("none selected");
		editPromiseDescription.setText("none selected");
		editPromise.setEnabled(false);
		editPromiseDescription.setEnabled(false);
		buttonPlus.setEnabled(false);
		buttonMinus.setEnabled(false);
		buttonEquals.setEnabled(false);
		button3Save.setEnabled(false);
		button3Add.setEnabled(false);
		
		button3Add.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Map<String,String> temphashmap = currentparty.get_promises();				
				temphashmap.put(editPromise.getText().toString(), editPromiseDescription.getText().toString());
				currentparty.set_promises(temphashmap);
				
				MyDBHandler db = new MyDBHandler(context, null, null, 1);
				db.alterParty(currentparty);
				mViewPager.setCurrentItem(1);
				Fragment2.setCurrentParty(currentparty.get_name());
			}
		});
		
		button3Save.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				currentparty.set_name(editPromise.getText().toString());
				currentparty.set_description(editPromiseDescription.getText().toString());
				
				MyDBHandler db = new MyDBHandler(context, null, null, 1);
				db.alterParty(currentparty);			
			}
		});
		
		buttonPlus.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				currentparty.set_plus_plus_one();
				
				MyDBHandler db = new MyDBHandler(context, null, null, 1);
				db.alterParty(currentparty);			
			}
		});
		buttonEquals.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				currentparty.set_equals_plus_one();
				
				MyDBHandler db = new MyDBHandler(context, null, null, 1);
				db.alterParty(currentparty);			
			}
		});
		buttonMinus.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				currentparty.set_minus_plus_one();
				
				MyDBHandler db = new MyDBHandler(context, null, null, 1);
				db.alterParty(currentparty);			
			}
		});
		
		return rootView;
	}
	
	public static void setCurrentPromises(String partyname, String recievedValue){
		MyDBHandler db = new MyDBHandler(context, null, null, 1);
		currentparty = db.findParty(partyname);
		
		plusSign.setText("+"+Integer.toString(currentparty.get_plus_sign()));
		equalsSign.setText("="+Integer.toString(currentparty.get_equals_sign()));
		minusSign.setText("-"+Integer.toString(currentparty.get_minus_sign()));
		
		if(recievedValue.equals("2")){
			partyNameText.setText(currentparty.get_name());
			
			String result = "@drawable/"+currentparty.get_filename();
			int imageResource = context.getResources().getIdentifier(result, null, context.getPackageName());
		    imageView.setImageResource(imageResource);
			
			editPromise.setText("");
			editPromiseDescription.setText("");
		}else{
			String value = currentparty.get_promises().get(recievedValue);
			partyNameText.setText(currentparty.get_name());
			
			String result = "@drawable/"+currentparty.get_filename();
			int imageResource = context.getResources().getIdentifier(result, null, context.getPackageName());
		    imageView.setImageResource(imageResource);
			
			editPromise.setText(recievedValue);
			editPromiseDescription.setText(value);
		}
		editPromise.setEnabled(true);
		editPromiseDescription.setEnabled(true);
		buttonPlus.setEnabled(true);
		buttonMinus.setEnabled(true);
		buttonEquals.setEnabled(true);
		button3Save.setEnabled(false);
		button3Add.setEnabled(true);
		
		db.close();
	}
}
