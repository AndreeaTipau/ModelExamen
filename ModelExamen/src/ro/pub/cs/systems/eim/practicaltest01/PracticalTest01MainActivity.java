package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class PracticalTest01MainActivity extends Activity {

	public boolean serviciuFlag = false;
	
	
	
	
	private class BtnListener implements OnClickListener{
		
		

		@Override
		public void onClick(View v) {
			
			// iau informatii BUTON 
			Button b = (Button)v;
			String buttonName =  b.getText().toString();
			int bId = b.getId();
			
			// informatii TEXT
			EditText editText1 = (EditText)findViewById(R.id.editText1);
			String value1 = editText1.getText().toString();
			int nr1 = Integer.parseInt(value1);
			
			
			EditText editText2 = (EditText)findViewById(R.id.editText2);
			String value2 = editText2.getText().toString();
			int nr2 = Integer.parseInt(value2);
			

			
			// B.1 -  setez pentru butoane			
			if( bId == R.id.button1 ){
				nr1++;
				editText1.setText(nr1 + "");
				
			}else if( bId == R.id.button2 ){
				nr2++;
				editText2.setText(nr2 + "");
				
			}
			//..............................
			
	
			// C - intentie catre cealalta activitate
			if( bId == R.id.button_Navigate ){
				Intent intent;
				intent = new Intent("ro.pub.cs.systems.eim.intent.action.PracticalTest01SecondaryActivity");
				intent.putExtra(Constants.Value1, value1);
				intent.putExtra(Constants.Value2, value2);
      		  	startActivityForResult(intent,Constants.COD_INTENTIE);
			}
			//.....................................
			 
			// D -  intentie pentru serviciu
			int sum = nr1 + nr2;
			if( sum >= Constants.Prag && serviciuFlag == false ){
				serviciuFlag = true;
				
				Intent intentService = new Intent(getBaseContext(), PracticalTest01Service.class);
				intentService.putExtra("Value1", nr1);
				intentService.putExtra("Value2", nr2);
				startService(intentService);
				
			}
			
			
		}
		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practical_test01_main);
		
		//B.2 restaurare date
		if( savedInstanceState != null ){
			if (savedInstanceState.containsKey(Constants.EditText1)) {
				EditText EditText1 = (EditText)findViewById(R.id.editText1);
				EditText1.setText(savedInstanceState.getString(Constants.EditText1));
			}
			if (savedInstanceState.containsKey(Constants.EditText2)) {
				EditText EditText2 = (EditText)findViewById(R.id.editText2);
				EditText2.setText(savedInstanceState.getString(Constants.EditText2));
			}
			
		}
		//..............................................
		
		
		
		BtnListener btnListener = new BtnListener();
		Button b1,b2,b3;
		
		//B.1
		b1 = (Button)findViewById(R.id.button1);
		b1.setOnClickListener(btnListener);
		b2 = (Button)findViewById(R.id.button2);
		b2.setOnClickListener(btnListener);
		//.................................
	
		//C
		b3 = (Button)findViewById(R.id.button_Navigate);
		b3.setOnClickListener(btnListener);
		//...................................
		
	
	}

	
	
	// B.2 RESTAURARE DATE
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Log.d(Constants.LOGTAG, "onRestoreInstanceState() method was invoked");
		
		if (savedInstanceState.containsKey(Constants.EditText1)) {
			EditText EditText1 = (EditText)findViewById(R.id.editText1);
			EditText1.setText(savedInstanceState.getString(Constants.EditText1));
		}
		if (savedInstanceState.containsKey(Constants.EditText2)) {
			EditText EditText2 = (EditText)findViewById(R.id.editText2);
			EditText2.setText(savedInstanceState.getString(Constants.EditText2));
		}
	}
	
	// B.2 SALVARE DATE
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
	  super.onSaveInstanceState(savedInstanceState);
	  
	  EditText EditText1 = (EditText)findViewById(R.id.editText1);
	  EditText EditText2 = (EditText)findViewById(R.id.editText2);
	  
	  savedInstanceState.putString(Constants.EditText1,EditText1.getText().toString());
	  savedInstanceState.putString(Constants.EditText2,EditText2.getText().toString());

	  Log.d(Constants.LOGTAG, "onSaveInstanceState() method was invoked");  
	 
	  
	  	}
	
	// C - parintele trateaza raspunsul de la copil
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		  switch(requestCode) {
		    case Constants.COD_INTENTIE:
		      if (resultCode == Activity.RESULT_OK) {
		    	  Toast.makeText(this, "ok", Toast.LENGTH_LONG).show();
		    	  Log.d("Apl", "ok");
		    	 
		      } else {
		    	  Toast.makeText(this, "Cancel", Toast.LENGTH_LONG).show();
		    	  
		      }
		      break;
		  }
		
		}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.practical_test01_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// D - partea de broadcast
	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Constants.ACTION_1);
		intentFilter.addAction(Constants.ACTION_2);
		intentFilter.addAction(Constants.ACTION_3);
		registerReceiver(receiver, intentFilter);
	}
	private CustomReceiver receiver = new CustomReceiver();
	
	public class CustomReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			// FAC CE CERE EXERCITIUL
			Log.d(Constants.LOGTAG, action);			
			Log.d(Constants.LOGTAG, intent.getStringExtra("message"));
		}
		
	}
	//.....................................................
	
	
	
}
