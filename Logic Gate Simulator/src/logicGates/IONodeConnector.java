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

public class IONodeConnector extends LogicGateComponent implements MouseListener, MouseMotionListener{

	int rectWidth = 20;
	int rectHeight = 5;
	
	int circleWidth = 20;
	int circleHeight = circleWidth;
	public static int offset = 10;
	
	boolean sameWire = false;
	
	IONodeConnector thisObject = this;
		
	IO type;
	
	boolean selected = false;
	
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	IONode parent;
	
	
	public IONodeConnector(IO type, IONode parent) {
		
		this.type = type;
		this.parent = parent;
		
		setSize(rectWidth + circleWidth + offset, circleHeight + offset);
		
		enableInputMethods(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		setFocusable(true);
		setVisible(true);
		
		frame.allNodeConnectors.add(this);
		
		addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if(thisObject.selected) {
					
					thisObject.setSelected(false);
					frame.firstNodeSelected = null;
					
				} else {
					
					if(thisObject.type == IO.input) {
						
						for(IONodeConnector node : frame.allNodeConnectors) {
							
							node.setSelected(false);
							
						}
						
						thisObject.setSelected(true);
						
						frame.firstNodeSelected = thisObject;
						
					} else {
						
						if(frame.firstNodeSelected != null) {
							
							if(thisObject.getConnectedWires().size() < 1) {
								
								Wire wire = new Wire(frame.firstNodeSelected, thisObject);
								parent.addWireConnection(wire);
								if(wire.isOn()) {
									
									parent.setOn(true);
									
								}
								frame.allWires.add(wire);
								
								frame.firstNodeSelected = null;
								
								for(IONodeConnector connect : frame.allNodeConnectors) {
									
									connect.setSelected(false);
									
								}
								
								for(IONodeAttached attach : frame.allAttachedNodes) {
									
									attach.setSelected(false);
									
								}
								
							}
							
						}
						
					}

				}
				
			}
			
		});
				
	}
	
	public void paintComponent (Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(new Color(74, 74, 74));
		
		if(type == IO.input) {
			
			g.fillRect(offset / 2, (circleHeight / 2)  - (rectHeight / 2) + (offset / 2), rectWidth, rectHeight);
			
			if(selected) {
				
				g.setColor(Color.green);
				
			} else {
				
				g.setColor(new Color(74, 74, 74));
				
			}
			
			g.fillOval(offset / 2 + rectWidth - 2, offset / 2, circleWidth, circleHeight);
			
			
		} else {
			
			g.setColor(new Color(74, 74, 74));
			
			g.fillRect(offset / 2 + circleWidth - 2, (circleHeight / 2)  - (rectHeight / 2) + (offset / 2), rectWidth, rectHeight);
			
			if(selected) {
				
				g.setColor(Color.green);
				
			} 
			
			g.fillOval(offset / 2, offset / 2, circleWidth, circleHeight);
			
		}
				
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	
		notifyListeners(e);
		
		if(e.getClickCount() == 2) {
			
			if(!parent.isSelectionNode()) {
				
				for(Wire wire : connectedWires) {
					
					wire.turnWireOff();
					wire.removeWire(type, false);
					
				}
				
				connectedWires.clear();
				
				this.setVisible(false);
				frame.mainFrame.remove(this);
				this.invalidate();
				
				this.parent.setVisible(false);
				frame.mainFrame.remove(this.parent);
				this.parent.invalidate();
				
			}
			
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		sameWire = false;
		
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
	
	public void setSelected(boolean selected) {
		
		this.selected = selected;
		repaint();
		
	}
	
	public void setOn(boolean on) {
		
		this.on = on;
		parent.setOn(on);
		
	}
	
	public IO getType() {
		
		return this.type;
		
	}
	
	public void removeAllActionListeners() {
		
		this.listeners.clear();
		
	}
	
}
