package simrgy.game.research;

import java.awt.Image;

import simrgy.game.Game;
import simrgy.game.Research;
import simrgy.game.ResearchAbstract;
import static simrgy.res.RessourceManager.*;

public class RWind2X extends ResearchAbstract {

	//Singleton
	private static RWind2X instance = null;
	public static RWind2X getInstance(){
		if(instance==null){
			instance = new RWind2X("2 mit einem Streich", "Halbiert Bauzeit von Windrädern", RWindPlus.getInstance(), 500000000.0, 100000 );
		}
		return instance;
	}
	
	private RWind2X(String name, String desc, Research parent, double kosten, long forschungsdauer) {
		super(name, desc, parent, kosten, forschungsdauer);
	}
	
	public Image getImage() {return rWind;}
	
	public void researchEffect(Game g) {
		g.rWindBuildTime *= 2.0; //beschleunigt das bauen um +100%
	}

}
