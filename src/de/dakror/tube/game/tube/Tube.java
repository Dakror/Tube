package de.dakror.tube.game.tube;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author Dakror
 */
public class Tube
{
	Row[] rows;
	int activeRow;
	
	public Tube(int n)
	{
		float step = 360f / n;
		float radius = (0.5f * Field.SIZE) / (float) Math.cos(Math.toRadians((180 - step) / 2f)) - 0.08f;
		
		rows = new Row[n];
		for (int i = 0; i < n; i++)
		{
			float degs = step * i;
			float rads = (float) Math.toRadians(degs);
			rows[i] = new Row((float) Math.cos(rads) * radius - 0.5f, (float) Math.sin(rads) * radius, degs);
		}
	}
	
	public int getRowCount()
	{
		return rows.length;
	}
	
	public Row getRow(int index)
	{
		return rows[index];
	}
	
	public void render()
	{
		for (int i = 0; i < rows.length; i++)
		{
			glPushMatrix();
			rows[i].render(i == activeRow);
			glPopMatrix();
		}
	}
	
	public float getRowDegree()
	{
		return 360f / getRowCount();
	}
	
	public void setActiveRow(int n)
	{
		activeRow = n % getRowCount();
	}
	
	public int getActiveRow()
	{
		return activeRow;
	}
}
