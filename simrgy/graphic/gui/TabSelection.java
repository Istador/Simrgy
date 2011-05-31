package simrgy.graphic.gui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import static simrgy.res.RessourceManager.*;

import simrgy.applet.Main;
import simrgy.graphic.GraphicObject;

public class TabSelection implements GraphicObject {
	public int top;
	public int left;
	public int width;
	public int height;
	
	private double tabwidth;
	private int round = 20;
	
	private GUI gui;
	
	GraphicObject selectedTab;
	
	public TabSelection(GUI g) 
	{
		gui = g;
		top   = 30;
		left  = getGUI().left;
		width = getGUI().width;
		height = 30;
		tabwidth = width/3;
		selectedTab = getGUI().getStatusTab();
	}	

	public void draw() {
		Graphics g = getBackbuffer();
		g.setFont(f_tabselect_tabfont);
		drawTab(g, "Forsche", width, ( getSelectedTab()==getResearchTab() ? c_tabselect_highlight : c_tabselect_bg) );
		drawTab(g, "Baue", (int)(tabwidth*2), ( getSelectedTab()==getBuildTab() ? c_tabselect_highlight : c_tabselect_bg) );
		drawTab(g, "Stats",(int)(tabwidth), ( getSelectedTab()==getStatusTab() ? c_tabselect_highlight : c_tabselect_bg) );
	}
	
	public void click(int x, int y) {
		GraphicObject tmp = selectedTab;
		//einfache Tab-Auswahl (ohne round edge)
		getMain().play(sClick);
		if(x<left+width && x>left+tabwidth*2) selectedTab=getResearchTab();
		else if(x<left+width && x>left+tabwidth) selectedTab=getBuildTab();
		else if(x<left+width && x>left) selectedTab=getStatusTab();
		if(tmp!=null && tmp != selectedTab) tmp.mouseOut(); //Bugfix, das tmp weiterhin denkt er sei mouseOver, wenn schneller wechsel.
	}
	public void mouseOver(int x, int y) {}
	public void mouseOut(){}
	
	public GUI getGUI(){return gui;}
	public Main getMain() { return getGUI().getMain(); }
	public Graphics getBackbuffer() { return getGUI().getBackbuffer(); }
	
	public GraphicObject getSelectedTab(){ return selectedTab; }
	public StatusTab getStatusTab(){ return getGUI().getStatusTab(); }
	public BuildTab getBuildTab(){ return getGUI().getBuildTab(); }
	public ResearchTab getResearchTab(){ return getGUI().getResearchTab(); }
	
	protected void drawTab(Graphics g, String name, int width, java.awt.Color background){
		//Füllung
		g.setColor(background);
		g.fillRoundRect(left-round, 30, width+round-1, 100, round, round);
		//schwarzer Rand
		g.setColor(cBlack);
		g.drawRoundRect(left-round, 30, width+round-1, 100, round, round);
		//Text
		g.drawString(name, left+width-(f_size(g, f_tabselect_tabfont, name)[1])-round/2, 55);
	}
	
	public void keyPress(KeyEvent ke){}
}
