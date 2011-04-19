package simrgy.graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

public class CenteredButton implements Button {

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
	public CenteredButton(Graphics g, String name, Color fontcolor, Color highlightcolor, int x, int y, Font f, Runnable action){
		this.name = name;
		this.fontcolor = fontcolor;
		this.highlightcolor = highlightcolor;
		this.action = action;
		this.font = f;
		Rectangle2D bounds = new TextLayout(name, f, ((Graphics2D)g).getFontRenderContext()).getBounds();
		width = (int) Math.ceil(bounds.getWidth())+80;
		height = (int) Math.ceil(bounds.getHeight())+20;
		left = x-width/2;
		top = y-height/2;
	}
	
	public void draw(Graphics g) {
		//Highlight
		if(highlight){
			g.setColor(highlightcolor);
			g.fillRect(left, top, width, height);
		}
		
		//Font / Color
		g.setFont(font);
		g.setColor(fontcolor);
		
		//bounding Box
		g.drawLine(left, top, left, top+height);
		g.drawLine(left, top+height, left+width, top+height);
		g.drawLine(left+width, top+height, left+width, top);
		g.drawLine(left+width, top, left, top);
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

	
}
