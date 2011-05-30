package simrgy.game;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import simrgy.applet.Main;
import simrgy.game.buildings.AKW;
import simrgy.game.buildings.HQ;
import simrgy.game.buildings.Kohle;
import simrgy.game.buildings.Solar;
import simrgy.game.buildings.Staudamm;
import simrgy.game.buildings.Windrad;
import simrgy.graphic.map.Grid;

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
	public double sonnenintensit‰t = 1.0;
	
	protected Lock research_mutex = new ReentrantLock();
	protected Map<Research, Long> researching; //Zeit in ms die bisher geforscht
	protected Set<Research> finishedResearch;
	
	public int uran = 20000;
	public int uran_max = 20000;
	public int kohle = 50000;
	public int kohle_max = 50000;
	
	public double mw;
	public double mw_atom;
	public double mw_wind;
	public double mw_kohle;
    public double mw_sonne;
    public double mw_wasser;
    
    public double CO2;
    public double zufriedenheit;
	
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
		zufriedenheit = 50.0;
		
		strombedarf = 50000; //171527.777; //617,5 Mrd kWh = 617,5 Mil MWh = 171527,777 MW
		max_strombedarf = 100000.0;

		//Forschung init
		researching = new HashMap<Research, Long>();
		finishedResearch = new HashSet<Research>();
		
		//Wind initialisieren
		windpower = 1.0;
		windrel = new double[cols][rows];
		for(int y=0; y<rows; y++){
			double rel = 1.0/(y+1);
			for(int x=0; x<cols; x++){
				// rel +- random[-0.2, +0.1999~]
				rel = rel + (rnd.nextDouble()-0.5)*2/5;
				rel = (rel>1.0 ? 1.0 : ( rel<0.0 ? 0.0 : rel ) ); //0.0 bis 1.0 einhalten
				windrel[x][y] = rel; 
			}
		}
		
		//Sonne initialisieren
        sonnenintensit‰t = 1.0;
        sonnenrel = new double[cols][rows];
        for(int y=0; y<rows; y++){
            double rels = 1-(1.0/(y+1));
            for(int x=0; x<cols; x++){
            	// rels +- random[-0.2, +0.1999~]
				rels = rels + (rnd.nextDouble()-0.5)*2/5;
				rels = (rels>1.0 ? 1.0 : ( rels<0.0 ? 0.0 : rels ) ); //0.0 bis 1.0 einhalten
                sonnenrel[x][y] = rels;
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
		//http://de.wikipedia.org/wiki/Liste_deutscher_Kraftwerke
		
		//Wind
		placeBuilding(7, 0, Windrad.newFinishedWindrad(this, "Baltic 2", 80));
		placeBuilding(3, 0, Windrad.newFinishedWindrad(this, "Windpark Reuﬂenkˆge", 68));
		placeBuilding(2, 1, Windrad.newFinishedWindrad(this, "Windpark Holtriem", 40));
		placeBuilding(6, 3, Windrad.newFinishedWindrad(this, "Windparks Sachsen Anhalt", 60));
		placeBuilding(3, 4, Windrad.newFinishedWindrad(this, "Windpark Sintfeld", 65));
		
		//Solar
		placeBuilding(6, 4, Solar.newFinishedSolar(this, "Solarpark Kˆthen", 2));
		placeBuilding(7, 4, Solar.newFinishedSolar(this, "Solarpark Waldpolenz", 2));
		placeBuilding(9, 3, Solar.newFinishedSolar(this, "Solarpark Lieberose", 3));
		placeBuilding(8, 8, Solar.newFinishedSolar(this, "Solarpark Pocking", 1));
		
		//Kohle
		placeBuilding(0, 4, Kohle.newFinishedKohle(this, "Neurath", 5, 441.0));
		placeBuilding(0, 5, Kohle.newFinishedKohle(this, "Niederauﬂem", 9, 430.0));
		placeBuilding(3, 6, Kohle.newFinishedKohle(this, "Staudinger Groﬂkrotzenburg", 5, 400.0));
		placeBuilding(8, 3, Kohle.newFinishedKohle(this, "J‰nschwalde", 6, 500.0));
		placeBuilding(7, 5, Kohle.newFinishedKohle(this, "Lippendorf", 2, 933.6));
		placeBuilding(9, 5, Kohle.newFinishedKohle(this, "Boxberg", 3, 635.7));
		placeBuilding(9, 4, Kohle.newFinishedKohle(this, "Schwarze Pumpe", 2, 800.0));
		
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
			if( money-b.getBaukosten()>=0 && placeBuilding(x,y,b) ){
				money -= b.getBaukosten();
				return true;
			}
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
	
	public void removeBuilding(Building b){
        for(int y=0; y<rows; y++){
            for(int x=0; x<cols; x++){
                if(b == buildings[x][y]){
                	removeBuilding(x, y);
                	money += (double)(b.getBaukosten()/2);
                }
            }
        }
	}
	
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
	
	public double getWindpower(int x, int y){
		if( x>=0 && y>=0 && x<cols && y<rows ) return windrel[x][y];
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
	
	public double getSolarPower(int x, int y){
		if( x>=0 && y>=0 && x<cols && y<rows ) return sonnenrel[x][y];
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
	
	public double getZufriedenheit()
	{
		double tmp = 0.0;
		//double differenz = max_strombedarf - strombedarf;
		//double tmp1 = max_strombedarf/100;
		//double tmp2 = strombedarf/100;
		
		if(mw < strombedarf){
			tmp -= 1;
	    }
		else{
			tmp += 1;
		}
		
	    if(CO2 > 500){
	    	tmp -= 1;
	    }

		return tmp;
	}
	
	public void tick(long timeDiff){
		if(running){
			//Uhrzeit
			time += timeDiff;
			
			//Usereingaben auswerten (die zwischendurch in einer Queue zwischengelagert werden)
			//...

			if(new Long(time/1000) > new Long(last_update_time/1000))
			{
				//timeDiff seit letztem statusupdate
				long tdms = time-last_update_time;
				double tds = ((double)tdms)/1000.0;
				
				//Strombedarf berechnen
				strombedarf = 50000.0 + time/1000*5; //ansteigender Strombedarf (5MW/s)
				strombedarf = strombedarf + 12500.0*Math.sin(((time/100.0)%360.0)/180.0*Math.PI);
				strombedarf = (strombedarf >= max_strombedarf ? max_strombedarf : strombedarf ); //nicht > als max
				
				//Wetterverh‰ltnisse ‰ndern
				windpower = (windpower-0.5 + (rnd.nextDouble()-0.5)/10*2*tds) % 0.5 + 0.5; //Windkraft um max. 10% pro sekunde ‰ndern
				sonnenintensit‰t = (sonnenintensit‰t-0.5 + (rnd.nextDouble()-0.5)/10*2*tds) % 0.5 + 0.5;
				//TODO Sonne wie Stromverbrauch abh‰ngig von der Uhrzeit (sinus * -1 f¸r den gegenteiligen Effekt (Nachts wenig Sonne, viel Stromverbrauch))
			
				//Geb‰ude
				mw = 0.0;
				
				mw_atom = 0.0;
				mw_wind = 0.0;
				mw_kohle = 0.0;
                mw_sonne = 0.0;
                mw_wasser = 0.0;
				personal = 0;
				CO2 = 0.0;
				//zufriedenheit = 0;
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
							if( b instanceof AKW ) { mw_atom += b.consumeMW(); CO2 += b.getCo2(); }
							else if( b instanceof Windrad )  { mw_wind += b.consumeMW(); CO2 += b.getCo2(); }
							else if( b instanceof Kohle ) { mw_kohle += b.consumeMW(); CO2 += b.getCo2(); }
                            else if( b instanceof Solar ) { mw_sonne += b.consumeMW(); CO2 += b.getCo2(); }
                            else if( b instanceof Staudamm ) { mw_wasser += b.consumeMW(); CO2 += b.getCo2(); }
							else mw += b.consumeMW();
							
						}
				mw += mw_atom + mw_wind + mw_kohle + mw_sonne + mw_wasser;
				
				//Stromverkauf
				money += (double)mw * getStrompreis() * tds;
				
				//Zufriedenheit
		        zufriedenheit += getZufriedenheit() * tds;
		        if(zufriedenheit>100.0)
		        {
		        	zufriedenheit = 100.0;
		        }
		        if(zufriedenheit<0.0)
		        {
		        	zufriedenheit = 0.0;
		        }
		        
				
				//Update Zeit aktualisieren
				last_update_time=time;
			}

			//Geb‰ude tick
			for(Building[] tmp : buildings)
				for(Building b : tmp)
					if(b != null)
						b.tick(timeDiff);
			
			//Forschung tick
			research_mutex.lock();
			Set<Research> remove = new HashSet<Research>(); //Objekte die entfernt werden sollen
			for(Research r : researching.keySet()){
				researching.put(r, researching.get(r)+timeDiff); //Zeit erhˆhen
				if( r.isDone(this) ){
					remove.add(r);
				}
			}
			//entferne Objekte
			for(Research r : remove){
				researching.remove(r);
				finishedResearch.add(r);
				r.researchEffect(this); //Forschung ausf¸hren
			}
			research_mutex.unlock();
			
			//Spezielle Geb‰udefunktionen
			//...
			
			//Zuf‰llige Ereignisse
			//...
		}
	}
	
	public boolean consumeUran(){
		if(uran-1>=0){
			uran-=1;
			return true;
		}
		return false;
	}
	
	public boolean consumeKohle(){
		if(kohle-1>=0){
			kohle-=1;
			return true;
		}
		return false;
	}
		
	public boolean isResearching(Research r){
		return researching.containsKey(r);
	}
	public boolean isResearchDone(Research r){
		return finishedResearch.contains(r);
	}
	public long getResearchTime(Research r){
		if(researching.containsKey(r)) return (long) researching.get(r);
		return 0;
	}
	public boolean addResearch(Research r){
		if(isResearchDone(r)) return false;
		if(isResearching(r)) return false;
		research_mutex.lock();
		researching.put(r, (long) 0);
		research_mutex.unlock();
		return true;
	}
	
	public Main getMain(){return main;}
}
