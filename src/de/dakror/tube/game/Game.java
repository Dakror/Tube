package de.dakror.tube.game;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import org.lwjgl.opengl.Display;

import de.dakror.tube.game.tube.Row;
import de.dakror.tube.settings.CFG;
import de.dakror.tube.util.math.MathHelper;

/**
 * @author Dakror
 */
public class Game
{
	public static final float zNear = 0.1f;
	public static final float zFar = 100f;
	
	public static Game currentGame;
	Row[] rows = new Row[1];
	
	public Game()
	{
		rows[0] = new Row(50, 50);
		
	}
	
	public void gameLoop()
	{
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glEnable(GL_LIGHTING);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(30, Display.getWidth() / (float) Display.getHeight(), zNear, zFar);
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		for (int i = 0; i < rows.length; i++)
		{
			CFG.p(i);
			glPushMatrix();
			rows[i].render();
			glPopMatrix();
		}
		
		
		
		Display.update();
		Display.sync(60);
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
		
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		
		float amb = 0.5f;
		
		glLightModel(GL_LIGHT_MODEL_AMBIENT, MathHelper.asFloatBuffer(new float[] { amb, amb, amb, 1 }));
		glMaterial(GL_FRONT, GL_DIFFUSE, MathHelper.asFloatBuffer(new float[] { 1, 0, 0, 1 }));
		glMaterial(GL_FRONT, GL_AMBIENT, MathHelper.asFloatBuffer(new float[] { 0.1f, 0.1f, 0.1f, 1 }));
		glLight(GL_LIGHT0, GL_POSITION, MathHelper.asFloatBuffer(new float[] { 0, 0, 0, 1 }));
		glEnable(GL_COLOR_MATERIAL);
		glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
		glMaterialf(GL_FRONT, GL_SHININESS, 1f);
		
		glEnable(GL_FOG);
	}
}
