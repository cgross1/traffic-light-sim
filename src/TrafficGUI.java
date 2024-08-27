											
/*
 * UMGC
 * CMSC 335 / 7381 (2238)
 * Project 3
 * Christopher Gross
 * Dec 12, 2023
 * 
 * TrafficGUI class creates GUI and generates simulation based on user inputs
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.*;


public class TrafficGUI extends JFrame implements ActionListener{
	
	JFrame frame;
	
	JPanel mainPanel;
	JPanel updatedMainPanel;
	SimPanel simPanel;
	JPanel car1OptionsPanel;
	JPanel car2OptionsPanel;
	JPanel car3OptionsPanel;
	JPanel light1OptionsPanel;
	JPanel light2OptionsPanel;
	JPanel light3OptionsPanel;
	
	String numCarsChoice[] = {"1", "2", "3"};
	String numLightsChoice[] = {"2", "3"};
	
	//JComboBox chooseCars = new JComboBox(numCarsChoice);
	//JComboBox chooseLights = new JComboBox(numLightsChoice);
	
	JLabel chooseCarsLabel = new JLabel("Number of Cars");
	JLabel chooseLightsLabel = new JLabel("Number of Lights");
	JLabel clockField;
	JLabel car1Label = new JLabel("Car 1      ");
	JLabel car2Label = new JLabel("Car 2      ");
	JLabel car3Label = new JLabel("Car 3      ");
	JLabel car1SpeedLabel = new JLabel("Choose Speed:     ");
	JLabel car2SpeedLabel = new JLabel("Choose Speed:     ");
	JLabel car3SpeedLabel = new JLabel("Choose Speed:     ");
	JLabel light1Label = new JLabel("Light 1         ");
	JLabel light2Label = new JLabel("Light 2         ");
	JLabel light3Label = new JLabel("Light 3         ");
	JLabel light1GreenDuration = new JLabel("Seconds Green:  ");
	JLabel light1YellowDuration = new JLabel("Seconds Yellow:  ");
	JLabel light1RedDuration = new JLabel("Seconds Red:  ");
	JLabel light2GreenDuration = new JLabel("Seconds Green:  ");
	JLabel light2YellowDuration = new JLabel("Seconds Yellow:  ");
	JLabel light2RedDuration = new JLabel("Seconds Red:  ");
	JLabel light3GreenDuration = new JLabel("Seconds Green:  ");
	JLabel light3YellowDuration = new JLabel("Seconds Yellow:  ");
	JLabel light3RedDuration = new JLabel("Seconds Red:  ");
	
	JTextField car1SpeedField = new JTextField("5", 3);
	JTextField car2SpeedField = new JTextField("8", 3);
	JTextField car3SpeedField = new JTextField("10", 3);
	JTextField light1GreenField = new JTextField("3", 3);
	JTextField light1YellowField = new JTextField("3", 3);
	JTextField light1RedField = new JTextField("40", 3);
	JTextField light2GreenField = new JTextField("3", 3);
	JTextField light2YellowField = new JTextField("3", 3);
	JTextField light2RedField = new JTextField("40", 3);
	JTextField light3GreenField = new JTextField("3", 3);
	JTextField light3YellowField = new JTextField("3", 3);
	JTextField light3RedField = new JTextField("40", 3);
	
	JCheckBox car2CheckBox = new JCheckBox();
	JCheckBox car3CheckBox = new JCheckBox();
	JCheckBox light2CheckBox = new JCheckBox();
	JCheckBox light3CheckBox = new JCheckBox();
	
	JButton startButton;
	JButton pauseButton;

	Clock clock;
	
	Car car1;
	Car car2;
	Car car3;
	
	StopLight light1;
	StopLight light2;
	StopLight light3;
	
	Thread clockThread;
	Thread car1Thread;
	Thread car2Thread;
	Thread car3Thread;
	Thread light1Thread;
	Thread light2Thread;
	Thread light3Thread;
	Thread simPanelThread;
	
	//array lists are not used
	ArrayList<StopLight> lights = new ArrayList<>();
    ArrayList<Car> cars = new ArrayList<>();
	
	boolean isRunning = false;
	
	int numCars;
	int numLights;
	int lightY = 80;
	int carX = 10;
	int carY = 95;
	
	Color carPanelColor = new Color(237,203,128);
	Color lightPanelColor = new Color(159,227,159);
	
	
	public TrafficGUI() {
		//frame settings
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,400);
		frame.setLayout(new FlowLayout());
		frame.setTitle("Traffic Analyzer");
		
		/*//old main panel
		mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());
		mainPanel.setPreferredSize(new Dimension(1000,100));
		mainPanel.setBackground(Color.gray);
		*/
		updatedMainPanel = new JPanel();
		updatedMainPanel.setLayout(new FlowLayout());
		updatedMainPanel.setPreferredSize(new Dimension(1000,150));
		updatedMainPanel.setBackground(Color.gray);
		
		car1OptionsPanel = new JPanel();
		car1OptionsPanel.setBackground(carPanelColor);
		car1OptionsPanel.add(car1Label);
		car1OptionsPanel.add(car1SpeedLabel);
		car1OptionsPanel.add(car1SpeedField);
		
		car2OptionsPanel = new JPanel();
		car2OptionsPanel.setBackground(carPanelColor);
		car2OptionsPanel.add(car2CheckBox);
		car2OptionsPanel.add(car2Label);
		car2OptionsPanel.add(car2SpeedLabel);
		car2OptionsPanel.add(car2SpeedField);
		
		car3OptionsPanel = new JPanel();
		car3OptionsPanel.setBackground(carPanelColor);
		car3OptionsPanel.add(car3CheckBox);
		car3OptionsPanel.add(car3Label);
		car3OptionsPanel.add(car3SpeedLabel);
		car3OptionsPanel.add(car3SpeedField);
		
		light1OptionsPanel = new JPanel();
		light1OptionsPanel.setBackground(lightPanelColor);
		light1OptionsPanel.add(light1Label);
		light1OptionsPanel.add(light1GreenDuration);
		light1OptionsPanel.add(light1GreenField);
		light1OptionsPanel.add(light1YellowDuration);
		light1OptionsPanel.add(light1YellowField);
		light1OptionsPanel.add(light1RedDuration);
		light1OptionsPanel.add(light1RedField);
		
		light2OptionsPanel = new JPanel();
		light2OptionsPanel.setBackground(lightPanelColor);
		light2OptionsPanel.add(light2CheckBox);
		light2OptionsPanel.add(light2Label);
		light2OptionsPanel.add(light2GreenDuration);
		light2OptionsPanel.add(light2GreenField);
		light2OptionsPanel.add(light2YellowDuration);
		light2OptionsPanel.add(light2YellowField);
		light2OptionsPanel.add(light2RedDuration);
		light2OptionsPanel.add(light2RedField);
		
		light3OptionsPanel = new JPanel();
		light3OptionsPanel.setBackground(lightPanelColor);
		light3OptionsPanel.add(light3CheckBox);
		light3OptionsPanel.add(light3Label);
		light3OptionsPanel.add(light3GreenDuration);
		light3OptionsPanel.add(light3GreenField);
		light3OptionsPanel.add(light3YellowDuration);
		light3OptionsPanel.add(light3YellowField);
		light3OptionsPanel.add(light3RedDuration);
		light3OptionsPanel.add(light3RedField);
		
		//button
		startButton = new JButton("Start");
		pauseButton = new JButton("Pause");
		
		//clock thread
		clockField = new JLabel("00:00");
		clock = new Clock(clockField);
		clockThread = new Thread(clock);
		
		//action listeners
		startButton.addActionListener(this);
		pauseButton.addActionListener(this);
		
		//add to panel & frame
		/* old main panel
		mainPanel.add(chooseCarsLabel);
		mainPanel.add(chooseCars);
		mainPanel.add(chooseLightsLabel);
		mainPanel.add(chooseLights);
		mainPanel.add(startButton);
		frame.add(mainPanel);
		*/
		updatedMainPanel.add(car1OptionsPanel);
		updatedMainPanel.add(car2OptionsPanel);
		updatedMainPanel.add(car3OptionsPanel);
		updatedMainPanel.add(light1OptionsPanel);
		updatedMainPanel.add(light2OptionsPanel);
		updatedMainPanel.add(light3OptionsPanel);
		updatedMainPanel.add(startButton);
		frame.add(updatedMainPanel);
		frame.setVisible(true);
	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) {

		if (e.getSource() == startButton) {
			System.out.println("starting");
			/*  old implementation
			numCars = Integer.valueOf(numCarsChoice[chooseCars.getSelectedIndex()]);
			System.out.println("numCars: " + numCars);
			numLights = Integer.valueOf(numLightsChoice[chooseLights.getSelectedIndex()]);
			System.out.println("num cars: " + numCars);
			System.out.println("num lights: " + numLights);
			*/

			light1 = new StopLight(250,lightY, Integer.valueOf(light1GreenField.getText()), 
					Integer.valueOf(light1YellowField.getText()), Integer.valueOf(light1RedField.getText()));
			light1Thread = new Thread(light1);
			light1Thread.start();
			lights.add(light1);
			
			if (light2CheckBox.isSelected()) {
				System.out.println("light2 box selected");
				//light2 = new StopLight(500,lightY);
				light2 = new StopLight(500,lightY, Integer.valueOf(light2GreenField.getText()), 
						Integer.valueOf(light2YellowField.getText()), Integer.valueOf(light2RedField.getText()));
				light2Thread = new Thread(light2);
				light2Thread.start();
				lights.add(light2);
			}
			if (light3CheckBox.isSelected()) {
				System.out.println("light3 box selected");
				//light3 = new StopLight(750,lightY);
				light3 = new StopLight(750,lightY, Integer.valueOf(light3GreenField.getText()), 
						Integer.valueOf(light3YellowField.getText()), Integer.valueOf(light3RedField.getText()));
				light3Thread = new Thread(light3);
				light3Thread.start();
				lights.add(light3);
			}
			
			car1 = new Car(carX, carY, Integer.valueOf(car1SpeedField.getText()), this, 1);
			car1Thread = new Thread(car1);
			car1Thread.start();
			cars.add(car1);
			
			if (car2CheckBox.isSelected()) {
				car2 = new Car(carX, carY, Integer.valueOf(car2SpeedField.getText()), this, 2);
				car2Thread = new Thread(car2);
				car2Thread.start();
				cars.add(car2);
			}
			if (car3CheckBox.isSelected()) {
				car3 = new Car (carX, carY, Integer.valueOf(car3SpeedField.getText()), this, 3);
				car3Thread = new Thread(car3);
				car3Thread.start();
				cars.add(car3);
			}

			updatedMainPanel.removeAll();
			updatedMainPanel.setBackground(Color.pink);
			updatedMainPanel.setPreferredSize(new Dimension(1000,50));
			updatedMainPanel.add(pauseButton);
			updatedMainPanel.add(clockField);
			/*  old panel 
			mainPanel.removeAll();
			mainPanel.setBackground(Color.pink);
			mainPanel.add(pauseButton);
			mainPanel.add(clockField);
			*/
			//mainPanel
			
			synchronized(clockThread) {
				clockThread.start();
			}
			
			//mainPanel.revalidate();
			//revalidate();
			//mainPanel.repaint();
			//create lights here 
			//light 1 location is at 250
			//light 2 location is at 500
			//light 3 location is at 750
			
			
			simPanel = new SimPanel();
			simPanel.setPreferredSize(new Dimension(1000,300));
			frame.add(simPanel);
			simPanelThread = new Thread(simPanel);
			
			synchronized(simPanelThread) { 
				simPanelThread.start();
			}
		
			repaint();
			isRunning = true;
		}
		
		else if (e.getSource() == pauseButton) {
			if (isRunning) {
				System.out.println("pause sim");
				pauseButton.setText("Resume");
				pauseAllThreads();
				updatedMainPanel.revalidate();
				updatedMainPanel.repaint();
				isRunning = false;
			}
			else {
				System.out.println("resume sim");
				pauseButton.setText("Pause"); 
				resumeAllThreads();
				updatedMainPanel.revalidate();
				updatedMainPanel.repaint();
				isRunning = true;
			}
		}
	}
	
	public void pauseAllThreads() {
		clock.setIsRunning(false);
		light1.setIsRunning(false);
		car1.setIsRunning(false);
		if (Objects.nonNull(light2)) {light2.setIsRunning(false);}
		if (Objects.nonNull(light3)) {light3.setIsRunning(false);}
		if (Objects.nonNull(car2)) {car2.setIsRunning(false);}
		if (Objects.nonNull(car3)) {car3.setIsRunning(false);}
	}
	
	public void resumeAllThreads() {
		clock.setIsRunning(true);
		light1.setIsRunning(true);
		car1.setIsRunning(true);
		if (Objects.nonNull(light2)) {light2.setIsRunning(true);}
		if (Objects.nonNull(light3)) {light3.setIsRunning(true);}
		if (Objects.nonNull(car2)) {car2.setIsRunning(true);}
		if (Objects.nonNull(car3)) {car3.setIsRunning(true);}
	}
	
	public double[] checkForRedLights() {
		double[] redLightLocations = new double[3];
		//set to negative numbers to avoid false stop during first loop.
		redLightLocations[0] = -99;
		redLightLocations[1] = -99;
		redLightLocations[2] = -99;
		
		if (Objects.nonNull(light1)) {redLightLocations[0] = light1.checkRed();}
		if (Objects.nonNull(light2)) {redLightLocations[1] = light2.checkRed();}
		if (Objects.nonNull(light3)) {redLightLocations[2] = light3.checkRed();}
		
		return redLightLocations;
	}	

	
	//run traffic gui JFrame
	public static void main(String[] args) {
		new TrafficGUI();
	}
	
	
	//Class for traffic visualization panel
	public class SimPanel extends JPanel implements Runnable{
		Road road;
		
		public SimPanel() {
			setBackground(Color.white);
			road = new Road();
			add(road);
			add(light1); 
			add(car1);
			if (Objects.nonNull(car2)) {
				add(car2);
			}
			if (Objects.nonNull(car3)) {
				add(car3);
			}
			if(Objects.nonNull(light2)) {
				add(light2);
			}
			if(Objects.nonNull(light3)) {
				add(light3);
			}
			//repaint();
		}
		
		public void paint(Graphics g) {
			super.paint(g);

			road.paintComponent(g);
			light1.paintComponent(g);
			light1.decrementLightCount();
			car1.paintComponent(g);
			if (Objects.nonNull(car2)) {
				car2.paintComponent(g);
			}
			if (Objects.nonNull(car3)) {
				car3.paintComponent(g);
			}
			if(Objects.nonNull(light2)) {
				light2.paintComponent(g);
				light2.decrementLightCount();
				System.out.println("light2 painted");
			}
			if(Objects.nonNull(light3)) {
				light3.paintComponent(g);
				light3.decrementLightCount();
				System.out.println("light3 painted");
			}

		}

		@Override
		public void run() {

			while (true) {
				if (isRunning) {
					repaint();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
	//Class draws road line on simulation panel
	public class Road extends JComponent{
		
	    public void paintComponent(Graphics g){
		    g.drawLine(0, 100, 1000, 100);
	    }
	}
}
