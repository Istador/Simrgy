package simrgy.graphic.gui;

import simrgy.applet.*;
import simrgy.game.*;
import simrgy.game.research.*;
import simrgy.graphic.Button;
import simrgy.graphic.ButtonImage;
import simrgy.graphic.GraphicObject;
import static simrgy.res.RessourceManager.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ResearchTab implements GraphicObject {
	public int top;
	public int left;
	public int width;
	public int height;
	
	private GUI gui;
	
	private Button over = null; //forschung über dem man mit der maus ist
	
	private java.util.Map<Button,Research> buttons; 
	
	private int box; //ausmaße eines Icons
	
	public ResearchTab(GUI g) {
		gui = g;
		top   = 61;
		left  = getGUI().left+26;
		width = getMain().getWidth()-left-1;
		height = getMain().getHeight()-top-1;
		
		int a = (width-4*5)/3;
		int b = (height-6*5)/5;
		box = (a<=b ? a : b );
		
		buttons = new java.util.HashMap<Button,Research>();
		//AKW1 Uran Vorrat
		Research r = RAKW1.getInstance();
		Button but = new ButtonImage(this, r.getImage(), cWhite, c_research_highlight, left+5, top+5, box, box, null);
		buttons.put(but, r);
		//AKW2 Sarkophag
		r = RAKW2.getInstance();
		but = new ButtonImage(this, r.getImage(), cWhite, c_research_highlight, left+10+box, top+5, box, box, null);
		buttons.put(but, r);
		//AKW3 Endlager
		r = RAKW3.getInstance();
		but = new ButtonImage(this, r.getImage(), cWhite, c_research_highlight, left+15+2*box, top+5, box, box, null);
		buttons.put(but, r);
		//Energiesparlampen
		r = REnergiesparlampen.getInstance();
		but = new ButtonImage(this, r.getImage(), cWhite, c_research_highlight, left+5, top+10+box, box, box, null);
		buttons.put(but, r);
		//Solarpanel
		r = RSolar.getInstance();
		but = new ButtonImage(this, r.getImage(), cWhite, c_research_highlight, left+10+box, top+10+box, box, box, null);
		buttons.put(but, r);
		//Sonnenoutput
		r = RSonne.getInstance();
		but = new ButtonImage(this, r.getImage(), cWhite, c_research_highlight, left+15+2*box, top+10+box, box, box, null);
		buttons.put(but, r);
		//2x Windräder
		r = R2XWind.getInstance();
		but = new ButtonImage(this, r.getImage(), cWhite, c_research_highlight, left+5, top+15+2*box, box, box, null);
		buttons.put(but, r);
		//Kohle Vorräte
		r = RKohleVorrat.getInstance();
		but = new ButtonImage(this, r.getImage(), cWhite, c_research_highlight, left+5, top+20+3*box, box, box, null);
		buttons.put(but, r);
		//Kohle CO2-Filter
		r = RKohleCO2.getInstance();
		but = new ButtonImage(this, r.getImage(), cWhite, c_research_highlight, left+10+box, top+20+3*box, box, box, null);
		buttons.put(but, r);
		
	}
	
	public void draw() {
		//hintergrund
		Graphics g = getBackbuffer();
		Game game = getMain().getGame();
		g.setColor(c_research_bg);
		g.fillRect(left, top, width, height);
		
		//Forschungs Buttons
		for(Button b : buttons.keySet()){
			if(b!=null){
				Research r = buttons.get(b);
				
				//Button zeichnen
				b.draw();
				
				//einfärben
				Color c = null;
				if(r.isDone(game)) c=cBlack30;
				else if (!r.isPossible(game) && !r.isResearching(game)) c = cRed30;
				if(c!=null){
					Point p = b.getTL();
					g.setColor(c);
					g.fillRect( (int)p.getX(), (int)p.getY(), b.getWidth(), b.getHeight() );
				}
				
				//Statusbalken
				if(r.isResearching(game)){
					double fortschritt = r.getStatus(game);
					Point pt = b.getTL();
					drawStatusBalken(g, (int)pt.x, (int)pt.y+b.getHeight()-7, b.getWidth(), 7, fortschritt);
				}
			}
		}
		
		//wenn über einem Forschungsbutton
		if(over!=null){
			Research r = buttons.get(over);
			
			//Parent von Forschung markieren
			Research p = r.getParent();
			if(p!=null){
				for(Button b : buttons.keySet()){
					Research tmp = buttons.get(b);
					if(b!=null && tmp!=null){
						if(tmp!=p) b.markiere(null);
						else if(r.isResearching(game) || r.isDone(game)) b.markiere(cBlack);
						else if(p.isDone(game)) b.markiere(cGreen); 
						else if(p.isResearching(game)) b.markiere(cOrange);
						else b.markiere(cRed);
					}
				}
			}
			
			//mouse Over Forschungsinfos
			
			//Untergrund
			g.setColor(c_research_mouseOver_bg);
			g.fillRect(left+5, top+height-box, 3*box+10, box);
			//Rand
			g.setColor(c_research_mouseOver_rand);
			g.drawRect(left+5, top+height-box, 3*box+10, box);
			//Name
			g.setColor(c_research_mouseOver_caption);
			g.setFont(f_research_caption);
			g.drawString(r.getName(), left+10, top+height-box+20);
			//Beschreibung
			g.setColor(c_research_mouseOver_text);
			g.setFont(f_research_text);
			g.drawString(r.getDesc(), left+10, top+height-box+40);
			
			if (r.isDone(game)){
				// "erforscht"
				g.setColor(cBlue);
				g.setFont(f_research_caption);
				int strwidth = f_size(g, f_research_caption, "Erforscht")[1]; 
				g.drawString("Erforscht", left+width/2-strwidth/2, top+height-5);
			}
			else if(r.isResearching(game)){
				//Statusbalken
				double fortschritt = r.getStatus(game);
				drawStatusBalken(g, left+10, top+height-20, 3*box, 15, fortschritt);
			}
			else{
				//Forschungskosten
				if(r.enoughMoney(game))	g.setColor(cGreen);
				else g.setColor(cRed);
				g.setFont(f_research_text);
				g.drawString("€", left+10, top+height-5);
				String kosten = df_money.format(r.getKosten());
				int strwidth = f_size(g, f_research_text, kosten)[1]; 
				g.drawString(kosten, left+width-10-strwidth, top+height-5);
			}
		}
		else{
			//nicht über einer Forschung
			for(Button b : buttons.keySet())
				b.markiere(null);
		}
	}

	protected void drawStatusBalken(Graphics g, int left, int top, int width, int height, double fortschritt){
		g.setColor(c_research_todo);
		g.fillRect(left, top, width, height);
		g.setColor(c_research_done);
		g.fillRect(left+1, top, (int)(width*fortschritt), height);
		g.setColor(cBlack);
		g.drawRect(left, top, width, height);
	}
	
	public void click(int x, int y) {
		for(Button b : buttons.keySet()){
			if(b!=null && b.contains(x,y)){
				//b.mouseOut();
				buttons.get(b).startResearch(getMain().getGame());
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
		if(over!=null && over!=old /*&& buttons.get(over).isPossible(getMain().getGame())*/ ){
			over.mouseOver();
		}
		if(old!=null && over!=old){
			old.mouseOut();
		}
	}
	
	public void mouseOut() {
		if(over!=null){
			over.mouseOut();
			over.markiere(null);
			over = null;
		}
		for(Button b : buttons.keySet())
			b.markiere(null);
	}

	public GUI getGUI(){return gui;}
	public Main getMain() { return getGUI().getMain(); }
	public Graphics getBackbuffer() { return getGUI().getBackbuffer(); }
	public void keyPress(KeyEvent ke){}
	
}
