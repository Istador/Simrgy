package simrgy.graphic;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import simrgy.applet.Main;

//Overlay mit referenz auf Main
public class OverlayMain implements GraphicObject {

	protected Main main;
	
	public OverlayMain(Main m){ main=m; init(); }
	
	public void draw() {}
	public void click(int x, int y) {}
	public void mouseOver(int x, int y) {}
	public void mouseOut() {}
	public Graphics getBackbuffer() { return getMain().getBackbuffer(); }
	public Main getMain() { return main; }
	public void keyPress(KeyEvent ke){}
	
	public void init(){}

}
