package simrgy.game.buildings;

import java.awt.Image;

import simrgy.game.*;
import simrgy.game.actions.*;
import simrgy.res.RessourceManager;

public class Kohle extends BuildingAbstract implements Building {

	private static int personal_per_module = 10; //?
	
	protected double mw_module = 600 ;
	protected double co2_kg = 1000.0;
	protected int zufriedenheit = 3;
	
	public Kohle(Game g, String name){
		super(g, name);
		
		underground = 1; //Land benötigt
		max_modules = 10;
		bauzeit_per_module = 24000;
		baukosten_per_module = 478800000.0; //478,8 Mio per Module
		
		actions.add(ARename.getInstance());
		actions.add(AIncModules.getInstance());
		actions.add(ADeploy.getInstance());
		actions.add(ACancel.getInstance());
	}

	public static Kohle newFinishedKohle(Game g, String name, int module, double mw){
		Kohle ret = new Kohle(g, name);
		ret.modules=module;
		ret.bauzeit_so_far = ret.bauzeit_per_module * ret.modules;
		ret.mw_module=mw;
		return ret;
	}
	public double getMW(){return mw_module * (double)activeModules();}
	public String getBuildingMWText(){return String.valueOf((int)mw_module*modules);}
	public String getBuildingCO2Text(){return String.valueOf((int)co2_kg * modules);}
	public double getCO2() {return co2_kg * (double)activeModules() * getGame().rKohleCO2;}
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
	
}