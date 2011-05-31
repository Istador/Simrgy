package simrgy.game.research;

import java.awt.Image;

import simrgy.game.Game;
import simrgy.game.Research;
import simrgy.game.ResearchAbstract;
import static simrgy.res.RessourceManager.*;

public class RAKW2 extends ResearchAbstract {

	//Singleton
	private static RAKW2 instance = null;
	public static RAKW2 getInstance(){
		if(instance==null){
			instance = new RAKW2("Sarkophage errichten", "+10% Reaktorsicherheit", RAKW1.getInstance(), 500000000.0, 60000 );
		}
		return instance;
	}
	
	private RAKW2(String name, String desc, Research parent, double kosten, long forschungsdauer) {
		super(name, desc, parent, kosten, forschungsdauer);
	}
	
	public Image getImage() {return rSarkophag;}
	
	public void researchEffect(Game g) {
		g.rAKWSecure *= 1.10;
	}

}
