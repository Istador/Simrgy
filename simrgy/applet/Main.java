package simrgy.applet;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import simrgy.graphic.Graphic;

public class Main extends Applet implements MouseListener {

	Graphic graphic = Graphic.getInstance();
	
	public void init(){
		Dimension d = new Dimension(800,600);
		setPreferredSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
		setSize(800,600);
		addMouseListener(this); 
		repaint();
	}
	
	public void start(){
		Music.play(this, "07 - game over.mp3");
	}
	
	
	public void paint(Graphics g){
		graphic.g=g;
		graphic.draw();
	}
	
	public void mouseClicked (MouseEvent me) {
		int x = me.getX();
		int y = me.getY();
		graphic.click(x, y);
	}
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	} 
	
}
