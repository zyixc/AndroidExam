package com.AndroidExamApp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.AndroidExamApp.data.MyDBHandler;
import com.AndroidExamApp.data.Party;

public class Fragment3 extends Fragment {
	static Context context;
	TextView plusSign;
	TextView equalsSign;
	TextView minusSign;
	Button buttonPlus;
	Button buttonEquals;
	Button buttonMinus;
	static EditText editPromise;
	static EditText editPromiseDescription;
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
		context = this.getActivity().getApplicationContext();
		plusSign = (TextView) rootView.findViewById(R.id.textViewPlus3);
		equalsSign = (TextView) rootView.findViewById(R.id.textViewEquals3);
		minusSign = (TextView) rootView.findViewById(R.id.textViewMinus3);
		editPromise = (EditText) rootView.findViewById(R.id.editName);
		editPromiseDescription = (EditText) rootView.findViewById(R.id.editPartyDescription);
		buttonPlus = (Button) rootView.findViewById(R.id.buttonPlus);
		buttonMinus = (Button) rootView.findViewById(R.id.buttonMinus);
		buttonEquals = (Button) rootView.findViewById(R.id.buttonEquals);
		
		return rootView;
	}
	
	public static void setCurrentParty(String partyname){
		MyDBHandler db = new MyDBHandler(context, null, null, 1);
		currentparty = db.findParty(partyname);
		String[] values = new String[currentparty.get_promises().keySet().size()];
        values = currentparty.get_promises().keySet().toArray(values);
		
	}
}
