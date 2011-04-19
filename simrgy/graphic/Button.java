package simrgy.graphic;

public interface Button extends GraphicObject {
	public boolean contains(int x, int y);
	public void click();
	public void mouseOver();
}
