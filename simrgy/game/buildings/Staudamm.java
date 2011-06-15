package simrgy.game.buildings;

import java.awt.Image;

import simrgy.game.*;
import simrgy.game.actions.*;
import simrgy.res.RessourceManager;

public class Staudamm extends BuildingAbstract implements Building {
    
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
    
    public double getMW(){return mw_module * activeModules() * getGame().rH2OEnergy;}
    public String getBuildingMWText(){return String.valueOf((int)mw_module*modules);}
    public String getBuildingCO2Text(){return String.valueOf((int)co2_kg * modules);}
    public double getCO2() {return co2_kg * activeModules();}
    public int getZufriedenheit() {return zufriedenheit * activeModules();}
    
    public double consumeMW(){ return getMW(); }
    
    public Image getImage(){ return RessourceManager.staudamm; }
    
    public int getPersonal() { return personal_per_module * modules; }
    
    
    public boolean drawModules(){return false;}
}