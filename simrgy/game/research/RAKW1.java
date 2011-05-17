package simrgy.game.research;

import java.awt.Image;

import simrgy.game.Game;
import simrgy.game.Research;
import simrgy.game.ResearchAbstract;
import static simrgy.res.RessourceManager.*;

public class RAKW1 extends ResearchAbstract {

	//Singleton
	private static RAKW1 instance = null;
	public static RAKW1 getInstance(){
		if(instance==null){
			instance = new RAKW1("Uranvorkommen suchen", "+10.000 Uran", null, 250000000.0, 30000 );
		}
		return instance;
	}
	
	private RAKW1(String name, String desc, Research parent, double kosten, long forschungsdauer) {
		super(name, desc, parent, kosten, forschungsdauer);
	}
	
	public Image getImage() {return none;}
	
	public void researchEffect(Game g) {
		g.uran += 10000;
		g.uran_max += 10000;
	}

}
