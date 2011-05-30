package simrgy.game.buildings;

import java.awt.Image;

import simrgy.game.*;
import simrgy.game.actions.*;
import simrgy.res.RessourceManager;

public class Kohle extends BuildingAbstract implements Building {

	public static int underground = 1; //Land benötigt
	private int modules = 1;
	private int max_modules = 10;
	private static int personal_per_module = 10; //?
	
	protected double baukosten_per_module = 478800000.0; //478,8 Mio per Module
	
	protected long bauzeit_so_far = 0;
	protected static long bauzeit_per_module = 24000;
	
	protected double mw_module = 600 ;
	protected double co2_kg = 1000.0;
	protected int zufriedenheit = 3;
	
	public Kohle(Game g, String name){
		super(g, name);
		actions.add(Rename.getInstance());
		actions.add(IncModules.getInstance());
		actions.add(Deploy.getInstance());
	}

	public static Kohle newFinishedKohle(Game g, String name, int module, double mw){
		Kohle ret = new Kohle(g, name);
		ret.modules=module;
		ret.bauzeit_so_far = bauzeit_per_module * ret.modules;
		ret.mw_module=mw;
		return ret;
	}
	public double getMoneyCostH(){return getPersonal() * getGame().getPersonalkosten();}
	public double getMW(){return mw_module * activeModules();}
	public double getCo2() {return co2_kg * activeModules();}
	public int getZufriedenheit() {return zufriedenheit * activeModules();}
	
	//MW produzieren - und Kohle verbrauchen
	public double consumeMW(){
		double mw = 0.0;
		for(int i=0; i<activeModules(); i++){
			if(game.consumeKohle()) mw += mw_module; 
		}
		return mw;
	}
	
	public Image getImage(){ return RessourceManager.kohle; }

	public int getPersonal() { return personal_per_module * modules; }
	public long getBauzeit() { return bauzeit_per_module; } // 4 Jahre -> 0,4 Minuten -> 24 sekunden
	public double getBaukosten() { return baukosten_per_module * modules; } //478,8 Mio per Module
	
	protected int activeModules(){
		return (int) (bauzeit_so_far / bauzeit_per_module) ;
	}
	
	public boolean newModule(){
		if(!moreModulesPossible()) return false;
		getGame().money-=baukosten_per_module;
		modules++;
		return true;
	}
	
	public boolean moreModulesPossible(){
		if(baukosten_per_module>=game.money) return false;
		return modules+1<=max_modules;
	}
	
	public void tick(long miliseconds){
		//bau im gange
		long max_bauzeit = modules * bauzeit_per_module; 
		if( max_bauzeit > bauzeit_so_far ){
			bauzeit_so_far += miliseconds;
			bauzeit_so_far = (bauzeit_so_far > max_bauzeit ? max_bauzeit : bauzeit_so_far) ; 
		}	
		else if(!building){
			bauzeit_so_far -= miliseconds*2;
			bauzeit_so_far = (bauzeit_so_far < 0 ? 0 : bauzeit_so_far) ;
			if(bauzeit_so_far == 0) game.removeBuilding(this);
		}
	}
	
	public double getBaustatus() {
		return (double)bauzeit_so_far / ( (double)modules * (double)bauzeit_per_module );
	}
	
	public int getUnderground(){return underground;}
	
}