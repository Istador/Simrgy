package simrgy.game;

import java.awt.Image;

import simrgy.graphic.GridObject;
import simrgy.res.RessourceManager;

public abstract class BuildingAbstract implements Building {

	public static int underground = 0;
	
	protected Game game;
	
	protected String name;
	protected GridObject gridObject;

	protected BuildingAbstract(Game g, String n){
		game = g;
		name = n;
	}

	public Image getImage() {return RessourceManager.none;}
	public String getName() {return name;}
	public void attachGridObject(GridObject o) {gridObject=o;}

	public Game getGame(){return game;}
}
