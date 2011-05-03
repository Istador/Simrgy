package simrgy.game.buildings;

import java.awt.Image;

import simrgy.game.*;
import simrgy.res.RessourceManager;

public class Staudamm extends BuildingAbstract implements Building {

	public static int underground = 4; // Fluss benötigt
	
	public Staudamm(Game g, String name){
		super(g, name);
	}
	
	public double getMoneyPerSecond(){return 0.0;}
	public double getPowerPerSecond(){return 0.0;}
	
	public Image getImage(){ return RessourceManager.staudamm; }
}