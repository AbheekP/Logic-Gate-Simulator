package logicGates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JComponent;

public class LogicGate extends LogicGateComponent implements MouseListener, MouseMotionListener {

	private gateType gate;
	
	int width = 90;
	int height = 55;
	
	boolean draggable = true;
		
	AffineTransform at = new AffineTransform();
	FontRenderContext frc = new FontRenderContext(at, true, true);
	Font font = new Font("Arial", Font.BOLD, 20);
	
	int textWidth;
	int textHeight;
	
	private volatile int screenX = 0;
	private volatile int screenY = 0;
	private volatile int compX = 0;
	private volatile int compY = 0;
	
	boolean selectionGate = false;
	
	Point previousPoint;
	Point compCorner;
	
	IONodeAttached input1;
	IONodeAttached input2;
	IONodeAttached output;
	IONodeAttached NOTInput;
	
	public boolean inputA = false;
	public boolean inputB = false;
	
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
			
	public LogicGate(gateType gate) {
		
		compCorner = new Point(0, 0);
		
		setSize(width, height);
		
		enableInputMethods(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		setFocusable(true);
		setVisible(true);
		
		this.gate = gate;
		
		repaint();
		
		if(this.gate == gateType.NOT) {
			
			NOTInput = new IONodeAttached(this, IO.input, false);
			frame.inputNodes.add(NOTInput);
			frame.allAttachedNodes.add(NOTInput);
			
			NOTInput.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					
					if(NOTInput.getSelected()) {
						
						NOTInput.setSelected(false);
						
					} else {
						
						for(IONodeAttached node : frame.inputNodes) {
							
							node.setSelected(false);
							
						}
						
						if(frame.firstNodeSelected != null) {
							
							if(NOTInput.getConnectedWires().size() < 1) {
								
								NOTInput.setSelected(true);
								Wire wire = new Wire(frame.firstNodeSelected, NOTInput);
								frame.allWires.add(wire);
								frame.firstNodeSelected.addWireConnection(wire);
								
								frame.firstNodeSelected = null;
								
								for(int i = 0; i < frame.allAttachedNodes.size(); i++) {
									
									frame.allAttachedNodes.get(i).setSelected(false);
									
								}
								
								for(IONodeConnector nodes : frame.allNodeConnectors) {
									
									nodes.setSelected(false);
								
								}
								
								inputA = NOTInput.getConnectedWires().get(0).isOn();
								calculateOutput(inputA, true);
								
							}
							
						}
						
					}
					
				}
				
			});
			
			frame.mainFrame.add(NOTInput);
			
		} else {
			
			input1 = new IONodeAttached(this, IO.input, true);
			frame.inputNodes.add(input1);
			frame.allAttachedNodes.add(input1);
			
			input1.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
						
					if(frame.firstNodeSelected != null) {
						
						if(input1.getConnectedWires().size() < 1) {
							
							Wire wire = new Wire(frame.firstNodeSelected, input1);
							frame.allWires.add(wire);
							
							frame.firstNodeSelected = null;
							
							for(int i = 0; i < frame.allAttachedNodes.size(); i++) {
								
								frame.allAttachedNodes.get(i).setSelected(false);
								
							}
							
							for(IONodeConnector nodes : frame.allNodeConnectors) {
								
								nodes.setSelected(false);
								
							}
							
							ArrayList<Wire> thisInputConnectedWires = input1.getConnectedWires();
							ArrayList<Wire> otherInputConnectedWires = input2.getConnectedWires();
							
							if(otherInputConnectedWires.size() != 0) {
								
								inputA = thisInputConnectedWires.get(0).isOn();
								inputB = otherInputConnectedWires.get(0).isOn();
								calculateOutput(inputA, inputB);
								
							}
							
						}
						
					} 
					
				}
				
			});
			
			input2 = new IONodeAttached(this, IO.input, false);
			frame.inputNodes.add(input2);
			frame.allAttachedNodes.add(input2);
			
			input2.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {

					if(frame.firstNodeSelected != null) {
						
						if(input2.getConnectedWires().size() < 1) {
							
							frame.allWires.add(new Wire(frame.firstNodeSelected, input2));
							frame.firstNodeSelected = null;
							
							for(JComponent node2 : frame.allAttachedNodes) {
								
								((IONodeAttached) node2).setSelected(false);
								
							}
							
							for(IONodeConnector nodes : frame.allNodeConnectors) {
								
								nodes.setSelected(false);
								
							}
							
							ArrayList<Wire> otherInputConnectedWires = input1.getConnectedWires();
							ArrayList<Wire> thisInputConnectedWires = input2.getConnectedWires();
							
							if(otherInputConnectedWires.size() != 0) {
								
								inputA = thisInputConnectedWires.get(0).isOn();
								inputB = otherInputConnectedWires.get(0).isOn();
								calculateOutput(inputA, inputB);
								
							}
							
						}
						
					}
					
				}
				
			});
			
			frame.mainFrame.add(input1);
			frame.mainFrame.add(input2);
			
		}
		
		output = new IONodeAttached(this, IO.output, false);
		frame.outputNodes.add(output);
		frame.allAttachedNodes.add(output);
		
		output.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if(output.getSelected()) {
					
					output.setSelected(false);
					frame.firstNodeSelected = null;
					
				} else {
					
					for(IONodeAttached node : frame.outputNodes) {
						
						node.setSelected(false);
						
					}
					
					output.setSelected(true);
					frame.firstNodeSelected = output;
					
				}
				
			}
			
		});

		frame.mainFrame.add(output);
		frame.mainFrame.add(this);
		
	}
	
	public void calculateOutput(boolean A, boolean B) {
			
		switch(this.gate) {
		
		case NOT:
			if(A) {
				
				this.on = false;
				output.on = false;
				
			} else {
				
				this.on = true;
				output.on = true;
				
			}
			break;
			
		case AND:
			if(A && B) {
				
				this.on = true;
				output.on = true;
				
			} else {
				
				this.on = false;
				output.on = false;
				
			}
			break;
		
		case NAND:
			if(A && B) {
				
				this.on = false;
				output.on = false;
				
			} else {
				
				this.on = true;
				output.on = true;
				
			}
			break;
			
		case OR:
			if(A || B) {
				
				this.on = true;
				output.on = true;
				
			} else {
				
				this.on = false;
				output.on = false;
				
			}
			break;
			
		case NOR:
			if(A || B) {
				
				this.on = false;
				output.on = false;
				
			} else {
				
				this.on = true;
				output.on = true;
				
			}
			break;
			
		case XOR:
			if(A && B) {
				
				this.on = false;
				output.on = false;
				
			} else if (!A && !B){
				
				this.on = false;
				output.on = false;
				
			} else {
				
				this.on = true;
				output.on = true;
				
			}
			break;
			
		case XNOR:
			if(A && B) {
				
				this.on = true;
				output.on = true;
				
			} else if (!A && !B){
				
				this.on = true;
				output.on = true;
				
			} else {
				
				this.on = false;
				output.on = false;
				
			}
			break;
			
		}
		
		
		
		sendOutput();
	
	}
	
	public void sendOutput() {
		
		ArrayList<Wire> outputConnectedWires = output.getConnectedWires();
		
		for(Wire wire : outputConnectedWires) {
			
			if(this.on) {
				
				wire.turnWireOn();
				
			} else {
				
				wire.turnWireOff();
				
			}
			
		}
		
	}
	
	public void paintComponent (Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(gate.getColor());
		g.fillRoundRect(0, 0, width, height, 15, 15);
		
		g.setColor(Color.white);
		g.setFont(font);
		
		String label = gate.toString();
		textWidth = (int) (font.getStringBounds(label, frc).getWidth());
		textHeight = (int) (font.getStringBounds(label, frc).getHeight());
		
		g.drawString(label, width / 2 -  (textWidth / 2), height / 2 + (textHeight / 2) - 3);
		
	}
	
	public void setLocation(int x, int y) {
		
		super.setLocation(x, y);
		
		if(this.gate == gateType.NOT) {
			
			NOTInput.setLocation(this.getX() - (NOTInput.getWidth() / 2), this.getY() + (this.getHeight() / 2) - (output.getHeight() / 2));
			output.setLocation(this.getX() + this.getWidth() - (output.getWidth() / 2), this.getY() + (this.getHeight() / 2) - (output.getHeight() / 2));
			
		} else {
			
			input1.setLocation(this.getX() - (input1.getWidth() / 2), this.getY() + (this.getHeight() / 4) - (input1.getHeight() / 2));
			input2.setLocation(this.getX() - (input2.getWidth() / 2), this.getY() + this.getHeight() - (this.getHeight() / 4) - (input2.getHeight() / 2));
			output.setLocation(this.getX() + this.getWidth() - (output.getWidth() / 2), this.getY() + (this.getHeight() / 2) - (output.getHeight() / 2));
			
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getClickCount() == 2) {
			
			if(this.gate == gateType.NOT) {
				
				NOTInput.mouseClicked(e);
				NOTInput.setVisible(false);
				frame.mainFrame.remove(NOTInput);
				NOTInput.invalidate();
				
			} else {
				
				input1.mouseClicked(e);
				input1.setVisible(false);
				frame.mainFrame.remove(input1);
				input1.invalidate();
				
				input2.mouseClicked(e);
				input2.setVisible(false);
				frame.mainFrame.remove(input2);
				input2.invalidate();
				
			}
			
			output.mouseClicked(e);
			output.setVisible(false);
			frame.mainFrame.remove(output);
			output.invalidate();
			
			this.setVisible(false);
			frame.mainFrame.remove(this);
			this.invalidate();
			
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		screenX = e.getXOnScreen();
		screenY = e.getYOnScreen();
		
		compX = getX();
		compY = getY();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if(this.selectionGate) {
			
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
		
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		if(draggable) {
			
			int deltaX = e.getXOnScreen() - screenX;
			int deltaY = e.getYOnScreen() - screenY;
			
			setLocation(compX + deltaX, compY + deltaY);
			
			frame.dragged = true;
			
		}
						
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		
		
	}
	
	public gateType getGateType() {
		
		return this.gate;
		
	}
	
	public void setSelectionGate() {
		
		this.draggable = false;
		this.selectionGate = true;
		this.listeners.clear();
		
		if(this.gate == gateType.NOT) {
			
			NOTInput.removeAllActionListeners();
			
		} else {
			
			input1.removeAllActionListeners();
			input2.removeAllActionListeners();
			
		}
		
		output.removeAllActionListeners();
		
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
	
}
