package simrgy.graphic;

import java.awt.*;
import java.net.*;
import javax.swing.*;

import simrgy.applet.Main;

public class GUI implements GraphicObject {

	public int left;
	public int width;
	
	//Singleton
	private static GUI instance = null;
	private GUI(){
		left  = Map.getInstance().getWidth();
		width = 800-left;
	}	
	public static GUI getInstance(){
		if(instance == null)
			instance = new GUI();
		return instance;
	}
	
	
	//GraphicObject Methods
	public void draw(Graphics g){
		g.setColor(Color.YELLOW);
		g.fillRect(left, 0, width, 600);
	}

	public void click(int x, int y) {
		System.out.println("GUI clicked at: ( "+String.valueOf(x)+", "+String.valueOf(y)+" )");
	}
	
	public void mouseOver(int x, int y) {}
	public void mouseOut() {}
}
