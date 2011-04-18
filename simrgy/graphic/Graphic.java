package simrgy.graphic;

import java.awt.Color;
import java.awt.Graphics;

public class Graphic implements GraphicObject {

	public Graphics g;
	private Map map = Map.getInstance();
	private GUI gui = GUI.getInstance();
	
	//Singleton
	private static Graphic instance = null;
	private Graphic(){}
	public static Graphic getInstance(){
		if(instance == null)
			instance = new Graphic();
		return instance;
	}
	
	
	public void draw(){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 600);
		
		map.draw();
		gui.draw();
	}
	
	public void click(int x, int y) {
		if( x  < map.width && y <= 600 ) map.click(x, y);
		if( x >= map.width && y <= 600 && x <= 800 ) gui.click(x, y);
	}

	public void mouseOver(int x, int y) {
		
	}

	
}
