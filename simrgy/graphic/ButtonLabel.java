package simrgy.graphic;

import simrgy.applet.*;
import static simrgy.res.RessourceManager.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ButtonLabel implements Button {

	protected GraphicObject parent;
	
	protected String name;
	protected boolean highlight = false;
	protected Color highlightcolor;
	protected Color fontcolor;
	protected Color markierung;
	protected Runnable action;
	protected Font font;
	protected int top;
	protected int left;
	protected int width;
	protected int height;
	
	
	protected ButtonLabel(){}
	public ButtonLabel(GraphicObject parent, String name, Color fontcolor, Color highlightcolor, int x, int y, Font f, Runnable action){
		this.parent = parent;
		this.name = name;
		this.fontcolor = fontcolor;
		this.highlightcolor = highlightcolor;
		this.action = action;
		this.font = f;
		int[] b = f_size(getBackbuffer(), f, name);
		width = b[1];
		height = b[0];
		left = x;
		top = y;
	}
	
	public void draw() {
		Graphics g = getBackbuffer();
			
		//Markierung
		if(markierung != null){
			g.setColor(markierung);
			g.fillRect(left, top, width, height);
		}
		
		//Color
		if(highlight)
			g.setColor(highlightcolor);
		else
			g.setColor(fontcolor);
		
		//Font
		g.setFont(font);
		
				
		//String
		g.drawString(name, left, top+height);
	}

	public void click() {
		getMain().play(sClick);
		if(action != null){ action.run(); }
	}
	public void click(int x, int y) { if(contains(x,y)) click(); }

	public void mouseOver() {highlight = true;}
	public void mouseOver(int x, int y) { if(contains(x,y)) mouseOver(); else mouseOut(); }
	public void mouseOut() {highlight = false;}

	public boolean contains(int x, int y){
		return x>=left && x<=left+width && y>=top && y<=top+height;
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
	
	public String getText(){
		return name;
	}
	
	public void setTL(int top, int left){
		this.top = top;
		this.left = left;
	}
}
