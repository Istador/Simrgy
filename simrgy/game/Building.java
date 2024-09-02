package simrgy.game;

import java.awt.Image;
import java.util.List;

import simrgy.graphic.map.GridObject;

public interface Building {
	public Image getImage();
	public String getName();
	public String getBuildingMWText();
	public String getBuildingCO2Text();
	public String getModulesText();
	public String getPersonalText();
	
	public void attachGridObject(GridObject o);
	
	public double getMoneyCostH();
	public double getMW();
	public double consumeMW();
	public double getCO2();
	public int getZufriedenheit();
	
	public int getUnderground();
	
	public int getPersonal();
	public long getBauzeit();
	public double getBaukosten();
	public double getBaukostenPerModule();
	
	public double getBaustatus(); // 0.0 - 1.0
	
	public List<Action> getActions();
	public void removeAction(Action a);
	
	public void setName(String name);
	
	public void tick(long miliseconds);
	
	public Game getGame();
	
	public boolean drawModules();
	public int activeModules();
	public int modules();
	
	public boolean newModule();
	public boolean moreModulesPossible();
	
	public boolean enoughMoney();
	
	public void deploy(); //abreißen
	public boolean isDeploying();
}
