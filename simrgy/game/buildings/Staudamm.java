package simrgy.game.buildings;

import java.awt.Image;

import simrgy.game.*;
import simrgy.game.actions.*;
import simrgy.res.RessourceManager;

public class Staudamm extends BuildingAbstract implements Building {
    
    protected int personal = 5;
    protected double mw_module = 1060;
    private static int personal_per_module = 10; //?
    protected double co2_kg = 5.0;
    protected int zufriedenheit = 7;
   
    
    public Staudamm(Game g, String name){
        super(g, name);
        
		underground = 4; // Fluss benötigt
		max_modules = 1;
		bauzeit_per_module = 60000; //10Jahre Bauzeit -> 1 Minute -> 1:0 -> 60s
		baukosten_per_module = 600000000.0; //600,0 Mio per Module
        
        actions.add(ARename.getInstance());
        actions.add(AIncModules.getInstance());
        actions.add(ADeploy.getInstance());
        actions.add(ACancel.getInstance());
    }
    
    public double getMoneyCostH(){return getPersonal() * getGame().getPersonalkosten();}
    public double getMW(){return mw_module * activeModules();}
    public String getBuildingMWText(){return String.valueOf((int)mw_module*modules);}
    public double getCO2() {return co2_kg * activeModules();}
    public int getZufriedenheit() {return zufriedenheit * activeModules();}
    
    public double consumeMW(){ return getMW(); }
    
    public Image getImage(){ return RessourceManager.staudamm; }
    
    public int getPersonal() { return personal_per_module * modules; }
    public long getBauzeit() { return bauzeit_per_module; }
    public double getBaukosten() { return baukosten_per_module * modules; }
    
    
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
        if( building && max_bauzeit > bauzeit_so_far ){
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
    
	public boolean drawModules(){return false;}
}