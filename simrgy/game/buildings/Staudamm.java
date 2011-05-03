package simrgy.game.buildings;

import java.awt.Image;

import simrgy.game.*;
import simrgy.game.actions.Rename;
import simrgy.res.RessourceManager;

public class Staudamm extends BuildingAbstract implements Building {

	public static int underground = 4; // Fluss ben�tigt
	
	public Staudamm(Game g, String name){
		super(g, name);
		actions.add(Rename.getInstance());
	}
	
	public double getMoneyCostH(){return 0.0;}
	public double getMW(){return 0.0;}
	
	public Image getImage(){ return RessourceManager.staudamm; }
	
	public int getPersonal() { return 0; }
	public long getBauzeit() { return 0; }
	public double getBaukosten() { return 0; }
	public void tick(long miliseconds) {}
	
	public double getBaustatus() { return 1.0; }
	
	public int getUnderground(){return underground;}
}