package com.AndroidExamApp;

import com.AndroidExamApp.data.MyDBHandler;
import com.AndroidExamApp.data.Party;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment2 extends Fragment {
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
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
		
		
		return rootView;
	}
	
	public void setCurrentParty(Party party){
		
	}
	
}
