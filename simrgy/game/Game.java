package simrgy.game;

import java.util.Random;

import simrgy.applet.Main;
import simrgy.game.buildings.AKW;
import simrgy.game.buildings.HQ;
import simrgy.game.buildings.Windrad;
import simrgy.graphic.Grid;

public class Game {

	private Main main;
	
	public int rows = 10; //Zeilen
	public int cols = 10; //Spalten
	
	Random rnd;
	
	public double money; // Geld in Ä
	public long time; // millisekunden runtime
	protected boolean running;
	Building[][] buildings;
	protected int[][] bautyp; // 0=nicht baubar, 1=land, 2=land+see/fluﬂ, 3=wasser - nicht public setzen, x und y vertauscht!
	
	double[][] windrel; //Wind ralativ zur Position
	public double windpower = 1.0; // 0.0..1.0 Overall power
	public double windrichtung = 90.0; // grad.  90∞=N, 0∞=E
	
	public double mwh;
	public double mwh_atom;
	public double mwh_wind;
	
	//Konstruktor
	public Game(Main m){
		main = m;
		init();
	}
	
	//Spiel auf Anfangszustand
	private void init(){
		rnd = new Random();
		running = false;
		money = 100000000.0;
		time = 0;
		
		mwh = 0.0;
		mwh_atom = 0.0;
		mwh_wind = 0.0;
		

		//Wind initialisieren
		windpower = 1.0;
		windrichtung = 90.0;
		windrel = new double[cols][rows];
		for(int y=0; y<rows; y++){
			double rel = 1.0/(y+1);
			for(int x=0; x<cols; x++){
				// rel +- random[-0.2, +0.1999~]
				windrel[x][y] = rel + (rnd.nextDouble()-0.5)*2/5; 
			}
		}
		
		// bit f¸r bit betrachten (verundet)
		// 0=nicht bebaubar, 1=land, 2=see, 4=fluss, 8=wasser
		bautyp = new int[][] {
				{	8,	8,	8,	9,	9,	8,	8,	8,	8,	8	},
				{	8,	8,	9,	13,	5,	9,	11,	1,	9,	8	},
				{	0,	13,	1,	5,	1,	5,	7,	7,	1,	0	},
				{	0,	5,	5,	7,	5,	5,	5,	5,	5,	1	}, 
				{	5,	5,	5,	5,	5,	1,	5,	5,	1,	5	},
				{	1,	5,	5,	1,	5,	1,	5,	1,	5,	5	},
				{	5,	5,	5,	5,	5,	5,	1,	0,	0,	0	},
				{	0,	1,	5,	5,	1,	1,	5,	5,	0,	0	},
				{	0,	1,	5,	5,	5,	5,	5,	5,	5,	0	},
				{	0,	1,	1,	3,	1,	7,	5,	3,	0,	0	}
		}; //x und y vertauscht! daf¸r aber einfacher mit der Karte zu vergleichen.
		
		
		//Geb‰ude
		buildings = new Building[cols][rows];
		
		//Hauptquatier
		placeBuilding(4, 1, new HQ(this));
		
		//Wind
		placeBuilding(4, 0, new Windrad(this, "Windanlage Nord"));
		
		//AKWs
		placeBuilding(3, 1, AKW.newAKW(this, "Unterweser", 1));
		placeBuilding(8, 1, AKW.newAKW(this, "Greifenwald", 3));
		
		
	}
	
	public void start(){ running = true; }
	public void pause(){ running = false; }
	public void restart(){ stop(); start(); }
	public void stop(){ init(); }
	
	private boolean placeBuilding(int x, int y, Building b){
		Grid grid = getMain().getGraphic().getMap().getGrid();
		if(buildings[x][y]!=null) { return false; }
		buildings[x][y]=b;
		if( grid.addBuilding(x, y, b) ==false ) { buildings[x][y]=null; return false; }
		return true;
	}
	
	@SuppressWarnings("unused")
	private void removeBuilding(int x, int y){
		Grid grid = getMain().getGraphic().getMap().getGrid();
		buildings[x][y]=null;
		grid.removeBuilding(x, y);
	}
		
	public double getWindpower(Building b){
		for(int y=0; y<rows; y++){
			for(int x=0; x<cols; x++){
				if(b == buildings[x][y]){
					return windrel[x][y] * windpower;
				}
			}
		}
		return 0.0;
	}
	
	public int getBautyp(int x, int y){
		if( x>=0 && y>=0 && x<cols && y<rows ) return bautyp[y][x]; //da bei erzeugung vertauscht, hier so ausgeben
		return 0;
	}
	
	public Building getBuilding(int x, int y){
		if( x>=0 && y>=0 && x<cols && y<rows ) return buildings[x][y];
		return null;
	}
	
	public double getStrompreis(){
		return 249.5; // Ä/(MWh)
	}
	
	public double getPersonalkosten(){
		return 2000.0/31/24; // Ä/h
	}
	
	public void tick(long timeDiff){
		if(running){
			//timeDiff in halben Sekunden
			double tds = timeDiff/500.0;
			
			//Usereingaben auswerten (die zwischendurch in einer Queue zwischengelagert werden)
			//...
			
			//nur einmal alle halbe sekunde
			if(new Long((time+timeDiff)/500) > new Long(time/500))
			{
				//Wetterverh‰ltnisse ‰ndern
				windrichtung = (windrichtung-180.0 + (rnd.nextDouble()-0.5)*20*3.6) % 180.0 +180.0; //Windrichtung max 36∞ pro halbe sekunde ‰ndern
				windpower = (windpower-0.5 + (rnd.nextDouble()-0.5)/10*2) % 0.5 + 0.5; //Windkraft um max. 10% pro halbe sekunde ‰ndern
			
				//Gelderzeugung
				for(Building[] tmp : buildings)
					for(Building b : tmp)
						if(b != null)
							money += b.getMoneyPerSecond()*tds;
				
				//anzeige der Stromerzeugung
				mwh = 0.0;
				mwh_atom = 0.0;
				mwh_wind = 0.0;
				for(Building[] tmp : buildings)
					for(Building b : tmp)
						if(b != null){
							if( b instanceof AKW ) mwh_atom += b.getPowerPerSecond();
							else if( b instanceof Windrad ) mwh_wind += b.getPowerPerSecond();
							else mwh += b.getPowerPerSecond();
						}
				mwh += mwh_atom + mwh_wind;
			}
			
			
			//Spezielle Geb‰udefunktionen
			//...
			
			//Uhrzeit
			time += timeDiff;
			
			//Zuf‰llige Ereignisse
			//...
		}
	}
	
	public Main getMain(){return main;}
}
