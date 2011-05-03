package simrgy.game;

import java.awt.Image;

import simrgy.graphic.GridObject;

public interface Building {
	public Image getImage();
	public String getName();
	public void attachGridObject(GridObject o);
	
	public double getMoneyPerSecond();
	public double getPowerPerSecond();
	
	public Game getGame();
}
