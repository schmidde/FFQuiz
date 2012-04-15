package de.dennowar.view;

import de.dennowar.R;
import de.dennowar.plain.ActivityRegistry;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class InitActivity extends Activity {
	
	private int grad = 2;
	private int maxBarValue = 100;
	private int delay = 60;
	
	ProgressThread progThread;
    ProgressDialog progDialog;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init);
        ActivityRegistry.register(this);
        
        showDialog(0);
    }
    
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // Get the current value of the variable total from the message data
            // and update the progress bar.
            int total = msg.getData().getInt("total");
            progDialog.setProgress(total);
            if (total >= maxBarValue){
                dismissDialog(0);
                progThread.setState(ProgressThread.DONE);
                startActivity(new Intent(InitActivity.this, StartActivity.class));
                finish();
            }
        }
    };
    
    // Method to create a progress bar dialog of either spinner or horizontal type
    @Override
    protected Dialog onCreateDialog(int id) {
        switch(id) {
        case 0:                      // Spinner
            progDialog = new ProgressDialog(this);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setMessage("Loading...");
            progThread = new ProgressThread(handler);
            progThread.start();
            return progDialog;
        case 1:                      // Horizontal
            progDialog = new ProgressDialog(this);
            progDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progDialog.setMax(maxBarValue);
            progDialog.setMessage("Loading Database...");
            progThread = new ProgressThread(handler);
            progThread.start();
            return progDialog;
        default:
            return null;
        }
    }
    
    private class ProgressThread extends Thread {	
        
        // Class constants defining state of the thread
        final static int DONE = 0;
        final static int RUNNING = 1;
        
        Handler mHandler;
        int mState;
        int total;
    
        // Constructor with an argument that specifies Handler on main thread
        // to which messages will be sent by this thread.
        
        ProgressThread(Handler h) {
            mHandler = h;
        }
        
        // Override the run() method that will be invoked automatically when 
        // the Thread starts.  Do the work required to update the progress bar on this
        // thread but send a message to the Handler on the main UI thread to actually
        // change the visual representation of the progress. In this example we count
        // the index total down to zero, so the horizontal progress bar will start full and
        // count down.
        
        @Override
        public void run() {
            mState = RUNNING;   
            total = 0;
            while (mState == RUNNING) {
                // The method Thread.sleep throws an InterruptedException if Thread.interrupt() 
                // were to be issued while thread is sleeping; the exception must be caught.
                try {
                    // Control speed of update (but precision of delay not guaranteed)
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    Log.e("ERROR", "Thread was Interrupted");
                }
                
                // Send message (with current value of  total as data) to Handler on UI thread
                // so that it can update the progress bar.
                
                Message msg = mHandler.obtainMessage();
                Bundle b = new Bundle();
                b.putInt("total", total);
                msg.setData(b);
                mHandler.sendMessage(msg);
                
                total++;    // Count down
            }
        }
        
        // Set current state of thread (use state=ProgressThread.DONE to stop thread)
        public void setState(int state) {
            mState = state;
        }
    }
}