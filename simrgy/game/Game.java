package simrgy.game;

import java.util.Random;

import simrgy.applet.Main;
import simrgy.game.buildings.AKW;
import simrgy.game.buildings.HQ;
import simrgy.game.buildings.Kohle;
import simrgy.game.buildings.Solar;
import simrgy.game.buildings.Staudamm;
import simrgy.game.buildings.Windrad;
import simrgy.graphic.Grid;

public class Game {

	private Main main;
	
	public int rows = 10; //Zeilen
	public int cols = 10; //Spalten
	
	Random rnd;
	
	public double money; // Geld in Ä
	public long time; // millisekunden runtime
	private long last_update_time;
	protected boolean running;
	Building[][] buildings;
	protected int[][] bautyp; // 0=nicht baubar, 1=land, 2=land+see/fluﬂ, 3=wasser - nicht public setzen, x und y vertauscht!
	
	double[][] windrel; //Wind ralativ zur Position
	double[][] sonnenrel; //Sonne relativ zur Position
	public double windpower = 1.0; // 0.0..1.0 Overall power
	public double windrichtung = 90.0; // grad.  90∞=N, 0∞=E
	public double sonnenintensit‰t = 1.0;
	
	public double mw;
	public double mw_atom;
	public double mw_wind;
	public double mw_kohle;
    public double mw_sonne;
    public double mw_wasser;
    
    public double CO2;
    public int zufriedenheit;
	
	public long personal;
	
	public double strombedarf; //in MW
	public double max_strombedarf; //in MW
	
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
		last_update_time=time;
		
		mw = 0.0;
		mw_atom = 0.0;
		mw_wind = 0.0;
		mw_kohle = 0.0;
        mw_sonne = 0.0;
        mw_wasser = 0.0;
		
		personal = 0;
		zufriedenheit = 0;
		
		strombedarf = 171527.777; //617,5 Mrd kWh = 617,5 Mil MWh = 171527,777 MW
		max_strombedarf = 200000.0;

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
		
		//Sonne initialisieren
        sonnenintensit‰t = 1.0;
        sonnenrel = new double[cols][rows];
        for(int y=0; y<rows; y++)
        {
            double rels = 1-(1.0/(y+1));
            for(int x=0; x<cols; x++)
            {
                sonnenrel[x][y] = rels + (rnd.nextDouble()-0.5)*2/5;
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
		//placeBuilding(4, 0, new Windrad(this, "Windanlage Nord"));
	
		//AKWs
		// http://de.wikipedia.org/wiki/Liste_der_Kernreaktoren_in_Deutschland
		boolean moratorium = true; // zeige AKWs im Moratorium
		placeBuilding(7, 8, AKW.newFinishedAKW(this, "Isar/Ohu", (moratorium ? 2 : 1), 1193.5)); // 1 Reaktor Moratorium
		placeBuilding(3, 1, AKW.newFinishedAKW(this, "Brokdorf", 1, 1480.0));
		if(moratorium) placeBuilding(2, 6, AKW.newFinishedAKW(this, "Biblis", 2, 1262.5)); // Moratorium
		placeBuilding(2, 7, AKW.newFinishedAKW(this, "Philippsburg", (moratorium ? 2 : 1), 1197.0)); // 1 Reaktor Moratorium
		placeBuilding(3, 3, AKW.newFinishedAKW(this, "Grohnde", 1, 1430.0));
		if(moratorium) placeBuilding(3, 2, AKW.newFinishedAKW(this, "Unterweser", 1, 1410.0)); //Moratorium
		if(moratorium) placeBuilding(5, 2, AKW.newFinishedAKW(this, "Kr¸mmel", 1, 1402.0)); // Moratorium
		placeBuilding(1, 3, AKW.newFinishedAKW(this, "Emsland", 1, 1400.0));
		placeBuilding(3, 7, AKW.newFinishedAKW(this, "Neckarwestheim", 2, 1120.0));
		placeBuilding(4, 6, AKW.newFinishedAKW(this, "Grafenrheinfeld", 1, 1345.0));
		placeBuilding(4, 8, AKW.newFinishedAKW(this, "Gundremmingen", 2, 1344.0));
		
	}
	
	public void start(){ running = true; }
	public void pause(){ running = !running; }
	public void restart(){ stop(); start(); }
	public void stop(){ init(); }
	
	public boolean buildBuilding(int x, int y, Building b){
		if( (getBautyp(x,y) & b.getUnderground()) != 0 ){
			return placeBuilding(x,y,b);
		}
		return false;
	}
	
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
	
	public double getSolarPower(Building b)
    {
        for(int y=0; y<rows; y++){
            for(int x=0; x<cols; x++){
                if(b == buildings[x][y]){
                    return sonnenrel[x][y] * sonnenintensit‰t;
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
			//Uhrzeit
			time += timeDiff;
			
			//Usereingaben auswerten (die zwischendurch in einer Queue zwischengelagert werden)
			//...
			
			//nur einmal alle halbe sekunde


			if(new Long(time/1000) > new Long(last_update_time/1000))
			{
				//timeDiff seit letztem statusupdate
				long tdms = time-last_update_time;
				double tds = ((double)tdms)/1000.0;
				
				//Wetterverh‰ltnisse ‰ndern
				windrichtung = (windrichtung-180.0 + (rnd.nextDouble()-0.5)*20*3.6*tds) % 180.0 +180.0; //Windrichtung max 36∞ pro sekunde ‰ndern
				windpower = (windpower-0.5 + (rnd.nextDouble()-0.5)/10*2*tds) % 0.5 + 0.5; //Windkraft um max. 10% pro sekunde ‰ndern
				sonnenintensit‰t = (sonnenintensit‰t-0.5 + (rnd.nextDouble()-0.5)/10*2*tds) % 0.5 + 0.5;
			
				//Geb‰ude
				mw = 0.0;
				
				mw_atom = 0.0;
				mw_wind = 0.0;
				mw_kohle = 0.0;
                mw_sonne = 0.0;
                mw_wasser = 0.0;
				personal = 0;
				CO2 = 0.0;
				zufriedenheit = 0;
				for(Building[] tmp : buildings)
					for(Building b : tmp)
						if(b != null){
							//Personal z‰hlen
							personal += b.getPersonal();
							//Geb‰udekosten
							money -= b.getMoneyCostH() * tds;
							//CO2
							//CO2 += b.getCo2();
							//Stromerzeugung
							if( b instanceof AKW ) { mw_atom += b.getMW(); CO2 += b.getCo2(); }
							else if( b instanceof Windrad )  { mw_wind += b.getMW(); CO2 += b.getCo2(); }
							else if( b instanceof Kohle ) { mw_kohle += b.getMW(); CO2 += b.getCo2(); }
                            else if( b instanceof Solar ) { mw_sonne += b.getMW(); CO2 += b.getCo2(); }
                            else if( b instanceof Staudamm ) { mw_wasser += b.getMW(); CO2 += b.getCo2(); }
							else mw += b.getMW();
							
						}
				mw += mw_atom + mw_wind + mw_kohle + mw_sonne + mw_wasser;
				
				//Stromverkauf
				money += mw * getStrompreis() * tds;
				
				//Update Zeit aktualisieren
				last_update_time=time;
			}

			//Geb‰ude tick
			for(Building[] tmp : buildings)
				for(Building b : tmp)
					if(b != null)
						b.tick(timeDiff);
			
			//Spezielle Geb‰udefunktionen
			//...
	
			
			//Zuf‰llige Ereignisse
			//...
		}
	}
	
	public Main getMain(){return main;}
}
