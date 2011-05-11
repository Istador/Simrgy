package simrgy.graphic;

import simrgy.applet.*;
import simrgy.game.Building;
import simrgy.game.buildings.*;
import simrgy.res.RessourceManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.Locale;

public class BuildTab implements GraphicObject {
	public int top;
	public int left;
	public int width;
	public int height;
	private Color backgroundColor = new Color(0xBEE554); //CatEye
	private Font font = new Font("Helvetica", Font.PLAIN, 14);
	private Font caption_font = new Font("Helvetica", Font.PLAIN, 18);
	
	//private boolean mouseOverBuilding = false;
	
	private Button selected = null;
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
		//Staudamm
		but = new ButtonImage(this, RessourceManager.staudamm, Color.WHITE, hc, left+5, top+15+2*box, box, box, null);
		buttons.put(but, 6);
		//Wind
		but = new ButtonImage(this, RessourceManager.windrad, Color.WHITE, hc, left+5, top+20+3*box, box, box, null);
		buttons.put(but, 5);
		//Solar
		but = new ButtonImage(this, RessourceManager.solar, Color.WHITE, hc, left+5, top+25+4*box, box, box, null);
		buttons.put(but, 4);
	}

	public void draw() {
		//Hintergrund
		Graphics g = getBackbuffer();
		g.setColor(backgroundColor);
		g.fillRect(left, top, width, height);
		
		//Gebäude Buttons
		for(Button b : buttons.keySet()){
			if(b!=null) b.draw();
		}
		
		//mouse Over Gebäudeinfos
		Button infos = ( over!=null ? over : selected ); 
		if(infos!=null){
			//Gebäude
			Building b = getBuilding(buttons.get(infos));
			//Untergrund
			g.setColor(Color.YELLOW);
			g.fillRect(left+width-2*box, top+height-box, 2*box, box);
			//Rand
			g.setColor(Color.BLACK);
			g.drawRect(left+width-2*box, top+height-box, 2*box, box);
			//Name
			g.setColor(Color.BLACK);
			g.setFont(caption_font);
			g.drawString(b.getName(), left+width+5-2*box, top+height-box+20);
			//Baukosten
			g.setColor(Color.BLACK);
			g.setFont(font);
			DecimalFormat ein = (DecimalFormat) DecimalFormat.getInstance(Locale.GERMAN);
			g.drawString("€", left+width+5-2*box, top+height-5);
			String kosten = ein.format(b.getBaukosten());
			Rectangle2D bounds = new TextLayout(kosten, font, ((Graphics2D)g).getFontRenderContext()).getBounds();
			int strwidth = (int) Math.ceil(bounds.getWidth()); 
			g.drawString(kosten, left+width-5-strwidth, top+height-5);
		}
	}

	public void click(int x, int y) {
		clearSelectedBuilding();
		for(Button b : buttons.keySet()){
			if(b!=null && b.contains(x, y)){
				selected = b;
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
		default: return null;
		}
	}
}
