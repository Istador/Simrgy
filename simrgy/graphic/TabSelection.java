package simrgy.graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

import simrgy.applet.Main;

public class TabSelection implements GraphicObject {
	public int top;
	public int left;
	public int width;
	public int height;
	
	private double tabwidth;
	private int round = 20;

	private Color backgroundColor = new Color(0xE3A869); //melon
	private Color highlightColor = new Color(0xFF8C00); //darkorange(SVG)
	
	private Font tabFont = new Font("Helvetica", Font.PLAIN, 18);
	
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
		g.setFont(tabFont);
		drawTab(g, "Forsche", width, ( getSelectedTab()==getResearchTab() ? highlightColor : backgroundColor) );
		drawTab(g, "Baue", (int)(tabwidth*2), ( getSelectedTab()==getBuildTab() ? highlightColor : backgroundColor) );
		drawTab(g, "Stats",(int)(tabwidth), ( getSelectedTab()==getStatusTab() ? highlightColor : backgroundColor) );
	}
	
	public void click(int x, int y) {
		//einfache Tab-Auswahl (ohne round edge)
		if(x<left+width && x>left+tabwidth*2) selectedTab=getResearchTab();
		else if(x<left+width && x>left+tabwidth) selectedTab=getBuildTab();
		else if(x<left+width && x>left) selectedTab=getStatusTab();
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
	
	protected void drawTab(Graphics g, String name, int width, Color background){
		//Füllung
		g.setColor(background);
		g.fillRoundRect(left-round, 30, width+round-1, 100, round, round);
		//schwarzer Rand
		g.setColor(Color.BLACK);
		g.drawRoundRect(left-round, 30, width+round-1, 100, round, round);
		//Text
		Rectangle2D bounds = new TextLayout(name, tabFont, ((Graphics2D)g).getFontRenderContext()).getBounds();
		g.drawString(name, left+width-((int)bounds.getWidth())-round/2, 55);
	}
}
