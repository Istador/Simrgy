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
			instance = new RKohleVorrat("Kohlevorr‰te aufstocken", "+30.000 Kohle", null, 250000000.0, 30000 );
		}
		return instance;
	}
	
	private RKohleVorrat(String name, String desc, Research parent, double kosten, long forschungsdauer) {
		super(name, desc, parent, kosten, forschungsdauer);
	}
	
	public Image getImage() {return rKohle;}
	
	public void researchEffect(Game g) {
		g.rKohleCO2 *= 0.3; //verringere den CO2-Ausstoﬂ von Kohlekraftwerken auf 30%
	}

}
