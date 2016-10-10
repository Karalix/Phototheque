package scenegraph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class ContainerNode extends Node {


	protected List<Node> children;
	

	public ContainerNode(Color color, AffineTransform transform) {
		super(color, transform);
		children = new ArrayList<>();
	}

	public List<Node> getChildren() {
		return children;
	}
	
	/**
	 * Add a Node as a child to the ContainerNode
	 * @param newChild
	 * @return the index of the added Child
	 */
	public int addChild(Node newChild) {
		children.add(newChild);
		newChild.setParent(this);
		calculateBounds();
		return children.size()-1;
	}
	
	public Node getChild(int index){
		return children.get(index);
	}
	
	private void calculateBounds() {
		int minX1 = Integer.MAX_VALUE;
		int minY1 = Integer.MAX_VALUE;
		int maxX2 = 0;
		int maxY2 = 0;
		
		for(Node child : children) {
			if(child.getX1() < minX1){
				minX1 = child.getX1();
			}
			if(child.getY1() < minY1){
				minY1 = child.getY1();
			}
			if(child.getX2() > maxX2){
				maxX2 = child.getX2();
			}
			if(child.getY2() > maxY2){
				maxY2 = child.getY2();
			}
		}
		
		x1 = minX1;
		y1 = minY1;
		x2 = maxX2;
		y2 = maxY2;
		
	}

	@Override
	public void paintNode(Graphics2D graphics) {
		for(Node child : children) {
			child.paintNode(graphics);
		}
		
	}
}
