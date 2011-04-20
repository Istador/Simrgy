package simrgy.graphic;

import simrgy.applet.*;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;

public class CenteredButton implements Button {

	protected GraphicObject parent;
	
	protected String name;
	protected boolean highlight = false;
	protected Color highlightcolor;
	protected Color fontcolor;
	protected Runnable action;
	protected Font font;
	protected int top;
	protected int left;
	protected int width;
	protected int height;
	
	
	protected CenteredButton(){}
	public CenteredButton(GraphicObject parent, String name, Color fontcolor, Color highlightcolor, int x, int y, Font f, Runnable action){
		this.parent = parent;
		this.name = name;
		this.fontcolor = fontcolor;
		this.highlightcolor = highlightcolor;
		this.action = action;
		this.font = f;
		Rectangle2D bounds = new TextLayout(name, f, ((Graphics2D)getBackbuffer()).getFontRenderContext()).getBounds();
		width = (int) Math.ceil(bounds.getWidth())+80;
		height = (int) Math.ceil(bounds.getHeight())+20;
		left = x-width/2;
		top = y-height/2;
	}
	
	public void draw() {
		Graphics g = getBackbuffer();
		//Highlight
		if(highlight){
			g.setColor(highlightcolor);
			g.fillRect(left, top, width, height);
		}
		
		//Font / Color
		g.setFont(font);
		g.setColor(fontcolor);
		
		//bounding Box
		g.drawRect(left, top, width, height);
		
		//String
		g.drawString(name, left+40, top+height-10);
	}

	public void click() { if(action != null) action.run(); }
	public void click(int x, int y) { if(contains(x,y)) click(); }

	public void mouseOver() {highlight = true;}
	public void mouseOver(int x, int y) { if(contains(x,y)) mouseOver(); }
	public void mouseOut() {highlight = false;}

	public boolean contains(int x, int y){
		return x>=left && x<=left+width && y>=top && y<=top+height;
	}
	
	public GraphicObject getParent(){return parent;}
	public Main getMain() {return getParent().getMain();}
	public Graphics getBackbuffer(){return getParent().getBackbuffer();}

}
