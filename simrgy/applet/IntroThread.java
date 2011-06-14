package simrgy.applet;

import static simrgy.res.RessourceManager.*;

import simrgy.res.RessourceManager;
import javazoom.jl.player.*;

public class IntroThread extends Thread {
	
	private static boolean playing = false;
	private Player mp;
		
	public void run(){
		try {
			mp = new Player(RessourceManager.class.getResourceAsStream(sIntro));
			mp.play();
	
		} catch (Exception e) {
			//e.printStackTrace();
		}
		playing = false;
	}
	
	public void close(){
		mp.close();
	}
	
	private static IntroThread thread;
		
	public static void play_intro(){
		if(thread==null && !playing){
			playing = true;
			thread = new IntroThread();
			thread.start();
		}
	}
	
	public static void stop_intro(){
		if(thread!=null  && playing){
			playing = false;
			thread.close();
			thread = null;
		}
	}
}
