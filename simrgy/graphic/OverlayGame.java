package simrgy.graphic;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import simrgy.applet.Main;
import simrgy.game.Game;

//Overlay mit referenz auf Game
public class OverlayGame implements GraphicObject {

	protected Game game;
	
	public OverlayGame(Game g){ game=g; init(); }
	
	public void draw() {}
	public void click(int x, int y) {}
	public void mouseOver(int x, int y) {}
	public void mouseOut() {}
	public Graphics getBackbuffer() { return getMain().getBackbuffer(); }
	public Main getMain() { return game.getMain(); }
	public void keyPress(KeyEvent ke){}
	
	public void init(){}

}
