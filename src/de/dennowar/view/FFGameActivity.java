package de.dennowar.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.dennowar.R;
import de.dennowar.db.DataBaseHelper;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

public class FFGameActivity extends Activity {
    /** Called when the activity is first created. */
	private Context ctx = this;
	private DataBaseHelper mAdapter;
	private Cursor c;
	private String antwort = null;
	
	TextView tv;
	RadioButton rba;
	RadioButton rbb;
	RadioButton rbc;
	RadioButton rbd;
	RadioGroup rg_antwort;
	Button btn_antwort;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mAdapter = new DataBaseHelper(this);
        
        try {
			mAdapter.createDataBase();
			mAdapter.openDataBase();
			
			c = mAdapter.fetchQuestions(1);
			
			tv = (TextView) findViewById(R.id.tv_frage);
			rba = (RadioButton) findViewById(R.id.rb_a);
			rbb = (RadioButton) findViewById(R.id.rb_b);
			rbc = (RadioButton) findViewById(R.id.rb_c);
			rbd = (RadioButton) findViewById(R.id.rb_d);
			rg_antwort = (RadioGroup) findViewById(R.id.rg_antwort);
			btn_antwort = (Button) findViewById(R.id.btn_antwort);
			
			tv.setText(c.getString(c.getColumnIndex("frage")));
			rba.setText(c.getString(c.getColumnIndex("a")));
			rbb.setText(c.getString(c.getColumnIndex("b")));
			rbc.setText(c.getString(c.getColumnIndex("c")));
			rbd.setText(c.getString(c.getColumnIndex("d")));
			
			rg_antwort.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					Log.i("Checked: ", ""+checkedId);
					switch(checkedId){
					case R.id.rb_a:
						antwort = "a";
						break;
					case R.id.rb_b:
						antwort = "b";
						break;
					case R.id.rb_c:
						antwort = "c";
						break;
					case R.id.rb_d:
						antwort = "d";
						break;
					}
					Log.i("Antwort: ", antwort);
				}
			});
			btn_antwort.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
										
					if(c.getString(c.getColumnIndex("richtig")).equals(antwort)){
						Log.i("Richtig: ", antwort);
						if(c.moveToNext()){
							
							rg_antwort.clearCheck();
							tv.setText(c.getString(c.getColumnIndex("frage")));
							rba.setText(c.getString(c.getColumnIndex("a")));
							rbb.setText(c.getString(c.getColumnIndex("b")));
							rbc.setText(c.getString(c.getColumnIndex("c")));
							rbd.setText(c.getString(c.getColumnIndex("d")));
							
						}
						else c.moveToFirst();
					}
					else{
						Log.i("Falsch: ", antwort);
						CharSequence s = "Falsche Antwort! Versuchs nochmal.";
						Toast t = Toast.makeText(ctx, s, Toast.LENGTH_SHORT);
						t.show();
					}					
				}
			});
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
}