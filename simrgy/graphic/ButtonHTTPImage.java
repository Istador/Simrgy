package simrgy.graphic;


import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.net.URI;

import static simrgy.res.RessourceManager.*;

import simrgy.applet.Main;
import simrgy.graphic.menu.RunnableString;

public class ButtonHTTPImage implements Button {

	protected ButtonImage child;
		
	protected ButtonHTTPImage(){}
	public ButtonHTTPImage(GraphicObject parent, Image img, String url, int x, int y, int width, int height){
		RunnableString r = new RunnableString(url) {
			public void run() { 
				try {
					if( Desktop.isDesktopSupported() ) {
						//Desktop desk = Desktop.getDesktop();
						Desktop.getDesktop().browse(new URI(str));
					}
				} catch (Exception e) {}
			}
		};
		this.child = new ButtonImage(parent, img, null, cGreen30, x, y, width, height, r);
	}
	
	public void draw() { child.draw(); }
	public void mouseOver() { child.mouseOver(); }
	public void mouseOver(int x, int y) { child.mouseOver(x, y); }
	public void mouseOut() { child.mouseOut(); }
	
	public void click(int x, int y) { child.click(x, y); }
	public Graphics getBackbuffer() { return child.getBackbuffer(); }
	public Main getMain() { return child.getMain(); }
	public void keyPress(KeyEvent ke) { child.keyPress(ke); }
	public boolean contains(int x, int y) { return child.contains(x, y); }
	public void click() { child.click(); }
	public Point getTL() { return child.getTL(); }
	public Point getTR() { return child.getTR(); }
	public Point getBL() { return child.getBL(); }
	public Point getBR() { return child.getBR(); }
	public int getWidth() { return child.getWidth(); }
	public int getHeight() { return child.getHeight(); }
	public void markiere(Color c) { child.markiere(c); }	
	//public void setTL(int top, int left) { child.setTL(top, left); }
}
