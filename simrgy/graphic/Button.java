package simrgy.graphic;

import java.awt.Color;
import java.awt.Point;

public interface Button extends GraphicObject {
	public boolean contains(int x, int y);
	public void click();
	public void mouseOver();
	
	public Point getTL();
	public Point getTR();
	public Point getBL();
	public Point getBR();
	
	public int getWidth();
	public int getHeight();
	
	public void markiere(Color c);
}
