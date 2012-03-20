package simrgy.graphic.menu;

import simrgy.applet.*;
import simrgy.graphic.Button;
import simrgy.graphic.ButtonCenteredText;
import simrgy.graphic.ButtonHTTPImage;
import simrgy.graphic.Checkbox;
import simrgy.graphic.Graphic;
import simrgy.graphic.GraphicObject;
import simrgy.graphic.RechtsklickTab;
import static simrgy.res.RessourceManager.*;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Settings implements GraphicObject {
	
	protected Graphic graphic; //parent

	public int top;
	public int left;
	public int width;
	public int height;
	private Button[] buttons;
	
	public boolean speed = false; //schneller?
	public boolean drawgrid = false;
	public boolean music = true;	
	
	public Settings(Graphic g){
		graphic = g;
		
		top=getMain().top;
		left=getMain().left;
		width=getMain().width;
		height=getMain().height;
		
		//Zurück zum Menü klick
		Runnable r1 = new RunnableMain(getMain()) {
			public void run() { 
				main.getGraphic().getSettings().apply();
				if(getMain().getGame().started)
					RechtsklickTab.getInstance().run(getMain().getGame().hq);
				else
					main.getGraphic().removeOverlay();
				//main.getGraphic().showMenu();
			}
		};
		
		buttons = new Button[5];
		buttons[0] = new ButtonCenteredText(this, "Zurück", c_menu_button_text, c_menu_button_highlight, width/2, height-45, f_menu_button, r1);
		buttons[1] = new Checkbox(this, drawgrid, null, c_menu_button_highlight, 50, 150, 50, 50);
		buttons[2] = new Checkbox(this, music, null, c_menu_button_highlight, 50, 250, 50, 50);
		buttons[3] = new Checkbox(this, speed, null, c_menu_button_highlight, 50, 350, 50, 50);
		buttons[4] = new ButtonHTTPImage(this, mims, "http://mims.projects.emercs.com/", width-30-194, height-30-96, 194, 96);
	}
		
	//GraphicObject Methods
	public void draw(){
		Graphics g = getBackbuffer();
		g.setColor(c_menu_rand);
		g.fillRect(left, top, width, height);
		g.setColor(c_menu_bg);
		g.fillRect(left+20, top+20, width-40, height-40);
	
		g.setFont(f_menu_caption);
		g.setColor(c_menu_caption);
		int[] f = f_size(g, f_menu_caption, "Einstellungen");
		int strheight = f[0];
		int strwidth = f[1]; 
		int strtop = 20+strheight;
		int strleft = width/2-strwidth/2+left;
		g.drawString("Einstellungen", strleft, strtop);
		
		g.setFont(f_menu_bigtext);
		g.drawString("Grid zeichnen", 125, 175);
		g.drawString("Musik an", 125, 275);
		g.drawString("Schnellere Spielgeschwindigkeit", 125, 375);
		
		for(Button b : buttons) if(b!=null) b.draw();
	}

	public void click(int x, int y) { for(Button b : buttons) if(b!=null) b.click(x,y); }

	public void mouseOver(int x, int y) {
		for(Button b : buttons) if(b!=null) b.mouseOver(x, y);
	}
	public void mouseOut() {
		for(Button b : buttons) if(b!=null) b.mouseOut();
	}

	//apply settings
	public void apply(){
		drawgrid = ((Checkbox)this.buttons[1]).isActive();
		music = ((Checkbox)this.buttons[2]).isActive();
		speed = ((Checkbox)this.buttons[3]).isActive();
		if(getMain().getGame().started){
			if(!music) Music.stop_music();
			else Music.play_music();
		}
		
	}
	
	public Graphic getGraphic(){return graphic;}
	public Main getMain(){return getGraphic().getMain();}
	public Graphics getBackbuffer(){return getGraphic().getBackbuffer();}
	public void keyPress(KeyEvent ke){
		if(getMain().getGame().started){
			RechtsklickTab.getInstance().run(getMain().getGame().hq);
			((Checkbox)this.buttons[1]).setActive(drawgrid);
			((Checkbox)this.buttons[2]).setActive(music);
			((Checkbox)this.buttons[3]).setActive(speed);
		}
	}
	
}
