package de.dakror.tube.game.tube;

import static org.lwjgl.opengl.GL11.*;

public class Field
{
	int pos;
	boolean block;
	
	public Field(int pos, boolean block)
	{
		this.pos = pos;
		this.block = block;
	}
	
	public void render()
	{
		glTranslatef(0, 0, pos);
		glColor3f(1, 0, 0);
		glBegin(GL_QUADS);
		glVertex3f(1, 0, 1);
		glVertex3f(0, 0, 1);
		glVertex3f(0, 0, 0);
		glVertex3f(1, 0, 0);
		glEnd();
	}
}
