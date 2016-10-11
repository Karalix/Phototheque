package scenegraph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class ShapeNode extends Node {
	
	protected Shape shape ;

	public ShapeNode(Shape shape, Color color, AffineTransform transform ) {
		super(color, transform);
		this.shape = shape;
		Rectangle bounds = shape.getBounds();
		x1 = bounds.x;
		y1 = bounds.y;
		x2 = x1+bounds.width;
		y2 = y2+bounds.height;
	}

	@Override
	protected void paintSpecializedNode(Graphics2D graphics) {
		graphics.draw(shape);
		
	}

}
