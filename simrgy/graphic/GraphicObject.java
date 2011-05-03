package simrgy.graphic;

import simrgy.applet.*;

import java.awt.*;
import java.awt.event.KeyEvent;



public interface GraphicObject {

	public void draw();
	public void click(int x, int y);
	public void mouseOver(int x, int y);
	public void mouseOut();
	public Graphics getBackbuffer();
	public Main getMain();
	
	public void keyPress(KeyEvent ke);
	
}
