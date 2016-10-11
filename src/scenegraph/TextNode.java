package scenegraph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

public class TextNode extends Node {
	
	protected StringBuilder text;
	private int x;
	private int y;
	private Font font = new Font("TimesRoman", Font.PLAIN, 12);

	public TextNode(int x, int y, Color color, AffineTransform transform) {
		super(color, transform);
		this.x = x ;
		this.y = y ;
		text = new StringBuilder();
	}
	
	public TextNode(int x, int y, String font, int fontSize, Color color, AffineTransform transform) {
		super(color, transform);
		this.x = x ;
		this.y = y ;
		this.font = new Font(font, Font.PLAIN, fontSize);
		text = new StringBuilder();
	}

	public void addChar(String newChar) {
		text.append(newChar);
	}
	
	@Override
	protected void paintSpecializedNode(Graphics2D graphics) {
		Rectangle bounds = graphics.getClipBounds();
		int width = bounds.width ;
		graphics.setFont(font);
		graphics.drawString(text.toString(), x, y);

	}

}
