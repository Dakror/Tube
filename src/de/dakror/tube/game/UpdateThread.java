/*******************************************************************************
 * Copyright 2015 Maximilian Stark | Dakror <mail@dakror.de>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


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
