package simrgy.applet;

import java.util.TimerTask;

import simrgy.graphic.Graphic;

public class GameThread extends Thread{
	protected Main main;
	
	protected boolean running = false;
	
	public GameThread(Main m){
		this.main=m;
	}
	
	public void run() {
		running=true;
		while(running)	main.repaint();
	}


    public void done() {
    	running = false;
    }
    
    public boolean isRunning(){
    	return running;
    }
}
