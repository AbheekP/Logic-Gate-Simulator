package logicGates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class IONodeAttached extends LogicGateComponent implements MouseListener, MouseMotionListener {

	LogicGate attachedGate;
	IO type;
	
	final int width = 20;
	final int height = width;
	final int offset = 10;
		
	boolean A;
		
	boolean selected = false;
		
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	
	public IONodeAttached(LogicGate gate, IO type, boolean A) {
		
		on = false;
		this.A = A;
		attachedGate = gate;
		this.type = type;
		
		setSize(width + offset, height + offset);
		
		enableInputMethods(true);
		addMouseListener(this);
		
		setFocusable(true);
		setVisible(true);
				
		repaint();
		
	}
	
	public void paintComponent (Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(this.selected) {
			
			g.setColor(Color.green);
			
		} else {
			
			g.setColor(new Color(74, 74, 74));
			
		}
		
		g.fillOval(offset / 2, offset / 2, width, height);
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getClickCount() == 2) {
			
			for(Wire wire : attachedGate.output.getConnectedWires()) {
				
				wire.turnWireOff();
				
			}
			
			for(Wire wire : connectedWires) {
				
				wire.removeWire(type, true);
				
			}
			
			connectedWires.clear();
			
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		notifyListeners(e);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		

		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		
		
	}

	public void addActionListener(ActionListener listener) {
		
		listeners.add(listener);
		
	}
	
	public void notifyListeners(MouseEvent e) {
		
		ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, new String(), e.getWhen(), e.getModifiers());
		synchronized(listeners) {
			
			for(int i = 0; i < listeners.size(); i++) {
				
				ActionListener tmp = listeners.get(i);
				tmp.actionPerformed(evt);
				
			}
			
		}
		
	}
	
	public void setSelected(boolean selected) {
		
		this.selected = selected;
		repaint();
		
	}
	
	public boolean getSelected() {
		
		return this.selected;
		
	}
	
	public void setOn(boolean on) {
		
		if(attachedGate.getGateType() == gateType.NOT) {
			
			attachedGate.inputA = on;
			attachedGate.calculateOutput(attachedGate.inputA, true);
			
		} else {
			
			if(this.A) {
				
				attachedGate.inputA = on;
				
			} else {
				
				attachedGate.inputB = on;
				
			}
			
			attachedGate.calculateOutput(attachedGate.inputA, attachedGate.inputB);
			
		}
		
	}
	
	public void removeAllActionListeners() {
		
		listeners.clear();
		
	}
	
}
