package simrgy.game;

import java.awt.Image;

public interface Research {
	
	public Image getImage();
	
	public String getName();
	public String getDesc();
	
	public Research getParent();
	
	public double getKosten();
	public boolean enoughMoney(Game g);
	
	public long getForschungszeit();
	
	public void researchEffect(Game g); //mache was mit dem Spiel 
	
	public boolean isPossible(Game g); //checkt vorraussetzung und Geld. false wenn abgeschlossen
	public boolean startResearch(Game g); //nur wenn possible
	public boolean isResearching(Game g);
	public boolean isDone(Game g); //status==1.0
	public double getStatus(Game g); // 0.0 .. 1.0
	
	
}
