package simrgy.applet;

import static simrgy.res.RessourceManager.*;

import simrgy.res.RessourceManager;
import javazoom.jl.player.*;

public class Music extends Thread {
	
	private static boolean playMusic = false;
	private Player mp;
		
	public void run(){
		try {
			while(playMusic){
				mp = new Player(RessourceManager.class.getResourceAsStream(getRandomBackgroundMusic()));
				mp.play();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		mp.close();
	}
	
	private static Music thread;
	
	public static void play_music(){
		if(thread==null && !playMusic){
			playMusic = true;
			thread = new Music();
			thread.start();
		}
	}
	
	public static void stop_music(){
		if(thread!=null  && playMusic){
			playMusic = false;
			thread.close();
			thread = null;
		}
	}
}
