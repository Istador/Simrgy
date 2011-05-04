package simrgy.graphic;

import simrgy.applet.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ButtonImage implements Button {

	protected GraphicObject parent;
	
	protected String name;
	protected boolean highlight = false;
	protected Color highlightcolor;
	protected Color markierung = null;
	protected Color backcolor;
	protected Runnable action;
	protected Rectangle bounds;
	protected int top;
	protected int left;
	protected int width;
	protected int height;
	protected Image img;
	
	
	protected ButtonImage(){}
	public ButtonImage(GraphicObject parent, Image img, Color backcolor, Color highlightcolor, int x, int y, int width, int height, Runnable action){
		this.parent = parent;
		this.backcolor = backcolor;
		this.highlightcolor = highlightcolor;
		this.action = action;
		this.img = img; 
		this.bounds = new Rectangle(x,y,width,height);
		this.top = y;
		this.left = x;
		this.width = width;
		this.height = height;
		
	}
	
	public void draw() {
		Graphics g = getBackbuffer();
		//Background
		if(backcolor != null){
			g.setColor(backcolor);
			g.fillRect(left, top, width, height);
		}
		
		//Bild
		if(img != null){
			g.drawImage(img, left, top, width, height, null);
		}
		
		//Highlight
		if(highlight){
			g.setColor(highlightcolor);
			g.fillRect(left, top, width, height);
		}
				
		if(markierung==null){
			//normale bounding Box
			g.setColor(Color.BLACK);
			g.drawRect(left, top, width, height);
		}
		else{
			//markierungs bounding Box
			g.setColor(markierung);
			g.fillRect(left, top, width, 5); //oben
			g.fillRect(left, top, 5, height); //links
			g.fillRect(left+width-5, top, 5, height); //rechts
			g.fillRect(left, top+height-5, width, 5); //unten
		}
		
	}

	public void click() { if(action != null) action.run(); }
	public void click(int x, int y) { if(contains(x,y)) click(); }

	public void mouseOver() {highlight = true;}
	public void mouseOver(int x, int y) { if(contains(x,y)) mouseOver(); }
	public void mouseOut() {highlight = false;}

	public boolean contains(int x, int y){
		return bounds.contains(x, y);
	}
	
	public GraphicObject getParent(){return parent;}
	public Main getMain() {return getParent().getMain();}
	public Graphics getBackbuffer(){return getParent().getBackbuffer();}
	public void keyPress(KeyEvent ke){}
	
	public int getHeight() { return height; }
	public int getWidth() { return width; }
	public Point getTL() {return new Point(left, top);}
	public Point getBL() {return new Point(left, top+height);}
	public Point getTR() {return new Point(left+width, top);}
	public Point getBR() {return new Point(left+width, top+height);}
	public void markiere(Color c) {
		markierung = c;
	}
}
