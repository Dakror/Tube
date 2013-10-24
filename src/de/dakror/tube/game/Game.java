package de.dakror.tube.game;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import de.dakror.tube.game.tube.Field;
import de.dakror.tube.game.tube.Row;
import de.dakror.tube.util.DriverCamera;

/**
 * @author Dakror
 */
public class Game
{
	public static final float zNear = 0.1f;
	public static final float zFar = 50f;
	
	public static DriverCamera camera = new DriverCamera();
	public static Game currentGame;
	
	public float cameraSpeed = 0.3f;
	public int cameraRotationSpeed = 180;
	
	Row[] rows;
	
	public Game()
	{
		new UpdateThread();
		
		createTube(12);
	}
	
	public void createTube(int n)
	{
		float step = 360f / n;
		float radius = (0.5f * Field.SIZE) / (float) Math.cos(Math.toRadians((180 - step) / 2f)) - 0.08f;
		
		rows = new Row[n];
		for (int i = 0; i < n; i++)
		{
			float degs = step * i;
			float rads = (float) Math.toRadians(degs);
			rows[i] = new Row((float) Math.cos(rads) * radius, (float) Math.sin(rads) * radius, degs);
		}
	}
	
	public void gameLoop()
	{
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(30, Display.getWidth() / (float) Display.getHeight(), zNear, zFar);
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		glRotatef(180, 1, 0, 0);
		glTranslatef(-0.5f, -1f, -camera.getPosZ());
		
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
		
		for (int i = 0; i < rows.length; i++)
		{
			glPushMatrix();
			rows[i].render();
			glPopMatrix();
		}
		
		Display.update();
		Display.sync(60);
	}
	
	public static Vector3f getNormalizedRotationVectorForSidewardMovement(Vector3f v)
	{
		double x = Math.sin(Math.toRadians(v.y));
		double y = 0;
		double z = Math.cos(Math.toRadians(v.y));
		
		return new Vector3f((float) -x, (float) -y, (float) z);
	}
	
	public static void init()
	{
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
		glEnable(GL_FOG);
	}
}
