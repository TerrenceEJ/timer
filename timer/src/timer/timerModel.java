package timer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JLabel;

public class timerModel {
	static int interval = 0;
	static Timer time;
	
	private static int countDown;
	
	public int getCountDown() {
		return interval;
	}
	
	public void getCurrentCount(int seconds) {
		time = new Timer();
		int delay = 1000;
		int period = 1000;
		interval = seconds;
		if(seconds > 0) {
			time.schedule(new TimerTask() {
				public void run() {
					setInterval();
				}
			}, delay, period);
		}
		else { //in case user leaves every field blank
			interval = 1;
			time.schedule(new TimerTask() {
				public void run() {
					setInterval();
				}
			}, delay, period);
		}
	}
	
	public void pause() {
		int now = getCountDown();
		interval = now; // grab the displaying countdown
		time.cancel();
	}
	
	public static final int setInterval() {
		if (interval == 1) 
			time.cancel();
		System.out.println(timerModel.interval);
			return --interval;
	}
	
	public <T> void update(final T object, final T value) {
		if(object instanceof JLabel) {
			String newValue = value.toString(); //make sure object passing through is converted into a String
			((JLabel) object).setText(newValue);
		}
		else {
			String newButton = value.toString();
			((JButton) object).setText(newButton); //making a case if object passing through is a Button or Label
		}
	}
	
	public void resume() {
		time = new Timer();
		int delay = 1000;
		int period = 1000;
		time.schedule(new TimerTask() {
			public void run() {
				setInterval();
			}
		}, delay, period);
	}
	
	public String convert(int interval) {
		String time = String.format("%02d : %02d : %02d", 
			    TimeUnit.SECONDS.toHours(interval) - 
				TimeUnit.SECONDS.toHours(TimeUnit.SECONDS.toHours(interval)),
				TimeUnit.SECONDS.toMinutes(interval) - 
				TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(interval)),
			    TimeUnit.SECONDS.toSeconds(interval) -
			    TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(interval))
			);
		
		return time;
	}
	
}
