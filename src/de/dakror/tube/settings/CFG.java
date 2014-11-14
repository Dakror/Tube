package de.dakror.tube.settings;

import java.io.File;
import java.util.Arrays;

public class CFG {
	public static final File DIR = new File(System.getProperty("user.home") + "/.dakror/Tube");
	public static boolean FULLSCREEN = false;
	
	static {
		DIR.mkdirs();
	}
	
	public static void p(Object... objects) {
		if (objects.length == 1) System.out.println(objects[0]);
		else System.out.println(Arrays.toString(objects));
	}
}
