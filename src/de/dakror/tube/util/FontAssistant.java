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

import java.awt.Font;
import java.util.HashMap;

import org.newdawn.slick.TrueTypeFont;

public class FontAssistant {
	private static HashMap<Font, TrueTypeFont> fonts = new HashMap<>();
	
	public static Font GAMEFONT;
	
	public static TrueTypeFont getFont(Font f) {
		if (fonts.containsKey(f)) return fonts.get(f);
		else {
			TrueTypeFont uf = new TrueTypeFont(f, f.getSize() < 20);
			fonts.put(f, uf);
			return uf;
		}
	}
	
	static {
		try {
			GAMEFONT = Font.createFont(Font.TRUETYPE_FONT, FontAssistant.class.getResourceAsStream("/alagard.ttf"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
