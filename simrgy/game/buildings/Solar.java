package simrgy.game.buildings;

import java.awt.Image;

import simrgy.game.*;
import simrgy.game.actions.*;
import simrgy.game.research.RSolarPlus;
import simrgy.res.RessourceManager;

//Photovoltaikanlage
public class Solar extends BuildingAbstract implements Building {

    
    static int personal_per_module = 10; //?
    protected double co2_kg = 4.0;
    protected int zufriedenheit = 9;
    
    public Solar(Game g, String name){
        super(g, name);
        
		underground = 1; //Land ben�tigt
		max_modules = 6;
		bauzeit_per_module = 12000; //2 Jahre -> 12 Sekunden
		baukosten_per_module = 130000000.0; //pro Rad
        
        actions.add(ARename.getInstance());
        actions.add(AIncModules.getInstance());
        actions.add(ADeploy.getInstance());
        actions.add(ACancel.getInstance());
    }
    
	public static Solar newFinishedSolar(Game g, String name, int module){
		Solar ret = new Solar(g, name);
		ret.modules=module;
		ret.bauzeit_so_far = ret.bauzeit_per_module * ret.modules;
		return ret;
	}
        
    public double getMW(){
        //Pro Solarkraftwerk: 10-40 MW, Wetterabh�ngig
        double pro = 10.0 + 30.0 * getGame().getSolarPower(this) * getGame().sonnenintensitaet;
        return pro * activeModules() * getGame().rSolarEnergy;
    }
    public String getBuildingMWText(){return "10-40";}
    public String getBuildingCO2Text(){return String.valueOf((int)co2_kg * modules);}
    public double getCO2() {return co2_kg * activeModules();}
    public int getZufriedenheit() {return zufriedenheit * activeModules();}
    
	public double consumeMW(){ return getMW(); }
    
    public Image getImage(){ return RessourceManager.solar; }

    public int getPersonal(){ return personal_per_module * modules; }
    
    
    //�berschreiben, wegen Forschung
	public boolean moreModulesPossible(){
		if(!game.moneySubValid(baukosten_per_module)) return false;
		return modules+1<=max_modules*(game.isResearchDone(RSolarPlus.getInstance())?2:1);
	}
	
	//�berschreiben, wegen Forschung
	public String getModulesText(){
		String ret = "";
		if(modules>activeModules())
			ret += activeModules()+"+"+(modules-activeModules());
		else
			ret += activeModules();

		ret += "/"+max_modules*(game.isResearchDone(RSolarPlus.getInstance())?2:1);
		return ret;
	}
}