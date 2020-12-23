package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JFrame;

import logicGates.IO;
import logicGates.IONode;
import logicGates.LogicGate;
import logicGates.gateType;

public class gateSelection extends JComponent implements MouseListener{

	JFrame frame = Driver.frame;
	
	int width = 150;;
	int height = frame.getHeight();
	
	int iconWidth;
	int iconHeight;
	
	public gateSelection() {
		
		setSize(width, height);
		
		enableInputMethods(true);
		addMouseListener(this);
		
		setFocusable(true);
		setVisible(true);
		
		
		gateType[] types = gateType.values();
		
		for(int i = 0; i < types.length; i++) {
			
			LogicGate gate = new LogicGate(types[i]);
			gate.setLocation(width / 2 - gate.getWidth() / 2, 20 + (85 * i));
			gate.setSelectionGate();
			
			int k = i;
			gate.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					
					LogicGate addedGate = new LogicGate(types[k]);
					addedGate.setLocation((frame.getWidth() / 2) - (addedGate.getWidth() / 2) + (width / 2), (frame.getHeight() / 2) - (addedGate.getHeight()));
					
				}
				
			});
			
		}
		
		IONode inputNode = new IONode(IO.input);
		inputNode.setLocation(width / 2 - inputNode.getWidth() / 2 - inputNode.getConnectorNode().getWidth() / 2 + 5, 20 + (85 * types.length - 1));
		inputNode.setSelectionNode();
		inputNode.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				IONode addedNode = new IONode(IO.input);
				addedNode.setLocation((frame.getWidth() / 2) - (addedNode.getWidth() / 2) + (width / 2), (frame.getHeight() / 2) - (addedNode.getHeight()));
				//inputNode is getting added twice to frame for some reason
			}
			
		});
		
		IONode outputNode = new IONode(IO.output);
		outputNode.setLocation(width / 2 - outputNode.getWidth() / 2 + outputNode.getConnectorNode().getWidth() / 2 - 5, 20 + (85 * types.length - 1) + 80);
		outputNode.setSelectionNode();
		outputNode.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				IONode addedNode = new IONode(IO.output);
				addedNode.setLocation((frame.getWidth() / 2) - (addedNode.getWidth() / 2) + (width / 2), (frame.getHeight() / 2) - (addedNode.getHeight()));
				
			}
			
		});
		
		repaint();
		frame.add(this);

	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(Color.gray);
		g.fillRect(0, 0, width, height);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
