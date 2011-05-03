package simrgy.game.buildings;

import java.awt.*;
import simrgy.game.*;
import simrgy.res.RessourceManager;

public class AKW extends BuildingAbstract implements Building {
	
	public static int underground = 6; //Fluss oder See benötigt benötigt
	
	int modules = 1;
	int personal = 100;
	double leistung = 0.0;
	
	protected AKW(Game g, String name){
		super(g, name);
	}
	
	public static AKW newAKW(Game g, String name, int module){
		AKW ret = new AKW(g, name);
		ret.modules=module;
		return ret;
	}
	
	public Image getImage() {
		if(modules==1) return RessourceManager.akw_1;
		if(modules==2) return RessourceManager.akw_2;
		if(modules==3) return RessourceManager.akw_3;
		return super.getImage();
	}

	public double getMoneyPerSecond(){ return getPowerPerSecond() * getGame().getStrompreis() - personal * getGame().getPersonalkosten() ;}
	public double getPowerPerSecond(){ return modules * 1600.0 * leistung; }
	
}
