package simrgy.graphic.map;

import simrgy.applet.*;
import simrgy.graphic.Graphic;
import simrgy.graphic.GraphicObject;
import static simrgy.res.RessourceManager.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Map implements GraphicObject {

	private Graphic graphic; //parent
	private Grid grid;
	private int width;
	private int height;
	
	public Map(Graphic g){
		graphic = g; 
		height = getMain().getHeight();
		width = map.getWidth(null)/(map.getHeight(null)/height);
		grid = new Grid(this);
	}
	
	
	//GraphicObject Methods	
	public void draw(){
		Graphics g = getBackbuffer();
		g.drawImage(map, 0, 0, width, height, null);
		grid.draw();
	}

	public void click(int x, int y) {
		if(x>=0 && x<=width && y>=0 && y<=height)
			grid.click(x, y);
	}
	
	public void mouseOver(int x, int y) {
		if(x>=0 && x<=width && y>=0 && y<=height)
			getGrid().mouseOver(x, y);
		else
			getGrid().mouseOut();
	}
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
