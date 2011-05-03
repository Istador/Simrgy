package simrgy.graphic;

import simrgy.applet.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.*;
import javax.swing.*;

public class Map implements GraphicObject {

	private Graphic graphic; //parent
	private Grid grid;
	private Image img;
	private int width;
	private int height;
	
	public Map(Graphic g){
		graphic = g; 
		URL mapurl = getClass().getResource("../res/img/map.png");
		img = new ImageIcon(mapurl).getImage();
		height = getMain().getHeight();
		width = img.getWidth(null)/(img.getHeight(null)/height);
		grid = new Grid(this);
		
	}
	
	
	//GraphicObject Methods	
	public void draw(){
		Graphics g = getBackbuffer();
		g.drawImage(img, 0, 0, width, height, null);
		grid.draw();
	}

	public void click(int x, int y) {
		grid.click(x, y);
	}
	
	public void mouseOver(int x, int y) {getGrid().mouseOver(x, y);}
	public void mouseOut() {getGrid().mouseOut();}

	
	//Getter
	public int getWidth() {return width;}
	public int getHeight() {return height;}	
	public Grid getGrid() {return grid;}
	
	public Graphic getGraphic(){return graphic;}
	public Main getMain(){return getGraphic().getMain();}
	public Graphics getBackbuffer(){return getGraphic().getBackbuffer();}
	public void keyPress(KeyEvent ke){}
	
}
