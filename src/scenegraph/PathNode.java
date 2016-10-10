package scenegraph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

public class PathNode extends Node {
	
	GeneralPath path;

	public PathNode(int i, int j, Color color, AffineTransform transform) {
		super(color, transform);

		path = new GeneralPath() ;
		//Initializing path start
		path.moveTo(i, j);
		
		Rectangle bounds = path.getBounds();
		x1 = bounds.x;
		y1 = bounds.y;
		x2 = x1+bounds.width;
		y2 = y2+bounds.height;
	}

	public void addPoint(int x, int y){
		path.lineTo(x, y);
	}

	@Override
	public void paintNode(Graphics2D graphics) {
		graphics.setPaint(contextColor);
		graphics.setStroke(new BasicStroke(2));
		//Antialiasing on
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.draw(path);

	}

}
