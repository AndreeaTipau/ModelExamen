package ro.pub.cs.systems.eim.practicaltest01;

import java.util.Date;
import java.util.Random;
 
import android.content.Context;
import android.content.Intent;
import android.util.Log;
 
public class ProcessingThread extends Thread {
 
  private Context context = null;
  private boolean isRunning = true;
 
  private Random random = new Random();
 
  private double arithmeticMean;
  private double geometricMean;
 
  public ProcessingThread(Context context, int firstNumber, int secondNumber) {
    this.context = context;
 
    // ceea ce au cerut la cerinta
    arithmeticMean = (firstNumber + secondNumber) / 2;
    geometricMean = Math.sqrt(firstNumber * secondNumber);
  }
 
  @Override
  public void run() {
	  
    Log.d(Constants.LOGTAG, "Thread has started!");
    
    while (isRunning) {
    	
      // odata la 10 secunde trimite mesajul 	
      sendMessage();
      sleep();
    }
    
    Log.d(Constants.LOGTAG, "Thread has stopped!");
  }
 
  private void sendMessage() {
  
	  // noua intentie catre aplicatie
	  Intent intent = new Intent();
      intent.setAction(Constants.ACTION_TYPE[random.nextInt(Constants.ACTION_TYPE.length)]);
      intent.putExtra("message", new Date(System.currentTimeMillis()) + " " + arithmeticMean + " " + geometricMean);
    
      context.sendBroadcast(intent);
  }
 
  private void sleep() {
    try {
    // aici doarme 10 secunde
      Thread.sleep(10000);
    } catch (InterruptedException interruptedException) {
      interruptedException.printStackTrace();
    }
  }
 
  public void stopThread() {
    isRunning = false;
  }
}
