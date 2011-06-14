package simrgy.game.research;

import java.awt.Image;

import simrgy.game.Game;
import simrgy.game.Research;
import simrgy.game.ResearchAbstract;
import static simrgy.res.RessourceManager.*;

public class RNetz extends ResearchAbstract {

	//Singleton
	private static RNetz instance = null;
	public static RNetz getInstance(){
		if(instance==null){
			instance = new RNetz("Effizientes Netz", "+5% Wirkungsgrad", null,  7500000000.0, 200000 );
		}
		return instance;
	}
	
	private RNetz(String name, String desc, Research parent, double kosten, long forschungsdauer) {
		super(name, desc, parent, kosten, forschungsdauer);
	}
	
	public Image getImage() {return rNetz;}
	
	public void researchEffect(Game g) {
		g.rNetz*=0.95;
	}

}