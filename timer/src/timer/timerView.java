package timer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class timerView extends JFrame{
	static Timer update;
	
	private JLabel minutesLabel = new JLabel("Minutes:");
	private JLabel hoursLabel = new JLabel("Hours:");
	private JLabel secondsLabel = new JLabel("Seconds:");
	private JTextArea inputHours = new JTextArea(1, 10);
	private JTextArea inputMinutes = new JTextArea(1, 10);
	private JTextArea inputSeconds = new JTextArea(1, 10);
	private JLabel displaySeconds = new JLabel(String.valueOf(timerModel.interval)); //so user knows where the timer shows
	private JButton startTimer = new JButton("Start");
	private JButton restartTimer = new JButton("Reset");
	
	timerView(){
		//Set up the view and add the components
		//JFrame frame = new JFrame("Timer");
		Container timerPanel = this.getContentPane();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//JFrame frame = new JFrame("BoxLayout");

		Box bv = Box.createVerticalBox();
		Box bh = Box.createHorizontalBox();
		timerPanel.add(Box.createHorizontalGlue());
		timerPanel.add(bh, BorderLayout.SOUTH);
		timerPanel.add(bv, BorderLayout.CENTER);
		
		bh.add(Box.createHorizontalStrut(35));//add spacing from borders
		
		bv.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
		
		bv.add(hoursLabel);
		bv.add(inputHours);
		
		bv.add(minutesLabel);
		bv.add(inputMinutes);
		
		bv.add(secondsLabel);
		bv.add(inputSeconds);
		
		bv.add(getDisplaySeconds());
		
		bh.add(getStartTimer());
		bh.add(getRestartTimer());
		//yPanel.add(getStartTimer());
		//yPanel.add(getRestartTimer());
		

		this.pack();
		this.setVisible(true);
	}
	
	public int getInput() {
		if(inputSeconds.getText().isEmpty()) {
			int seconds = 0;
			return seconds;
		}
		return Integer.parseInt(inputSeconds.getText());
	}
	
	public int getInputH() {
		if(inputHours.getText().isEmpty()) {
			int hours = 0;
			return hours;
		}
		return Integer.parseInt(inputHours.getText());
	}
	
	public int getInputM() {
		if(inputMinutes.getText().isEmpty()) {
			int minutes = 0;
			return minutes;
		}
		return Integer.parseInt(inputMinutes.getText());
	}
	
	public int getDisplayInput() {
		return Integer.parseInt(getDisplaySeconds().getText());
	}
	
	public void setDisplayInput(int interval) {
		getDisplaySeconds().setText(Integer.toString(interval));
	}
	
	public void setDisplayButton(String status) {
		startTimer.setText(status);
	}
	
	//if startTimer is pressed perform method
	void startTimerListener(ActionListener listenForTimerButton) {
			getStartTimer().addActionListener(listenForTimerButton);
	}
	
	//if startTimer is pressed perform method
	void restartListener(ActionListener listenForRestartButton) {
			getRestartTimer().addActionListener(listenForRestartButton);
	}
		
	
	//Open a popup that contains the error message passed
	void displayErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage);
	}

	public JButton getStartTimer() {
		return startTimer;
	}

	public void setStartTimer(JButton startTimer) {
		this.startTimer = startTimer;
	}

	public JLabel getDisplaySeconds() {
		return displaySeconds;
	}

	public void setDisplaySeconds(JLabel displaySeconds) {
		this.displaySeconds = displaySeconds;
	}

	public void startPropertyChangeListener(PropertyChangeListener event) {
		getDisplaySeconds().addPropertyChangeListener(event);
	}

	public JButton getRestartTimer() {
		return restartTimer;
	}

	public void setRestartTimer(JButton restartTimer) {
		this.restartTimer = restartTimer;
	}
}

