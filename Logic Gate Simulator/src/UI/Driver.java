package UI;

import javax.swing.JFrame;

import logicGates.frame;

public class Driver {
	
	public static JFrame frame;

	public static void main(String[] args) {
		
		frame frameObj = new frame();
		frame = logicGates.frame.mainFrame;
		gateSelection gateSelection = new gateSelection();
		gateSelection.setLocation(0, 0);
		frameObj.setVisible(true);
		
	}
	
}
