package de.dakror.tube.settings;

import java.io.File;

public class CFG
{
	public static final File DIR = new File(System.getProperty("user.home") + "/.dakror/Tube");
	static
	{
		DIR.mkdirs();
	}
}
