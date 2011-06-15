package simrgy.graphic.gui;

import simrgy.applet.*;
import simrgy.game.Building;
import simrgy.game.buildings.*;
import simrgy.graphic.Button;
import simrgy.graphic.ButtonImage;
import simrgy.graphic.GraphicObject;
import static simrgy.res.RessourceManager.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class BuildTab implements GraphicObject {
	public int top;
	public int left;
	public int width;
	public int height;
	
	private Button selected = null; //markiertes gebäude per klick
	private Button over = null; //gebäude über dem man mit der maus ist
	
	private java.util.Map<Button,Integer> buttons; 
	
	private int box; //ausmaße eines Icons
	
	private GUI gui;

	public BuildTab(GUI g) {
		gui = g;
		top   = 61;
		left  = getGUI().left+26;
		width = getMain().getWidth()-left-1;
		height = getMain().getHeight()-top-1;
		int a = (width-4*5)/3;
		int b = (height-6*5)/5;
		box = (a<=b ? a : b );
		
		buttons = new java.util.HashMap<Button,Integer>();
		//AKW
		Button but = new ButtonImage(this, akw_1, cWhite, c_build_highlight, left+5, top+5, box, box, null);
		buttons.put(but, 0);
		but = new ButtonImage(this, akw_2, cWhite, c_build_highlight, left+10+box, top+5, box, box, null);
		buttons.put(but, 1);
		but = new ButtonImage(this, akw_3, cWhite, c_build_highlight, left+15+2*box, top+5, box, box, null);
		buttons.put(but, 2);
		//Laufwasserkraftwerk
		but = new ButtonImage(this, laufwasser, cWhite, c_build_highlight, left+5, top+10+box, box, box, null);
		buttons.put(but, 7);
		//Staudamm
		but = new ButtonImage(this, staudamm, cWhite, c_build_highlight, left+10+box, top+10+box, box, box, null);
		buttons.put(but, 6);
		//Kohle
		but = new ButtonImage(this, kohle, cWhite, c_build_highlight, left+5, top+15+2*box, box, box, null);
		buttons.put(but, 3);
		//Solar
		but = new ButtonImage(this, solar, cWhite, c_build_highlight, left+5, top+20+3*box, box, box, null);
		buttons.put(but, 4);
		//Wind
		but = new ButtonImage(this, windrad, cWhite, c_build_highlight, left+5, top+25+4*box, box, box, null);
		buttons.put(but, 5);
	}

	public void draw() {
		//Hintergrund
		Graphics g = getBackbuffer();
		g.setColor(c_build_bg);
		g.fillRect(left, top, width, height);
		
		//Gebäude Buttons
		for(Button butt : buttons.keySet()){
			if(butt!=null){				
				Building b = getBuilding(buttons.get(butt));
				
				//Button zeichnen
				butt.draw();
				
				//einfärben, wenn nicht genug geld
				if(!b.enoughMoney()){
					Point p = butt.getTL();
					g.setColor(cRed30);
					g.fillRect( (int)p.getX(), (int)p.getY(), butt.getWidth(), butt.getHeight() );
				}
				
			}
		}
		
		//mouse Over Gebäudeinfos
		Button infos = ( over!=null ? over : selected ); 
		if(infos!=null){
			//Gebäude
			Building b = getBuilding(buttons.get(infos));
			//Untergrund
			g.setColor(c_build_mouseOver_bg);
			g.fillRect(left+width-2*box, top+height-box, 2*box, box);
			//Rand
			g.setColor(c_build_mouseOver_rand);
			g.drawRect(left+width-2*box, top+height-box, 2*box, box);
			//Name
			g.setColor(c_build_mouseOver_caption);
			g.setFont(f_build_caption);
			g.drawString(b.getName(), left+width+5-2*box, top+height-box+18);
			//MW
			g.setColor(c_build_mouseOver_caption);
			g.setFont(f_build_text);
			g.drawString(b.getBuildingMWText()+" MW", left+width+5-2*box, top+height-box+20+13);
			//CO2
			g.drawString(b.getBuildingCO2Text()+"kg CO2", left+width+5-2*box, top+height-box+20+28);
			//Baukosten
			if(b.enoughMoney())	g.setColor(cGreen);
			else g.setColor(cRed);
			g.setFont(f_build_text);
			g.drawString("€", left+width+5-2*box, top+height-5);
			String kosten = df_money.format(b.getBaukosten());
			
			int strwidth = f_size(g, f_build_text, kosten)[1]; 
			g.drawString(kosten, left+width-5-strwidth, top+height-5);
		}
	}

	public void click(int x, int y) {
		clearSelectedBuilding();
		for(Button b : buttons.keySet()){
			if(b!=null && b.contains(x, y)){
				selected = b;
				b.markiere(cBlack);
				b.click();
			}
		}
	}
	
	
	public void mouseOver(int x, int y) {
		Button old = over;
		over = null;
		for(Button b : buttons.keySet()){
			if(b!=null && b.contains(x, y)) over = b;
		}
		if(over!=null && over!=old) over.mouseOver();
		if(old!=null && over!=old) old.mouseOut();
	}
	
	public void mouseOut() {
		if(over!=null){
			over.mouseOut();
			over = null;
		}
	}

	public GUI getGUI(){return gui;}
	public Main getMain() { return getGUI().getMain(); }
	public Graphics getBackbuffer() { return getGUI().getBackbuffer(); }
	public void keyPress(KeyEvent ke){}
	
	public Building getSelectedBuilding(){
		if(over!=null) return getBuilding(buttons.get(over));
		if(selected!=null) return getBuilding(buttons.get(selected));
		return null;
	}
	
	public void clearSelectedBuilding(){
		if(selected!=null) selected.markiere(null);
		selected = null;
		
	}
	
	protected Building getBuilding(int i){
		switch(i) {
		case 0: return AKW.newAKW(getMain().getGame(), "AKW", 1); //AKW1 0
		case 1: return AKW.newAKW(getMain().getGame(), "AKW", 2); //AKW2 1
		case 2: return AKW.newAKW(getMain().getGame(), "AKW", 3); //AKW3 2
		case 3: return new Kohle(getMain().getGame(), "Kohlekraftwerk"); //Kohle
		case 4: return new Solar(getMain().getGame(), "Photovoltaik"); //Solar
		case 5: return new Windrad(getMain().getGame(), "Windpark"); //Wind 
		case 6: return new Staudamm(getMain().getGame(), "Staudamm"); //Staudamm
		case 7: return new Laufwasser(getMain().getGame(), "Laufkraftwerk"); //Laufwasserkraftwerk
		default: return null;
		}
	}
}
