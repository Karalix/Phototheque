package scenegraph;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
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
		int totalUsableWidth = bounds.width-x ;
		int leftWidth = totalUsableWidth;
		int wordDrawXOrigin = bounds.width - leftWidth ;
		int wordDrawYOrigin;
		StringBuilder charsLeftToWrite = new StringBuilder();
		String[] words = text.toString().split(" ");

		graphics.setFont(font);
		FontMetrics metrics = graphics.getFontMetrics();
		
		wordDrawYOrigin = y + metrics.getMaxAscent()+metrics.getDescent()+metrics.getLeading();
		
		//If total width space is too small for even 1 char, just return, leave it, PEBKAC
		if(metrics.stringWidth("w") > totalUsableWidth) {
			return;
		}
		
		//Iterate on the words
		for(String word : words) {
			String wordToDraw ;
			boolean wordWasChargedInWordToDraw = false ;
			
			while(charsLeftToWrite.length() > 0 || !wordWasChargedInWordToDraw){
				
				//If CharsLeftToWrite are all drawn, we charge word into it to be drawn
				if(!wordWasChargedInWordToDraw && charsLeftToWrite.length()==0) {
					charsLeftToWrite.append(word);
					wordWasChargedInWordToDraw = true;
				}
				wordToDraw = charsLeftToWrite.toString();
				charsLeftToWrite.setLength(0);
				
				
				//If the word is too big, we want to draw it on a new line
				if(metrics.stringWidth(wordToDraw)  > leftWidth) {
					//But if the word is too long even for a whole line
					if(metrics.stringWidth(wordToDraw) > totalUsableWidth){
						while(metrics.stringWidth(wordToDraw) > totalUsableWidth) {
							charsLeftToWrite.insert(0,wordToDraw.charAt(wordToDraw.length()-1));
							wordToDraw = wordToDraw.substring(0, wordToDraw.length()-1);
						}
						//Now that all computation is done we add a space to wordToDraw, don't care if there is not enough place to draw it, Thug life
						wordToDraw += " ";
						//We draw what we can of wordToDraw
						graphics.drawString(wordToDraw, wordDrawXOrigin, wordDrawYOrigin);
						//Update xOrigin and stuff
						leftWidth = leftWidth - metrics.stringWidth(wordToDraw);
						wordDrawXOrigin = wordDrawXOrigin + metrics.stringWidth(wordToDraw) ;
					}
					//Going to new line
					wordDrawYOrigin = metrics.getMaxAscent()+metrics.getDescent()+metrics.getLeading()+wordDrawYOrigin;
					leftWidth = totalUsableWidth;
					wordDrawXOrigin = bounds.width - leftWidth ;
					if(charsLeftToWrite.length() > 0) {
						continue;
					}
				}
				//Now that all computation is done we add a space to wordToDraw, don't care if there is not enough place to draw it, Thug life
				wordToDraw += " ";
				//Draw the wordToDraw normally
				graphics.drawString(wordToDraw, wordDrawXOrigin, wordDrawYOrigin);
				leftWidth = leftWidth - metrics.stringWidth(wordToDraw);
				wordDrawXOrigin = wordDrawXOrigin + metrics.stringWidth(wordToDraw) ;
				
				
			}
			
			
		}

	}

}
