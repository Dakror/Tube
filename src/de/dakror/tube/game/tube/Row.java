package de.dakror.tube.game.tube;

import static org.lwjgl.opengl.GL11.*;

@Deprecated
public class Row
{
	Field[] fields = new Field[300];
	float x;
	float y;
	
	/**
	 * In Degrees
	 */
	float angle;
	
	@Deprecated
	public Row(float x, float y)
	{
		this(x, y, 0);
	}
	
	@Deprecated
	public Row(float x, float y, float angle)
	{
		this.x = x;
		this.y = y;
		this.angle = angle;
		for (int i = 0; i < fields.length; i++)
		{
			fields[i] = new Field(i, false);
		}
	}
	
	@Deprecated
	public void render(boolean active)
	{
		if (active) glColor3f(1, 1, 1);
		else glColor3f(0.75f, 0.75f, 0.75f);
		
		glTranslatef(x + 0.5f, y, 0);
		glRotatef(angle - 90, 0, 0, 1);
		glTranslatef(-0.5f, 0, 0);
		for (Field f : fields)
		{
			glPushMatrix();
			f.render();
			glPopMatrix();
		}
	}
}
