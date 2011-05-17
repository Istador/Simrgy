package simrgy.game;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import simrgy.graphic.map.GridObject;
import simrgy.res.RessourceManager;

public abstract class BuildingAbstract implements Building {

	public static int underground = 0;
	
	protected Game game;
	
	protected String name;
	protected GridObject gridObject;
	
	protected List<Action> actions;
	
	protected boolean building = true; //bauen oder abreiﬂen

	protected BuildingAbstract(Game g, String n){
		game = g;
		name = n;
		actions = new ArrayList<Action>();
	}

	public Image getImage() {return RessourceManager.none;}
	public String getName() {return name;}
	public void setName(String n) {name=n;}
	public void attachGridObject(GridObject o) {gridObject=o;}

	public List<Action> getActions(){
		return new ArrayList<Action>(actions);
	}
	
	public void removeAction(Action a){
		actions.remove(a);
	}

	public Game getGame(){return game;}
	
	public boolean enoughMoney(){
		return game.money >= getBaukosten();
	}
	
	public void deploy(){
		building = false;
	}
	public boolean isDeploying(){
		return !building;
	}
}
