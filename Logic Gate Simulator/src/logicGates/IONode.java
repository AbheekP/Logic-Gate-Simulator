package logicGates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JComponent;

public class IONode extends LogicGateComponent implements MouseListener, MouseMotionListener{
	
	IO type;
	
	final int width = 40;
	final int height = width;
	final int offset = 10;

	volatile int screenX = 0;
	volatile int screenY = 0;
	volatile int inFrameX = 0;
	volatile int inFrameY = 0;
	
	Point previousPoint;
	Point compCorner;
	
	IONode thisObject = this;
	IONodeConnector connectorNode;
	
	boolean draggable = true;
	boolean selectionNode = false;
	
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	
	public IONode(IO type) {
		
		this.type = type;
		on = false;
		
		setSize(width + offset, height + offset);
		
		enableInputMethods(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
		setVisible(true);
		addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				on = !on;
				thisObject.setOn(on);
				connectorNode.setOn(on);
				ArrayList<Wire> childConnectedWires = connectorNode.getConnectedWires();
				
				for(Wire wire : childConnectedWires) {
					
					if(on) {
						
						wire.turnWireOn();
						
					} else {
						
						wire.turnWireOff();
						
					}
					
				}
				
			}
			
		});
		
		repaint();
		
		connectorNode = new IONodeConnector(type, this);
		frame.mainFrame.add(connectorNode);
		frame.mainFrame.add(this);
				
	}
	
	public void setLocation(int x, int y) {
		
		super.setLocation(x, y);
		
		if(type == IO.input) {
			
			connectorNode.setLocation(this.getX() + width, this.getY() + width - (connectorNode.getWidth() / 2) - (IONodeConnector.offset / 2));

		} else {
			
			connectorNode.setLocation(this.getX() - connectorNode.getWidth() + IONodeConnector.offset + 3, this.getY() + width - (connectorNode.getWidth() / 2) - (IONodeConnector.offset / 2));
			
		}
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(this.isOn()) {
			
			g.setColor(new Color(217, 0, 47)); //green 

			
		} else {
			
			g.setColor(new Color(74, 74, 74)); //gray 

			
		}
		
		g.fillOval(offset / 2, offset / 2, width, height);
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
		if(draggable) {
			
			int deltaX = e.getXOnScreen() - screenX;
			int deltaY = e.getYOnScreen() - screenY;
			
			setLocation(inFrameX + deltaX, inFrameY + deltaY);	
			
			frame.dragged = true;
			
		}
				
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	
		if(!this.selectionNode) {
			
			if(type == IO.input) {
				
				notifyListeners(e);
				repaint();
				
			}
			
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		screenX = e.getXOnScreen();
		screenY = e.getYOnScreen();
		
		inFrameX = getX();
		inFrameY = getY();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		if(this.selectionNode) {
			
			notifyListeners(e);
			
		} else {
			
			frame.dragged = false;
			
			for(Wire wire : frame.allWires) {
				
				wire.repaint();
				
			}
			
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
	
	public void setSelectionNode() {
		
		this.draggable = false;
		this.selectionNode = true;
		this.listeners.clear();
		this.connectorNode.removeAllActionListeners();
		
	}
	
	public IONodeConnector getConnectorNode() {
		
		return this.connectorNode;
		
	}
	
	public boolean isSelectionNode() {
		
		return this.selectionNode;
		
	}
	
}
