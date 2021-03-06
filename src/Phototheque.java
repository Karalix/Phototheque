import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Photo;
import model.PhotoCollection;


public class Phototheque extends JFrame{
	private JLabel status = null ;
	private PhotoComponent photoComponent = null ;
	
	public static void main(String args[]) {
		try 
	    { 
	        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
	    } 
	    catch(Exception e){ 
	    }
		Phototheque myPhototheque = new Phototheque() ;
		
		myPhototheque.pack();
		myPhototheque.setVisible(true);
	}
	
	public Phototheque() {
		super("Phototheque");
		
		this.setPreferredSize(new Dimension(800,600));
		this.setJMenuBar(createMyMenuBar());
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		this.add(getStatusBar(),BorderLayout.PAGE_END);
		this.add(getToolBar(),BorderLayout.NORTH);
		this.add(getMainPanel(), BorderLayout.CENTER);
	}
	
	private JMenuBar createMyMenuBar() {
		JMenuBar myMenuBar = new JMenuBar();
		
		//Menu File
		JMenu menuFile = new JMenu("File") ;
		JMenuItem menuItemImport = new JMenuItem("Import");
		JMenuItem menuItemDelete = new JMenuItem("Delete");
		JMenuItem menuItemQuit = new JMenuItem("Quit");
		
		menuItemImport.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				FileFilter imageFilter = new FileNameExtensionFilter(
					    "Image files", ImageIO.getReaderFileSuffixes());
				fc.addChoosableFileFilter(imageFilter);
				fc.setAcceptAllFileFilterUsed(false);
				
				int returnVal = fc.showOpenDialog(Phototheque.this);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            int newIndex = PhotoCollection.getInstance().addPhoto(new Photo(fc.getSelectedFile().getAbsolutePath()));
		            photoComponent.showPhoto(PhotoCollection.getInstance().getPhoto(newIndex));
		            changeStatus(fc.getSelectedFile().getAbsolutePath()+" : loaded");
		        }
				
			}
		});
		menuItemDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				photoComponent.showPhoto(null);
				changeStatus("Photo cleared");
				
			}
		});
		menuItemQuit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				changeStatus("Quitting");
				dispatchEvent(new WindowEvent(Phototheque.this, WindowEvent.WINDOW_CLOSING));				
			}
		});
		menuFile.add(menuItemImport);
		menuFile.add(menuItemDelete);
		menuFile.add(menuItemQuit);
		
		//Menu View
		JMenu menuView = new JMenu("View");
		JMenuItem menuItemPhotoViewer = new JMenuItem("Photo Viewer");
		JMenuItem menuItemBrowser = new JMenuItem("Browser");
		JMenuItem menuItemSplitMode = new JMenuItem("Split mode");
		menuItemPhotoViewer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				changeStatus("Clicked Photo Viewer");
				
			}
		});
		menuItemBrowser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				changeStatus("Clicked Browser");
				
			}
		});
		menuItemSplitMode.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				changeStatus("Clicked Split mode");
				
			}
		});
		menuView.add(menuItemPhotoViewer);
		menuView.add(menuItemBrowser);
		menuView.add(menuItemSplitMode);
		
		myMenuBar.add(menuFile);
		myMenuBar.add(menuView);
		
		
		
		return myMenuBar ;
	}
	
	private JPanel getToolBar() {
		JPanel toolBar = new JPanel();
		toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.LINE_AXIS));
		toolBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		
		
		JToggleButton familyToggle = new JToggleButton("Family");
		JToggleButton vacationToggle = new JToggleButton("Vacation");
		JToggleButton schoolToggle = new JToggleButton("School");
		familyToggle.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				changeStatus("Family switched" + (e.getStateChange() == ItemEvent.SELECTED ? " on" : " off")) ; 
				
			}
		});
		vacationToggle.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				changeStatus("Vacation switched" + (e.getStateChange() == ItemEvent.SELECTED ? " on" : " off"));
				
			}
		});
		schoolToggle.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				changeStatus("School switched" + (e.getStateChange() == ItemEvent.SELECTED ? " on" : " off"));
				
			}
		});
		toolBar.add(familyToggle);
		toolBar.add(vacationToggle);
		toolBar.add(schoolToggle);
		
		return toolBar ;
	}
	
	private JScrollPane getMainPanel() {
		photoComponent = new PhotoComponent();
		JScrollPane scrollPane = new JScrollPane(photoComponent);
		return scrollPane ;
	}
	
	private JLabel getStatusBar() {
		status = new JLabel(" ");
		status.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
		
		return status ;
	}
	
	private void changeStatus(String newStatus) {
		this.status.setText(newStatus) ;
	}

}
