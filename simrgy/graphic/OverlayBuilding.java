package simrgy.graphic;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import simrgy.applet.Main;
import simrgy.game.Building;

//Overlay mit referenz auf Building
public class OverlayBuilding implements GraphicObject {

	protected Building building;
	
	public OverlayBuilding(Building b){ building=b; init(); }
	
	public void draw() {}
	public void click(int x, int y) {}
	public void mouseOver(int x, int y) {}
	public void mouseOut() {}
	public Graphics getBackbuffer() { return getMain().getBackbuffer(); }
	public Main getMain() { return building.getGame().getMain(); }
	public void keyPress(KeyEvent ke){}
	
	public void init(){}

}
