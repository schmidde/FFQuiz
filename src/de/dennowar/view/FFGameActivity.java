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
	private int pos, runde = 0;
	private Context ctx = this;
	private DataBaseHelper mAdapter;
	private Cursor c;
	private String antwort = null;
	private List<Integer> list = new ArrayList<Integer>();
	
	private TextView tv_frage;
	private RadioButton rba;
	private RadioButton rbb;
	private RadioButton rbc;
	private RadioButton rbd;
	private RadioGroup rg_antwort;
	private Button btn_antwort;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tv_frage = (TextView) findViewById(R.id.tv_frage);
		rba = (RadioButton) findViewById(R.id.rb_a);
		rbb = (RadioButton) findViewById(R.id.rb_b);
		rbc = (RadioButton) findViewById(R.id.rb_c);
		rbd = (RadioButton) findViewById(R.id.rb_d);
		rg_antwort = (RadioGroup) findViewById(R.id.rg_antwort);
		btn_antwort = (Button) findViewById(R.id.btn_antwort);
        
        mAdapter = new DataBaseHelper(this);
        
        try {
			mAdapter.createDataBase();
			mAdapter.openDataBase();
			
			pos = getRandPos(c.getCount());
			c = mAdapter.fetchQuestions(1);
			
			c.moveToPosition(pos);
						
			tv_frage.setText((runde+1)+". "+c.getString(c.getColumnIndex("frage")));
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
									
					if(c.getString(c.getColumnIndex("richtig")).equals(antwort) && (runde < 15)){
						runde++;
						Log.i("Richtig: ", antwort);
						
						while(isNotNew(pos = getRandPos(c.getCount()))){}
						
						if(c.moveToPosition(pos)){
							
							rg_antwort.clearCheck();
							tv_frage.setText((runde+1)+". "+c.getString(c.getColumnIndex("frage")));
							rba.setText(c.getString(c.getColumnIndex("a")));
							rbb.setText(c.getString(c.getColumnIndex("b")));
							rbc.setText(c.getString(c.getColumnIndex("c")));
							rbd.setText(c.getString(c.getColumnIndex("d")));
						}
						else {Log.w("Oh oh, moveToPosition ist -1", ""); c.moveToFirst();}
					}
					else{
						Log.i("Anwort ist falsch: ", antwort);
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
    
    public void show(){
		
	}
    
    private int getRandPos(int count){
    	double d = Math.random();
    	d = d*100;
    	d= d/(100/count);
    	int i = (int)d;
 
    	return i;
    }
    private boolean isNotNew(int position){
    	boolean res = false;
    	
    	if(list.contains(position)){
    		Log.i("bereits vorhanden: ", ""+position);
    		res = true;
    	}
    	else {
    		Log.i("NEU: ", ""+position);
    		list.add(position);
    	}
    	return res;
    }
}