package simrgy.game.buildings;

import java.awt.Image;
import simrgy.game.*;
import simrgy.res.RessourceManager;

public class HQ extends BuildingAbstract implements Building {

	public static int underground = 1; //Land benötigt
	
	public HQ(Game g){
		super(g, "Wattenfail");
	}

	public double getMoneyCostH(){return 0.0;}
	public double getMW(){return 0.0;}
	public double getCo2() {return 0.0;}
	public int getZufriedenheit() {return 0;}
	
	public Image getImage(){ return RessourceManager.hq; }

	public int getPersonal() { return 100; }
	public long getBauzeit() { return 0; }
	public double getBaukosten() { return 0; }
	public void tick(long miliseconds) {}
	
	public double getBaustatus() { return 1.0; }
	public int getUnderground(){return underground;}

	public boolean newModule() {return false;}
	public boolean moreModulesPossible() {return false;}
}
