package simrgy.graphic;

import java.awt.*;
import java.net.*;
import javax.swing.*;

import simrgy.applet.Main;

public class Map implements GraphicObject {

	private Grid grid;
	private Image img;
	private int width;
	private int height;
	
	//Singleton
	private static Map instance = null;
	private Map(){
		URL mapurl = getClass().getResource("../res/img/map.png");
		img = new ImageIcon(mapurl).getImage();
		height = 600;
		width = img.getWidth(null)/(img.getHeight(null)/height);
		
	}	
	public static Map getInstance(){
		if(instance == null){
			instance = new Map();
			instance.grid = Grid.getInstance();
		}
		return instance;
	}
	
	
	//GraphicObject Methods	
	public void draw(Graphics g){
		g.drawImage(img, 0, 0, width, height, null);
		grid.draw(g);
	}

	public void click(int x, int y) {
		grid.click(x, y);
	}
	
	public void mouseOver(int x, int y) {grid.mouseOver(x, y);}
	public void mouseOut() {grid.mouseOut();}

	
	//Getter
	public int getWidth() {return width;}
	public int getHeight() {return height;}	
	
}
