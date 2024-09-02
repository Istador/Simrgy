package simrgy.game.research;

import java.awt.Image;

import simrgy.game.Game;
import simrgy.game.Research;
import simrgy.game.ResearchAbstract;
import static simrgy.res.RessourceManager.*;

public class RWindPlus extends ResearchAbstract {

	//Singleton
	private static RWindPlus instance = null;
	public static RWindPlus getInstance(){
		if(instance==null){
			instance = new RWindPlus("Mehr Windräder", "doppelte Modulanzahl pro Feld", null,  500000000.0, 90000 );
		}
		return instance;
	}
	
	private RWindPlus(String name, String desc, Research parent, double kosten, long forschungsdauer) {
		super(name, desc, parent, kosten, forschungsdauer);
	}
	
	public Image getImage() {return rPlusWind;}
	
	public void researchEffect(Game g) {
		//effekt über game.isResearchDone(this)
	}

}