package simrgy.graphic;

import simrgy.applet.*;

import java.awt.*;
import java.awt.font.*;
import java.text.DecimalFormat;

public class GUI implements GraphicObject {
	
	private Graphic graphic; //parent
	
	private TabSelection tabSelection;
	private StatusTab statusTab;
	private BuildTab buildTab;
	private ResearchTab researchTab;
	
	private Color backgroundColor = new Color(0xE3A869); //melon
	
	private Font dollarFont = new Font("Helvetica", Font.PLAIN, 30);
	
	public int top;
	public int left;
	public int width;
	public int height;

	public GUI(Graphic g){
		graphic = g;
		top   = getMain().top;
		left  = getGraphic().getMap().getWidth();
		width = getMain().getWidth()-left;
		height = getMain().getHeight();
		statusTab = new StatusTab(this);
		buildTab = new BuildTab(this);
		researchTab = new ResearchTab(this);
		tabSelection = new TabSelection(this);
	}
	

	
	protected void drawEnergy(Graphics g, int target, int actual){ //target & actual in %
		int round = 30;
		int tmpwidth = 25;
		boolean both = true;
		Color c1, c2 = null;
		int top1, top2 = 0;
		//weiße Hintergrundfarbe
		g.setColor(Color.WHITE);
		g.fillRect(left+1, 61, tmpwidth-1, height-62); 
		//wie was wo wann warum zeichnen
		int ztop = top+60+((height-61)-(height-61)*target/100);
		int atop = top+60+((height-61)-(height-61)*actual/100);
        if(target > actual){ //zu wenig Strom
        	top1=ztop;	c1 = Color.RED;
        	top2=atop;	c2 = Color.GREEN;
        }
        else if( target < actual ){ //zu viel Strom
        	top1=atop;	c1 = Color.YELLOW;
        	top2=ztop;	c2 = Color.GREEN;
        }
        else{ //ausreichend Strom
        	both=false;
        	top1=atop;	c1 = Color.GREEN;      	
        }
        //Balken zeichnen
        g.setColor(c1);
        g.fillRoundRect(left, top1, tmpwidth, height-top1-1+round, round, round);
        g.setColor(Color.BLACK);
        g.drawRoundRect(left, top1, tmpwidth, height-top1-1+round, round, round);
        if(both){
        	g.setColor(c2);
        	g.fillRoundRect(left, top2, tmpwidth, height-top2-1+round, round, round);
        	g.setColor(Color.BLACK);
            g.drawRoundRect(left, top2, tmpwidth, height-top2-1+round, round, round);
        }
        //Umrandung
		g.setColor(Color.BLACK);
        g.drawRect(left, 60, tmpwidth, height-60);
	}
	
	//GraphicObject Methods
	public void draw(){
		Graphics g = getBackbuffer();
		
		//Hintergrund
		g.setColor(Color.RED.darker());
		g.fillRect(left, top, width, height);
		
		//Geld
		DecimalFormat df = new DecimalFormat("#");
		String money = df.format(getMain().getGame().money); 
		g.setFont(dollarFont);
		int strwidth = (int) new TextLayout(money, dollarFont, ((Graphics2D)g).getFontRenderContext()).getBounds().getWidth();
		g.setColor(Color.BLACK);
		g.drawString("€", left+5, 25);
		g.drawString(money, left+width-10-strwidth, 25);
		
		//TabSelection
		getTabSelection().draw();
		//fehlende Tab Linie links
		g.setColor(Color.BLACK);
		g.drawLine(left, 30, left, 60);
		
		//Tab Füllung
		g.setColor(backgroundColor);
		g.fillRect(left, 60, width-1, height-61);
		//draw selected Tab
		getSelectedTab().draw();
		
		//Ernergiebalken
		drawEnergy(g, 80, 90);
		
		//Rand
		g.setColor(Color.BLACK);
		g.drawRect(left, 60, width-1, height-61);
	}

	public void click(int x, int y) {
		if( y>=0 && y<=60) getTabSelection().click(x, y);
		else if( y>60 && y<=height && x>=left+25 ) getSelectedTab().click(x, y);
	}
	
	public void mouseOver(int x, int y) {
		if( y>=0 && y<=60) { getTabSelection().mouseOver(x, y); getSelectedTab().mouseOut(); }
		else if( y>60 && y<=height && x>=left+25 ) { getSelectedTab().mouseOver(x, y); getTabSelection().mouseOut(); }
		else getSelectedTab().mouseOut(); getTabSelection().mouseOut();
	}
	public void mouseOut() {
		getTabSelection().mouseOut();
		getSelectedTab().mouseOut();
	}
	
	public GraphicObject getSelectedTab() { return getTabSelection().getSelectedTab(); }
	public TabSelection getTabSelection() { return tabSelection; }
	public StatusTab getStatusTab() { return statusTab; }
	public BuildTab getBuildTab() { return buildTab; }
	public ResearchTab getResearchTab() { return researchTab; }
	
	public Graphic getGraphic(){return graphic;}
	public Main getMain(){return getGraphic().getMain();}
	public Graphics getBackbuffer(){return getGraphic().getBackbuffer();}

}
