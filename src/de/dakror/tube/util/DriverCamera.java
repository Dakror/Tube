package de.dakror.tube.util;

import org.lwjgl.input.Keyboard;

public class DriverCamera
{
	float posZ;
	float rotation;
	public static final float SPEED = 0.05f;
	
	public DriverCamera()
	{
		posZ = 0;
		rotation = 0;
		
	}
	
	public void update()
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			posZ += SPEED;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			posZ -= SPEED;
		}
	}
	
	public float getPosZ()
	{
		return posZ;
	}
	
	public void setPosZ(float posZ)
	{
		this.posZ = posZ;
	}
	
	public float getRotation()
	{
		return rotation;
	}
	
	public void setRotation(float rotation)
	{
		this.rotation = rotation;
	}
}
