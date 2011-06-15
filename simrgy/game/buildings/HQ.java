package simrgy.game.buildings;

import java.awt.Image;
import simrgy.game.*;
import simrgy.game.actions.*;
import simrgy.res.RessourceManager;

public class HQ extends BuildingAbstract implements Building {
	
	public HQ(Game g){
		super(g, "Wattenfail");
		
		bauzeit_so_far = 1;
		
		underground = 1;
		max_modules = 1;
		bauzeit_per_module = 1;
		baukosten_per_module = 0.0;
		
		actions.add(ASettings.getInstance());
		actions.add(ASurrender.getInstance());
		actions.add(ACancel.getInstance());
	}

	public double getMW(){return 0.0;}
	public String getBuildingMWText(){return "0";}
	public String getBuildingCO2Text(){return "0";}
	public double getCO2() {return 0.0;}
	public int getZufriedenheit() {return 0;}
	
	public Image getImage(){ return RessourceManager.hq; }

	public int getPersonal() { return 100; }
	public long getBauzeit() { return 0; }
	public double getBaukosten() { return 0; }
	public void tick(long miliseconds) {}
	
	public double getBaustatus() { return 1.0; }

	public boolean newModule() {return false;}
	public boolean moreModulesPossible() {return false;}

	public double consumeMW() {return 0.0;}
	
	public boolean drawModules(){return false;}
}
