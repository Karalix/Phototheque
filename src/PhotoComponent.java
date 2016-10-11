import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import model.Photo;
import scenegraph.ContainerNode;
import scenegraph.PathNode;
import scenegraph.TextNode;


public class PhotoComponent extends JComponent {
	
	protected Photo currentPhoto;
	protected BufferedImage currentImage ;
	protected String imagePath ;
	protected boolean isFlipped;
	
	private MouseAdapter mouseAdapter;
	private KeyAdapter keyAdapter;
	private boolean isDrawing = false;
	private boolean isWriting = false;
	private int nodeNumber;
	
	public PhotoComponent() {
		super();
		int defaultWidth, defaultHeigth ;
		defaultHeigth = defaultWidth = 50 ;
		this.setSize(defaultWidth,defaultHeigth);
		this.setPreferredSize(this.getSize());
		
		//DEBUG
		currentPhoto = new Photo("./img.png", new ContainerNode(Color.BLACK, null));


	    try {
			currentImage = ImageIO.read(new File(currentPhoto.getPhotoPath()));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	

	@Override
	protected void paintComponent(final Graphics graphics) {
		if(mouseAdapter == null) {
			mouseAdapter = new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent event) {
					if(event.getClickCount() == 1 && isFlipped) {
						PhotoComponent.this.requestFocus();
						isWriting = true ;
						nodeNumber = currentPhoto.getSceneGraph().addChild(new TextNode(event.getX(), event.getY(), Color.BLACK, null));
					}
					if(event.getClickCount() == 2) {
						isWriting = false;
						isFlipped = !isFlipped ;
					}
				}
				
				@Override
				public void mouseDragged(MouseEvent event) {
					if(isFlipped) {
						isWriting = false ;
						//Creation of a new stroke
						if(!isDrawing) {
							//strokeNumber is kept to enable fast retrieving when we want to add a new point
							nodeNumber = currentPhoto.getSceneGraph().addChild(new PathNode(event.getX(), event.getY(),Color.BLACK, new AffineTransform()));
							isDrawing = true ;
						}
						//Addition of a new point to the stroke
						((PathNode)(currentPhoto.getSceneGraph().getChild(nodeNumber))).addPoint(event.getX(), event.getY());
						//System.out.println(event.getX()+":"+event.getY());
					}
					super.mouseDragged(event);
				}
				
				@Override
				public void mouseReleased(MouseEvent arg0) {
					//once click released, the next time we drag we create a new stroke
					isDrawing = false ;
					super.mouseReleased(arg0);
				}
				
			};
			

			this.addMouseListener(mouseAdapter);
			this.addMouseMotionListener(mouseAdapter);
		}
		
		if(keyAdapter == null) {
			keyAdapter = new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					if(isWriting) {
						((TextNode)currentPhoto.getSceneGraph().getChild(nodeNumber)).addChar(String.valueOf(e.getKeyChar()));
					}
					super.keyPressed(e);
				}
			};
			this.addKeyListener(keyAdapter);
		}
		
		Color oldColor = graphics.getColor();
		
		//Painting a neat background
		graphics.setColor(new Color(250, 250,255));
		graphics.fillRect(0, 0, this.getParent().getWidth(), this.getParent().getHeight());
		//Paint lines
		graphics.setColor(new Color(220,220,255));
		
		for(int i = 0 ; i <= this.getParent().getHeight()/15 ; i++) {
			graphics.drawLine(0, i*15, this.getParent().getWidth(), i*15);
		}
		for(int i = 0 ; i <= this.getParent().getWidth()/15 ; i++) {
			graphics.drawLine(i*15, 0, i*15, this.getParent().getHeight());
		}
		
		if(currentImage != null){
			int width, height ;
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
		    
		    //TEMPORARY
		    xOrigin = 0 ;
		    yOrigin= 0 ;
			
		    graphics.setClip(xOrigin, yOrigin, xOrigin+width, yOrigin+height);
		    
			if(!isFlipped){
				//Trying to show the image
				graphics.drawImage(currentImage, xOrigin, yOrigin, null);
			}
			else {
				graphics.setColor(new Color(255,250,220));
				graphics.fillRect(xOrigin, yOrigin, width, height);
				currentPhoto.getSceneGraph().paintNode((Graphics2D)graphics);
	
			}
		}

	    this.revalidate();
	    
		graphics.setColor(oldColor);
		super.paintComponent(graphics);
	}
}
