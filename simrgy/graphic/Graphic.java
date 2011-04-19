package simrgy.graphic;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Graphic implements GraphicObject {

	private Map map;
	private GUI gui;
	

	
	//Singleton
	private static Graphic instance = null;
	private Graphic(){}
	public static Graphic getInstance(){
		if(instance == null){
			instance = new Graphic();
			instance.map = Map.getInstance();
			instance.gui = GUI.getInstance();
		}
		return instance;
	}
	
	
	public void draw(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 600);
	
		map.draw(g);
		gui.draw(g);
	}
	
	public void click(int x, int y) {
		if( x  < map.getWidth() && y <= 600 ) map.click(x, y);
		if( x >= map.getWidth() && y <= 600 && x <= 800 ) gui.click(x, y);
	}

	public void mouseOver(int x, int y) {
		if( x  < map.getWidth() && y <= 600 ){ map.mouseOver(x, y); gui.mouseOut(); }
		if( x >= map.getWidth() && y <= 600 && x <= 800 ){ gui.mouseOver(x, y); map.mouseOut(); }
	}
	public void mouseOut() {}
	
}
