package simrgy.graphic;

import java.awt.Graphics;

public interface GraphicObject {

	public void draw(Graphics g);
	public void click(int x, int y);
	public void mouseOver(int x, int y);
	public void mouseOut();
	
}
