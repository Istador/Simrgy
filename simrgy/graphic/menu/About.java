package simrgy.graphic.menu;

import simrgy.applet.*;
import simrgy.graphic.Button;
import simrgy.graphic.ButtonCenteredText;
import simrgy.graphic.ButtonHTTPLabel;
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
	
	private int boxheight;
	private int boxwidth;
	
	public About(Graphic g){
		graphic = g;
		
		top=getMain().top;
		left=getMain().left;
		width=getMain().width;
		height=getMain().height;
		boxheight=180;
		boxwidth=width/2-left-25-5;
		
		Runnable r1 = new RunnableMain(getMain()) {
			public void run() { 
				main.getGraphic().showMenu();
			}
		};
		
		buttons = new Button[7];
		buttons[0] = new ButtonCenteredText(this, "Zurück", c_menu_button_text, c_menu_button_highlight, width/2, height-45, f_menu_button, r1);
		buttons[1] = new ButtonHTTPLabel(this, "https://blackpinguin.de/", "https://blackpinguin.de/", 0, 0, f_menu_smaltext);
		
		buttons[2] = new ButtonHTTPLabel(this, "Celestial Aeon Project - Swashing the buck - creative commons by-nc-sa 3.0", "http://www.jamendo.com/track/585845", 0, 0, f_menu_smaltext);
		buttons[3] = new ButtonHTTPLabel(this, "Dj Fab - The heaven is not so far - creative commons by-nc-sa 2.0 fr", "http://www.jamendo.com/track/358852", 0, 0, f_menu_smaltext);
		buttons[4] = new ButtonHTTPLabel(this, "stefano mocini - rebirth - creative commons by-sa 3.0", "http://www.jamendo.com/track/770629", 0, 0, f_menu_smaltext);
		buttons[5] = new ButtonHTTPLabel(this, "Xera - Ñubes - creative commons by-nc-nd 3.0", "http://www.jamendo.com/track/38494", 0, 0, f_menu_smaltext);
		buttons[6] = new ButtonHTTPLabel(this, "Adult Only - Shoreless - creative commons by-nc-sa 3.0", "http://www.jamendo.com/track/608009", 0, 0, f_menu_smaltext);
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
		int strtop = 30+strheight;
		int strleft = width/2-strwidth/2+left;
		g.drawString("Credits", strleft, strtop);
		
		g.setFont(f_menu_bigtext);
		g.setColor(c_menu_text);
		f = f_size(g, f_menu_bigtext, "Ein Spiel von:");
		strheight = f[0];
		strtop += 20+strheight;
		strwidth = f[1]; 
		g.drawString("Ein Spiel von:", width/2-strwidth/2+left, strtop);
		
		strtop += 10;
		//linke box
		drawLeftBox(g, strtop, left+25, boxwidth, boxheight, strheight);
		//rechte box
		drawRightBox(g, strtop, width/2+5, boxwidth, boxheight, strheight);
		strtop += 180;
		
		g.setFont(f_menu_bigtext);
		g.setColor(c_menu_text);
		f = f_size(g, f_menu_bigtext, "Musik:");
		strheight = f[0];
		strtop += 10+strheight;
		strwidth = f[1]; 
		g.drawString("Musik:", width/2-strwidth/2+left, strtop);
		
		strtop += 5 ;
		((ButtonHTTPLabel)buttons[2]).setTL(strtop, left+30);
		
		strtop += 5 + buttons[2].getHeight();
		((ButtonHTTPLabel)buttons[3]).setTL(strtop, left+30);
		
		strtop += 5 + buttons[3].getHeight();
		((ButtonHTTPLabel)buttons[4]).setTL(strtop, left+30);
		
		strtop += 5 + buttons[4].getHeight();
		((ButtonHTTPLabel)buttons[5]).setTL(strtop, left+30);
		
		strtop += 5 + buttons[5].getHeight();
		((ButtonHTTPLabel)buttons[6]).setTL(strtop, left+30);
		
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
		//TODO Text?
		((ButtonHTTPLabel)buttons[1]).setTL(top+height-10-buttons[1].getHeight(), left+10+imgwidth);
	}
	
	public void drawRightBox(Graphics g, int top, int left, int width, int height, int strheight){
		g.setColor(c_menu_text);
		g.drawRect(left, top, width, height);
		int imgwidth = (int)( 338.0/(405.0/((double)height-10.0)) );
		g.drawImage(none, left+5, top+5, imgwidth, height-10, null); //TODO Bild
		g.setFont(f_menu_bigtext);
		g.drawString("Sebastian Möllmann", left+10+imgwidth, top+5+strheight);
		//TODO Text?
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
