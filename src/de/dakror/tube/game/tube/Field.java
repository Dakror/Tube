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


package de.dakror.tube.game.tube;

import static org.lwjgl.opengl.GL11.*;
import de.dakror.tube.util.RenderAssistant;

public class Field {
	public static final float SIZE = 1;
	
	int pos;
	boolean block;
	
	@Deprecated
	public Field(int pos, boolean block) {
		this.pos = pos;
		this.block = block;
	}
	
	@Deprecated
	public void render() {
		glTranslatef(0, 0, pos);
		glRotatef(90, 1, 0, 0);
		float size = 0.85f;
		
		if (System.getProperty("os.version").equals("6.0")) // vista fix
		{
			glLineWidth(5f);
			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
			glColor3f(0, 0, 0);
			
			RenderAssistant.renderRect(0, 0, 1, 1, 0, 0, size, size);
			glLineWidth(1f);
			
			glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
			
			glColor3f(1, 1, 1);
			RenderAssistant.renderRect(0.01f, 0.01f, 0.98f, 0.98f, 0, 0, size, size);
		} else {
			glEnable(GL_TEXTURE_2D);
			RenderAssistant.bindTexture("/img/field.png");
			
			RenderAssistant.renderRect(0, 0, 1, 1, 0, 0, size, size);
		}
		
	}
}
