package com.AndroidExamApp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.AndroidExamApp.data.Party;

public class LoadDataArrayAdapter extends ArrayAdapter<String> {
		ArrayList<Party> partylist = null;  
		Context mycontext;
		
		public LoadDataArrayAdapter(Context context,ArrayList<Party> parties, String[] values) {
		    super(context, R.layout.fragment1_row, values);
		    this.mycontext = context;
		    this.partylist = parties;
		 }

		  @Override
		  public View getView(int position, View convertView, ViewGroup parent) {
		    LayoutInflater inflater = (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.fragment1_row, parent, false);
		    TextView textView = (TextView) rowView.findViewById(R.id.labelfragment1);
		    TextView textplus = (TextView) rowView.findViewById(R.id.textViewPlus1);
		    TextView textequals = (TextView) rowView.findViewById(R.id.textViewEquals1);
		    TextView textminus = (TextView) rowView.findViewById(R.id.textViewMinus1);
		    ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);
		    textView.setText(partylist.get(position).get_name());
		    textplus.setText("+"+Integer.toString(partylist.get(position).get_plus_sign()));
		    textequals.setText("="+Integer.toString(partylist.get(position).get_equals_sign()));
		    textminus.setText("-"+Integer.toString(partylist.get(position).get_minus_sign()));
		    String result = "@drawable/"+partylist.get(position).get_filename();
		    int imageResource = mycontext.getResources().getIdentifier(result, null, mycontext.getPackageName());
			if(imageResource!=0){
				imageView.setImageResource(imageResource);
			}
		    
		    return rowView;
		  }
	}