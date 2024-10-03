
/*
 * Christopher Gross
 * 
 * Clock class creates timer to run during simulation
 */

import javax.swing.*;

public class Clock implements Runnable{
	JLabel clockField;
	boolean isRunning = true;

	public Clock(JLabel clockField) {
		this.clockField = clockField;
	}
	
	@Override
	public void run() {
		
		int minutes = 0;
		int seconds = 0;
		
		while(true) {
			String timer = String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
			clockField.setText(timer);
			
			try {
				Thread.sleep(1000);
			}
			catch(InterruptedException e) {
				System.out.println("clock thread has been interrupted");
			}
			
			if (isRunning) { //skip this if program is in paused state.
				seconds++;
				if (seconds==60) {
					minutes++;
					seconds = 0;
				}
			}
		}
	}
	
	public void setIsRunning(boolean b) {
		isRunning = b;
	}

}
