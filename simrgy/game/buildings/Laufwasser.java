package simrgy.game.buildings;

import java.awt.Image;

import simrgy.game.*;
import simrgy.game.actions.*;
import simrgy.res.RessourceManager;

public class Laufwasser extends BuildingAbstract implements Building {
    
    protected double mw_module = 20;
    private static int personal_per_module = 2; //?
    protected double co2_kg = 1.0;
    protected int zufriedenheit = 7;
   
    
    public Laufwasser(Game g, String name){
        super(g, name);
        
		underground = 4; // Fluss benötigt
		max_modules = 15;
		bauzeit_per_module = 12000; //2 Jahre Bauzeit -> 12s
		baukosten_per_module = 40000000.0; //40,0 Mio per Module
        
        actions.add(ARename.getInstance());
        actions.add(AIncModules.getInstance());
        actions.add(ADeploy.getInstance());
        actions.add(ACancel.getInstance());
    }
    
	public static Laufwasser newFinishedLWKW(Game g, String name, int module){
		Laufwasser ret = new Laufwasser(g, name);
		ret.modules=module;
		ret.bauzeit_so_far = ret.bauzeit_per_module * ret.modules;
		return ret;
	}
    
    public double getMW(){return mw_module * activeModules() * getGame().rH2OEnergy;}
    public String getBuildingMWText(){return String.valueOf((int)mw_module*modules);}
    public double getCO2() {return co2_kg * activeModules();}
    public int getZufriedenheit() {return zufriedenheit * activeModules();}
    
    public double consumeMW(){ return getMW(); }
    
    public Image getImage(){ return RessourceManager.laufwasser; }
    
    public int getPersonal() { return personal_per_module * modules; }
}