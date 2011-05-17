package simrgy.graphic.gui;

import simrgy.applet.*;
import simrgy.graphic.GraphicObject;
import static simrgy.res.RessourceManager.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ResearchTab implements GraphicObject {
	public int top;
	public int left;
	public int width;
	public int height;
	
	private GUI gui;

	public ResearchTab(GUI g) {
		gui = g;
		top   = 61;
		left  = getGUI().left+26;
		width = getMain().getWidth()-left-1;
		height = getMain().getHeight()-top-1;
	}
	
	public void draw() {
		Graphics g = getBackbuffer();
		g.setColor(c_research_bg);
		g.fillRect(left, top, width, height);
	}

	public void click(int x, int y) {}
	public void mouseOver(int x, int y) {}
	public void mouseOut(){}

	public GUI getGUI(){return gui;}
	public Main getMain() { return getGUI().getMain(); }
	public Graphics getBackbuffer() { return getGUI().getBackbuffer(); }
	public void keyPress(KeyEvent ke){}
	
}
