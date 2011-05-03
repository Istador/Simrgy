package simrgy.graphic;

import simrgy.applet.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;


public class StatusTab implements GraphicObject {
	public int top;
	public int left;
	public int width;
	public int height;

	private Font font = new Font("Helvetica", Font.PLAIN, 14);
	
	private GUI gui;
	
	public StatusTab(GUI g){
		gui = g;
		top   = 61;
		left  = getGUI().left+26;
		width = getMain().getWidth()-left-1;
		height = getMain().getHeight()-top-1;
	}	

	public void draw() {
		Graphics g = getBackbuffer();
		g.setFont(font);
		g.setColor(Color.BLACK);
		
		int ttop = top+15;
		int tleft = left+5;
		int tright = left+150;
		
		DecimalFormat ein = new DecimalFormat("0");
		DecimalFormat mw = new DecimalFormat("0.00");
		
		g.drawString("Zeit:", tleft, ttop);
		g.drawString(String.valueOf(getMain().getGame().time/1000), tright, ttop);
		
		ttop+=40;
		
		g.drawString("Windstärke:", tleft, ttop);
		g.drawString(ein.format(getMain().getGame().windpower*100.0)+" %", tright, ttop);
		ttop+=20;
		g.drawString("Windrichtung:", tleft, ttop);
		g.drawString(ein.format(getMain().getGame().windrichtung)+" °", tright, ttop);
		
		ttop+=40;
		
		g.drawString("Strombedarf:", tleft, ttop);
		g.drawString(mw.format(getMain().getGame().strombedarf)+" MW", tright, ttop);
		ttop+=20;
		g.drawString("Stromerzeugung:", tleft, ttop);
		g.drawString(mw.format(getMain().getGame().mw)+" MW", tright, ttop);
		ttop+=20;
		g.drawString("Atomkraft:", tleft, ttop);
		g.drawString(mw.format(getMain().getGame().mw_atom)+" MW", tright, ttop);
		ttop+=20;
		g.drawString("Windkraft:", tleft, ttop);
		g.drawString(mw.format(getMain().getGame().mw_wind)+" MW", tright, ttop);
	}

	public void click(int x, int y) {}
	public void mouseOver(int x, int y) {}
	public void mouseOut(){}
	
	public GUI getGUI(){return gui;}
	public Main getMain() { return getGUI().getMain(); }
	public Graphics getBackbuffer() { return getGUI().getBackbuffer(); }
	public void keyPress(KeyEvent ke){}
}
