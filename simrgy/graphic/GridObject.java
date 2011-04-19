package simrgy.graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import simrgy.game.Building;

public /*abstract*/ class GridObject {
	private int left;
	private int top;
	private int width;
	private int height;
	private Building building;
	private boolean showName;
	private Color highlightColor;
	
	protected GridObject(int left, int top, int width, int height, Building building){
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
		this.building = building;
		this.showName = false;	
		highlightColor = null;
		building.attachGridObject(this);
	}
	
	public void draw(Graphics g){
		if(highlightColor != null){
			g.setColor(highlightColor);
			g.fillRect(left, top, width, height);
		}
		g.drawImage(building.getImage(), left, top, width, height, null);
		if(showName){
			g.setColor(Color.BLACK);
			g.drawString(building.getName(), left+1, top+11);
		}
	}
	public void click(){
		System.out.println(building.getName()+" clicked");
	}
	public void mouseOver(){highlightYellow(); this.showName = true;}
	public void mouseOut(){highlightNone(); this.showName = false;}
	
	public void highlightRed(){highlightColor=Color.RED;}
	public void highlightYellow(){highlightColor=Color.YELLOW;}
	public void highlightNone(){highlightColor=null;}
}
