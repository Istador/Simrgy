package simrgy.graphic;

import simrgy.applet.*;
import simrgy.game.*;

import java.awt.*;



public class Grid implements GraphicObject {
	private int width;
	private int height;
	private int xspacing = 10;
	private int yspacing = 10;
	private int elementwidth;
	private int elementheight;
	private GridObject[][] buildings;
	private boolean drawgrid = true;
	GridObject over;
	
	private Map map;
	
	public Grid(Map m){
		map = m;
		width = getMap().getWidth();
		height = getMap().getHeight();
		elementwidth = width / xspacing;
		elementheight = height / yspacing;
		buildings = new GridObject[xspacing][yspacing];
		//System.out.println(elementwidth+" "+elementheight);
		
	}	

	//GraphicObject Methods	
	public void draw(){
		Graphics g = getBackbuffer();
		if(drawgrid){
			g.setColor(Color.BLACK);
			//Horizonteles Grid (oben nach unten)
			for(int i=1; i<xspacing; i++)
				g.drawLine(i*elementwidth, 0, i*elementwidth, height);
			//Vertikales Grid (links nach rechts)
			for(int i=1; i<yspacing; i++)
				g.drawLine(0, i*elementheight, width, i*elementheight);
		}
		
		//Gebäude zeichnen
		for(GridObject[] a : buildings)
			for(GridObject el : a) if(el!=null) el.draw();
	}

	public void click(int x, int y) {
		//logische Koordinaten abrunden, da karte nicht quadratisch
		int a = x/elementwidth; if(a>=xspacing)a--;
		int b = y/elementheight; if(b>=yspacing)b--;
		if(buildings[a][b]!=null) buildings[a][b].click();
	}
	
	public void mouseOver(int x, int y) {
		//logische Koordinaten abrunden, da karte nicht quadratisch
		int a = x/elementwidth; if(a>=xspacing)a--;
		int b = y/elementheight; if(b>=yspacing)b--;

		GridObject old = over; //altes Gebäude zwischenspeichern
		over = buildings[a][b]; //neues Gebäude merken
		//neuen mouseOver senden
		if(over!=null && over!=old) over.mouseOver();
		//alten mouseOut senden
		if(old!=null && over!=old) old.mouseOut();

	}
	public void mouseOut() {
		if(over!=null)
			{
			//alten mouseOut senden
			over.mouseOut();
			//alten löschen
			over=null;
			}
	}	
	
	public boolean addBuilding(int x, int y, Building b){
		if(x>=0 && y>=0 && x<xspacing && y<yspacing)
			{
			buildings[x][y] = new GridObject(this, x*elementwidth, y*elementheight, elementwidth, elementheight, b);
			return true;
			}
		return false;
	}
	
	public Map getMap(){return map;}
	public Main getMain(){return getMap().getMain();}
	public Graphics getBackbuffer(){return getMap().getBackbuffer();}
}
