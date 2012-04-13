package de.dennowar.view;

import de.dennowar.R;
import de.dennowar.plain.ActivityRegistry;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LoseActivity extends Activity {
    /** Called when the activity is first created. */
	int runde;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lose);
        ActivityRegistry.register(this);
        
        runde = getIntent().getIntExtra("de.dennowar.view.runde", 1);
        
        TextView tvl = (TextView) findViewById(R.id.tv_lose);
        tvl.append("\n\nDu hast gerade einmal " + (runde-2) + " Runden geschafft!");
        
        Button btnNeu = (Button) findViewById(R.id.btn_lose);
        Button btnEnd = (Button) findViewById(R.id.btn_ende);
        
        btnNeu.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		startActivity(new Intent(LoseActivity.this, StartActivity.class));
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