
/* 
 * UMGC
 * CMSC 335 / 7381 (2238)
 * Project 3
 * Christopher Gross
 * Dec 12, 2023
 * 
 * Creates Car objects. Updates location and movement based on stop lights.
 */

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

public class Car extends JComponent implements Runnable{
	
	String name;
	double x;
	int y;
	double velocity; //speed and direction of car when traveling
	double stopBuffer; //area where cars need to stop at lights
	boolean isRunning = true;
	boolean carStopped = false;
	Color carColor;	//random color
	TrafficGUI trafficGUI;
	int carNumber;
	double currentSpeed;
	
	
	public Car(double x, int y, int velocity, TrafficGUI trafficGUI, int carNumber) {
		
		System.out.println("creating new car");
		this.x = x;
		this.y = y;
		this.velocity = velocity;
		stopBuffer = velocity/2;
		this.trafficGUI = trafficGUI;
		carColor = new Color((int)(Math.random() * 0x1000000));
		this.carNumber = carNumber;
		name = "Car " + Integer.toString(carNumber);
	}
	
	public void paintComponent(Graphics g) {
		//System.out.println("in car pc");
		super.paintComponent(g);
		g.setColor(carColor);
		g.fillRect((int)x, y, 10, 10);
		g.setColor(Color.black);
		g.drawString(name + ": " + getInformation(), 300, (120 + 30*carNumber));
		//System.out.println("car paint string above? " + (120 + (30*carNumber)));
	}

	@Override
	public void run() {

		
		while (true) {
			//System.out.println("running car");
			if (isRunning) {
				//double nextXLocation = x+velocity;
				
				//check for stop at new x
				double[] redLightLocations = trafficGUI.checkForRedLights();
				System.out.println(redLightLocations[0] + " " + redLightLocations[1] + " " + redLightLocations[2]);
				//if locations match stop that car until not red.
				
				//checks if car is close to red light when red light is on. If so, stop.
				if ((x+Math.abs(velocity) >= redLightLocations[0] && x-Math.abs(velocity) <= redLightLocations[0]) ||
						(x+Math.abs(velocity) >= redLightLocations[1] && x-Math.abs(velocity) <= redLightLocations[1]) ||
						x+Math.abs(velocity) >= redLightLocations[2] && x-Math.abs(velocity) <= redLightLocations[2]) {
					//System.out.println(x + " " + (x+velocity) + " " + (x-velocity));
					currentSpeed = 0;
					carStopped = true;
					
					System.out.println("stopping");
				}
				else {
					carStopped = false;
					System.out.println("moving car");
					//currentSpeed = velocity;
				}
				
				
				/*
				for (int i=0; i<redLightLocations.length; i++) {
					System.out.println(x + " " + (x+velocity) + " " + (x-velocity));
					if (x+velocity >= redLightLocations[i] && x-velocity <= redLightLocations[i]) {
						carStopped = true;
						System.out.println("stopping");
					}
					else {
						//isRunning = true;
						//carStopped = false;
						System.out.println("moving car");
					}
					
				}
				*/
				if (carStopped) {
					currentSpeed = 0;
				}
				else {
					x = x + velocity;
					currentSpeed = velocity;
				}
				
				//car in reverse does not stop at stop sign.
				if (x >=1000) {
					//turn around car to go in reverse
					velocity = velocity*-1;
				}
				if (x <= 0) {
					//turn around car to go right
					velocity = Math.abs(velocity);
				}
				
				
			}
			//this.repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void setIsRunning(boolean b) {
		isRunning = b;
		if (isRunning == false) { //does not update currentSpeed in gui when paused
			currentSpeed = 0;
		}
		else {
			currentSpeed = velocity;
		}
	}
	
	public String getInformation() {
		return "   Position: " + x + ".    Running Speed: " + velocity + " m/s." + "    Current Speed: " + currentSpeed + " m/s.";
	}

}
