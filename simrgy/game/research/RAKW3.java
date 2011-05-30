package simrgy.game.research;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import simrgy.game.*;
import simrgy.graphic.*;
import simrgy.graphic.menu.RunnableMain;
import static simrgy.res.RessourceManager.*;

public class RAKW3 extends ResearchAbstract {

	//Singleton
	private static RAKW3 instance = null;
	public static RAKW3 getInstance(){
		if(instance==null){
			instance = new RAKW3("Endlager finden", "Erhöht die Zufriedenheit sehr", RAKW2.getInstance(), 1000000000.0, 120000 );
		}
		return instance;
	}
	
	private RAKW3(String name, String desc, Research parent, double kosten, long forschungsdauer) {
		super(name, desc, parent, kosten, forschungsdauer);
	}
	
	public Image getImage() {return rEndlager;}
	
	public void researchEffect(Game g) {
		// TODO 
		/* Ausgabe: 
		 *  Forschung "Endlager finden" abgeschlossen!
		 *  
		 *  Ihre Wissenschaftler konnten leider kein Endlager, für atomaten Abfall finden.
		 *  Die Forschung hat folglich keinen Effekt. Tut uns Leid
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
				caption =  "Forschung \"Endlager finden\" abgeschlossen";
				
				int[] b = f_size(getBackbuffer(), f_rclick_caption, caption);
				caption_height = b[0];
				caption_width = b[1];
				
				width = caption_width+30>160 ? caption_width+30 : 160;
				height = 140;
				top = getMain().height/2-height/2;
				left = getMain().width/2-width/2;
				
				Runnable r = new RunnableMain(getMain()) {
					public void run() { 
						main.getGraphic().setOverlay(null);
						main.getGame().pause();
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
				g.drawString("Ihre Wissenschaftler konnten leider kein Endlager, für", left+15, top+15+caption_height+20);
				g.drawString("atomaten Abfall finden. Die Forschung hat folglich", left+15, top+15+caption_height+35);
				g.drawString("keinen Effekt. Tut uns Leid :(", left+15, top+15+caption_height+50);
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
					getMain().getGame().pause();
				}
			}

			public void mouseOver(int x, int y) { for(Button b : buttons) if(b!=null) b.mouseOver(x, y); }
			public void mouseOut() { for(Button b : buttons) if(b!=null) b.mouseOut(); }
			public void keyPress(KeyEvent ke){}

		};
		/**
		 * Klassen Ende
		 */
		g.pause();
		g.getMain().getGraphic().setOverlay(go);
		
		
	}

}
