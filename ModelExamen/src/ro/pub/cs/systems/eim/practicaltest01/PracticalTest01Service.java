package ro.pub.cs.systems.eim.practicaltest01;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
 
public class PracticalTest01Service extends Service {
 
  private ProcessingThread processingThread = null;
 
  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
	
	  Bundle data = intent.getExtras();
	  int value1 = data.getInt("Value1");
	  int value2 = data.getInt("Value2");
	  Log.d(Constants.LOGTAG, "in serviciu");
	  /* pornesc threadul */
	  processingThread = new ProcessingThread(this, value1, value2);
	  processingThread.start();
    
	  return Service.START_REDELIVER_INTENT;
  }
 
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
 
  @Override
  public void onDestroy() {
    processingThread.stopThread();
  }
 
}