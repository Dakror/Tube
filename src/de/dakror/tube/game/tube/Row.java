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

@Deprecated
public class Row {
	Field[] fields = new Field[300];
	float x;
	float y;
	
	/**
	 * In Degrees
	 */
	float angle;
	
	@Deprecated
	public Row(float x, float y) {
		this(x, y, 0);
	}
	
	@Deprecated
	public Row(float x, float y, float angle) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new Field(i, false);
		}
	}
	
	@Deprecated
	public void render(boolean active) {
		if (active) glColor3f(1, 1, 1);
		else glColor3f(0.75f, 0.75f, 0.75f);
		
		glTranslatef(x + 0.5f, y, 0);
		glRotatef(angle - 90, 0, 0, 1);
		glTranslatef(-0.5f, 0, 0);
		for (Field f : fields) {
			glPushMatrix();
			f.render();
			glPopMatrix();
		}
	}
}
