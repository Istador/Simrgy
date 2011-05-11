package simrgy.graphic;

import simrgy.applet.*;
import simrgy.game.*;
import simrgy.game.buildings.Solar;
import simrgy.game.buildings.Windrad;

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
	public boolean drawgrid = false;
	GridObject over;
	
	private Font prozent_font = new Font("Helvetica", Font.PLAIN, 18);
		
	private Map map;
	
	public Grid(Map m){
		map = m;
		width = getMap().getWidth();
		height = getMap().getHeight();
		elementwidth = width / cols;
		elementheight = height / rows;
		buildings = new GridObject[cols][rows];		
	}	

	//GraphicObject Methods	
	public void draw(){
		Graphics g = getBackbuffer();
				
		//Bauraster zeichnen
		drawHighlightUnderground();
		
		//Grid zeichnen
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
					getMain().getGame().buildBuilding(a, b, bts);
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
		
	//Bauraster zeichnen
	protected void drawHighlightUnderground(){
		Graphics g = getBackbuffer();
		Game game = getMain().getGame();
		GUI gui = getMap().getGraphic().getGUI();
		Building b = gui.getBuildTab().getSelectedBuilding();
		
		//wenn Bautab nicht an
		if(gui.getSelectedTab() != gui.getBuildTab() ) return;
		
		//wenn kein Gebäude ausgewählt
		if(b==null) return;
		
		int highlightUnderground = b.getUnderground();
		if(highlightUnderground==0) return;

		Color nichtbaubar = new Color(0,0,0,0.6f); //ausschwärtzen
		g.setFont(prozent_font);

		//Für alle Felder
		for(int x=0; x<cols; x++)
			for(int y=0; y<rows; y++){
				String proz = ""; //Prozentzahl für Gebäude mit variablen Untergrund
				//auf dem kein Gebäude steht
				if(game.getBuilding(x, y)==null)
					if( (game.getBautyp(x, y) & highlightUnderground) != 0 ){
						//Color baubar dynamisch
						Color baubar = new Color(0, 1, 0, 0.4f);
						if( b instanceof Windrad || b instanceof Solar ){
							float attr = 0.0f; //attraktivität (0.0 .. 1.0)
							if( b instanceof Windrad ){
								attr = (float) game.getWindpower(x, y);
							}
							else{
								attr = (float) game.getSolarPower(x, y);
							}
							proz = String.valueOf((int)(attr*100))+"%";
							float red = 2.0f - (attr*2>1.0f ? attr*2 : 1.0f );
							float green = (attr*2>1.0f ? 1.0f : attr*2 );
							baubar = new Color(red, green, 0, 0.4f);//variabel (funktioniert)!
							}						
						g.setColor(baubar); //Färbe grün bei passendem Untergrund
					}
					else g.setColor(nichtbaubar); //Färbe rot bei ungünstigem Untergrund
				else g.setColor(nichtbaubar); //Färbe rot, wenn da schon ein Gebäude steht.
				g.fillRect(x*elementwidth, y*elementheight, elementwidth, elementheight);
				if(!proz.equals("")){
					g.setColor(Color.BLACK);
					g.drawString(proz, x*elementwidth+5, (y+1)*elementheight-5);
				}
			}
		//Färbe den überschüssigen Rand rot
		g.setColor(nichtbaubar);
		int tmp = cols*elementwidth;
		g.fillRect(tmp, 0, width-tmp, height);
	}
	
	public Map getMap(){return map;}
	public Main getMain(){return getMap().getMain();}
	public Graphics getBackbuffer(){return getMap().getBackbuffer();}
	public void keyPress(KeyEvent ke){}
}
