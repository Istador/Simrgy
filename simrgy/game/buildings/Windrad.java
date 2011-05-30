package simrgy.game.buildings;

import java.awt.Image;

import simrgy.game.*;
import simrgy.game.actions.*;
import simrgy.res.RessourceManager;

public class Windrad extends BuildingAbstract implements Building {

	public static int underground = 9; //Land benötigt
	
	static double baukosten_per_module = 3570000.0; //pro Rad
	static int personal_per_module = 1; //?
	
	protected long bauzeit_so_far = 0;
	protected static long bauzeit_per_module = 5000; //4 Wochen -> 1 Minuten / 12 -> 5 Sekunden
	
	private int modules = 1;
	private int max_modules = 100;
	protected double co2_kg = 1.0;
	protected int zufriedenheit = 8;
	
	public Windrad(Game g, String name){
		super(g, name);
		actions.add(Rename.getInstance());
		actions.add(IncModules.getInstance());
		actions.add(Deploy.getInstance());
	}
	
	public static Windrad newFinishedWindrad(Game g, String name, int module){
		Windrad ret = new Windrad(g, name);
		ret.modules=module;
		ret.bauzeit_so_far = bauzeit_per_module * ret.modules;
		return ret;
	}
	
	public Image getImage(){ return RessourceManager.windrad; }
	
	public double getMoneyCostH(){
		return getPersonal() * getGame().getPersonalkosten();
		}
	
	public double getMW(){
		//Pro Windrad: 2-6 MW, Wetterabhängig
		double pro = 2.0 + 4.0 * getGame().getWindpower(this);
		return pro * activeModules() ;
		}
	
	public double consumeMW(){ return getMW(); }
	
	public double getCo2() {return co2_kg * activeModules();}
	public int getZufriedenheit() {return zufriedenheit * activeModules();}
	
	public int getPersonal(){
		return personal_per_module * modules;
	}
		
	public long getBauzeit() { return bauzeit_per_module; }
	public double getBaukosten() { return baukosten_per_module * modules; }
	
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