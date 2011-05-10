package simrgy.game;

import java.awt.Image;
import java.util.List;

import simrgy.graphic.GridObject;

public interface Building {
	public Image getImage();
	public String getName();
	public void attachGridObject(GridObject o);
	
	public double getMoneyCostH();
	public double getMW();
	public double getCo2();
	public int getZufriedenheit();
	
	public int getUnderground();
	
	public int getPersonal();
	public long getBauzeit();
	public double getBaukosten();
	
	public double getBaustatus(); // 0.0 - 1.0
	
	public List<Action> getActions();
	
	public void setName(String name);
	
	public void tick(long miliseconds);
	
	public Game getGame();
}
