package logicGates;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

public class Wire extends JComponent {

	LogicGateComponent node1;
	LogicGateComponent node2;
	
	boolean on;
	
	public Wire(LogicGateComponent node1, LogicGateComponent node2) {
		
		setSize(frame.frameWidth, frame.frameHeight);
		setVisible(true);
		frame.mainFrame.add(this);
		
		node1.addWireConnection(this);
		node2.addWireConnection(this);
		
		this.node1 = node1;
		this.node2 = node2;
		
		on = node1.isOn();
		
		repaint();
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		if(!frame.dragged) {
			
			super.paintComponent(g);
			
			Graphics2D g2d = (Graphics2D) g;
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2d.setStroke(new BasicStroke(3));
	        
	        if(on) {
	        	
	        	g2d.setColor(new Color(217, 0, 47));
	        	
	        } else {
	        	
		        g2d.setColor(Color.black);

	        }
	        
	        int outwards = 50;
	        
	        if((Math.abs(node1.getY() - node2.getY()) < 25) && node2.getX() > node1.getX()) {
	        	
		        g2d.drawLine(node1.getX() + node1.getWidth() / 2, node1.getY() + node1.getHeight() / 2, node2.getX() + node2.getWidth() / 2, node2.getY() + node2.getHeight() / 2);
	        	
	        } else if(node2.getX() > node1.getX() && node2.getY() > node1.getY()) {
	        	
	        	g2d.drawLine(node1.getX() + node1.getWidth() / 2, node1.getY() + node1.getHeight() / 2, (node1.getX() + node1.getWidth() / 2) + ((node2.getX() + node2.getWidth() / 2) - (node1.getX() + node1.getWidth() / 2)) / 2, node1.getY() + node1.getHeight() / 2);
	        	g2d.drawLine((node1.getX() + node1.getWidth() / 2) + ((node2.getX() + node2.getWidth() / 2) - (node1.getX() + node1.getWidth() / 2)) / 2, node1.getY() + node1.getHeight() / 2, (node1.getX() + node1.getWidth() / 2) + ((node2.getX() + node2.getWidth() / 2) - (node1.getX() + node1.getWidth() / 2)) / 2, node2.getY() + node2.getHeight() / 2);
	        	g2d.drawLine((node1.getX() + node1.getWidth() / 2) + ((node2.getX() + node2.getWidth() / 2) - (node1.getX() + node1.getWidth() / 2)) / 2, node2.getY() + node2.getHeight() / 2, node2.getX() + node2.getWidth() / 2, node2.getY() + node2.getHeight() / 2);
	        	
	        } else if (node2.getX() > node1.getX() && node2.getY() < node1.getY()) {
	        	
	        	g2d.drawLine(node1.getX() + node1.getWidth() / 2, node1.getY() + node1.getHeight() / 2, (node1.getX() + node1.getWidth() / 2) + ((node2.getX() + node2.getWidth() / 2) - (node1.getX() + node1.getWidth() / 2)) / 2, node1.getY() + node1.getHeight() / 2);
	        	g2d.drawLine((node1.getX() + node1.getWidth() / 2) + ((node2.getX() + node2.getWidth() / 2) - (node1.getX() + node1.getWidth() / 2)) / 2, node1.getY() + node1.getHeight() / 2, (node1.getX() + node1.getWidth() / 2) + ((node2.getX() + node2.getWidth() / 2) - (node1.getX() + node1.getWidth() / 2)) / 2, node2.getY() + node2.getHeight() / 2);
	        	g2d.drawLine((node1.getX() + node1.getWidth() / 2) + ((node2.getX() + node2.getWidth() / 2) - (node1.getX() + node1.getWidth() / 2)) / 2, node2.getY() + node2.getHeight() / 2, node2.getX() + node2.getWidth() / 2, node2.getY() + node2.getHeight() / 2);
	        	
	        } else if (node2.getX() - node1.getX() < 0 && node2.getY() > node1.getY()) {
	        		        	
	        	g2d.drawLine(node1.getX() + node1.getWidth() / 2, node1.getY() + node1.getHeight() / 2, node1.getX() + node1.getWidth() / 2 + outwards, node1.getY() + node1.getHeight() / 2);
	        	g2d.drawLine(node1.getX() + node1.getWidth() / 2 + outwards, node1.getY() + node1.getHeight() / 2, node1.getX() + node1.getWidth() / 2 + outwards, ((node1.getY() + node1.getHeight() / 2) + (node2.getY() + node1.getHeight())) / 2);
	        	g2d.drawLine(node1.getX() + node1.getWidth() / 2 + outwards, ((node1.getY() + node1.getHeight() / 2) + (node2.getY() + node1.getHeight())) / 2, node2.getX() + node2.getWidth() / 2 - outwards, ((node1.getY() + node1.getHeight() / 2) + (node2.getY() + node1.getHeight())) / 2);
	        	g2d.drawLine(node2.getX() + node2.getWidth() / 2 - outwards, ((node1.getY() + node1.getHeight() / 2) + (node2.getY() + node1.getHeight())) / 2, node2.getX() + node2.getWidth() / 2 - outwards, node2.getY() + node2.getHeight() / 2);
	        	g2d.drawLine(node2.getX() + node2.getWidth() / 2 - outwards, node2.getY() + node2.getHeight() / 2, node2.getX() + node2.getWidth() / 2, node2.getY() + node2.getHeight() / 2);
	        	
	        } else if(node2.getX() - node1.getX() < 0 && node2.getY() < node1.getY()) {
	        	
	        	g2d.drawLine(node1.getX() + node1.getWidth() / 2, node1.getY() + node1.getHeight() / 2, node1.getX() + node1.getWidth() / 2 + outwards, node1.getY() + node1.getHeight() / 2);
	        	g2d.drawLine(node1.getX() + node1.getWidth() / 2 + outwards, node1.getY() + node1.getHeight() / 2, node1.getX() + node1.getWidth() / 2 + outwards, ((node1.getY() + node1.getHeight() / 2) + (node2.getY() + node2.getHeight())) / 2);
	        	g2d.drawLine(node1.getX() + node1.getWidth() / 2 + outwards, ((node1.getY() + node1.getHeight() / 2) + (node2.getY() + node2.getHeight())) / 2, node2.getX() + node2.getWidth() / 2 - outwards, ((node1.getY() + node1.getHeight() / 2) + (node2.getY() + node2.getHeight())) / 2);
	        	g2d.drawLine(node2.getX() + node2.getWidth() / 2 - outwards, ((node1.getY() + node1.getHeight() / 2) + (node2.getY() + node2.getHeight())) / 2, node2.getX() + node2.getWidth() / 2 - outwards, node2.getY() + node2.getWidth() / 2);
	        	g2d.drawLine(node2.getX() + node2.getWidth() / 2 - outwards, node2.getY() + node2.getWidth() / 2, node2.getX() + node2.getWidth() / 2, node2.getY() + node2.getHeight() / 2);
	        	
	        }    
			
		}
	
	}
	
	public void turnWireOn() {
		
		this.on = true;
		node2.setOn(true);
		repaint();
		
	}
	
	public void turnWireOff() {
		
		this.on = false;
		node2.setOn(false);
		repaint();
		
	}
	
	public boolean isOn() {
		
		return this.on;
		
	}
	
	public void removeWire(IO type, boolean gateNode) {
		
		if(gateNode) {
			
			if(type == IO.output) {
				
				if(node2.getClass() == IONodeConnector.class) {
					
					IONodeConnector node = (IONodeConnector) node2;
					node.parent.connectedWires.remove(this);
					node.connectedWires.remove(this);
					
				} else {
					
					node2.connectedWires.remove(this);
					
				}
				
			} else {
				
				node1.connectedWires.remove(this);
				
			}
			
		} 
		
		frame.mainFrame.remove(this);
		frame.mainFrame.repaint();
		
	}
	
	public LogicGateComponent getNode2() {
		
		return this.node2;
		
	}
	
}
