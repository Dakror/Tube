/*******************************************************************************
 * Copyright 2015 Maximilian Stark | Dakror <mail@dakror.de>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


package de.dakror.tube.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.opengl.PNGDecoder;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

public class Assistant {
	public static boolean isInternetReachable() {
		try {
			return InetAddress.getByName("dakror.de").isReachable(60000);
		} catch (Exception e) {
			return false;
		}
	}
	
	public static ByteBuffer loadImage(InputStream is) {
		try {
			PNGDecoder decoder = new PNGDecoder(is);
			ByteBuffer bb = ByteBuffer.allocateDirect(decoder.getWidth() * decoder.getHeight() * 4);
			decoder.decode(bb, decoder.getWidth() * 4, PNGDecoder.RGBA);
			bb.flip();
			is.close();
			return bb;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void setFileContent(File f, String s) {
		f.getParentFile().mkdirs();
		try {
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f));
			osw.write(s);
			osw.close();
		} catch (Exception e) {}
	}
	
	public static String getURLContent(URL u) {
		String res = "", line = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(u.openStream()));
			while ((line = br.readLine()) != null)
				res += line;
			br.close();
		} catch (IOException e) {
			return null;
		}
		return res;
	}
	
	public static String getFileContent(File f) {
		String res = "", line = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			while ((line = br.readLine()) != null)
				res += line;
			br.close();
		} catch (IOException e) {
			return null;
		}
		return res;
	}
	
	public static String getFolderChecksum(File folder) {
		if (!folder.exists()) return null;
		String[] files = folder.list();
		Arrays.sort(files);
		String f = Arrays.toString(files) + getFolderSize(folder);
		return MD5(f.getBytes());
	}
	
	public static String MD5(byte[] b) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return HexBin.encode(md.digest(b));
	}
	
	public static long getFolderSize(File directory) {
		long length = 0;
		for (File file : directory.listFiles()) {
			if (file.isFile()) length += file.length();
			else length += getFolderSize(file);
		}
		return length;
	}
	
	public static void deleteFolder(File folder) {
		for (File f : folder.listFiles()) {
			if (f.isDirectory()) deleteFolder(f);
			f.delete();
		}
	}
	
	public static String formatBinarySize(long size, int digits) {
		final String[] levels = { "", "K", "M", "G", "T" };
		for (int i = levels.length - 1; i > -1; i--)
			if (size > (long) Math.pow(1024, i)) {
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(digits);
				df.setMinimumFractionDigits(digits);
				return df.format(size / Math.pow(1024, i)) + levels[i] + "B";
			}
		return null;
	}
	
	public static void copyInputStream(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int len = in.read(buffer);
		while (len >= 0) {
			out.write(buffer, 0, len);
			len = in.read(buffer);
		}
		in.close();
		out.close();
	}
	
	public static <T> ArrayList<T> asList(T[] t) {
		ArrayList<T> list = new ArrayList<>();
		for (T t1 : t)
			list.add(t1);
		return list;
	}
}
