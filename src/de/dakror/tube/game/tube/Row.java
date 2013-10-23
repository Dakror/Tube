package de.dakror.tube.game.tube;

import static org.lwjgl.opengl.GL11.*;

public class Row
{
	Field[] fields = new Field[5];
	int x;
	int y;
	
	public Row(int x, int y)
	{
		this.x = x;
		this.y = y;
		for (int i = 0; i < fields.length; i++)
		{
			fields[i] = new Field(i, false);
		}
	}
	
	public void render()
	{
		glTranslatef(x, y, 0);
		for (int i = 0; i < fields.length; i++)
		{
			glPushMatrix();
			fields[i].render();
			glPopMatrix();
		}
	}
	
}
