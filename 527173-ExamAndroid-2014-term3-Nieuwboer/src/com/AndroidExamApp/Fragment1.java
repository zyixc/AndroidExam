package com.AndroidExamApp;

import com.AndroidExamApp.data.MyDBHandler;
import com.AndroidExamApp.data.Product;
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
public class Fragment1 extends Fragment {
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	TextView idView;
	EditText productBox;
	EditText quantityBox;
	Context context;
	
	public static Fragment1 newInstance() {
		Fragment1 fragment = new Fragment1();				
		return fragment;
	}

	public Fragment1() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment1, container,	false);
		context = this.getActivity().getApplicationContext();
		
		idView = (TextView) rootView.findViewById(R.id.productID);
		productBox = (EditText) rootView.findViewById(R.id.productName);
		quantityBox = (EditText) rootView.findViewById(R.id.productQuantity);
		
		final Button buttonADD = (Button) rootView.findViewById(R.id.buttonADD);
        buttonADD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newProduct(v);
            }
        });
		
        final Button buttonFIND = (Button) rootView.findViewById(R.id.buttonFIND);
        buttonFIND.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lookupProduct(v);
            }
        });
		
        final Button buttonDELETE = (Button) rootView.findViewById(R.id.buttonDELETE);
        buttonDELETE.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeProduct(v);
            }
        });
		
		return rootView;
	}
	
	public void newProduct (View view) {
    	MyDBHandler dbHandler = new MyDBHandler(context, null, null, 1);
    	int quantity = 1;
    	if(!quantityBox.getText().toString().isEmpty())
    		quantity = Integer.parseInt(quantityBox.getText().toString());
    	Product product = new Product(productBox.getText().toString(), quantity);
    	dbHandler.addProduct(product);
	   	productBox.setText("");
	   	quantityBox.setText("");
    }
    
    public void lookupProduct (View view) {
    	MyDBHandler dbHandler = new MyDBHandler(context, null, null, 1);
    	
    	Product product = dbHandler.findProduct(productBox.getText().toString());
    
    	if (product != null) {
    		idView.setText(String.valueOf(product.getID()));
    		quantityBox.setText(String.valueOf(product.getQuantity()));
       	} else {
       		idView.setText("No Match Found");
       	}        	
    }
   
    public void removeProduct (View view) {
    	MyDBHandler dbHandler = new MyDBHandler(context, null, null, 1);
    	
    	boolean result = dbHandler.deleteProduct(productBox.getText().toString());
    	
    	if (result){
    		idView.setText("Record Deleted");
    		productBox.setText("");
    		quantityBox.setText("");
    	}
    	else{
    		idView.setText("No Match Found");
    	}
    }
}
