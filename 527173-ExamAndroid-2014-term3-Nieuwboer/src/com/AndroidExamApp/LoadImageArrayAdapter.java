package com.AndroidExamApp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LoadImageArrayAdapter extends ArrayAdapter<String> {
	String[] values;	
	Context mycontext;
		
		public LoadImageArrayAdapter(Context context, String[] values) {
		    super(context, R.layout.fragment1_row, values);
		    this.mycontext = context;
		    this.values = values;
		 }

		  @Override
		  public View getView(int position, View convertView, ViewGroup parent) {
		    LayoutInflater inflater = (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.fragment1_row, parent, false);
		    TextView textView = (TextView) rowView.findViewById(R.id.labelfragment1);
		    ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);
		    textView.setText(values[position]);
		    textView.setTextColor(Color.WHITE);
		    String result = "@drawable/"+values[position];
		    int imageResource = mycontext.getResources().getIdentifier(result, null, mycontext.getPackageName());
			if(imageResource!=0){
				imageView.setImageResource(imageResource);
			}
		    
		    return rowView;
		  }
	}