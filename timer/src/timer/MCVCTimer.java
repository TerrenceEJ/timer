package timer;

public class MCVCTimer {
	
	public static void main(String[] args) {
	timerView theView = new timerView();
	timerModel theModel = new timerModel();
	timerController theController = new timerController(theView, theModel);
	
	theView.setVisible(true);
	}
}
