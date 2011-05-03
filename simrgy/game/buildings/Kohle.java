package simrgy.game.buildings;

import java.awt.Image;

import simrgy.game.*;
import simrgy.game.actions.Rename;
import simrgy.res.RessourceManager;

public class Kohle extends BuildingAbstract implements Building {

	public static int underground = 1; //Land benötigt
	private int modules = 1;
	private int max_modules = 5;
	private static int personal_per_module = 10; //?
	
	protected long bauzeit_so_far = 0;
	protected static long bauzeit_per_module = 24000;
	
	protected double mw_module = 600 ;
	
	public Kohle(Game g, String name){
		super(g, name);
		actions.add(Rename.getInstance());
	}

	public double getMoneyCostH(){return getPersonal() * getGame().getPersonalkosten();}
	public double getMW(){return mw_module * activeModules();}
	
	public Image getImage(){ return RessourceManager.kohle; }

	public int getPersonal() { return personal_per_module * modules; }
	public long getBauzeit() { return bauzeit_per_module; } // 4 Jahre -> 0,4 Minuten -> 24 sekunden
	public double getBaukosten() { return 478800000; } //478,8 Mio per Module
	
	protected int activeModules(){
		return (int) (bauzeit_so_far / bauzeit_per_module) ;
	}
	
	public boolean newModule(){
		if(modules+1>max_modules) return false;
		getGame().money-=getBaukosten();
		modules++;
		return true;
	}
	
	public void tick(long miliseconds){
		//bau im gange
		long max_bauzeit = modules * bauzeit_per_module; 
		if( max_bauzeit > bauzeit_so_far ){
			bauzeit_so_far += miliseconds;
			bauzeit_so_far = (bauzeit_so_far > max_bauzeit ? max_bauzeit : bauzeit_so_far) ; 
		}	
	}
	
	public double getBaustatus() {
		return (double)bauzeit_so_far / ( (double)modules * (double)bauzeit_per_module );
	}
	
	public int getUnderground(){return underground;}
	
}