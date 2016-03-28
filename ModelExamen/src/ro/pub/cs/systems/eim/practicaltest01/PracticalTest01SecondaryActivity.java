package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01SecondaryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practical_test01_secondary);
		
		// C - intentia de la tata 
		Intent intentFromParent = getIntent();
		if( intentFromParent != null ){
			 Bundle data = intentFromParent.getExtras();
			  if( data != null ){
				  String value1 = data.getString(Constants.Value1);
				  String value2 = data.getString(Constants.Value2);
				  
				  if( value1 == null )
					  value1 = "0";
				  
				  if( value2 == null )
					  value2 = "0";
				  
				  Log.d("Apl", value1 + " + " + value2);
				  int v1 = Integer.parseInt(value1);
				  int v2 = Integer.parseInt(value2);
				   v1 = v1 + v2;

				  final EditText number = (EditText)findViewById(R.id.suma);
				  number.setText( v1  + "" );
		  
				  // butoanele
				  BtnListener btnListener = new BtnListener();
					
					Button b5,b6;
					b5 = (Button)findViewById(R.id.button_ok);
					b5.setOnClickListener(btnListener);
					b6 = (Button)findViewById(R.id.button_cancel);
					b6.setOnClickListener(btnListener);  
			  }
		}
		
	}
	
	// listener butoane . Aici se fac intentii catre parinte
	private class BtnListener implements OnClickListener{

		@Override
		public void onClick(View v) {
		
			Button b = (Button)v;
			String buttonName =  b.getText().toString();
			int bId = b.getId();
			
			if( bId == R.id.button_ok ){
				//ok
				Intent intentToParent = new Intent();
				setResult(Activity.RESULT_OK, intentToParent);
				finish();

				
			}else if( bId == R.id.button_cancel ){
				//cancel
				Intent intentToParent = new Intent();
				setResult(Activity.RESULT_CANCELED, intentToParent);
				finish();
				
			}

	
			 
			
		}
		
	}
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.practical_test01_secondary, menu);
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
}
