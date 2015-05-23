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

import java.util.Arrays;
import java.util.Comparator;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import de.dakror.tube.game.Game;
import de.dakror.tube.util.RenderAssistant;

/**
 * @author Dakror
 */
public class Tube {
	int activeRow;
	
	Vector2f[] verts;
	Ring[] rings;
	
	public Tube(int n) {
		verts = new Vector2f[n];
		
		rings = new Ring[(int) (Game.zFar + 25)];
		
		float step = 360f / n;
		float radius = (0.5f * Field.SIZE) / (float) Math.cos(Math.toRadians((180 - step) / 2f)) - 0.08f;
		
		for (int i = 0; i < n; i++) {
			float degs = step * i;
			float rads = (float) Math.toRadians(degs);
			
			float x = (float) Math.cos(rads) * radius;
			float y = (float) Math.sin(rads) * radius;
			
			verts[i] = new Vector2f(x, y);
		}
		
		for (int i = 0; i < rings.length; i++) {
			rings[i] = new Ring(verts, i);
		}
	}
	
	public int getRowCount() {
		return verts.length;
	}
	
	public void render() {
		for (int i = 0; i < rings.length - 1; i++) {
			for (int j = 0; j < verts.length; j++) {
				glPushMatrix();
				
				if (j == activeRow % getRowCount()) glColor3f(1, 1, 1);
				else glColor3f(0.75f, 0.75f, 0.75f);
				
				renderPlane(rings[i].getRow(j), rings[i + 1].getRow(j));
				
				glPopMatrix();
			}
		}
	}
	
	public float getRowDegree() {
		return 360f / getRowCount();
	}
	
	public void renderPlane(Vector3f[] l, Vector3f[] r) {
		glEnable(GL_TEXTURE_2D);
		RenderAssistant.bindTexture("/img/field.png");
		glBegin(GL_QUADS);
		
		float s = 0.85f;
		
		glTexCoord2f(s, s);
		glVertex3v(r[0]);
		
		glTexCoord2f(s, 0);
		glVertex3v(r[1]);
		
		glTexCoord2f(0, 0);
		glVertex3v(l[1]);
		
		glTexCoord2f(0, s);
		glVertex3v(l[0]);
		
		glEnd();
	}
	
	public static void glVertex3v(Vector3f v) {
		glVertex3f(v.x, v.y, v.z);
	}
	
	public void setActiveRow(int n) {
		activeRow = n % getRowCount();
	}
	
	public int getActiveRow() {
		return activeRow;
	}
	
	public void appendFirstRing() {
		rings[0].setZ(rings[rings.length - 1].getZ() + 1);
		
		Arrays.sort(rings, new Comparator<Ring>() {
			
			@Override
			public int compare(Ring o1, Ring o2) {
				return Math.round(o1.getZ() - o2.getZ());
			}
		});
	}
	
	public Ring getRing(int index) {
		return rings[index];
	}
}
