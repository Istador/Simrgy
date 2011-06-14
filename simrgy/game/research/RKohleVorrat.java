package simrgy.game.research;

import java.awt.Image;

import simrgy.game.Game;
import simrgy.game.Research;
import simrgy.game.ResearchAbstract;
import static simrgy.res.RessourceManager.*;

public class RKohleVorrat extends ResearchAbstract {

	//Singleton
	private static RKohleVorrat instance = null;
	public static RKohleVorrat getInstance(){
		if(instance==null){
			instance = new RKohleVorrat("Kohlevorräte aufstocken", "+50.000 Kohle", null, 400000000.0, 30000 );
		}
		return instance;
	}
	
	private RKohleVorrat(String name, String desc, Research parent, double kosten, long forschungsdauer) {
		super(name, desc, parent, kosten, forschungsdauer);
	}
	
	public Image getImage() {return rKohle;}
	
	public void researchEffect(Game g) {
		g.kohle += 50000;
		g.kohle_max += 50000;
	}

}
