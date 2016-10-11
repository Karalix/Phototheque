package scenegraph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;


public abstract class Node {
	protected Node parent;
	
	protected boolean isVisible;
	protected Color contextColor = Color.BLACK;
	protected AffineTransform contexteTransform;
	
	protected int x1, y1, x2, y2 ;
	
	public Node(Color color, AffineTransform transform){
		this.contextColor = color;
		this.contexteTransform = transform;
	}
	
	public final void paintNode(Graphics2D graphics) {
		Color oldColor = graphics.getColor();
		graphics.setColor(contextColor);
		paintSpecializedNode(graphics);
		graphics.setColor(oldColor);
	}
	
	protected abstract void paintSpecializedNode(Graphics2D graphics);
	
	public Node getParent() {
		return parent;
	}
	protected void setParent(Node parent) {
		this.parent = parent;
	}
	public boolean isVisible() {
		return isVisible;
	}
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	public Color getContextColor() {
		return contextColor;
	}
	protected void setContextColor(Color contextColor) {
		this.contextColor = contextColor;
	}

	public int getX1() {
		return x1;
	}

	public int getY1() {
		return y1;
	}

	public int getX2() {
		return x2;
	}

	public int getY2() {
		return y2;
	}
	
	
	
	
	

}
