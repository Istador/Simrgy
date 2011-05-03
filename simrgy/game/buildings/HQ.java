package simrgy.game.buildings;

import java.awt.Image;
import simrgy.game.*;
import simrgy.res.RessourceManager;

public class HQ extends BuildingAbstract implements Building {

	public static int underground = 1; //Land benötigt
	
	public HQ(Game g){
		super(g, "Wattenfail");
	}

	public double getMoneyPerSecond(){return 0.0;}
	public double getPowerPerSecond(){return 0.0;}
	
	public Image getImage(){ return RessourceManager.hq; }
}
