package simrgy.game.actions;


import static simrgy.res.RessourceManager.*;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import simrgy.game.Action;
import simrgy.game.Building;
import simrgy.graphic.Button;
import simrgy.graphic.ButtonCenteredText;
import simrgy.graphic.GraphicObject;
import simrgy.graphic.OverlayBuilding;

public class Deploy implements Action {

	//Singleton
	private Deploy(){}
	protected static Action instance = null;
	public static Action getInstance(){
		if(instance==null) instance = new Deploy();
		return instance;
	}
	
	public void run(Building b) {
		if( !isPossible(b) ) return;
		
		/**
		 * Klassen Start
		 * Nachfragen ob abreißen
		 */
		GraphicObject go = new OverlayBuilding(b) {
			int top;
			int left;
			int width;
			int height;
			
			protected String caption;
			int caption_height;
			int caption_width;
			
			
			private ButtonCenteredText[] buttons;
			
			public void init(){
				caption =  building.getName() + " abreißen?";
				
				int[] b = f_size(getBackbuffer(), f_rclick_caption, caption);
				caption_height = b[0];
				caption_width = b[1];
				
				width = caption_width+30>160 ? caption_width+30 : 160;
				height = 140;
				top = getMain().height/2-height/2;
				left = getMain().width/2-width/2;
				
				buttons = new ButtonCenteredText[2];
				buttons[0] = new ButtonCenteredText(this, "Ja", c_rclick_caption, c_rclick_button_highlight, left+width/2, top+25+caption_height+20, f_rclick_button, null);
				buttons[1] = new ButtonCenteredText(this, "Nein", c_rclick_caption, c_rclick_button_highlight, left+width/2, top+25+caption_height+60, f_rclick_button, null);
			}
			
			
			public void draw() {
				Graphics g = getBackbuffer(); 
				g.setColor(c_rclick_rand);
				g.fillRect(left, top, width, height);
				g.setColor(c_rclick_bg);
				g.fillRect(left+10, top+10, width-20, height-20);
				g.setColor(c_rclick_caption);
				g.setFont(f_rclick_caption);
				g.drawString(caption, left+(width-caption_width)/2, top+10+5+caption_height);

				//Draw Buttons
				for(Button b : buttons) if(b!=null) b.draw();
			}

			public void click(int x, int y) {
				//im Fenster
				if(x>=left && x<=left+width && y>=top && y<=top+height){
					for(ButtonCenteredText a : buttons){
						if(a.contains(x, y)){
							if(a.getText().equals("Ja")) building.deploy();
							getMain().getGraphic().removeOverlay();
							getMain().getGame().pause();
						}

					}
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
		b.getGame().getMain().getGraphic().setOverlay(go);
	}

	
	public String getName(){
		return "abreißen";
	}
	
	public boolean isPossible(Building b){
		if(!b.getActions().contains(instance)) return false;
		if(b.isDeploying()) return false;
		return true;
	}
	
}
