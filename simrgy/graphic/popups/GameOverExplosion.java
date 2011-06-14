package simrgy.graphic.popups;

import static simrgy.res.RessourceManager.c_rclick_bg;
import static simrgy.res.RessourceManager.c_rclick_button_highlight;
import static simrgy.res.RessourceManager.c_rclick_caption;
import static simrgy.res.RessourceManager.c_rclick_rand;
import static simrgy.res.RessourceManager.c_rclick_text;
import static simrgy.res.RessourceManager.f_rclick_button;
import static simrgy.res.RessourceManager.f_rclick_caption;
import static simrgy.res.RessourceManager.f_rclick_text;
import static simrgy.res.RessourceManager.f_size;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import simrgy.game.Game;
import simrgy.graphic.Button;
import simrgy.graphic.ButtonCenteredText;
import simrgy.graphic.OverlayGame;
import simrgy.graphic.menu.RunnableMain;

public class GameOverExplosion extends OverlayGame {
	
	public GameOverExplosion(Game g) {
		super(g);
	}
	
	int top;
	int left;
	int width;
	int height;
	
	protected String caption;
	int caption_height;
	int caption_width;
		
		
	private ButtonCenteredText[] buttons;
		
	public void init(){
		caption =  "Game Over";
		
		int[] b = f_size(getBackbuffer(), f_rclick_caption, caption);
		caption_height = b[0];
		caption_width = b[1];
		
		width = caption_width+30>400 ? caption_width+30 : 400;
		height = 140;
		top = getMain().height/2-height/2;
		left = getMain().width/2-width/2;
		
		Runnable r = new RunnableMain(getMain()) {
			public void run() { 
				main.getGame().stop();
				main.getGraphic().removeOverlay();
				main.getGraphic().showMenu();
			}
		};
		
		buttons = new ButtonCenteredText[1];
		buttons[0] = new ButtonCenteredText(this, "Zum Hauptmenü", c_rclick_caption, c_rclick_button_highlight, left+width/2, top+height-35, f_rclick_button, r);
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
		g.drawString("Huch, da ist dir wohl ein Atomkraftwerk in die Luft geflogen.", left+15, top+15+caption_height+20);
		g.drawString("Kann ja mal passieren... benutze besser schnell deine", left+15, top+15+caption_height+35);
		g.drawString("Zeitmaschine, bevor die Bevölkerung dich lyncht.", left+15, top+15+caption_height+50);
		//Draw Buttons
		for(Button b : buttons) if(b!=null) b.draw();
	}

	public void click(int x, int y) {
		//im Fenster
		if(x>=left && x<=left+width && y>=top && y<=top+height){
			for(Button b : buttons) if(b!=null)	b.click(x, y);
		}
	}

	public void mouseOver(int x, int y) { for(Button b : buttons) if(b!=null) b.mouseOver(x, y); }
	public void mouseOut() { for(Button b : buttons) if(b!=null) b.mouseOut(); }
	public void keyPress(KeyEvent ke){}
}
