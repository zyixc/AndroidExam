package com.AndroidExamApp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment3 extends Fragment {
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static Fragment3 newInstance() {
		Fragment3 fragment = new Fragment3();
		return fragment;
	}

	public Fragment3() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment3, container,	false);
		return rootView;
	}
}
