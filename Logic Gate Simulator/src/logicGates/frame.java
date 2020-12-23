package logicGates;

import java.util.ArrayList;

import javax.swing.JFrame;

public class frame {

	public static JFrame mainFrame;
	
	public static final int frameWidth = 1200;
	public static final int frameHeight = 800;
	
	public static boolean dragged = false;
			
	public static ArrayList<IONodeAttached> inputNodes = new ArrayList<IONodeAttached>();
	public static ArrayList<IONodeAttached> outputNodes = new ArrayList<IONodeAttached>();
	public static ArrayList<IONodeAttached> allAttachedNodes = new ArrayList<IONodeAttached>();
	public static ArrayList<IONodeConnector> allNodeConnectors = new ArrayList<IONodeConnector>();
	public static ArrayList<Wire> allWires = new ArrayList<Wire>();
	
	public static LogicGateComponent firstNodeSelected;
		
	public frame() {

		mainFrame = new JFrame("Logic Gate Simulator");
		mainFrame.setSize(frameWidth, frameHeight);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setLayout(null);
		mainFrame.setResizable(false);
		
	}
	
	public void setVisible(boolean visible) {
		
		mainFrame.setVisible(visible);
		
	}

}
