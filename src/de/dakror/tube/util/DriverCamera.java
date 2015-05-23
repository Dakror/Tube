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
 

package de.dakror.tube.util;

import org.lwjgl.input.Keyboard;

import de.dakror.tube.game.Game;

public class DriverCamera {
	float posZ;
	float lastZ;
	float rotation;
	public static final float DEGREES = 3f;
	public static final float SPEED = 0.2f;
	
	public DriverCamera() {
		posZ = 0;
		rotation = 0;
		lastZ = -1;
	}
	
	public void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) posZ += SPEED;
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) posZ -= SPEED;
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) rotation -= DEGREES;
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) rotation += DEGREES;
		
		if (lastZ == -1 || posZ > lastZ + 1) {
			Game.currentGame.tube.appendFirstRing();
			lastZ = posZ;
		}
	}
	
	public float getPosZ() {
		return posZ;
	}
	
	public void setPosZ(float posZ) {
		this.posZ = posZ;
	}
	
	public float getRotation() {
		return rotation;
	}
	
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	public float getRelativeRotation() {
		float rot = rotation % 360;
		
		if (rot < 0) rot = 360 + rot;
		
		return rot;
	}
}
