package logicGates;

import java.awt.Color;

public enum gateType {
	NOT (Color.black), 
	AND (new Color(0, 145, 19)),
	NAND (new Color(0, 145, 138)), 
	OR (new Color(0, 157, 214)), 
	NOR (new Color(203, 0, 214)), 
	XOR (new Color(199, 0, 66)), 
	XNOR (new Color(73, 145, 0)),
	;
	
	private final Color gateColor;
	
	gateType(Color gateColor) {
		
		this.gateColor = gateColor;
		
	}
	
	public Color getColor() {
		
		return this.gateColor;
		
	}
	
}
