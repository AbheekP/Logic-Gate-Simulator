package logicGates;

import java.util.ArrayList;

import javax.swing.JComponent;

public class LogicGateComponent extends JComponent{

	ArrayList<Wire> connectedWires = new ArrayList<Wire>();
	boolean on;
	
	public ArrayList<Wire> getConnectedWires() {
		
		return this.connectedWires;
		
	}
	
	public void addWireConnection(Wire wire) {
		
		connectedWires.add(wire);
		
	}
	
	public void setOn(boolean on) {
		
		this.on = on;
		this.repaint();
		
	} 
	
	public boolean isOn() {
		
		return this.on;
		
	}
	
}
