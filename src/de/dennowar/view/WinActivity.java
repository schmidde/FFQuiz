package de.dennowar.view;

import de.dennowar.R;
import de.dennowar.plain.ActivityRegistry;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WinActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win);
        ActivityRegistry.register(this);
        
        Button btnNeu = (Button) findViewById(R.id.btn_win);
        Button btnEnd = (Button) findViewById(R.id.btn_ende);
        
        btnNeu.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		startActivity(new Intent(WinActivity.this, StartActivity.class));
        	}
        });
        btnEnd.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		ActivityRegistry.finishAll();
        	}
        });
    }
}