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

import java.io.File;
import java.io.FileOutputStream;

import de.dakror.tube.settings.CFG;

public class MediaAssistant {
	public static void initNatives() {
		File natives = new File(CFG.DIR, "natives");
		if (!natives.exists()) {
			try {
				File tmpFile = new File(CFG.DIR, "tmp.zip");
				Assistant.copyInputStream(MediaAssistant.class.getResourceAsStream("/natives.zip"), new FileOutputStream(tmpFile));
				ZipAssistant.unzip(tmpFile, natives);
				tmpFile.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean needMediaUpdate(String folder) {
		try {
			new File(CFG.DIR, folder).mkdirs();
			boolean need = !Assistant.getFolderChecksum(new File(CFG.DIR, folder)).equals(CFG.class.getField(folder.toUpperCase() + "_CS").get(null));
			return need;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}
}
