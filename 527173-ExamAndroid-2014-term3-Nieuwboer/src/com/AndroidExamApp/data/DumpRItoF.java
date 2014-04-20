package com.AndroidExamApp.data;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.widget.Toast;

public class DumpRItoF extends Service {
	  private Looper mServiceLooper;
	  private ServiceHandler mServiceHandler;

	  public Context activityContext = this;
 
	  // Handler that receives messages from the thread
	  private final class ServiceHandler extends Handler {
	      public ServiceHandler(Looper looper) {
	          super(looper);
	      }
	      @Override
	      public void handleMessage(Message msg) {
	    	  MyDBHandler db = new MyDBHandler(activityContext, null, null, 1);
	    	  ArrayList<Party> result = db.getAll();
	    	   
	    	  String eol = System.getProperty("line.separator");
	    	  BufferedWriter writer = null;
	    	  try {
	    		  
	    		  writer = new BufferedWriter(new OutputStreamWriter(openFileOutput("result_DBdump.txt",Context.MODE_APPEND)));
	    		  Calendar c = Calendar.getInstance();
	    		  writer.write("This is DB dump ;" + c.getTime().toString()+ "; " +  eol);
	    		  
	    		  for(int i = 0; i<result.size(); i++){
	    				int ri = (result.get(i).get_plus_sign() - (result.get(i).get_equals_sign() / 10) - result.get(i).get_minus_sign());
	    			  	writer.write(result.get(i).get_name() + " " + ri + eol);
	    		  }
	    		  
	    	  } catch (Exception e) {
	    		  e.printStackTrace();
	    	  } finally {
	    		  if (writer != null) {
	    			  try {
	    				  writer.close();
	    			  } catch (IOException e) {
	    				  e.printStackTrace();
	    			  }
	    		  }
	    	  }
	          
	          // Stop the service using the startId, so that we don't stop
	          // the service in the middle of handling another job
	          stopSelf(msg.arg1);
	      }
	  }

	  @Override
	  public void onCreate() {
	    // Start up the thread running the service.  Note that we create a
	    // separate thread because the service normally runs in the process's
	    // main thread, which we don't want to block.  We also make it
	    // background priority so CPU-intensive work will not disrupt our UI.
	    HandlerThread thread = new HandlerThread("ServiceStartArguments",
	            Process.THREAD_PRIORITY_BACKGROUND);
	    thread.start();

	    // Get the HandlerThread's Looper and use it for our Handler
	    mServiceLooper = thread.getLooper();
	    mServiceHandler = new ServiceHandler(mServiceLooper);
	  }

	  @Override
	  public int onStartCommand(Intent intent, int flags, int startId) {
	      Toast.makeText(this, "service dumpRItoF starting", Toast.LENGTH_SHORT).show();

	      // For each start request, send a message to start a job and deliver the
	      // start ID so we know which request we're stopping when we finish the job
	      Message msg = mServiceHandler.obtainMessage();
	      msg.arg1 = startId;
	      mServiceHandler.sendMessage(msg);

	      // If we get killed, after returning from here, restart
	      return START_STICKY;
	  }

	  @Override
	  public IBinder onBind(Intent intent) {
	      // We don't provide binding, so return null
	      return null;
	  }

	  @Override
	  public void onDestroy() {
	    Toast.makeText(this, "service dumpRItoF done", Toast.LENGTH_SHORT).show();
	  }
	}