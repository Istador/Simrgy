package simrgy.applet;

import java.applet.Applet;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Timer;

import simrgy.graphic.Graphic;
import simrgy.graphic.Grid;
import simrgy.game.buildings.*;

public class Main extends Applet implements MouseListener, MouseMotionListener {

	Graphic graphic;
	private Image backbuffer;
	private Graphics backg;
	private GameThread gt;
	
	public void init(){	
		Dimension d = new Dimension(800,600);
		setPreferredSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
		setSize(800,600);
		
		//init Graphic, with backbuffer (no flickering)
		//Source: http://profs.etsmtl.ca/mmcguffin/learn/java/07-backbuffer/
		graphic = Graphic.getInstance();
		backbuffer = createImage(800, 600);
		backg = backbuffer.getGraphics();
		
		addMouseListener(this); 
		addMouseMotionListener(this);
	}
	
	public void start(){
		Grid.getInstance().addBuilding(3, 1, AKW.newAKW("Unterweser", 1));
		Grid.getInstance().addBuilding(7, 1, AKW.newAKW("Greifenwald", 3));
		Grid.getInstance().addBuilding(4, 1, new HQ());
		
		gt = new GameThread(this);
		gt.start();
		//Music.play(this, "07 - game over.mp3");
	}
	
	public void stop(){
		gt.done();
	}
	
	public void update( Graphics g ) {
		graphic.draw(backg);
		g.drawImage( backbuffer, 0, 0, null );
	}
	
	public void paint(Graphics g){
		update(g);
	}
	
	public void mouseClicked (MouseEvent me) {
		int x = me.getX();
		int y = me.getY();
		graphic.click(x, y);
		me.consume();
	}
	public void mouseMoved(MouseEvent me) {
		int x = me.getX();
		int y = me.getY();
		graphic.mouseOver(x, y);
		me.consume();
	} 
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	public void mouseDragged(MouseEvent arg0) {}
}
