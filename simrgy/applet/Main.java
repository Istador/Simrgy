package simrgy.applet;

import java.applet.Applet;

import java.awt.*;
import java.awt.event.*;
import simrgy.graphic.*;
import simrgy.game.buildings.*;

public class Main extends Applet implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = -7673817554230011101L;

	Graphic graphic;
	private Image backbuffer;
	private Graphics backg;
	private GameThread gt;
	
	public int top = 0;
	public int left = 0;
	public int width = 800;
	public int height = 600;
	
	public void init(){		
		Dimension d = new Dimension(width,height);
		setPreferredSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
		setSize(width, height);
		
		//init Graphic, with backbuffer (no flickering)
		//Source: http://profs.etsmtl.ca/mmcguffin/learn/java/07-backbuffer/
		backbuffer = createImage(getWidth(), getHeight());
		backg = backbuffer.getGraphics();
		
		graphic = new Graphic(this);
		
		addMouseListener(this); 
		addMouseMotionListener(this);
	}
	
	public void start(){
		setSize(width, height);
		
		Grid grid = getGraphic().getMap().getGrid();
		grid.addBuilding(3, 1, AKW.newAKW("Unterweser", 1));
		grid.addBuilding(8, 1, AKW.newAKW("Greifenwald", 3));
		grid.addBuilding(4, 1, new HQ());
		
		gt = new GameThread(this);
		gt.start();
	}
	
	public void stop(){
		gt.done();
	}
	
	public void update( Graphics g ) {
		getGraphic().draw();
		g.drawImage( backbuffer, 0, 0, null );
	}
	
	public void paint(Graphics g){
		update(g);
	}
	
	public void mouseClicked (MouseEvent me) {
		int x = me.getX();
		int y = me.getY();
		getGraphic().click(x, y);
		me.consume();
	}
	public void mouseMoved(MouseEvent me) {
		int x = me.getX();
		int y = me.getY();
		getGraphic().mouseOver(x, y);
		me.consume();
	} 
	public void mouseExited(MouseEvent me) {
		getGraphic().mouseOut();
		me.consume();
	}
	public void mouseEntered(MouseEvent me) {}
	public void mousePressed(MouseEvent me) {}
	public void mouseReleased(MouseEvent me) {}
	public void mouseDragged(MouseEvent arg0) {}
	
	public Graphic getGraphic(){return graphic;}
	public Graphics getBackbuffer(){return backg;}
}
