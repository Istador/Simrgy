package simrgy.game;

import java.awt.Image;

import static simrgy.res.RessourceManager.*;

public abstract class ResearchAbstract implements Research{
	protected final String name; //Name
	protected final String desc; //Beschreibung
	protected final double kosten;
	protected final long forschungsdauer; //in ms
	
	protected Research parent = null;
	
	protected ResearchAbstract(String name, String desc, Research parent, double kosten, long forschungsdauer ){
		this.name = name;
		this.desc = desc;
		this.parent = parent;
		this.kosten = kosten;
		this.forschungsdauer = forschungsdauer<=0 ? 1 : forschungsdauer; //darf nicht 0 sein, da sonst durch 0 geteilt wird.
	}
	
	public Image getImage() {return none;}

	public String getName(){return name;}
	public String getDesc(){return desc;}
	
	public Research getParent(){return parent;}
	
	public double getKosten(){return kosten;}
	public boolean enoughMoney(Game g){
		return g.moneySubValid(getKosten());
	}
	
	public long getForschungszeit(){
		return forschungsdauer;
	}
	
	public boolean isPossible(Game g){
		return enoughMoney(g) && (parent==null || parent.isDone(g)) && !isResearching(g) && !isDone(g);
	}
	public boolean startResearch(Game g){
		if (isPossible(g) && g.addResearch(this)) {
			g.money-=kosten;
			return true; 
		}
		return false;
	}
	public boolean isResearching(Game g){
		return g.isResearching(this) && !isDone(g);
	}
	public boolean isDone(Game g){
		return g.isResearchDone(this) || Double.compare(getStatus(g), 1.0) >= 0;
	}
	public double getStatus(Game g){
		if(g.isResearchDone(this)) return 1.0;
		double status = (double) ( (double)g.getResearchTime(this) / (double)forschungsdauer);
		status = ( status>=1.0 ? 1.0 : ( status <= 0.0 ? 0.0 : status ) );
		return status;
	}
	
	//public abstract void researchEffect(Game g);
	
}
