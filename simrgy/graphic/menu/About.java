package simrgy.graphic.menu;

import simrgy.applet.*;
import simrgy.graphic.Button;
import simrgy.graphic.ButtonCenteredText;
import simrgy.graphic.Graphic;
import simrgy.graphic.GraphicObject;
import static simrgy.res.RessourceManager.*;

import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class About implements GraphicObject {

	protected Graphic graphic; //parent
		
	public int top;
	public int left;
	public int width;
	public int height;
	private Button[] buttons;
	
	public About(Graphic g){
		graphic = g;
		
		top=getMain().top;
		left=getMain().left;
		width=getMain().width;
		height=getMain().height;
		
		Runnable r1 = new RunnableMain(getMain()) {
			public void run() { 
				main.getGraphic().showMenu();
			}
		};
		
		buttons = new Button[1];
		buttons[0] = new ButtonCenteredText(this, "Zurück zum Menü", c_menu_button_text, c_menu_button_highlight, width/2, height/60*55, f_menu_button, r1);
	}
	
	
	//GraphicObject Methods
	public void draw(){
		Graphics g = getBackbuffer();
		g.setColor(c_menu_bg);
		g.fillRect(left+20, top+20, width-40, height-40);
	
		g.setFont(f_menu_caption);
		g.setColor(c_menu_caption);
		int[] f = f_size(g, f_menu_caption, "Credits");
		int strheight = f[0];
		int strwidth = f[1]; 
		int strtop = 40+strheight;
		int strleft = width/2-strwidth/2+left;
		g.drawString("Credits", strleft, strtop);
		
		g.setFont(f_menu_bigtext);
		g.setColor(c_menu_text);
		f = f_size(g, f_menu_bigtext, "Ein Spiel von:");
		strheight = f[0];
		strtop += 20+strheight;
		strwidth = f[1]; 
		g.drawString("Ein Spiel von:", width/2-strwidth/2+left, strtop);
		
		strtop += 20;
		//linke box
		drawLeftBox(g, strtop, left+40, width/2-left-40-5, 200, strheight);
		//rechte box
		drawRightBox(g, strtop, width/2+5, width/2-left-40-5, 200, strheight);
		strtop += 200;
		
		g.setFont(f_menu_bigtext);
		g.setColor(c_menu_text);
		f = f_size(g, f_menu_bigtext, "Musik:");
		strheight = f[0];
		strtop += 20+strheight;
		strwidth = f[1]; 
		g.drawString("Musik:", width/2-strwidth/2+left, strtop);
		
		g.setFont(f_menu_smaltext);
		String str = "Celestial Aeon Project - Swashing the buck - creative commons by-nc-sa 3.0";
		strtop += 10 + f_size(g, f_menu_smaltext, str)[0];
		g.drawString(str, left+30, strtop);

		str = "Dj Fab - The heaven is not so far - creative commons by-nc-sa 2.0 fr";
		strtop += 10 + f_size(g, f_menu_smaltext, str)[0];
		g.drawString(str, left+30, strtop);
		
		str = "stefano mocini - rebirth - creative commons by-sa 3.0";
		strtop += 10 + f_size(g, f_menu_smaltext, str)[0];
		g.drawString(str, left+30, strtop);
		
		str = "Xera - Ñubes - creative commons by-nc-nd 3.0";
		strtop += 10 + f_size(g, f_menu_smaltext, str)[0];
		g.drawString(str, left+30, strtop);
		
		str = "Adult Only - Shoreless - creative commons by-nc-sa 3.0";
		strtop += 10 + f_size(g, f_menu_smaltext, str)[0];
		g.drawString(str, left+30, strtop);
		
		//Buttons zeichnen
		for(Button b : buttons) if(b!=null) b.draw();
	}

	public void drawLeftBox(Graphics g, int top, int left, int width, int height, int strheight){
		g.setColor(c_menu_text);
		g.drawRect(left, top, width, height);
		int imgwidth = (int)( 338.0/(405.0/((double)height-10.0)) );
		g.drawImage(rcl, left+5, top+5, imgwidth, height-10, null);
		g.setFont(f_menu_bigtext);
		g.drawString("Robin C. Ladiges", left+10+imgwidth, top+5+strheight);
		g.setFont(f_menu_smaltext);
		g.drawString("https://blackpinguin.de/", left+10+imgwidth, top+height-5);
	}
	
	public void drawRightBox(Graphics g, int top, int left, int width, int height, int strheight){
		g.setColor(c_menu_text);
		g.drawRect(left, top, width, height);
		int imgwidth = (int)( 338.0/(405.0/((double)height-10.0)) );
		g.drawImage(none, left+5, top+5, imgwidth, height-10, null);
		g.setFont(f_menu_bigtext);
		g.drawString("Sebastian Möllmann", left+10+imgwidth, top+5+strheight);
	}
	
	public void click(int x, int y) { for(Button b : buttons) if(b!=null) b.click(x,y); }
	
	public void mouseOver(int x, int y) {
		for(Button b : buttons) if(b!=null) b.mouseOver(x, y);
	}
	public void mouseOut() {
		for(Button b : buttons) if(b!=null) b.mouseOut();
	}

	public Graphic getGraphic(){return graphic;}
	public Main getMain(){return getGraphic().getMain();}
	public Graphics getBackbuffer(){return getGraphic().getBackbuffer();}
	public void keyPress(KeyEvent ke){}
	
}
