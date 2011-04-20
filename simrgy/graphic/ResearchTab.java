package simrgy.graphic;

import simrgy.applet.*;

import java.awt.*;

public class ResearchTab implements GraphicObject {
	public int top;
	public int left;
	public int width;
	public int height;
	private Color backgroundColor = new Color(0x89CFF0); //baby blue
	
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
		g.setColor(backgroundColor);
		g.fillRect(left, top, width, height);
	}

	public void click(int x, int y) {}
	public void mouseOver(int x, int y) {}
	public void mouseOut(){}

	public GUI getGUI(){return gui;}
	public Main getMain() { return getGUI().getMain(); }
	public Graphics getBackbuffer() { return getGUI().getBackbuffer(); }
}
