package simrgy.game.buildings;

import java.awt.*;

import simrgy.game.*;
import simrgy.game.actions.*;
import static simrgy.res.RessourceManager.*;

public class AKW extends BuildingAbstract implements Building {
		
	protected int personal = 100;
	protected static int max_personal_per_module = 100; //?
	protected static int min_personal_per_module = 10; //?
	protected double leistung = 1.0;
	protected double mw_module = 1600.0;
	protected double co2_kg = 3.0;
	protected int zufriedenheit = 1; 
	
	protected AKW(Game g, String name){
		super(g, name);
		
		underground = 6; //Fluss oder See benötigt benötigt
		max_modules = 3;
		bauzeit_per_module = 90000; //10-20 Jahre Bauzeit -> 1-2 Minuten -> 1:30 -> 90s
		baukosten_per_module = 5000000000.0; // 5 Mrd.
		
		actions.add(Rename.getInstance());
		actions.add(IncModules.getInstance());
		actions.add(Deploy.getInstance());
		setPersonal(personal);
	}
	
	public static AKW newAKW(Game g, String name, int module){
		AKW ret = new AKW(g, name);
		ret.modules=module;
		if(!ret.moreModulesPossible()) ret.removeAction(IncModules.getInstance());
		return ret;
	}
	
	public static AKW newFinishedAKW(Game g, String name, int module, double mw){
		AKW ret = new AKW(g, name);
		ret.modules=module;
		ret.bauzeit_so_far = ret.bauzeit_per_module * ret.modules;
		ret.mw_module=mw;
		return ret;
	}
	
	public Image getImage() {
		int m = activeModules();
		if(m==0) return akw_0;
		if(m==1) return akw_1;
		if(m==2) return akw_2;
		if(m==3) return akw_3;
		return super.getImage();
	}

	public double getMoneyCostH(){ return personal * getGame().getPersonalkosten() ;}
	public double getMW(){ return activeModules() * mw_module * leistung; }
	public String getBuildingMWText(){return String.valueOf((int)mw_module*modules);}
	public double getCO2() {return co2_kg * activeModules();}
	public int getZufriedenheit() {return zufriedenheit * activeModules();}
	
	//MW produzieren - und Uran verbrauchen
	public double consumeMW(){
		double mw = 0.0;
		for(int i=0; i<activeModules(); i++){
			if(game.consumeUran()) mw += mw_module * leistung; 
		}
		return mw;
	}
	
	public void setLeistung(double leistung){
		this.leistung = ( leistung>=1.0 ? 1.0 : ( leistung<=0.0 ? 0.0 : leistung ) );
	}
	
	public double getLeistung(){
		return leistung;
	}
	
	public void setPersonal(int personal){
		int min = min_personal_per_module * modules;
		int max = min_personal_per_module * modules;
		this.personal = ( personal>=max ? max : ( personal <= min ? min : personal ) );
	}
	
	public int getPersonal(){ return personal; }
	
	
	//überschreiben für Personaloptionen
	public boolean newModule(){
		boolean ret = super.newModule();
		if(ret)	setPersonal(personal); //Personalanpassung, damit min_personal
		return ret;
	}
	
	//überschreiben, da module grafisch angezeigt werden.
	public boolean drawModules(){return false;}
	
	
	//überschreiben für Atomaren-Unfall
	public boolean unfall = false;
	private long unfall_time = 0;
	private long tick_time = 0;
	public void tick(long miliseconds){
		super.tick(miliseconds);
		if(unfall){
			unfall_time+=miliseconds;
			if(unfall_time >= 30000){ //30 Sekunden Zeit zu reagieren
				game.stop();
			}
		}
		else if(building && activeModules()>=1){
			tick_time+=miliseconds;
			long n = tick_time/10000; //alle 10 Sekunden
			tick_time = tick_time%10000; //Rest
			for(int i=0; i<n; i++){
				for(int j=0; j<activeModules(); j++){
					//kann hochgehen
					int a = game.rnd.nextInt()%(int)((190.0+(double)personal/(double)modules)*game.rAKWSecure); // 0.5% alle 10 Sekunden pro Reaktor, Personal oder Forschung verringern die Wahrscheinlichkeit
					if(a==0){
						//unfall
						unfall = true;
						getGame().getMain().play(sAKWUnfall);	
					}
				}
			}
		}
	}
}
