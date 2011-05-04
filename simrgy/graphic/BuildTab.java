package simrgy.graphic;

import simrgy.applet.*;
import simrgy.game.Building;
import simrgy.game.buildings.*;
import simrgy.res.RessourceManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class BuildTab implements GraphicObject {
	public int top;
	public int left;
	public int width;
	public int height;
	private Color backgroundColor = new Color(0xBEE554); //CatEye
	
	//private boolean mouseOverBuilding = false;
	
	private Building selected = null;
	private Button selected_button = null;
	private Button over = null;
	
	private java.util.Map<Button,Integer> buttons; 
	
	private int box;
	
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
		Color hc = new Color(0, 1, 0, 0.3f);
		//AKW
		Button but = new ButtonImage(this, RessourceManager.akw_1, Color.WHITE, hc, left+5, top+5, box, box, null);
		buttons.put(but, 0);
		but = new ButtonImage(this, RessourceManager.akw_2, Color.WHITE, hc, left+10+box, top+5, box, box, null);
		buttons.put(but, 1);
		but = new ButtonImage(this, RessourceManager.akw_3, Color.WHITE, hc, left+15+2*box, top+5, box, box, null);
		buttons.put(but, 2);
		//Kohle
		but = new ButtonImage(this, RessourceManager.kohle, Color.WHITE, hc, left+5, top+10+box, box, box, null);
		buttons.put(but, 3);
		//Solar
		but = new ButtonImage(this, RessourceManager.solar, Color.WHITE, hc, left+5, top+15+2*box, box, box, null);
		buttons.put(but, 4);
		//Wind
		but = new ButtonImage(this, RessourceManager.windrad, Color.WHITE, hc, left+5, top+20+3*box, box, box, null);
		buttons.put(but, 5);
		//Staudamm
		but = new ButtonImage(this, RessourceManager.staudamm, Color.WHITE, hc, left+5, top+25+4*box, box, box, null);
		buttons.put(but, 6);
	}

	public void draw() {
		//Hintergrund
		Graphics g = getBackbuffer();
		g.setColor(backgroundColor);
		g.fillRect(left, top, width, height);
		
		for(Button b : buttons.keySet()){
			if(b!=null) b.draw();
		}
	}

	public void click(int x, int y) {
		clearSelectedBuilding();
		for(Button b : buttons.keySet()){
			if(b!=null && b.contains(x, y)){
				selected = getBuilding(buttons.get(b));
				selected_button = b;
				b.markiere(Color.BLACK);
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
		
		//highlight bauplätze
		if(over!=null)
			getMain().getGraphic().getMap().getGrid().highlightUnderground(getBuilding(buttons.get(over)).getUnderground());
		else
			getMain().getGraphic().getMap().getGrid().highlightUnderground(0);
	}
	public void mouseOut() {
		if(over!=null){
			over.mouseOut();
			getMain().getGraphic().getMap().getGrid().highlightUnderground(0);
			over = null;
			
		}
	}

	public GUI getGUI(){return gui;}
	public Main getMain() { return getGUI().getMain(); }
	public Graphics getBackbuffer() { return getGUI().getBackbuffer(); }
	public void keyPress(KeyEvent ke){}
	
	public Building getSelectedBuilding(){
		return selected;
	}
	
	public void clearSelectedBuilding(){
		selected = null;
		if(selected_button!=null) selected_button.markiere(null);
		selected_button = null;
		
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
		default: return null;
		}
	}
}
