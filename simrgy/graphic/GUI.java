package simrgy.graphic;

import java.awt.*;
import java.awt.font.TextLayout;
import java.net.*;
import javax.swing.*;

import simrgy.applet.Main;

public class GUI implements GraphicObject {
	
	public int top;
	public int left;
	public int width;
	public int height;
	
	private int selectedTab = 0;
	
	//Singleton
	private static GUI instance = null;
	private GUI(){
		top   = 0;
		left  = Map.getInstance().getWidth();
		width = 800-left;
		height = 600;
	}	
	public static GUI getInstance(){
		if(instance == null)
			instance = new GUI();
		return instance;
	}
	
	protected void drawTab(Graphics g, String name, int width, Color background){
		int round=20;
		//Füllung
		g.setColor(background);
		g.fillRoundRect(left-round, 30, width+round-1, 100, round, round);
		//schwarzer Rand
		g.setColor(Color.BLACK);
		g.drawRoundRect(left-round, 30, width+round-1, 100, round, round);
		//Text
		g.drawString(name, left+width-75, 55);
	}
	
	//GraphicObject Methods
	public void draw(Graphics g){
		
		
		//Hintergrund
		g.setColor(Color.RED);
		g.fillRect(left, top, width, height);
		
		//Geld
		String dollar = "123456.78"; 
		g.setFont(new Font("Helvetica", Font.PLAIN, 30));
		int strwidth = (int) new TextLayout(dollar, g.getFont(), ((Graphics2D)g).getFontRenderContext()).getBounds().getWidth();
		g.setColor(Color.BLACK);
		g.drawString("$", left+5, 25);
		g.drawString(dollar, left+width-10-strwidth, 25);
		
		//Tabs
		g.setFont(new Font("Helvetica", Font.PLAIN, 18));
		drawTab(g, "Forsche", width, ( selectedTab==2 ? Color.GREEN : Color.YELLOW) );
		drawTab(g, "Baue", (int)(width*0.67), ( selectedTab==1 ? Color.GREEN : Color.YELLOW) );
		drawTab(g, "Stats", (int)(width*0.34), ( selectedTab==0 ? Color.GREEN : Color.YELLOW) );
		
		//Tab Linie links
		g.setColor(Color.BLACK);
		g.drawLine(left, 30, left, 60);
		
		//Gelbe füllung
		g.setColor(Color.YELLOW);
		g.fillRect(left, 60, width-1, height-61);
		//Rand
		g.setColor(Color.BLACK);
		g.drawRect(left, 60, width-1, height-61);
		
		//TODO: draw selected Tab		
	}

	public void click(int x, int y) {
		System.out.println("GUI clicked at: ( "+String.valueOf(x)+", "+String.valueOf(y)+" )");
		if( y>=0 && y<=60){ //einfache Tab-Auswahl (ohne round edge)
			if(x>716) selectedTab=2;
			else if(x>635) selectedTab=1;
			else if(x>width) selectedTab=0;
		}
	}
	
	public void mouseOver(int x, int y) {}
	public void mouseOut() {}
}
