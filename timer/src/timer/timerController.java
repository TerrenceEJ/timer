package timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

public class timerController {
	private timerView theView;
	private timerModel theModel;
	private String updateText = "Start";
	//static Timer time;
	static Timer update;
	boolean isPressed = false;
	boolean finished = false;
	
	public static int inputSeconds = 0; //avoid calling multiple times
	public static int inputMinutes = 0;
	public static int inputHours = 0;
	
	public timerController(timerView theView, timerModel theModel) {
		this.theView = theView;
		this.theModel = theModel;
		
		//Tell the view that when ever the timer button is clicked to execute the actionPerformed method in the timerListener inner class
		this.theView.startTimerListener(new timerListener());
		this.theView.restartListener(new restartListener());
		
		this.theView.startPropertyChangeListener(new propertyListener());
		update = new Timer();
		int delay = 10;
		int period = 10;
		update.schedule(new TimerTask() { //constantly look for changes (this is the main UI handler)
			public void run() {
					theModel.update(theView.getStartTimer(), updateText);
					theModel.update(theView.getDisplaySeconds(), theModel.convert(timerModel.interval));
					System.out.println(theModel.convert(timerModel.interval));
			}
		}, delay, period);
	}
	
	class timerListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String setup = theView.getStartTimer().getText();
			
			//if(setup == "Start" && theView.getRestartTimer().getText() == "Reset" && finished) {
				//System.out.println("Do Nothing");
			//}
			
			if(!isPressed && setup == "Start") {
				try {
					
					updateText = "Stop"; //setup for pause functionality
					
					inputSeconds = theView.getInput();
					inputMinutes = theView.getInputM();
					inputHours = theView.getInputH();
		
					int total = inputSeconds + (inputMinutes * 60) + (inputHours * 3600);
					theModel.getCurrentCount(total);
					theView.setDisplayInput(timerModel.interval); //initialize timer	
					
					isPressed = true; // effectively makes startTimer into a toggle stop/start timer
	
					} catch(NumberFormatException ex) {
						System.out.println(ex);
						theView.displayErrorMessage("You need to enter an integer");
						updateText = "Start";
						isPressed = false;
					}
			}
			
			if(isPressed && setup == "Stop") {
					updateText = "Resume";
				 	theModel.pause(); //completely stop main timer and grab the current time
			}							
			
			if(setup == "Resume" && timerModel.interval != 0) {
					updateText = "Stop";
					theModel.resume();
			}
		}
	}
	
	class restartListener implements ActionListener{
		public void actionPerformed(ActionEvent click) {
			String setup = theView.getStartTimer().getText();
			
			if(setup == "Resume") {
				updateText = "Stop";
			}
			
			try {
				theModel.time.cancel(); //stop timer
				
				inputSeconds = theView.getInput();
				inputMinutes = theView.getInputM();
				inputHours = theView.getInputH();
	
				int total = inputSeconds + (inputMinutes * 60) + (inputHours * 3600);
				timerModel.interval = total; //set interval to input field so main gui timer can read status of interval
				
				updateText = "Start";
				isPressed = false;
				
			} catch (NumberFormatException e) { //check for blank if user decides to leave input box blank
				theModel.time.cancel();
				inputSeconds = 0;
				timerModel.interval = inputSeconds; 
				updateText = "Start";
				isPressed = false;
			}
		}
		
	}
	
	class propertyListener implements PropertyChangeListener{
		public void propertyChange(PropertyChangeEvent event) {
			String setup = theView.getStartTimer().getText();
			
			//check for button text changing to stop and timer to hit 0
			if (setup == "Stop" && timerModel.interval == 0) {
				updateText = "Start";	
				isPressed = false;
			}
		}
	}
}