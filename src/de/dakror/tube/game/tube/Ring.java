package de.dakror.tube.game.tube;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 * @author Dakror
 */
public class Ring
{
	Vector3f[] vertices;
	
	public Ring(Vector2f[] verts, int z)
	{
		vertices = new Vector3f[verts.length];
		
		for (int i = 0; i < verts.length; i++)
		{
			vertices[i] = new Vector3f(verts[i].x, verts[i].y, z);
		}
	}
	
	public void render()
	{
		GL11.glColor3f(1, 0, 0);
		for (int i = 0; i <= vertices.length; i++)
		{
			GL11.glBegin(GL11.GL_LINES);
			Vector3f v = vertices[i % vertices.length];
			GL11.glVertex3f(v.x, v.y, v.z);
			Vector3f v1 = vertices[(i + 1) % vertices.length];
			GL11.glVertex3f(v1.x, v1.y, v1.z);
			GL11.glEnd();
		}
	}
	
	public Vector3f[] getRow(int n)
	{
		return new Vector3f[] { vertices[n % vertices.length], vertices[(n + 1) % vertices.length] };
	}
	
	public void setZ(float z)
	{
		for (Vector3f v : vertices)
		{
			v.setZ(z);
		}
	}
	
	public float getZ()
	{
		return vertices[0].z;
	}
}
