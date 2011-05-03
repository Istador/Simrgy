package simrgy.game.buildings;

import java.awt.Image;

import simrgy.game.*;
import simrgy.res.RessourceManager;

public class Solar extends BuildingAbstract implements Building {

	public static int underground = 1; //Land benötigt
	
	public Solar(Game g, String name){
		super(g, name);
	}
	
	public double getMoneyPerSecond(){return 0.0;}
	public double getPowerPerSecond(){return 0.0;}
	
	public Image getImage(){ return RessourceManager.solar; }
}