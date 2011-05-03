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
				//60s = 1 Jahr (Bauzeit, Geld, Strom)
				//240s = 1 Tag (Sonne)
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
