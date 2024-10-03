
/*
 * Christopher Gross
 * 
 * Creates stop lights. cycles through different states based on user inputs and pause button
 */

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

public class StopLight extends JComponent implements Runnable{
	int x;
	int y;
	int greenDuration;
	int yellowDuration;
	int redDuration;
	int secondsLeft;
	Color currentColor = Color.green;
	boolean isRunning = true;
	
	public StopLight(int x, int y, int greenDuration, int yellowDuration, int redDuration) {
		System.out.println("creating new light");
		this.x = x;
		this.y = y;
		this.greenDuration = greenDuration;
		this.yellowDuration = yellowDuration;
		this.redDuration = redDuration;
	}
	
	public void paintComponent(Graphics g) {
		//System.out.println("in sl pc");
		super.paintComponent(g);
		g.setColor(currentColor);
		g.fillOval(x, y, 20, 20);
		g.setColor(Color.black);
		g.drawString(String.valueOf(secondsLeft), x+4, y-4);
	}

	@Override
	public void run() {
		// no check isRunning
		while (true) {

			currentColor = Color.green;
			//System.out.println("color green");
			secondsLeft = greenDuration;
			try {
				Thread.sleep(greenDuration*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(!isRunning) { //if paused do not go to next color
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			currentColor = Color.yellow;
			//System.out.println("color yellow");
			secondsLeft = yellowDuration;
			try {
				Thread.sleep(yellowDuration*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(!isRunning) { //if paused do not go to next color
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			currentColor = Color.red;
			//System.out.println("color red");
			secondsLeft = redDuration;
			try {
				Thread.sleep(redDuration*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(!isRunning) { //if paused do not go to next color
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void setIsRunning(boolean b) {
		isRunning = b;
	}
	
	public int checkRed() {
		if (currentColor == Color.red) {
			return x;
		}
		return -777; //if not red give negative number far away from x-values cars run on.
	}
	
	public void decrementLightCount() {
		secondsLeft--;
	}
}
