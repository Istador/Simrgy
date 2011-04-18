package simrgy.graphic;

import java.awt.*;
import java.net.*;
import javax.swing.*;

import simrgy.applet.Main;

public class Map implements GraphicObject {

	private static Image map;
	public static int width;
	public int height;
	
	//Singleton
	private static Map instance = null;
	private Map(){
		URL mapurl = getClass().getResource("../res/img/map.png");
		map = new ImageIcon(mapurl).getImage();
		this.width = map.getWidth(null)/(map.getHeight(null)/600);
	}	
	public static Map getInstance(){
		if(instance == null)
			instance = new Map();
		return instance;
	}
	
	public void draw(){
		Graphic.getInstance().g.drawImage(map, 0, 0, this.width, 600, null);
	}

	public void click(int x, int y) {
		System.out.println("Map clicked at: ( "+String.valueOf(x)+", "+String.valueOf(y)+" )");
	}
	
	public void mouseOver(int x, int y) {
		
	}
	
}
