package simrgy.graphic;

import simrgy.applet.*;
import simrgy.game.*;

import java.awt.*;
import java.awt.event.KeyEvent;



public class Grid implements GraphicObject {
	private int width;
	private int height;
	private int cols = 10;
	private int rows = 10;
	private int elementwidth;
	private int elementheight;
	private GridObject[][] buildings;
	private boolean drawgrid = true;
	GridObject over;
	
	private int highlightUnderground = 0;
	
	private Map map;
	
	public Grid(Map m){
		map = m;
		width = getMap().getWidth();
		height = getMap().getHeight();
		elementwidth = width / cols;
		elementheight = height / rows;
		buildings = new GridObject[cols][rows];
		//System.out.println(elementwidth+" "+elementheight);
		
	}	

	//GraphicObject Methods	
	public void draw(){
		Graphics g = getBackbuffer();
		
		if(highlightUnderground != 0)
			drawHighlightUnderground();
		
		if(drawgrid){
			g.setColor(Color.BLACK);
			//Horizonteles Grid (oben nach unten)
			for(int i=0; i<cols; i++)
				g.drawLine(i*elementwidth, 0, i*elementwidth, height);
			//Vertikales Grid (links nach rechts)
			for(int i=0; i<rows; i++)
				g.drawLine(0, i*elementheight, width, i*elementheight);
		}
		
		//Gebäude zeichnen
		for(GridObject[] a : buildings)
			for(GridObject el : a) if(el!=null) el.draw();
		//Das Gebäude über dem wir sind nochmal zeichnen (wegen Namensschildern)
		if(over!=null)
			over.draw();
	}

	public void click(int x, int y) {
		//logische Koordinaten abrunden, da karte nicht quadratisch
		int a = x/elementwidth; if(a>=cols)a--;
		int b = y/elementheight; if(b>=rows)b--;
		if(buildings[a][b]!=null) buildings[a][b].click();
		
		//Bauen
		BuildTab bt = getMap().getGraphic().getGUI().getBuildTab();
		if(getMap().getGraphic().getGUI().getSelectedTab() == bt ){
			Building bts = bt.getSelectedBuilding();
			if(bts != null){
				//kann bauen?
				if(buildings[a][b]==null)
					//baue
					if( getMain().getGame().buildBuilding(a, b, bts) )
						bt.clearSelectedBuilding();
			}
		}
	}
	
	public void mouseOver(int x, int y) {
		//logische Koordinaten abrunden, da karte nicht quadratisch
		int a = x/elementwidth; if(a>=cols)a--;
		int b = y/elementheight; if(b>=rows)b--;

		GridObject old = over; //altes Gebäude zwischenspeichern
		over = buildings[a][b]; //neues Gebäude merken
		//neuen mouseOver senden
		if(over!=null && over!=old) over.mouseOver();
		//alten mouseOut senden
		if(old!=null && over!=old) old.mouseOut();
		
		//Bauraster
		BuildTab bt = getMap().getGraphic().getGUI().getBuildTab();
		if(getMap().getGraphic().getGUI().getSelectedTab() == bt ){
			Building bts = bt.getSelectedBuilding();
			if(bts != null)
				highlightUnderground(bts.getUnderground());
			else
				highlightUnderground(0);
		}
		else
			highlightUnderground(0);
	}
	public void mouseOut() {
		if(over!=null)
			{
			//alten mouseOut senden
			over.mouseOut();
			//alten löschen
			over=null;
			}
		//highlightUnderground(0); //TODO
	}	
	
	public boolean addBuilding(int x, int y, Building b){
		if(x>=0 && y>=0 && x<cols && y<rows && buildings[x][y]==null)
			{
			buildings[x][y] = new GridObject(this, x*elementwidth, y*elementheight, elementwidth, elementheight, b);
			return true;
			}
		return false;
	}
	public void removeBuilding(int x, int y){
			buildings[x][y] = null;
	}
	
	public void highlightUnderground(int underground){
		highlightUnderground = underground;
	}
	
	protected void drawHighlightUnderground(){
		Graphics g = getBackbuffer();
		//Transparente Farben
		Color green = new Color(0,1,0,0.4f);
		Color red = new Color(1,0,0,0.4f);
		Game game = getMain().getGame();
		//Für alle Felder
		for(int x=0; x<cols; x++)
			for(int y=0; y<rows; y++){
				//auf dem kein Gebäude steht
				if(game.getBuilding(x, y)==null)
					if( (game.getBautyp(x, y) & highlightUnderground) != 0 )
						g.setColor(green); //Färbe grün bei passendem Untergrund
					else g.setColor(red); //Färbe rot bei ungünstigem Untergrund
				else g.setColor(red); //Färbe rot, wenn da schon ein Gebäude steht.
				g.fillRect(x*elementwidth, y*elementheight, elementwidth, elementheight);
			}
		//Färbe den überschüssigen Rand rot
		g.setColor(red);
		int tmp = cols*elementwidth;
		g.fillRect(tmp, 0, width-tmp, height);
	}
	
	public Map getMap(){return map;}
	public Main getMain(){return getMap().getMain();}
	public Graphics getBackbuffer(){return getMap().getBackbuffer();}
	public void keyPress(KeyEvent ke){}
}
