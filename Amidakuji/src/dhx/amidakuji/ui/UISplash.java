package dhx.amidakuji.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Copyright 2014 Benjamin Lšsch
 * 
 * This file is part of Amidakuji.

   Amidakuji is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   Amidakuji is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with Amidakuji. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * 
 * @author Benjamin Lšsch
 * @version 0.4
 * 
 */

public class UISplash implements Screen {

	private SpriteBatch batch;
	private AmidakujiMain game;
	
	private long lastTime;
	
	public UISplash(AmidakujiMain game) {
		batch = new SpriteBatch();
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		AmidakujiMain.drawFrame(batch, AmidakujiMain.tile);
		AmidakujiMain.bFontDpCompic.draw(batch, "AMIDAKUJI",
				AmidakujiMain.width/2 - AmidakujiMain.bFontDpCompic.getBounds("AMIDAKUJI").width/2,
				AmidakujiMain.height/2 + AmidakujiMain.bFontDpCompic.getBounds("AMIDAKUJI").height*2);
		
		if(System.currentTimeMillis() - lastTime < 500) {
			AmidakujiMain.bFontPressStart.draw(batch, "PRESS ENTER TO START",
					AmidakujiMain.width/2 - AmidakujiMain.bFontPressStart.getBounds("PRESS ENTER TO START").width/2,
					AmidakujiMain.height/2 - AmidakujiMain.bFontPressStart.getBounds("AMIDAKUJI").height);
		}
		if(System.currentTimeMillis() - lastTime > 1000) {
			lastTime = System.currentTimeMillis();
		}
		
		float y = AmidakujiMain.startY;
		float x = AmidakujiMain.tile.getWidth()*2;
		batch.draw(AmidakujiMain.pingu, x, y);
		y += AmidakujiMain.bFontPressStart.getCapHeight();
		x += AmidakujiMain.pingu.getWidth() + 15;
		AmidakujiMain.bFontPressStart.draw(batch, "GPLv3", x, y);
		y += AmidakujiMain.bFontPressStart.getCapHeight() + AmidakujiMain.lineYPadding;
		AmidakujiMain.bFontPressStart.draw(batch, "COPYRIGHT 2014 BENJAMIN LOESCH", x, y);
		batch.end();
		
		if(Gdx.input.isKeyPressed(Keys.ENTER)) {
			game.setScreen(game.getMenuBuild());
		}
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}

}
