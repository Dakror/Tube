package de.dakror.tube;

import java.io.File;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import de.dakror.tube.game.Game;
import de.dakror.tube.settings.CFG;
import de.dakror.tube.util.MediaAssistant;

public class Tube
{
	public static boolean running;
	
	public static void main(String[] args)
	{
		MediaAssistant.initNatives();
		Thread.currentThread().setName("Main Thread");
		System.setProperty("org.lwjgl.librarypath", new File(CFG.DIR, "natives").getAbsolutePath());
		try
		{
			running = true;
			setFullscreen();
			// Display.setIcon(new ByteBuffer[] { Assistant.loadImage(Tube.class.getResourceAsStream("/graphics/logo/logo16.png")), Assistant.loadImage(Tube.class.getResourceAsStream("/graphics/logo/logo32.png")) });
			Display.setTitle("Tube");
			Display.setResizable(true);
			try
			{
				Display.create(new PixelFormat(0, 8, 0, 4));
			}
			catch (Exception e)
			{
				Display.create();
			}
			
			Game.init();
			while (running)
			{
				if (Display.isCloseRequested()) break;
				
				Game.currentGame.gameLoop();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void setFullscreen()
	{
		if (CFG.FULLSCREEN) enterFullscreen();
		else leaveFullscreen();
	}
	
	public static void enterFullscreen()
	{
		try
		{
			Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
			Display.setVSyncEnabled(true);
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void leaveFullscreen()
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(1280, 720));
			Display.setFullscreen(false);
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}
}
