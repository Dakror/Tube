package de.dakror.tube.game;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import de.dakror.tube.game.tube.Row;
import de.dakror.tube.util.Camera;

/**
 * @author Dakror
 */
public class Game
{
	public static final float zNear = 0.1f;
	public static final float zFar = 100f;
	
	public static Camera camera = new Camera();
	public static Game currentGame;
	Row[] rows = new Row[1];
	
	public Game()
	{
		rows[0] = new Row(1, 1);
	}
	
	public void gameLoop()
	{
		glEnable(GL_DEPTH_TEST);
		// glEnable(GL_CULL_FACE);
		// glCullFace(GL_BACK);
		// glEnable(GL_LIGHTING);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(30, Display.getWidth() / (float) Display.getHeight(), zNear, zFar);
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		Mouse.setGrabbed(Mouse.isButtonDown(1));
		if (Mouse.isButtonDown(1))
		{
			Game.currentGame.rotateCamera();
		}
		
		glRotatef(camera.getRotation().x, 1, 0, 0);
		glRotatef(camera.getRotation().y, 0, 1, 0);
		glRotatef(camera.getRotation().z, 0, 0, 1);
		
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		for (int i = 0; i < rows.length; i++)
		{
			glPushMatrix();
			rows[i].render();
			glPopMatrix();
		}
		
		Display.update();
		Display.sync(60);
	}
	
	public void rotateCamera()
	{
		int cameraRotationSpeed = 180;
		
		float x = (Mouse.getY() - Display.getHeight() / 2) / (float) Display.getHeight() * cameraRotationSpeed;
		float y = (Mouse.getX() - Display.getWidth() / 2) / (float) Display.getWidth() * cameraRotationSpeed;
		
		if (Math.abs(camera.rotation.x - x) >= 90) x = 0;
		
		camera.rotate(-x, y, 0);
		
		Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
	}
	
	public static void init()
	{
		currentGame = new Game();
		camera.setRotation(0, -180, 0);
		
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
		
		// glEnable(GL_LIGHTING);
		// glEnable(GL_LIGHT0);
		//
		// float amb = 0.5f;
		//
		// glLightModel(GL_LIGHT_MODEL_AMBIENT, MathHelper.asFloatBuffer(new float[] { amb, amb, amb, 1 }));
		// glMaterial(GL_FRONT, GL_DIFFUSE, MathHelper.asFloatBuffer(new float[] { 1, 0, 0, 1 }));
		// glMaterial(GL_FRONT, GL_AMBIENT, MathHelper.asFloatBuffer(new float[] { 0.1f, 0.1f, 0.1f, 1 }));
		// glLight(GL_LIGHT0, GL_POSITION, MathHelper.asFloatBuffer(new float[] { 0, 0, 0, 1 }));
		// glEnable(GL_COLOR_MATERIAL);
		// glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
		// glMaterialf(GL_FRONT, GL_SHININESS, 1f);
		//
		// glEnable(GL_FOG);
	}
}
