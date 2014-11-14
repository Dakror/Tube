package de.dakror.tube.game;

import de.dakror.tube.Tube;


/**
 * @author Dakror
 */
public class UpdateThread extends Thread {
	public static UpdateThread currentUpdateThread;
	
	public boolean stopRequested;
	
	public UpdateThread() {
		currentUpdateThread = this;
		stopRequested = false;
		start();
	}
	
	@Override
	public void run() {
		while (Tube.running) {
			if (stopRequested) break;
			
			Game.camera.update();
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
