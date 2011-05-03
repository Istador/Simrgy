package simrgy.applet;


public class GameThread extends Thread{
	protected Main main;
	
	protected boolean running = false;
	
	public GameThread(Main m){
		this.main=m;
	}
	
	public void run() {		
		running=true;
		long lastTime = System.currentTimeMillis();
		while(running){
			long currTime = System.currentTimeMillis();
			long timeDiff = currTime - lastTime;
			lastTime = currTime;
			if(timeDiff != 0)
				{
				main.getGame().tick(timeDiff);
				main.repaint();
				}
		}
	}


    public void done() {
    	running = false;
    }
    
    public boolean isRunning(){
    	return running;
    }
}
