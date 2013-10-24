package de.dakror.tube.util;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.MipMap;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.TextureLoader;

import de.dakror.tube.settings.CFG;

public class RenderAssistant
{
	public static HashMap<String, Texture> textures = new HashMap<>();
	
	public static void storeTexture(String path)
	{
		Texture t = loadTexture(path);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		textures.put(path, t);
	}
	
	public static void bindTexture(String path)
	{
		if (path == null) return;
		
		Texture t = textures.get(path);
		if (t != null) t.bind();
		else
		{
			t = loadTexture(path);
			t.bind();
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			textures.put(path, t);
		}
	}
	
	private static Texture loadTexture(String path)
	{
		try
		{
			Texture texture = TextureLoader.getTexture(".png", RenderAssistant.class.getResourceAsStream(path));
			improveTexture(texture);
			return texture;
		}
		catch (Exception e)
		{
			CFG.p(path);
			e.printStackTrace();
		}
		return null;
	}
	
	public static void improveTexture(Texture texture)
	{
		texture.bind();
		
		int width = texture.getImageWidth();
		int height = texture.getImageHeight();
		
		byte[] texbytes = texture.getTextureData();
		int components = texbytes.length / (width * height);
		
		ByteBuffer texdata = BufferUtils.createByteBuffer(texbytes.length);
		texdata.put(texbytes);
		texdata.rewind();
		
		MipMap.gluBuild2DMipmaps(GL11.GL_TEXTURE_2D, components, width, height, components == 3 ? GL11.GL_RGB : GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, texdata);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, 8);
	}
	
	public static void renderRect(float posX, float posY, float sizeX, float sizeY)
	{
		renderRect(posX, posY, sizeX, sizeY, 1, 1);
	}
	
	public static void renderRect(float posX, float posY, float sizeX, float sizeY, float texSizeX, float texSizeY)
	{
		renderRect(posX, posY, sizeX, sizeY, 0, 0, texSizeX, texSizeY);
	}
	
	public static void renderRect(float posX, float posY, float sizeX, float sizeY, float texPosX, float texPosY, float texSizeX, float texSizeY)
	{
		glPushMatrix();
		{
			glDisable(GL_CULL_FACE);
			
			GL11.glBegin(GL11.GL_QUADS);
			{
				GL11.glTexCoord2d(texPosX, texPosY + texSizeY);
				GL11.glVertex2f(posX, posY + sizeY);
				
				GL11.glTexCoord2d(texPosX + texSizeX, texPosY + texSizeY);
				GL11.glVertex2f(posX + sizeX, posY + sizeY);
				
				GL11.glTexCoord2d(texPosX + texSizeX, texPosY);
				GL11.glVertex2f(posX + sizeX, posY);
				
				GL11.glTexCoord2d(texPosX, texPosY);
				GL11.glVertex2f(posX, posY);
			}
			GL11.glEnd();
		}
		glPopMatrix();
	}
	
	public static void renderCuboid(float x, float y, float z, float sizeX, float sizeY, float sizeZ)
	{
		glBegin(GL_LINES);
		{
			glVertex3f(x, y, z);
			glVertex3f(x + sizeX, y, z);
			
			glVertex3f(x, y + sizeY, z);
			glVertex3f(x + sizeX, y + sizeY, z);
			
			glVertex3f(x, y, z + sizeZ);
			glVertex3f(x + sizeX, y, z + sizeZ);
			
			glVertex3f(x, y + sizeY, z + sizeZ);
			glVertex3f(x + sizeX, y + sizeY, z + sizeZ);
			
			glVertex3f(x, y, z);
			glVertex3f(x, y, z + sizeZ);
			
			glVertex3f(x + sizeX, y, z);
			glVertex3f(x + sizeX, y, z + sizeZ);
			
			glVertex3f(x + sizeX, y + sizeY, z);
			glVertex3f(x + sizeX, y + sizeY, z + sizeZ);
			
			glVertex3f(x, y + sizeY, z);
			glVertex3f(x, y + sizeY, z + sizeZ);
			
			glVertex3f(x, y, z);
			glVertex3f(x, y + sizeY, z);
			
			glVertex3f(x + sizeX, y, z);
			glVertex3f(x + sizeX, y + sizeY, z);
			
			glVertex3f(x, y, z + sizeZ);
			glVertex3f(x, y + sizeY, z + sizeZ);
			
			glVertex3f(x + sizeX, y, z + sizeZ);
			glVertex3f(x + sizeX, y + sizeY, z + sizeZ);
		}
		glEnd();
	}
	
	public static void drawRect(float posX, float posY, float sizeX, float sizeY)
	{
		glPushMatrix();
		{
			glDisable(GL_CULL_FACE);
			GL11.glBegin(GL11.GL_QUADS);
			{
				GL11.glVertex2f(posX, posY + sizeY);
				
				GL11.glVertex2f(posX + sizeX, posY + sizeY);
				
				GL11.glVertex2f(posX + sizeX, posY);
				
				GL11.glVertex2f(posX, posY);
			}
			GL11.glEnd();
		}
		glPopMatrix();
	}
	
	public static void renderText(float x, float y, String text, Font f)
	{
		TextureImpl.bindNone();
		glEnable(GL_BLEND);
		FloatBuffer fb = BufferUtils.createFloatBuffer(16);
		glGetFloat(GL_CURRENT_COLOR, fb);
		FontAssistant.getFont(f).drawString(x, y, text, new Color(fb));
		glDisable(GL_BLEND);
	}
	
	public static void set2DRenderMode(boolean t)
	{
		if (t)
		{
			glMatrixMode(GL_PROJECTION);
			glPushMatrix();
			glLoadIdentity();
			glOrtho(0.0, Display.getWidth(), Display.getHeight(), 0.0, -1.0, 10.0);
			glMatrixMode(GL_MODELVIEW);
			glLoadIdentity();
			
			glClear(GL_DEPTH_BUFFER_BIT);
			glDisable(GL_TEXTURE_2D);
			glShadeModel(GL_SMOOTH);
			glDisable(GL_DEPTH_TEST);
		}
		else
		{
			glMatrixMode(GL_PROJECTION);
			glPopMatrix();
			glMatrixMode(GL_MODELVIEW);
		}
	}
	
	public static void glColorHex(String hex, float alpha)
	{
		glColor4f(Integer.parseInt(hex.substring(0, 2), 16) / 255f, Integer.parseInt(hex.substring(2, 4), 16) / 255f, Integer.parseInt(hex.substring(4, 6), 16) / 255f, alpha);
	}
	
	public static BufferedImage toBufferedImage(Image img)
	{
		BufferedImage image = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		image.getGraphics().drawImage(img, 0, 0, null);
		
		return image;
	}
	
	public static String[] wrap(String raw, Font f, int width)
	{
		String[] words = raw.split(" ");
		ArrayList<String> lines = new ArrayList<>();
		int wordIndex = 0;
		String line = "";
		TrueTypeFont ttf = FontAssistant.getFont(f);
		
		while (wordIndex < words.length)
		{
			if (ttf.getWidth(line + " " + words[wordIndex]) <= width)
			{
				line += " " + words[wordIndex];
				wordIndex++;
			}
			else
			{
				
				lines.add(line);
				line = "";
			}
		}
		lines.add(line);
		
		ArrayList<String> realLines = new ArrayList<>();
		for (String s : lines)
		{
			String[] nls = s.split("\n");
			for (String nl : nls)
				realLines.add(nl);
		}
		
		return realLines.toArray(new String[] {});
	}
}
