package simrgy.graphic.menu;

import simrgy.applet.*;
import simrgy.graphic.Button;
import simrgy.graphic.ButtonCenteredText;
import simrgy.graphic.Graphic;
import simrgy.graphic.GraphicObject;
import simrgy.res.RessourceManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.font.*;
import java.awt.geom.*;


public class About implements GraphicObject {

	protected Graphic graphic; //parent
	

	private Font caption = new Font("Helvetica", Font.PLAIN, 48);
	private Font big = new Font("Helvetica", Font.PLAIN, 20);
	private Font smal = new Font("Helvetica", Font.PLAIN, 16);
	
	public int top;
	public int left;
	public int width;
	public int height;
	private Button[] buttons;
	private Button over = null;
	
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
		
		Font f = new Font("Helvetica", Font.PLAIN, 30);
		buttons = new Button[1];
		buttons[0] = new ButtonCenteredText(this, "Zurück zum Menü", Color.BLACK, Color.GREEN, width/2, height/60*55, f, r1);
	}
	
	
	//GraphicObject Methods
	public void draw(){
		Graphics g = getBackbuffer();
		g.setColor(Color.WHITE);
		g.fillRect(left+20, top+20, width-40, height-40);
	
		g.setFont(caption);
		Rectangle2D bounds = new TextLayout("Credits", caption, ((Graphics2D)g).getFontRenderContext()).getBounds();
		int strheight = (int) Math.ceil(bounds.getHeight());
		int strwidth = (int) Math.ceil(bounds.getWidth()); 
		int strtop = 40+strheight;
		int strleft = width/2-strwidth/2+left;
		g.setColor(Color.BLACK);
		g.drawString("Credits", strleft, strtop);
		
		g.setFont(big);
		bounds = new TextLayout("Ein Spiel von:", big, ((Graphics2D)g).getFontRenderContext()).getBounds();
		strheight = (int) Math.ceil(bounds.getHeight());
		strtop += 20+strheight;
		strwidth = (int) Math.ceil(bounds.getWidth()); 
		g.drawString("Ein Spiel von:", width/2-strwidth/2+left, strtop);
		
		strtop += 20;
		//linke box
		drawLeftBox(g, strtop, left+40, width/2-left-40-5, 200, strheight);
		//rechte box
		drawRightBox(g, strtop, width/2+5, width/2-left-40-5, 200, strheight);
		strtop += 200;
		
		g.setFont(big);
		bounds = new TextLayout("Musik:", big, ((Graphics2D)g).getFontRenderContext()).getBounds();
		strheight = (int) Math.ceil(bounds.getHeight());
		strtop += 20+strheight;
		strwidth = (int) Math.ceil(bounds.getWidth()); 
		g.drawString("Musik:", width/2-strwidth/2+left, strtop);
		
		//Buttons zeichnen
		for(Button b : buttons) if(b!=null) b.draw();
	}

	public void drawLeftBox(Graphics g, int top, int left, int width, int height, int strheight){
		g.drawRect(left, top, width, height);
		int imgwidth = (int)( 338.0/(405.0/((double)height-10.0)) );
		g.drawImage(RessourceManager.rcl, left+5, top+5, imgwidth, height-10, null);
		g.setFont(big);
		g.drawString("Robin C. Ladiges", left+10+imgwidth, top+5+strheight);
		g.setFont(smal);
		g.drawString("https://blackpinguin.de/", left+10+imgwidth, top+height-5);
	}
	
	public void drawRightBox(Graphics g, int top, int left, int width, int height, int strheight){
		g.drawRect(left, top, width, height);
		int imgwidth = (int)( 338.0/(405.0/((double)height-10.0)) );
		g.drawImage(RessourceManager.none, left+5, top+5, imgwidth, height-10, null);
		g.setFont(big);
		g.drawString("Sebastian Möllmann", left+10+imgwidth, top+5+strheight);
	}
	
	public void click(int x, int y) { for(Button b : buttons) if(b!=null) b.click(x,y); }
	public void mouseOver(int x, int y) {
		Button old = over; //alten Button zwischenspeichern
		for(Button b : buttons) if( b!=null && b.contains(x, y)) over = b; //neuen Button merken
		//neuen mouseOver senden
		if(over!=null && over!=old) over.mouseOver();
		//alten mouseOut senden
		if(old!=null && over!=old) old.mouseOut();		
	}
	public void mouseOut() {
		if(over!=null){
			//alten mouseOut senden
			over.mouseOut();
			//alten löschen
			over=null;
		}
	}

	public Graphic getGraphic(){return graphic;}
	public Main getMain(){return getGraphic().getMain();}
	public Graphics getBackbuffer(){return getGraphic().getBackbuffer();}
	public void keyPress(KeyEvent ke){}
	
}
