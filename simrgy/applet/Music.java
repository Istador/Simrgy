package simrgy.applet;

import static simrgy.res.RessourceManager.*;
import java.io.ByteArrayInputStream;
import javazoom.jl.player.*;

public class Music extends Thread {
	
	public static boolean playMusic = true; 
	private Player mp;
		
	public void run(){
		try {
			while(playMusic){
				mp = new Player(new ByteArrayInputStream(getRandomBackgroundMusic()));
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
		if(thread==null){
			playMusic = true;
			thread = new Music();
			thread.start();
		}
	}
	
	public static void stop_music(){
		if(thread!=null){
			playMusic = false;
			thread.close();
			thread = null;
		}
	}
}
