package de.dennowar.view;

import de.dennowar.R;
import de.dennowar.plain.ActivityRegistry;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        ActivityRegistry.register(this);
        
        Button btn = (Button) findViewById(R.id.btn_los);
        
        btn.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		startActivity(new Intent(StartActivity.this, FFGameActivity.class));
        	}
        });
    }
}