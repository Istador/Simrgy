package simrgy.game;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import simrgy.applet.Main;
import simrgy.applet.Music;
import simrgy.game.buildings.*;
import simrgy.game.research.RSonne;
import simrgy.graphic.map.Grid;
import simrgy.graphic.popups.*;

public class Game {

	private Main main;
	
	public int rows; //Zeilen
	public int cols; //Spalten
	
	public Random rnd;
	
	public HQ hq; 
	
	//Research Parameter
	public double rAKWSecure;
	public double rSolarEnergy;
	public double rH2OEnergy;
	public double rNightEnergy;
	public double rWindBuildTime;
	public double rKohleCO2;
	public double rNetz;
	
	public double money; // Geld in €
	public double money_min; //minimales geld. alles dadrunter führt zum GameOver
	public long time; // millisekunden runtime
	private long last_update_time;
	
	public boolean running; //laueft spiel oder pausiert?
	public boolean started; //wurde das Spiel gestartet (läuft oder pausiert)
	
	protected Building[][] buildings;
	protected int[][] bautyp; // 0=nicht baubar, 1=land, 2=land+see/fluß, 3=wasser - nicht public setzen, x und y vertauscht!
	
	double[][] windrel; //Wind ralativ zur Position
	double[][] sonnenrel; //Sonne relativ zur Position
	public double windpower = 1.0; // 0.0..1.0 Overall power
	public double sonnenintensitaet = 1.0;
	
	protected Lock research_mutex = new ReentrantLock();
	protected Map<Research, Long> researching; //Zeit in ms die bisher geforscht
	protected Set<Research> finishedResearch;
	
	public double uran;
	public double uran_max;
	private boolean uran_zero_first;
	public int kohle;
	public int kohle_max;
	private boolean kohle_zero_first;
	
	public boolean akw_unfall; //zurzeit ein unfall
	public boolean akw_explosion; //spiel abbrechen?
	public boolean akw_unfall_first; //erster unfall?
	
	public double mw;
	public double mw_atom;
	public double mw_wind;
	public double mw_kohle;
    public double mw_sonne;
    public double mw_wasser;
    public double gewinn;
    public double gewinn_inland;
    public double gewinn_ausland;
    public double verlust_ausland;
    public double verlust_gebauedekosten;
    
    public double CO2;
    protected double zufriedenheit;
	
    public int liquidatoren_count = 0;
    
	public long personal;
	
	public long ansteigender_strombedarf_zeitpunkt;
	public boolean ansteigender_strombedarf;
	public double strombedarf; //in MW
	public double max_strombedarf; //in MW
	
	public double strompreis;
	public double strompreis_min;
	public double strompreis_max;
	
	public double highscore_sum_zufriedenheit;
	public double highscore_max_gewinn;
	public double highscore_max_money;
	
	//Konstruktor
	public Game(Main m){
		main = m;
		init();
	}
	
	//Spiel auf Anfangszustand
	private void init(){
		rnd = new Random();
	
		running = false;
		started = false;
		
		money = 1000000000.0;
		money_min = -500000000.0;
		time = 0;
		last_update_time=time;

		rows = 10;
		cols = 10;
		
		mw = 0.0;
		mw_atom = 0.0;
		mw_wind = 0.0;
		mw_kohle = 0.0;
        mw_sonne = 0.0;
        mw_wasser = 0.0;
        gewinn = 0.0;
        gewinn_inland = 0.0;
        gewinn_ausland = 0.0;
        verlust_ausland = 0.0;
        verlust_gebauedekosten = 0.0;
        
    	rAKWSecure = 1.0;
    	rSolarEnergy = 1.0;
    	rH2OEnergy = 1.0;
    	rNightEnergy = 1.0;
    	rWindBuildTime = 1.0;
    	rKohleCO2 = 1.0;
    	rNetz = 1.0;
        
    	uran = 30000.0;
    	uran_max = 30000.0;
    	uran_zero_first = true;
    	kohle = 150000;
    	kohle_max = 150000;
    	kohle_zero_first = true;
		
    	strompreis = 0.2495; // 0,2495 €/kWh -> 249,5 €/MWh
    	strompreis_min = 0.1;
    	strompreis_max = 0.5;
    	
		personal = 0;
		
		CO2 = 0;
		zufriedenheit = 1.0;
		
		akw_unfall = false;
		akw_explosion = false;
		akw_unfall_first = true;
		
		ansteigender_strombedarf = false;
		ansteigender_strombedarf_zeitpunkt = 0;
		strombedarf = 50000; //171527.777; //617,5 Mrd kWh = 617,5 Mil MWh = 171527,777 MW
		max_strombedarf = 100000.0;

		highscore_sum_zufriedenheit = 0.0;
		highscore_max_gewinn = 0.0;
		highscore_max_money = 0.0;
		
		//Forschung init
		researching = new HashMap<Research, Long>();
		finishedResearch = new HashSet<Research>();
		
		//Wind initialisieren
		windpower = 1.0;
		windrel = new double[cols][rows];
		for(int y=0; y<rows; y++){
			double relbasic = 0.7/rows*(rows-y)+0.3;
			for(int x=0; x<cols; x++){
				// rel +- random[-0.1, +0.0999~]
				double rel = relbasic + (rnd.nextDouble()-0.5)*2/10;
				rel = (rel>1.0 ? 1.0 : ( rel<0.0 ? 0.0 : rel ) ); //0.0 bis 1.0 einhalten
				windrel[x][y] = rel; 
			}
		}
		
		//Sonne initialisieren
        sonnenintensitaet = 1.0;
        sonnenrel = new double[cols][rows];
        for(int y=0; y<rows; y++){
        	double relbasic = 0.7/rows*(y+1)+0.3;
            for(int x=0; x<cols; x++){
            	// rels +- random[-0.1, +0.0999~]
				double rel = relbasic + (rnd.nextDouble()-0.5)*2/10;
				rel = (rel>1.0 ? 1.0 : ( rel<0.0 ? 0.0 : rel ) ); //0.0 bis 1.0 einhalten
                sonnenrel[x][y] = rel;
            }
        }
        
		
		// bit für bit betrachten (verundet)
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
				{	0,	1,	1,	3,	5,	7,	5,	3,	0,	0	}
		}; //x und y vertauscht! dafür aber SourceCode einfacher mit der Karte zu vergleichen.
		
		
		//Gebäude
		buildings = new Building[cols][rows];
		
		//Hauptquatier
		hq = new HQ(this);
		placeBuilding(4, 1, hq);
		
		//http://de.wikipedia.org/wiki/Liste_deutscher_Kraftwerke
		
		//Wind
		placeBuilding(7, 0, Windrad.newFinishedWindrad(this, "Baltic 2", 80));
		placeBuilding(3, 0, Windrad.newFinishedWindrad(this, "Windpark Reußenköge", 68));
		placeBuilding(2, 1, Windrad.newFinishedWindrad(this, "Windpark Holtriem", 40));
		placeBuilding(6, 3, Windrad.newFinishedWindrad(this, "Windparks Sachsen Anhalt", 60));
		placeBuilding(3, 4, Windrad.newFinishedWindrad(this, "Windpark Sintfeld", 65));
		
		//Solar
		placeBuilding(6, 4, Solar.newFinishedSolar(this, "Solarpark Köthen", 2));
		placeBuilding(7, 4, Solar.newFinishedSolar(this, "Solarpark Waldpolenz", 2));
		placeBuilding(9, 3, Solar.newFinishedSolar(this, "Solarpark Lieberose", 3));
		placeBuilding(8, 8, Solar.newFinishedSolar(this, "Solarpark Pocking", 1));
		
		//Laufwasser
		placeBuilding(4, 9, Laufwasser.newFinishedLWKW(this, "Illerkraftwerke", 5));
		placeBuilding(1, 5, Laufwasser.newFinishedLWKW(this, "an der Mosel", 7));
		
		//Kohle
		placeBuilding(0, 4, Kohle.newFinishedKohle(this, "Neurath", 5, 441.0));
		placeBuilding(0, 5, Kohle.newFinishedKohle(this, "Niederaußem", 9, 430.0));
		placeBuilding(3, 6, Kohle.newFinishedKohle(this, "Staudinger Großkrotzenburg", 5, 400.0));
		placeBuilding(8, 3, Kohle.newFinishedKohle(this, "Jänschwalde", 6, 500.0));
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
		if(moratorium) placeBuilding(5, 2, AKW.newFinishedAKW(this, "Krümmel", 1, 1402.0)); // Moratorium
		placeBuilding(1, 3, AKW.newFinishedAKW(this, "Emsland", 1, 1400.0));
		placeBuilding(3, 7, AKW.newFinishedAKW(this, "Neckarwestheim", 2, 1120.0));
		placeBuilding(4, 6, AKW.newFinishedAKW(this, "Grafenrheinfeld", 1, 1345.0));
		placeBuilding(4, 8, AKW.newFinishedAKW(this, "Gundremmingen", 2, 1344.0));
		
		
	}
	
	public void start(){
		if(getMain().getGraphic().getSettings().music)
			Music.play_music();
		running = true;
		started = true;
	}
	
	public void pause(){ running = !running; }
	public void restart(){ stop(); start(); }
	public void stop(){ Music.stop_music(); getMain().getGraphic().init(); init();  }
	
	public boolean buildBuilding(int x, int y, Building b){
		if( (getBautyp(x,y) & b.getUnderground()) != 0 ){
			if( moneySubValid(b.getBaukosten()) && placeBuilding(x,y,b) ){
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
                	money += (double)(b.getBaukosten()/4); // 1/4 der Baukosten zurück erhalten
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
					return windrel[x][y];
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
                    return sonnenrel[x][y];
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
		return strompreis*1000.0;
	}
	
	public void setStrompreis(double preis){
		strompreis = preis>strompreis_max ? strompreis_max : ( preis<strompreis_min ? strompreis_min : preis ) ;
	}
	
	public double getPersonalkosten(){
		return 2000.0/5; // €/s
	}
	
	
	public void calcZufriedenheit(){
		double zufr = 1.0;
		
		//Strompreis
		double preis = (strompreis-strompreis_min)/(strompreis_max-strompreis_min); //Strompreis normalisieren
		zufr -= preis*0.4;
		
		//CO2
		double co2 = CO2-10000.0;
		co2 = co2<0.0 ? 0.0 : co2 ; 
		co2 = co2/30000.0;
		zufr -= co2*0.2;
		
		//Forschung Sonnenoutput
		if(this.isResearchDone(RSonne.getInstance())){
			zufr -= 0.4;
		}
		
		//Atommüll
		double muell = (uran_max - uran)/50000.0;
		zufr -= 0.1 * muell;
		
		//Anzahl AKWs
		int akw_count = 0;
		for(Building[] tmp : buildings)
			for(Building b : tmp)
				if(b instanceof AKW)
					akw_count++;
		zufr -= (double)akw_count * 0.01;
		
		//Anzahl Lequidatoren
		zufr -= this.liquidatoren_count*0.01;
		
		zufr = (zufr<=0.0 ? 0.0 : ( zufr>=1.0) ? 1.0 : zufr );
		zufriedenheit = zufr;
	}
	public double getZufriedenheit(){		
		return zufriedenheit;
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
				strombedarf = 50000.0;
				max_strombedarf = 100000.0;
				if(ansteigender_strombedarf){
					if(ansteigender_strombedarf_zeitpunkt==0)
						ansteigender_strombedarf_zeitpunkt = time;
					double tmp = ((double)(time - ansteigender_strombedarf_zeitpunkt))/1000.0 ;
					strombedarf += tmp*50.0; //ansteigender Strombedarf (50MW/s)
					max_strombedarf += tmp*50.0; //ansteigender Strombedarf (50MW/s)	
				}
				strombedarf += 12500.0 * rNightEnergy * Math.sin(((time/100.0)%360.0)/180.0*Math.PI);
				strombedarf *= rNetz; //Leitungs-Wirkungsgrad durch Forschung verbessern -> geringerer Strombedarf
				strombedarf = (strombedarf >= max_strombedarf ? max_strombedarf : strombedarf ); //nicht > als max
				
				//Wetterverhältnisse ändern
				windpower = (windpower-0.5 + (rnd.nextDouble()-0.5)/10*2*tds) % 0.5 + 0.5; //Windkraft um max. 10% pro sekunde ändern			
				sonnenintensitaet = 0.5 - Math.sin(((time/100.0)%360.0)/180.0*Math.PI)/2.0; //abhängig von Zeit
				sonnenintensitaet = (sonnenintensitaet >= 1.0 ? 1.0 : ( sonnenintensitaet <= 0.0 ? 0.0 : sonnenintensitaet ) ); // zwischen 0.0 und 1.0
				
				mw = 0.0;
				mw_atom = 0.0;
				mw_wind = 0.0;
				mw_kohle = 0.0;
                mw_sonne = 0.0;
                mw_wasser = 0.0;
				personal = 0;
				CO2 = 0.0;
		        verlust_gebauedekosten = 0.0;
				akw_unfall = false;
		        
				//Gebäude
				//zufriedenheit = 0;
				for(Building[] tmp : buildings)
					for(Building b : tmp)
						if(b != null){
							//Personal zählen
							personal += b.getPersonal();
							//Gebäudekosten
							verlust_gebauedekosten += b.getMoneyCostH();
							//CO2
							CO2 += b.getCO2();
							//Stromerzeugung
							if( b instanceof AKW ) { mw_atom += b.consumeMW(); }
							else if( b instanceof Windrad )  { mw_wind += b.consumeMW(); }
							else if( b instanceof Kohle ) { mw_kohle += b.consumeMW(); }
                            else if( b instanceof Solar ) { mw_sonne += b.consumeMW(); }
                            else if( b instanceof Staudamm ) { mw_wasser += b.consumeMW(); }
                            else if( b instanceof Laufwasser ) { mw_wasser += b.consumeMW(); }
							else mw += b.consumeMW();
							
						}
				mw += mw_atom + mw_wind + mw_kohle + mw_sonne + mw_wasser;
				
				//Stromverkauf
				double verkauf_inland = 0.0;
				double verkauf_ausland = 0.0;
				double einkauf_ausland = 0.0;
				//wenn zuviel strom produziert wird verkaufe ins ausland
				if(mw>=strombedarf){
					verkauf_inland = strombedarf;
					verkauf_ausland = mw-strombedarf;
				}
				//wenn zuwenig produziert wird kaufe den rest im ausland ein
				else{
					verkauf_inland = mw;
					einkauf_ausland = strombedarf-mw;
				}
				//um Anzeigefehler zu verhindern
				if(mw>=max_strombedarf)
					max_strombedarf=mw;
				
				gewinn_inland = verkauf_inland * getStrompreis();
				gewinn_ausland = verkauf_ausland * strompreis_min*1000.0 * 0.5; //ins ausland verkaufen (weniger Gewinn als im eigenem Land)
				verlust_ausland = einkauf_ausland * strompreis_min*1000.0; //aus den ausland einkaufen (ohne an dem Strom zu verdienen)
				gewinn = gewinn_inland + gewinn_ausland - verlust_ausland - verlust_gebauedekosten;
				money += gewinn*tds;
				
				highscore_max_gewinn = (gewinn>=highscore_max_gewinn ? gewinn : highscore_max_gewinn);
				highscore_max_money = (money>=highscore_max_money ? money : highscore_max_money);
				
				//Zufriedenheit berechnen
				calcZufriedenheit();
				highscore_sum_zufriedenheit += zufriedenheit;
				
				//Update Zeit aktualisieren
				last_update_time=time;
				
				//System.out.println("HS: Z:"+highscore_sum_zufriedenheit/((double)time/1000.0)+", G:"+highscore_max_gewinn+", M:"+highscore_max_money);
			}

			//Gebäude tick
			for(Building[] tmp : buildings)
				for(Building b : tmp)
					if(b != null)
						b.tick(timeDiff);
			
			//Forschungs tick
			research_mutex.lock();
			Set<Research> remove = new HashSet<Research>(); //Objekte die entfernt werden sollen
			for(Research r : researching.keySet()){
				researching.put(r, researching.get(r)+timeDiff); //Zeit erhöhen
				if( r.isDone(this) ){
					remove.add(r);
				}
			}
			//entferne Objekte
			for(Research r : remove){
				researching.remove(r);
				finishedResearch.add(r);
				r.researchEffect(this); //Forschung ausführen
			}
			research_mutex.unlock();
			
			
			//Zufällige Ereignisse
			if(running){
				if(!ansteigender_strombedarf){
					//ab 15.000.000 Euro Gewinn steigender Strombedarf
					if(gewinn>=15000000.0 && time>=180000){
						ansteigender_strombedarf = true;
						getMain().getGraphic().setOverlay(new IncEnergyNeedProfit(this));
					}
					//oder, nach 10min
					else if (time>=600000){
						ansteigender_strombedarf = true;
						getMain().getGraphic().setOverlay(new IncEnergyNeedTime(this));
					}
				}
			
				//Erster AKW Unfall
				if(akw_unfall_first && akw_unfall){
					akw_unfall_first = false;
					getMain().getGraphic().setOverlay(new FirstAKWUnfall(this));
				}
				
				//Uranvorkommen auf 0 gefallen
				if(uran_zero_first && Double.compare(uran, 0.0)==0){
					uran_zero_first = false;
					getMain().getGraphic().setOverlay(new ZeroUran(this));
				}
			
				//Kohlevorkommen auf 0 gefallen
				if(kohle_zero_first && kohle==0){
					kohle_zero_first = false;
					getMain().getGraphic().setOverlay(new ZeroKohle(this));
				}
			
				//Game Over: zu wenig Geld
				if(money<money_min){
					getMain().getGraphic().setOverlay(new GameOverMoney(this));
				}
			
				//Game Over: AKW Unfall
				if(akw_explosion){
					getMain().getGraphic().setOverlay(new GameOverExplosion(this));
				}
			
				//Game Over: AKW Unfall
				if( Double.compare(getZufriedenheit(), 0.0) <= 0 ){
					getMain().getGraphic().setOverlay(new GameOverZufriedenheit(this));
				}
			}
		}
	}
	
	public boolean consumeUran(double ammount){
		uran-=ammount;
		if(Double.compare(uran, 0.0)<=0){
			uran=0.0;
			return false;
		}
		else
			return true;
	}
	
	public boolean consumeKohle(){
		if(kohle-1>=0){
			kohle-=1;
			return true;
		}
		return false;
	}
	
	//Forschung
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
	
	public boolean moneySubValid(double m){
		return ( this.money-m >= this.money_min );
	}
	
	public Main getMain(){return main;}
}
