package simrgy.graphic;

import static simrgy.res.RessourceManager.*;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.net.URI;

import simrgy.applet.Main;
import simrgy.graphic.menu.RunnableString;

public class ButtonHTTPLabel implements Button {

	protected ButtonLabel child;
	
	protected boolean highlight = false;
	
	protected ButtonHTTPLabel(){}
	public ButtonHTTPLabel(GraphicObject parent, String text, String url, int x, int y, Font f){
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
		this.child = new ButtonLabel(parent, text, cBlue, cRed, x, y, f, r);
		
	}
	
	public void draw() {
		child.draw();
		if(highlight)
			getBackbuffer().drawLine(child.left, child.top+child.height+1, child.left+child.width, child.top+child.height+1);
	}
	
	public void mouseOver() {
		highlight=true;
		child.mouseOver();
	}
	
	public void mouseOver(int x, int y) {
		if(contains(x,y))
			mouseOver();
		else
			mouseOut();
	}
	
	public void mouseOut() {
		highlight=false;
		child.mouseOut();
	}
	
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
	public void setTL(int top, int left) { child.setTL(top, left); }
}
