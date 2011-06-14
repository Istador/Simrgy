package simrgy.game.research;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import simrgy.game.Game;
import simrgy.game.Research;
import simrgy.game.ResearchAbstract;
import simrgy.graphic.Button;
import simrgy.graphic.ButtonCenteredText;
import simrgy.graphic.GraphicObject;
import simrgy.graphic.OverlayGame;
import simrgy.graphic.menu.RunnableMain;
import static simrgy.res.RessourceManager.*;

public class RSonne extends ResearchAbstract {

	//Singleton
	private static RSonne instance = null;
	public static RSonne getInstance(){
		if(instance==null){
			instance = new RSonne("Sonnenoutput erhöhen", "Verdoppelt den Sonnenausstoß", null, 15000000000.0, 500000 );
		}
		return instance;
	}
	
	private RSonne(String name, String desc, Research parent, double kosten, long forschungsdauer) {
		super(name, desc, parent, kosten, forschungsdauer);
	}
	
	public Image getImage() {return rSonne;}
	
	public void researchEffect(Game g) {
		g.rSolarEnergy *= 2.0; //verdoppele die erzeugte Energie der Sonne
		g.rH2OEnergy *= 0.2; // wasserkraft -80%
		//TODO zufriedenheit--, 
		
		
		/* Ausgabe: 
		 *  Forschung "Sonnenoutput erhöhen" abgeschlossen!
		 *  
		 *  Ihre Wissenschaftler konnten durch die Veränderung, einiger physikalischer Konstanten
		 *  den Sonnenoutput erhöhen. Dadurch Produzieren ihre Solarpanels nun doppelt so viel Strom.
		 *  Leider hat dies auch Katastrophale Auswirkungen auf die Umwelt. Aus Wasserkraft erzeugen
		 *  Sie nur noch einen Minimum an Strom.
		 */
		
		
		/**
		 * Klassen Start
		 * Nachfragen ob abreißen
		 */
		GraphicObject go = new OverlayGame(g) {
			int top;
			int left;
			int width;
			int height;
			
			protected String caption;
			int caption_height;
			int caption_width;
			
			
			private ButtonCenteredText[] buttons;
			
			public void init(){
				caption =  "Forschung \"Sonnenoutput erhöhen\" abgeschlossen";
				
				int[] b = f_size(getBackbuffer(), f_rclick_caption, caption);
				caption_height = b[0];
				caption_width = b[1];
				
				width = caption_width+30>500 ? caption_width+30 : 500;
				height = 180;
				top = getMain().height/2-height/2;
				left = getMain().width/2-width/2;
				
				Runnable r = new RunnableMain(getMain()) {
					public void run() { 
						main.getGraphic().removeOverlay();
					}
				};
				
				buttons = new ButtonCenteredText[1];
				buttons[0] = new ButtonCenteredText(this, "OK...", c_rclick_caption, c_rclick_button_highlight, left+width/2, top+height-35, f_rclick_button, r);
			}
			
			
			public void draw() {
				Graphics g = getBackbuffer(); 
				
				//rand
				g.setColor(c_rclick_rand);
				g.fillRect(left, top, width, height);
				
				//Hintergrund
				g.setColor(c_rclick_bg);
				g.fillRect(left+10, top+10, width-20, height-20);
								
				//Caption
				g.setColor(c_rclick_caption);
				g.setFont(f_rclick_caption);
				g.drawString(caption, left+(width-caption_width)/2, top+15+caption_height);

				//Text
				g.setColor(c_rclick_text);
				g.setFont(f_rclick_text);
				g.drawString("Ihren Wissenschaftler ist es zwar gelungen durch die Veränderung", left+15, top+15+caption_height+20);
				g.drawString("physikalischer Konstanten den Sonnenoutput zu erhöhen, wodurch", left+15, top+15+caption_height+35);
				g.drawString("ihre Solarpanels doppelt so viel Strom produzieren, aber leider", left+15, top+15+caption_height+50);
				g.drawString("hat dies auch katastrophale Auswirkungen auf die Umwelt.", left+15, top+15+caption_height+65);
				g.drawString("Aus Wasserkraft erzeugen Sie nun nur noch ein Minimum an Strom.", left+15, top+15+caption_height+80);
				//Draw Buttons
				for(Button b : buttons) if(b!=null) b.draw();
			}

			public void click(int x, int y) {
				//im Fenster
				if(x>=left && x<=left+width && y>=top && y<=top+height){
					for(Button b : buttons) if(b!=null)	b.click(x, y);
				}
				else{
					getMain().getGraphic().removeOverlay();
				}
			}

			public void mouseOver(int x, int y) { for(Button b : buttons) if(b!=null) b.mouseOver(x, y); }
			public void mouseOut() { for(Button b : buttons) if(b!=null) b.mouseOut(); }
			public void keyPress(KeyEvent ke){}

		};
		/**
		 * Klassen Ende
		 */
		g.getMain().getGraphic().setOverlay(go);
	}

}
