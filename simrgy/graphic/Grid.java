package simrgy.graphic;

import java.awt.*;
import java.net.URL;
import java.util.*;
import javax.swing.ImageIcon;

import simrgy.game.Building;
import simrgy.game.buildings.*;

public class Grid implements GraphicObject {
	private int width;
	private int height;
	private int xspacing = 10;
	private int yspacing = 10;
	private int elementwidth;
	private int elementheight;
	private GridObject[][] buildings;
	private boolean drawgrid = false;
	GridObject over;
	
	//Singleton
	private static Grid instance = null;
	private Grid(){
		Map map = Map.getInstance(); //ok, weil nur auf map interne methoden zugegriffen wird
		width = map.getWidth();
		height = map.getHeight();
		elementwidth = width / xspacing;
		elementheight = height / yspacing;
		buildings = new GridObject[xspacing][yspacing];
		//System.out.println(elementwidth+" "+elementheight);
		
	}	
	public static Grid getInstance(){
		if(instance == null)
			instance = new Grid();
		return instance;
	}
	

	//GraphicObject Methods	
	public void draw(Graphics g){
		//Geb�ude zeichnen
		for(GridObject[] a : buildings)
			for(GridObject el : a)
				if(el != null)
					el.draw(g);
		
		if(drawgrid){
			g.setColor(Color.BLACK);
			//Horizonteles Grid (oben nach unten)
			for(int i=1; i<xspacing; i++)
				g.drawLine(i*elementwidth, 0, i*elementwidth, height);
			//Vertikales Grid (links nach rechts)
			for(int i=1; i<yspacing; i++)
				g.drawLine(0, i*elementheight, width, i*elementheight);
		}
	}

	public void click(int x, int y) {
		int a = x/elementwidth; if(a>=xspacing)a--;
		int b = y/elementheight; if(b>=yspacing)b--;
		buildings[a][b].click();
	}
	
	public void mouseOver(int x, int y) {
		int a = x/elementwidth; if(a>=xspacing)a--;
		int b = y/elementheight; if(b>=yspacing)b--;

		GridObject old = null;
		if(over!=null) old = over;
		over = buildings[a][b];
		if(over!=null && over!=old) over.mouseOver();
		if(old!=null && over!=old) old.mouseOut();

	}
	public void mouseOut() {}	
	
	public boolean addBuilding(int x, int y, Building b){
		if(x>=0 && y>=0 && x<xspacing && y<yspacing)
			{
			buildings[x][y] = new GridObject(x*elementwidth, y*elementheight, elementwidth, elementheight, b);
			return true;
			}
		return false;
	}
	
}
