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

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;

import de.dakror.tube.game.tube.Tube;
import de.dakror.tube.util.DriverCamera;

/**
 * @author Dakror
 */
public class Game {
	public static final float zNear = 0.1f;
	public static final float zFar = 50f;
	
	public static DriverCamera camera = new DriverCamera();
	public static Game currentGame;
	
	public float cameraSpeed = 0.3f;
	public int cameraRotationSpeed = 180;
	
	public Tube tube;
	
	public Game() {
		tube = new Tube(12);
		
		new UpdateThread();
	}
	
	public void gameLoop() {
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(30, Display.getWidth() / (float) Display.getHeight(), zNear, zFar);
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		glRotatef(180, 1, 0, 0);
		glTranslatef(0, -1f, -camera.getPosZ());
		glRotatef(camera.getRelativeRotation(), 0, 0, 1);
		
		FloatBuffer fogColor = BufferUtils.createFloatBuffer(4);
		
		fogColor.put(new float[] { 0, 0, 0, 1 }).flip();
		glEnable(GL_FOG);
		glFogi(GL_FOG_MODE, GL_LINEAR);
		glFog(GL_FOG_COLOR, fogColor);
		glFogf(GL_FOG_DENSITY, 1);
		glHint(GL_FOG_HINT, GL_DONT_CARE);
		glFogf(GL_FOG_START, zFar - 40);
		glFogf(GL_FOG_END, zFar);
		
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		tube.render();
		
		tube.setActiveRow(tube.getRowCount() - Math.round((camera.getRelativeRotation() - tube.getRowDegree() * 2.5f) / tube.getRowDegree()));
		
		Display.update();
		Display.sync(60);
	}
	
	public static void init() {
		currentGame = new Game();
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glMatrixMode(GL_MODELVIEW);
		
		glShadeModel(GL_SMOOTH);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_POINT_SMOOTH);
		glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);
		glEnable(GL_LINE_SMOOTH);
		glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		
		glEnable(GL_FOG);
	}
}
