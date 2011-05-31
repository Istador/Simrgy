package simrgy.game.buildings;

import java.awt.Image;

import simrgy.game.*;
import simrgy.game.actions.*;
import simrgy.res.RessourceManager;

//Photovoltaikanlage
public class Solar extends BuildingAbstract implements Building {

    
    static int personal_per_module = 10; //?
    protected double co2_kg = 4.0;
    protected int zufriedenheit = 9;
    
    public Solar(Game g, String name){
        super(g, name);
        
		underground = 1; //Land benötigt
		max_modules = 5;
		bauzeit_per_module = 12000; //2 Jahre
		baukosten_per_module = 130000000.0; //pro Rad
        
        actions.add(Rename.getInstance());
        actions.add(IncModules.getInstance());
        actions.add(Deploy.getInstance());
    }
    
	public static Solar newFinishedSolar(Game g, String name, int module){
		Solar ret = new Solar(g, name);
		ret.modules=module;
		ret.bauzeit_so_far = ret.bauzeit_per_module * ret.modules;
		return ret;
	}
    
    public double getMoneyCostH()
    {
        return getPersonal() * getGame().getPersonalkosten();
    }
    
    public double getMW()
    {
        //Pro Solarkraftwerk: 10-40 MW, Wetterabhängig
        double pro = 10.0 + 30.0 * getGame().getSolarPower(this);
        return pro * activeModules() * getGame().rSolarEnergy;
    }
    public String getBuildingMWText(){return "10-40";}
    public double getCO2() {return co2_kg * activeModules();}
    public int getZufriedenheit() {return zufriedenheit * activeModules();}
    
	public double consumeMW(){ return getMW(); }
    
    public Image getImage(){ return RessourceManager.solar; }

    public int getPersonal(){ return personal_per_module * modules; }
    
    /*
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
    
    public double getBaustatus() 
    { 
        return (double)bauzeit_so_far / ( (double)modules * (double)bauzeit_per_module ); 
    }
    
    public int getUnderground(){return underground;}
    */
}