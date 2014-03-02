package dhx.amidakuji.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Copyright 2014 Benjamin L�sch
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
 * @author Benjamin L�sch
 * @version 0.4
 * 
 */

public class AmidakujiMain extends Game {
	
//	FreeTypeFontGenerator fontGenerator;
	
	// Screens
	private UIGameBuild gameBuild;
	private UIMenuBuild menuBuild;

	// Textures
	public static Texture tile;			// Texture for a->"Frame of Splash Screen" and b->"Amidakuji"
	public static Texture pingu;		// Texture for Splash Screen
	public static Texture scrollknob;	// Texture for scrollknob in UIGameBuild TODO: use 9patch to improve dynamic scaling (prettier scaled version)
	
	// BitmapFonts
	public static BitmapFont bFontPressStart;
	public static BitmapFont bFontDpCompic;	
	
	// size of window
	public static int width;
	public static int height;
	
	public static final float lineYPadding = 15;
	public static final float startY = 20;
	

	public static void drawFrame(SpriteBatch batch, Texture tex) {
		int count = AmidakujiMain.height/tex.getHeight() + 1;
		float y = 0;
		float rightX = AmidakujiMain.width - tex.getWidth();
		
		for(int counter = 0; counter < count; counter++) {
			batch.draw(tex, 0, y);
			batch.draw(tex, rightX, y);
			y += tex.getHeight();
		}
	}
	
	
	@Override
	public void create() {
		
//		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/prstartk.ttf"));	// TODO: use menuSkin.json instead (as in UIMenuBuild.class)
//		AmidakujiMain.bFontPressStart = fontGenerator.generateFont(14);
		AmidakujiMain.bFontPressStart = new BitmapFont(Gdx.files.internal("fonts/prstartk.fnt"));
		
//		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/dpcomic.ttf"));		// TODO: use menuSkin.json instead (as in UIMenuBuild.class)
//		AmidakujiMain.bFontDpCompic = fontGenerator.generateFont(100);
		AmidakujiMain.bFontDpCompic = new BitmapFont(Gdx.files.internal("fonts/dpcomic.fnt"));
		
		scrollknob = new Texture(Gdx.files.internal("ui/scrollbar.png"));
		
		tile = new Texture(Gdx.files.internal("tile_01.png"));
		pingu = new Texture(Gdx.files.internal("pingu.png"));
		
		// DESC-> screens
		menuBuild = new UIMenuBuild(bFontPressStart, this);
		gameBuild = new UIGameBuild(bFontPressStart, this);
		
		// DESC-> first screen is splash screen
		this.setScreen(new UISplash(this));
		
	}

	@Override
	public void dispose() {
		gameBuild.dispose();
		menuBuild.dispose();
		AmidakujiMain.bFontPressStart.dispose();
		AmidakujiMain.bFontDpCompic.dispose();
//		fontGenerator.dispose();
		AmidakujiMain.tile.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	public UIGameBuild getGameBuild() {
		return gameBuild;
	}

	public UIMenuBuild getMenuBuild() {
		return menuBuild;
	}
}
