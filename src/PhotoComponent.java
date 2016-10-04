import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;


public class PhotoComponent extends JComponent {
	
	protected BufferedImage currentImage ;
	protected String imagePath ;
	protected boolean isFlipped;
	
	public PhotoComponent() {
		super();
		int defaultWidth, defaultHeigth ;
		defaultHeigth = defaultWidth = 50 ;
		this.setSize(defaultWidth,defaultHeigth);
		this.setPreferredSize(this.getSize());
	}
	
	

	@Override
	protected void paintComponent(Graphics graphics) {
		
		Color oldColor = graphics.getColor();
		
		//Painting a neat background
		graphics.setColor(new Color(250, 250,255));
		graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
		//Paint lines
		graphics.setColor(new Color(220,220,255));
		
		for(int i = 0 ; i <= this.getParent().getHeight()/15 ; i++) {
			graphics.drawLine(0, i*15, this.getParent().getWidth(), i*15);
		}
		for(int i = 0 ; i <= this.getParent().getWidth()/15 ; i++) {
			graphics.drawLine(i*15, 0, i*15, this.getParent().getHeight());
		}
		
		//Trying to show the image
		try {
			int width, height ;
		    currentImage = ImageIO.read(new File("./img.png"));
		    width = currentImage.getWidth();
		    height = currentImage.getHeight();
		    

		    this.setSize(width, height);
		    this.setPreferredSize(new Dimension(width, height));
		    
		    //Center the image in the parent component
		    int xOrigin = (int)Math.round((this.getParent().getWidth()/2)-(width/2));
		    int yOrigin = (int)Math.round((this.getParent().getHeight()/2)-(height/2));
		    
		    //If the image is bigger than the parent component, then we don't want to center the image
		    xOrigin = width > this.getParent().getWidth() ? 0 : xOrigin ;
		    yOrigin = height > this.getParent().getHeight() ? 0 : yOrigin ;

			graphics.drawImage(currentImage, xOrigin, yOrigin, null);
			
		    this.revalidate();
		    
			
		} catch (IOException e) {
			System.err.println(e);
		}
		
		graphics.setColor(oldColor);
		super.paintComponent(graphics);
	}
}
