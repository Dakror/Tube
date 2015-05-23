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

import org.lwjgl.util.vector.Vector3f;

import de.dakror.tube.game.Game;

public class Camera {
	public Vector3f position = new Vector3f();
	public Vector3f rotation = new Vector3f();
	
	public Vector3f getPosition() {
		return new Vector3f(position.x, position.y, position.z);
	}
	
	public Vector3f getRotation() {
		return new Vector3f(rotation.x, rotation.y, rotation.z);
	}
	
	public void setPosition(Vector3f v) {
		this.setPosition(v.x, v.y, v.z);
	}
	
	public void setPosition(float x, float y, float z) {
		position = new Vector3f(x, y, z);
	}
	
	public void setRotation(Vector3f v) {
		this.setRotation(v.x, v.y, v.z);
	}
	
	public void setRotation(float x, float y, float z) {
		rotation = new Vector3f(x, y, z);
	}
	
	public void move(float x, float y, float z) {
		float speed = Game.currentGame.cameraSpeed;
		position.translate(x * speed, y * speed, z * speed);
	}
	
	public void move(double d, double y, double e) {
		this.move((float) d, (float) y, (float) e);
	}
	
	public void move(Vector3f v) {
		this.move(v.x, v.y, v.z);
	}
	
	public void rotate(float x, float y, float z) {
		rotation.translate(x, y, z);
	}
}
